/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

/**
 * @author zongtao liu
 *
 */
public class BitfinexWithdrawRes {
	
	/**
	 * “success” or “error”
	 */
	private String status;
	
	/**
	 * Success or error message
	 */
	private String message;
	
	/**
	 * ID of the withdrawal (0 if unsuccessful)
	 */
	private long withdrawal_id;

}
