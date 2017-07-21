/**
 * 
 */
package org.finance.mybtc.apiManager.impl.huobi;

import java.util.List;

import org.finance.mybtc.api.huobi.eth.response.Balance;
import org.finance.mybtc.api.huobi.eth.response.BalanceInfo;
import org.finance.mybtc.apiManager.ESymbol;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public class HuobiCny4NewAreaImpl extends AHuobiNewArea {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public float[] getBidAndAskPrice(String pair) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		long accountId = getAccountId();
		if (accountId == 0) {
			return 0;
		}
		Balance accountBalance = client.getAccountBalance(accountId);
		if (accountBalance != null && accountBalance.getState() == Balance.STATE_WORKING) {
			List<BalanceInfo> list = accountBalance.getList();
			for (BalanceInfo tmpBalanceInfo : list) {
				if (Strings.equals(BalanceInfo.CURRENCY_CNY, tmpBalanceInfo.getCurrency())
						&& Strings.equals(BalanceInfo.TYPE_TRADE, tmpBalanceInfo.getType())) {
					coinNum = Float.valueOf(tmpBalanceInfo.getBalance());
				}
			}
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol,float amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol,float amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount,String address) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.CNY;
	}

}
