/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinTrade extends OkcoinResponse {

	/**
	 * 订单ID
	 */
	private long order_id;

	/**
	 * @return the order_id
	 */
	public long getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id
	 *            the order_id to set
	 */
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

}
