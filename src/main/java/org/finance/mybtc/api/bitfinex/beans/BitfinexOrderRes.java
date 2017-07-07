/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

/**
 * @author zongtao liu
 *
 */
public class BitfinexOrderRes {
	
	private long id;
	
	private String symbol;
	private String exchange;
	private float price;
	private float avg_execution_price;
	private String side;
	private String type;
	private String timestamp;
	private boolean is_live;
	private boolean is_cancelled;
	private boolean is_hidden;
	private boolean was_forced;
	private float original_amount;
	private float remaining_amount;
	private float executed_amount;
	private long order_id;

}
