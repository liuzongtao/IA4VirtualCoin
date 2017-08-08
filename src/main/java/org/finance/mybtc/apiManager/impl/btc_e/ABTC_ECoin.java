/**
 * 
 */
package org.finance.mybtc.apiManager.impl.btc_e;

import java.util.Map;

import org.finance.mybtc.api.btc_e.local.BTC_EClient;
import org.finance.mybtc.api.btc_e.local.response.Ticker;
import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.api.btc_e.official.GetInfo;
import org.finance.mybtc.api.btc_e.official.Trade;
import org.finance.mybtc.api.btc_e.official.TradeApi;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.core.config.Configs;

/**
 * @author zongtao liu
 *
 */
public abstract class ABTC_ECoin implements IVirtualCoin {

	protected TradeApi client = new TradeApi(Configs.API_CONFIG_BTC_E.getApiKey(),
			Configs.API_CONFIG_BTC_E.getApiSecret());

	public abstract EBTC_ESymbol getSmybol();

	public abstract EBTC_EPairType getSellSmybol(ESymbol toSymbol);

	public abstract EBTC_EPairType getBuySmybol(ESymbol fromSymbol);

	public abstract EBTC_EPairType getPair();

	public abstract String getWithdrawType();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public double getCoinNum() {
		double coinNum = 0;
		GetInfo info = getInfo();
		if (info != null) {
			String balance = info.getBalance(getSmybol().getValue());
			coinNum = Double.valueOf(balance);
		}
		return coinNum;
	}

	/**
	 * It returns the information about the user's current balance
	 * 
	 * @return
	 */
	private GetInfo getInfo() {
		GetInfo info = null;
		if (client != null) {
			client.getInfo.runMethod();
			info = client.getInfo;
		}
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidPrice()
	 */
	@Override
	public double[] getBidAndAskPrice(String pair) {
		double[] result = new double[2];
		Ticker ticker = getTicker(EBTC_EPairType.valueOf(pair));
		if (ticker != null) {
			result[0] = Double.valueOf(ticker.getSell());
			result[1] = Double.valueOf(ticker.getBuy());
		}
		return result;
	}

	/**
	 * 获取交易数据
	 * 
	 * @return
	 */
	private Ticker getTicker(EBTC_EPairType pair) {
		BTC_EClient client = new BTC_EClient();
		String pairType = pair.getValue();
		Map<String, Ticker> tickerMap = client.getTickerMap(pairType);
		Ticker ticker = tickerMap.get(pairType);
		return ticker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol, double amount) {
		boolean result = false;
		EBTC_EPairType pair = getSellSmybol(fromSymbol);
		double[] priceArr = getBidAndAskPrice(pair.toString());
		Trade trade = trade(pair, "buy", 0.5f, (amount / priceArr[1]));
		if (trade != null && Double.valueOf(trade.getReceived()) > 0) {
			result = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol, double amount) {
		boolean result = false;
		EBTC_EPairType pair = getSellSmybol(toSymbol);
		Trade trade = trade(pair, "sell", 0.5f, amount);
		if (trade != null && Double.valueOf(trade.getReceived()) > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 进行交易
	 * 
	 * @param pair
	 * @param type
	 * @param rate
	 * @param amount
	 * @return
	 */
	private Trade trade(EBTC_EPairType pair, String type, float rate, double amount) {
		client.trade.setPair(pair.getValue());
		client.trade.setType(type);
		client.trade.setRate(String.valueOf(rate));
		client.trade.setAmount(String.valueOf(amount));
		client.trade.runMethod();
		return client.trade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(double amount, String address) {
		client.withDrawCoin.setCoinName(getWithdrawType());
		client.withDrawCoin.setAmount(String.valueOf(amount));
		client.withDrawCoin.setaddress(address);
		return client.withDrawCoin.runMethod();
	}

}
