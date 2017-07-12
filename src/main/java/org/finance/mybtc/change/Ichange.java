/**
 * 
 */
package org.finance.mybtc.change;

import org.finance.mybtc.change.bean.ChangeInfo;

/**
 * @author zongtao liu
 *
 */
public interface Ichange {
	
	public float preChange(float totalMoney,float  fromCoinBuyPrice,float toCoinSellPrice,float otherPfChangePrice);
	
	public ChangeInfo execExchange();

}
