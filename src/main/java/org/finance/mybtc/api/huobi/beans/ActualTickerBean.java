/**
 * 
 */
package org.finance.mybtc.api.huobi.beans;

/**
 * @author zongtao liu
 *
 */
public class ActualTickerBean {

	private long time;

	private TickerBean ticker;

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the ticker
	 */
	public TickerBean getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(TickerBean ticker) {
		this.ticker = ticker;
	}

}
