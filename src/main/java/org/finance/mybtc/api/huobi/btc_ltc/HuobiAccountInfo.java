/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public class HuobiAccountInfo {

	/**
	 * 总资产折合
	 */
	private double total;
	/**
	 * 净资产折合
	 */
	private double net_asset;
	/**
	 * 可用人民币（美元交易市场返回available_usd_display）
	 */
	private double available_cny_display;
	/**
	 * 可用比特币
	 */
	private double available_btc_display;
	/**
	 * 可用莱特币（只有人民币交易市场才会返回）
	 */
	private double available_ltc_display;
	/**
	 * 冻结人民币（美元交易市场返回frozen_usd_display）
	 */
	private double frozen_cny_display;
	/**
	 * 冻结比特币
	 */
	private double frozen_btc_display;
	/**
	 * 冻结莱特币（只有人民币交易市场才会返回）
	 */
	private double frozen_ltc_display;
	/**
	 * 申请人民币数量（美元交易市场返回loan_usd_display）
	 */
	private double loan_cny_display;
	/**
	 * 申请比特币数量
	 */
	private double loan_btc_display;
	/**
	 * 申请莱特币数量（只有人民币交易市场才会返回）
	 */
	private double loan_ltc_display;

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return the net_asset
	 */
	public double getNet_asset() {
		return net_asset;
	}

	/**
	 * @param net_asset
	 *            the net_asset to set
	 */
	public void setNet_asset(double net_asset) {
		this.net_asset = net_asset;
	}

	/**
	 * @return the available_cny_display
	 */
	public double getAvailable_cny_display() {
		return available_cny_display;
	}

	/**
	 * @param available_cny_display
	 *            the available_cny_display to set
	 */
	public void setAvailable_cny_display(double available_cny_display) {
		this.available_cny_display = available_cny_display;
	}

	/**
	 * @return the available_btc_display
	 */
	public double getAvailable_btc_display() {
		return available_btc_display;
	}

	/**
	 * @param available_btc_display
	 *            the available_btc_display to set
	 */
	public void setAvailable_btc_display(double available_btc_display) {
		this.available_btc_display = available_btc_display;
	}

	/**
	 * @return the available_ltc_display
	 */
	public double getAvailable_ltc_display() {
		return available_ltc_display;
	}

	/**
	 * @param available_ltc_display
	 *            the available_ltc_display to set
	 */
	public void setAvailable_ltc_display(double available_ltc_display) {
		this.available_ltc_display = available_ltc_display;
	}

	/**
	 * @return the frozen_cny_display
	 */
	public double getFrozen_cny_display() {
		return frozen_cny_display;
	}

	/**
	 * @param frozen_cny_display
	 *            the frozen_cny_display to set
	 */
	public void setFrozen_cny_display(double frozen_cny_display) {
		this.frozen_cny_display = frozen_cny_display;
	}

	/**
	 * @return the frozen_btc_display
	 */
	public double getFrozen_btc_display() {
		return frozen_btc_display;
	}

	/**
	 * @param frozen_btc_display
	 *            the frozen_btc_display to set
	 */
	public void setFrozen_btc_display(double frozen_btc_display) {
		this.frozen_btc_display = frozen_btc_display;
	}

	/**
	 * @return the frozen_ltc_display
	 */
	public double getFrozen_ltc_display() {
		return frozen_ltc_display;
	}

	/**
	 * @param frozen_ltc_display
	 *            the frozen_ltc_display to set
	 */
	public void setFrozen_ltc_display(double frozen_ltc_display) {
		this.frozen_ltc_display = frozen_ltc_display;
	}

	/**
	 * @return the loan_cny_display
	 */
	public double getLoan_cny_display() {
		return loan_cny_display;
	}

	/**
	 * @param loan_cny_display
	 *            the loan_cny_display to set
	 */
	public void setLoan_cny_display(double loan_cny_display) {
		this.loan_cny_display = loan_cny_display;
	}

	/**
	 * @return the loan_btc_display
	 */
	public double getLoan_btc_display() {
		return loan_btc_display;
	}

	/**
	 * @param loan_btc_display
	 *            the loan_btc_display to set
	 */
	public void setLoan_btc_display(double loan_btc_display) {
		this.loan_btc_display = loan_btc_display;
	}

	/**
	 * @return the loan_ltc_display
	 */
	public double getLoan_ltc_display() {
		return loan_ltc_display;
	}

	/**
	 * @param loan_ltc_display
	 *            the loan_ltc_display to set
	 */
	public void setLoan_ltc_display(double loan_ltc_display) {
		this.loan_ltc_display = loan_ltc_display;
	}

}
