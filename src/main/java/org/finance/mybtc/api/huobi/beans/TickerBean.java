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
	private double open;
	/***
	 * 收盘价
	 */
	private double last;
	/**
	 * 最低
	 */
	private double low;
	/**
	 * 最高
	 */
	private double high;
	/**
	 * 总量
	 */
	private double vol;
	/**
	 * 买价
	 */
	private double buy;
	/**
	 * 卖价
	 */
	private double sell;

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
	public double getOpen() {
		return open;
	}

	/**
	 * @param open
	 *            the open to set
	 */
	public void setOpen(double open) {
		this.open = open;
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
	 * @return the vol
	 */
	public double getVol() {
		return vol;
	}

	/**
	 * @param vol
	 *            the vol to set
	 */
	public void setVol(double vol) {
		this.vol = vol;
	}

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

}
