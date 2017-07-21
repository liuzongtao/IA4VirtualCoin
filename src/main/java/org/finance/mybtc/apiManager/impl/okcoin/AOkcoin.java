/**
 * 
 */
package org.finance.mybtc.apiManager.impl.okcoin;

import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.api.okCoin.beans.OkcoinTickerInfo;
import org.finance.mybtc.api.okCoin.beans.OkcoinTrade;
import org.finance.mybtc.api.okCoin.beans.OkcoinUserInfo;
import org.finance.mybtc.api.okCoin.beans.OkcoinWithdraw;
import org.finance.mybtc.api.okCoin.stock.IStockRestApi;
import org.finance.mybtc.api.okCoin.stock.impl.StockRestApi;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.core.config.Configs;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public abstract class AOkcoin implements IVirtualCoin {

	private static Log log = Logs.get();

	protected static IStockRestApi stockPost = new StockRestApi(StockRestApi.URL, Configs.API_CONFIG_OKCOIN.getApiKey(),
			Configs.API_CONFIG_OKCOIN.getApiSecret());

	public abstract EOkcoinSymbols getSmybol();

	public abstract float getWithdrawFees();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice(java.lang.
	 * String)
	 */
	@Override
	public float[] getBidAndAskPrice(String pair) {
		float[] result = new float[2];
		try {
			String ticker = stockPost.ticker(getSmybol().getValue());
			OkcoinTickerInfo info = Json.fromJson(OkcoinTickerInfo.class, ticker);
			if (info != null && info.getTicker() != null) {
				result[0] = info.getTicker().getBuy();
				result[1] = info.getTicker().getSell();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected OkcoinUserInfo getOkcoinUserInfo() {
		OkcoinUserInfo userInfo = null;
		try {
			String userinfo = stockPost.userinfo();
			userInfo = Json.fromJson(OkcoinUserInfo.class, userinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(org.finance.mybtc.
	 * apiManager.ESymbol, float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol, float amount) {
		boolean result = false;
		try {
			String tradeResult = stockPost.trade(getSmybol().getValue(), "buy_market", String.valueOf(amount), null);
			OkcoinTrade trade = Json.fromJson(OkcoinTrade.class, tradeResult);
			result = trade.isResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(org.finance.mybtc.
	 * apiManager.ESymbol, float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol, float amount) {
		boolean result = false;
		try {
			String tradeResult = stockPost.trade(getSmybol().getValue(), "sell_market", null, String.valueOf(amount));
			OkcoinTrade trade = Json.fromJson(OkcoinTrade.class, tradeResult);
			result = trade.isResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float,
	 * java.lang.String)
	 */
	@Override
	public boolean withdrawCoin(float amount, String address) {
		boolean result = false;
		try {
			float withdrawFees = getWithdrawFees();
			String withdrawResult = stockPost.withdraw(getSmybol().getValue(), String.valueOf(amount - withdrawFees),
					String.valueOf(withdrawFees), Configs.API_CONFIG_OKCOIN.getTradePwd(), address);
			OkcoinWithdraw withdraw = Json.fromJson(OkcoinWithdraw.class, withdrawResult);
			result = withdraw.isResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.IVirtualCoin#exchange(org.finance.mybtc.
	 * apiManager.ESymbol, float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, float amount) {
		return false;
	}

}
