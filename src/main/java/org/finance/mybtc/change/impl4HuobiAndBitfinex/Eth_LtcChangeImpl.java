/**
 * 
 */
package org.finance.mybtc.change.impl4HuobiAndBitfinex;

import org.finance.mybtc.change.AChange;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Eth_LtcChangeImpl extends AChange {

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
		return Const.HUOBI_ETH_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BITFINEX_LTC_WITHDRAW_FEE;
	}

}
