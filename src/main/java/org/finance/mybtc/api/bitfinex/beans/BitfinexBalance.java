/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

/**
 * @author zongtao liu
 *
 */
public class BitfinexBalance {

	/**
	 * “trading”, “deposit” or “exchange”
	 */
	private String type;
	
	/**
	 * Currency
	 */
	private String currency;
	
	/**
	 * How much balance of this currency in this wallet
	 */
	private float amount;
	
	/**
	 * How much X there is in this wallet that is available to trade
	 */
	private float available;
}
