/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;

/**
 * @author zongtao liu
 *
 */
public class HuobiCny4OldAreaImpl extends AHuobiOldArea {

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public float[] getBidAndAskPrice() {
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
	public boolean buyMarket(float amount) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(float amount) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount) {
		return false;
	}
}
