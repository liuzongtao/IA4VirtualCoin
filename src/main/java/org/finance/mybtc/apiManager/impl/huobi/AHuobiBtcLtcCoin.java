/**
 * 
 */
package org.finance.mybtc.apiManager.impl.huobi;

import org.finance.mybtc.api.huobi.beans.ActualTickerBean;
import org.finance.mybtc.api.huobi.btc_ltc.EHuobiCoinType;
import org.finance.mybtc.api.huobi.btc_ltc.SellOrBuyResult;
import org.finance.mybtc.api.huobi.btc_ltc.WithdrawCoinResult;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.core.config.Configs;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public abstract class AHuobiBtcLtcCoin extends AHuobiOldArea {

	public abstract EHuobiCoinType getCoinType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public float[] getBidAndAskPrice(String pair) {
		float[] result = new float[2];
		ActualTickerBean actualTickerInfo = service.getActualTickerInfo(getCoinType());
		if (actualTickerInfo != null && actualTickerInfo.getTicker() != null) {
			result[0] = actualTickerInfo.getTicker().getBuy();
			result[1] = actualTickerInfo.getTicker().getSell();
		}
		return result;
	}

	/**
	 * 
	 * 提币BTC/LTC
	 * 
	 * @param amount
	 * @param withdrawAddr
	 * @param withdrawFee
	 * @return
	 */
	protected WithdrawCoinResult withdrawCoin(float amount, String withdrawAddr, float withdrawFee) {
		WithdrawCoinResult result = null;
		try {
			String res = service.withdrawCoin(getCoinType(), withdrawAddr, amount,
					Configs.API_CONFIG_HUOBI.getTradePwd(), null, withdrawFee);
			if (Strings.isNotBlank(res)) {
				result = Json.fromJson(WithdrawCoinResult.class, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol, float amount) {
		boolean result = false;
		try {
			String buyMarketRes = service.buyMarket(getCoinType(), String.valueOf(amount), null, null);
			if (Strings.isNotBlank(buyMarketRes)) {
				SellOrBuyResult res = Json.fromJson(SellOrBuyResult.class, buyMarketRes);
				if (res != null && Strings.equals(res.getResult(), SellOrBuyResult.RESULT_SUCCESS)) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol, float amount) {
		boolean result = false;
		try {
			String sellMarketRes = service.sellMarket(getCoinType(), String.valueOf(amount), null, null);
			if (Strings.isNotBlank(sellMarketRes)) {
				SellOrBuyResult res = Json.fromJson(SellOrBuyResult.class, sellMarketRes);
				if (res != null && Strings.equals(res.getResult(), SellOrBuyResult.RESULT_SUCCESS)) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
