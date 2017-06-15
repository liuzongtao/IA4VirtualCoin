/**
 * 
 */
package org.finance.mybtc.api.huobi.beans;

/**
 * @author zongtao liu
 *
 */
public class TickerBean {

	private String symbol;
	/**
	 * 开盘
	 */
	private float open;
	/***
	 * 收盘价
	 */
	private float last;
	/**
	 * 最低
	 */
	private float low;
	/**
	 * 最高
	 */
	private float high;
	/**
	 * 总量
	 */
	private float vol;
	/**
	 * 买价
	 */
	private float buy;
	/**
	 * 卖价
	 */
	private float sell;

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the open
	 */
	public float getOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(float open) {
		this.open = open;
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

}
