/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

/**
 * @author zongtao liu
 *
 */
public class BitfinexSymbolDetail {
	
	/**
	 * The pair code
	 */
	private String pair;
	/**
	 * Maximum number of significant digits for price in this pair
	 */
	private int price_precision;
	
	/**
	 * Initial margin required to open a position in this pair
	 */
	private float initial_margin;
	/**
	 * Minimal margin to maintain (in %)
	 */
	private float minimum_margin;
	/**
	 * Maximum order size of the pair
	 */
	private float maximum_order_size;
	/**
	 * Expiration date for limited contracts/pairs
	 */
	private String expiration; 

}
