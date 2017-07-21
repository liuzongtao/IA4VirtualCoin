/**
 * 
 */
package org.finance.mybtc.change;

import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public abstract class AOkcoinChange extends AChange {

	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.AChange#getBuyFee()
	 */
	@Override
	public float getBuyFee() {
		return Const.OKCOIN_TRADE_FEE_BUY;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.AChange#getSellFee()
	 */
	@Override
	public float getSellFee() {
		return Const.OKCOIN_TRADE_FEE_SELL;
	}
	
	

}
