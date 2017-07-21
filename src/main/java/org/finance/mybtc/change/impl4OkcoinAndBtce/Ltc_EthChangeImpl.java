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
public class Ltc_EthChangeImpl extends AOkcoinChange {

	@Override
	public int getFromDigit() {
		return Const.LTC_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.ETH_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_LTC_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BTC_E_ETH_WITHDRAW_FEE;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.AOkcoinChange#getSellFee()
	 */
	@Override
	public float getSellFee() {
		return Const.OKCOIN_TRADE_FEE_SELL_ETH;
	}

}
