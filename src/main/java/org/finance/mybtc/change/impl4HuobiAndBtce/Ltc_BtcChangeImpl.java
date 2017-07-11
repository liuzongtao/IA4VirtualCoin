/**
 * 
 */
package org.finance.mybtc.change.impl4HuobiAndBtce;

import org.finance.mybtc.change.AChange;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Ltc_BtcChangeImpl extends AChange {

	@Override
	public int getFromDigit() {
		return Const.LTC_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.BTC_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.HUOBI_LTC_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BTC_E_BTC_WITHDRAW_FEE;
	}

}
