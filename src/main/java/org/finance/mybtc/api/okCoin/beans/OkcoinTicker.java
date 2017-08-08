/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinTicker {

	/**
	 * 买一价
	 */
	private double buy;
	/**
	 * 最高价
	 */
	private double high;
	/**
	 * 最新成交价
	 */
	private double last;
	/**
	 * 最低价
	 */
	private double low;
	/**
	 * 卖一价
	 */
	private double sell;
	/**
	 * 成交量(最近的24小时)
	 */
	private String vol;

	/**
	 * @return the buy
	 */
	public double getBuy() {
		return buy;
	}

	/**
	 * @param buy
	 *            the buy to set
	 */
	public void setBuy(double buy) {
		this.buy = buy;
	}

	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * @return the last
	 */
	public double getLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(double last) {
		this.last = last;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * @return the sell
	 */
	public double getSell() {
		return sell;
	}

	/**
	 * @param sell
	 *            the sell to set
	 */
	public void setSell(double sell) {
		this.sell = sell;
	}

	/**
	 * @return the vol
	 */
	public String getVol() {
		return vol;
	}

	/**
	 * @param vol
	 *            the vol to set
	 */
	public void setVol(String vol) {
		this.vol = vol;
	}

}
