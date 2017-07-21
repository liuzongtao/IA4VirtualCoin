/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinFunds {

	/**
	 * 账户资产，包含净资产及总资产
	 */
	private OkcoinAsset asset;
	/**
	 * :账户借款信息(只有在账户有借款信息时才会返回)
	 */
	private OkcoinCurrency borrow;
	/**
	 * 账户余额
	 */
	private OkcoinCurrency free;
	/**
	 * 账户冻结余额
	 */
	private OkcoinCurrency freezed;
	/**
	 * :账户理财信息(只有在账户有理财信息时才返回)
	 */
	private OkcoinCurrency union_fund;

	/**
	 * @return the asset
	 */
	public OkcoinAsset getAsset() {
		return asset;
	}

	/**
	 * @param asset
	 *            the asset to set
	 */
	public void setAsset(OkcoinAsset asset) {
		this.asset = asset;
	}

	/**
	 * @return the borrow
	 */
	public OkcoinCurrency getBorrow() {
		return borrow;
	}

	/**
	 * @param borrow
	 *            the borrow to set
	 */
	public void setBorrow(OkcoinCurrency borrow) {
		this.borrow = borrow;
	}

	/**
	 * @return the free
	 */
	public OkcoinCurrency getFree() {
		return free;
	}

	/**
	 * @param free
	 *            the free to set
	 */
	public void setFree(OkcoinCurrency free) {
		this.free = free;
	}

	/**
	 * @return the freezed
	 */
	public OkcoinCurrency getFreezed() {
		return freezed;
	}

	/**
	 * @param freezed
	 *            the freezed to set
	 */
	public void setFreezed(OkcoinCurrency freezed) {
		this.freezed = freezed;
	}

	/**
	 * @return the union_fund
	 */
	public OkcoinCurrency getUnion_fund() {
		return union_fund;
	}

	/**
	 * @param union_fund
	 *            the union_fund to set
	 */
	public void setUnion_fund(OkcoinCurrency union_fund) {
		this.union_fund = union_fund;
	}

}
