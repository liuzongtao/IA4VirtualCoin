/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinTickerInfo {

	/**
	 * 返回数据时服务器时间
	 */
	private long date;
	private OkcoinTicker ticker;

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}

	/**
	 * @return the ticker
	 */
	public OkcoinTicker getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(OkcoinTicker ticker) {
		this.ticker = ticker;
	}

}
