/**
 * 
 */
package org.finance.mybtc.change.impl;

import org.finance.mybtc.change.Ichange;
import org.finance.mybtc.constant.Const;
import org.finance.mybtc.utils.DecimalUtil;

/**
 * @author zongtao liu
 *
 */
public class Btc_LtcChangeImpl implements Ichange{

	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.Ichange#preChange()
	 */
	@Override
	public float preChange() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public float preChange(float total,float  btcPrice,float ltcPrice){
		float btcNum = DecimalUtil.decimalDown(total /btcPrice, Const.BTC_DIGIT);
		
		
		return 0;
	}

}
