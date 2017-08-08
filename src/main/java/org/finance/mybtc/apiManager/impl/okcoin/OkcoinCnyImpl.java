/**
 * 
 */
package org.finance.mybtc.apiManager.impl.okcoin;

import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.api.okCoin.beans.OkcoinFundsInfo;
import org.finance.mybtc.api.okCoin.beans.OkcoinUserInfo;
import org.finance.mybtc.apiManager.ESymbol;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class OkcoinCnyImpl extends AOkcoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public double getCoinNum() {
		double coinNum = 0;
		OkcoinUserInfo okcoinUserInfo = getOkcoinUserInfo();
		if (okcoinUserInfo != null && okcoinUserInfo.isResult()) {
			OkcoinFundsInfo info = okcoinUserInfo.getInfo();
			coinNum = info.getFunds().getFree().getCny();
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.okcoin.AOkcoin#getSmybol()
	 */
	@Override
	public EOkcoinSymbols getSmybol() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.okcoin.AOkcoin#getWithdrawFees()
	 */
	@Override
	public float getWithdrawFees() {
		return 0;
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
