/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinWithdraw extends OkcoinResponse {

	/**
	 * 提币申请Id
	 */
	private long withdraw_id;

	/**
	 * @return the withdraw_id
	 */
	public long getWithdraw_id() {
		return withdraw_id;
	}

	/**
	 * @param withdraw_id
	 *            the withdraw_id to set
	 */
	public void setWithdraw_id(long withdraw_id) {
		this.withdraw_id = withdraw_id;
	}

}
