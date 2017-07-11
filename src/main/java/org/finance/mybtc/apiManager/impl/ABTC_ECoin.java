/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

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
import org.finance.mybtc.configs.Configs;

/**
 * @author zongtao liu
 *
 */
public abstract class ABTC_ECoin implements IVirtualCoin {

	protected TradeApi client = new TradeApi(Configs.API_CONFIG_BTC_E.getApiKey(), Configs.API_CONFIG_BTC_E.getApiSecret());

	public abstract EBTC_ESymbol getSmybol();

	public abstract EBTC_EPairType getPair();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		GetInfo info = getInfo();
		if (info != null) {
			String balance = info.getBalance(getSmybol().getValue());
			coinNum = Float.valueOf(balance);
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
	public float[] getBidAndAskPrice() {
		float[] result = new float[2];
		Ticker ticker = getTicker();
		if (ticker != null) {
			result[0] = Float.valueOf(ticker.getSell());
			result[1] = Float.valueOf(ticker.getBuy());
		}
		return result;
	}

	/**
	 * 获取交易数据
	 * 
	 * @return
	 */
	private Ticker getTicker() {
		BTC_EClient client = new BTC_EClient();
		String pairType = getPair().getValue();
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
	public boolean buyMarket(ESymbol fromSymbol,float amount) {
		boolean result = false;
		float[] priceArr = getBidAndAskPrice();
		Trade trade = trade("bid", priceArr[0], amount);
		if (trade != null && Float.valueOf(trade.getReceived()) > 0) {
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
	public boolean sellMarket(ESymbol toSymbol,float amount) {
		boolean result = false;
		float[] priceArr = getBidAndAskPrice();
		Trade trade = trade("ask", priceArr[1], amount);
		if (trade != null && Float.valueOf(trade.getReceived()) > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 进行交易
	 * 
	 * @param type
	 * @param rate
	 * @param amount
	 * @return
	 */
	private Trade trade(String type, float rate, float amount) {
		client.trade.setPair(getPair().getValue());
		client.trade.setType(type);
		client.trade.setRate(String.valueOf(rate));
		client.trade.setAmount(String.valueOf(amount));
		client.trade.runMethod();
		return client.trade;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#exchange(java.lang.String, float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, float amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
