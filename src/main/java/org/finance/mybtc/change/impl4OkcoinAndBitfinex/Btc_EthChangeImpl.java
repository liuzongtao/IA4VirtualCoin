/**
 * 
 */
package org.finance.mybtc.change.impl4OkcoinAndBitfinex;

import org.finance.mybtc.change.AOkcoinChange;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Btc_EthChangeImpl extends AOkcoinChange {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getFromDigit()
	 */
	@Override
	public int getFromDigit() {
		return Const.BTC_DIGIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getToDigit()
	 */
	@Override
	public int getToDigit() {
		return Const.ETH_DIGIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getFromWithdrawFee()
	 */
	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_BTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getOtherWithdrawFee()
	 */
	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BITFINEX_ETH_WITHDRAW_FEE;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.AOkcoinChange#getSellFee()
	 */
	@Override
	public float getSellFee() {
		return Const.OKCOIN_TRADE_FEE_SELL_ETH;
	}
	
	

}
