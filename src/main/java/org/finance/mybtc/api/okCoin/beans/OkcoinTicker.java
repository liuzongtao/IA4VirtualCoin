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
	private float buy;
	/**
	 * 最高价
	 */
	private float high;
	/**
	 * 最新成交价
	 */
	private float last;
	/**
	 * 最低价
	 */
	private float low;
	/**
	 * 卖一价
	 */
	private float sell;
	/**
	 * 成交量(最近的24小时)
	 */
	private String vol;

	/**
	 * @return the buy
	 */
	public float getBuy() {
		return buy;
	}

	/**
	 * @param buy
	 *            the buy to set
	 */
	public void setBuy(float buy) {
		this.buy = buy;
	}

	/**
	 * @return the high
	 */
	public float getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(float high) {
		this.high = high;
	}

	/**
	 * @return the last
	 */
	public float getLast() {
		return last;
	}

	/**
	 * @param last
	 *            the last to set
	 */
	public void setLast(float last) {
		this.last = last;
	}

	/**
	 * @return the low
	 */
	public float getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(float low) {
		this.low = low;
	}

	/**
	 * @return the sell
	 */
	public float getSell() {
		return sell;
	}

	/**
	 * @param sell
	 *            the sell to set
	 */
	public void setSell(float sell) {
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
