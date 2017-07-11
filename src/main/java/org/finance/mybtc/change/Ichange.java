/**
 * 
 */
package org.finance.mybtc.change;

/**
 * @author zongtao liu
 *
 */
public interface Ichange {
	
	public float preChange(float totalMoney,float  fromCoinBuyPrice,float toCoinSellPrice,float otherPfChangePrice);
	
	public boolean execExchange();

}
