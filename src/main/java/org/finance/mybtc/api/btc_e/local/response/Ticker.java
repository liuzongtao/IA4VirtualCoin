/**
 * 
 */
package org.finance.mybtc.api.btc_e.local.response;

/**
 * @author zongtao liu
 *
 */
public class Ticker {

	private float high;
	private float low;
	private float avg;
	private float vol;
	private float vol_cur;
	private float last;
	private float buy;
	private float sell;
	private long updated;

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
	 * @return the avg
	 */
	public float getAvg() {
		return avg;
	}

	/**
	 * @param avg
	 *            the avg to set
	 */
	public void setAvg(float avg) {
		this.avg = avg;
	}

	/**
	 * @return the vol
	 */
	public float getVol() {
		return vol;
	}

	/**
	 * @param vol
	 *            the vol to set
	 */
	public void setVol(float vol) {
		this.vol = vol;
	}

	/**
	 * @return the vol_cur
	 */
	public float getVol_cur() {
		return vol_cur;
	}

	/**
	 * @param vol_cur
	 *            the vol_cur to set
	 */
	public void setVol_cur(float vol_cur) {
		this.vol_cur = vol_cur;
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
	 * @return the updated
	 */
	public long getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(long updated) {
		this.updated = updated;
	}

}
