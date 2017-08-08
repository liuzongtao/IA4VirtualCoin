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
	
	public float preChange(double totalMoney,double  fromCoinBuyPrice,double toCoinSellPrice,double otherPfChangePrice);
	
	public ChangeInfo execExchange();

}
