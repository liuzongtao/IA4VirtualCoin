/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

/**
 * @author zongtao liu
 *
 */
public class BitfinexTicker {
	
	/**
	 * (bid + ask) / 2
	 */
	private float mid;
	/**
	 * Innermost bid
	 */
	private float bid;
	/**
	 * Innermost ask
	 */
	private float ask;
	/**
	 * The price at which the last order executed
	 */
	private float last_price;
	/**
	 * Lowest trade price of the last 24 hours
	 */
	private float low;
	/**
	 * Highest trade price of the last 24 hours
	 */
	private float high;
	/**
	 * Trading volume of the last 24 hours
	 */
	private float volume;
	/**
	 * The timestamp at which this information was valid
	 */
	private String timestamp;
	
	

}
