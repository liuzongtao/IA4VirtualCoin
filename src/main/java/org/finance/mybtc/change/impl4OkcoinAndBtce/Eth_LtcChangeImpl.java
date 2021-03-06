/**
 * 
 */
package org.finance.mybtc.change.impl4OkcoinAndBtce;

import org.finance.mybtc.change.AOkcoinChange;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Eth_LtcChangeImpl extends AOkcoinChange {

	@Override
	public int getFromDigit() {
		return Const.ETH_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.LTC_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_ETH_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BTC_E_LTC_WITHDRAW_FEE;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.AOkcoinChange#getBuyFee()
	 */
	@Override
	public float getBuyFee() {
		return Const.OKCOIN_TRADE_FEE_BUY_ETH;
	}

}
