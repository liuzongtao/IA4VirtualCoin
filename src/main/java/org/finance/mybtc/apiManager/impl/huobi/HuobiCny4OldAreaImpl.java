/**
 * 
 */
package org.finance.mybtc.apiManager.impl.huobi;

import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;
import org.finance.mybtc.apiManager.ESymbol;

/**
 * @author zongtao liu
 *
 */
public class HuobiCny4OldAreaImpl extends AHuobiOldArea {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public double[] getBidAndAskPrice(String pair) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public double getCoinNum() {
		double coinNum = 0;
		HuobiAccountInfo account = getAccount();
		if (account != null) {
			coinNum = account.getAvailable_cny_display();
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol, double amount) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol, double amount) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(double amount, String address) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.CNY;
	}
}
