/**
 * 
 */
package org.finance.mybtc.change.impl4HuobiAndBtce;

import org.finance.mybtc.change.AHuobiChange;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Eth_BtcChangeImpl extends AHuobiChange {

	@Override
	public int getFromDigit() {
		return Const.ETH_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.BTC_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.HUOBI_ETH_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BTC_E_BTC_WITHDRAW_FEE;
	}

}
