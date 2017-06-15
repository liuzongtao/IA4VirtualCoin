/**
 * 
 */
package org.finance.mybtc.api.huobi.eth.request;

/**
 * @author zongtao liu
 *
 */
public class CreateWithdrawRequest {

	/**
	 * 提现地址ID
	 */
	private long addressId;

	/**
	 * 提币数量
	 */
	private String amount;

	/**
	 * @return the addressId
	 */
	public long getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
