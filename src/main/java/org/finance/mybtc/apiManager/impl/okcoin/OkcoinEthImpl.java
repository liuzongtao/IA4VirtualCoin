/**
 * 
 */
package org.finance.mybtc.apiManager.impl.okcoin;

import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.api.okCoin.beans.OkcoinFundsInfo;
import org.finance.mybtc.api.okCoin.beans.OkcoinUserInfo;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class OkcoinEthImpl extends AOkcoin {

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		OkcoinUserInfo okcoinUserInfo = getOkcoinUserInfo();
		if(okcoinUserInfo != null){
			OkcoinFundsInfo info = okcoinUserInfo.getInfo();
			coinNum = info.getFunds().getFree().getEth();
		}
		return coinNum;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.okcoin.AOkcoin#getSmybol()
	 */
	@Override
	public EOkcoinSymbols getSmybol() {
		return EOkcoinSymbols.ETH;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.okcoin.AOkcoin#getWithdrawFees()
	 */
	@Override
	public float getWithdrawFees() {
		return Const.OKCOIN_ETH_WITHDRAW_FEE;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.ETH;
	}

}
