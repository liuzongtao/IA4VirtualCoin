/**
 * 
 */
package org.finance.mybtc.api.huobi.eth.response;

/**
 * @author zongtao liu
 *
 */
public class Tick {

	/**
	 * K线id
	 */
	private long id;
	/**
	 * 响应生成时间点，单位：毫秒
	 */
	private long ts;
	/**
	 * 成交量
	 */
	private float amount;
	/**
	 * 成交笔数
	 */
	private int count;
	/***
	 * 开盘价
	 */
	private float open;
	/**
	 * 收盘价
	 */
	private float close;
	/**
	 * 最低价
	 */
	private float low;
	/**
	 * 最高价
	 */
	private float high;
	/***
	 * 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
	 */
	private float vol;

	/**
	 * 买盘,[price(成交价), amount(成交量)], 按price降序
	 */
	private Object[][] bids;
	/**
	 * 卖盘,[price(成交价), amount(成交量)], 按price升序
	 */
	private Object[][] asks;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ts
	 */
	public long getTs() {
		return ts;
	}

	/**
	 * @param ts
	 *            the ts to set
	 */
	public void setTs(long ts) {
		this.ts = ts;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
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
	 * @return the close
	 */
	public float getClose() {
		return close;
	}

	/**
	 * @param close
	 *            the close to set
	 */
	public void setClose(float close) {
		this.close = close;
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
	 * @return the bids
	 */
	public Object[][] getBids() {
		return bids;
	}

	/**
	 * @param bids
	 *            the bids to set
	 */
	public void setBids(Object[][] bids) {
		this.bids = bids;
	}

	/**
	 * @return the asks
	 */
	public Object[][] getAsks() {
		return asks;
	}

	/**
	 * @param asks
	 *            the asks to set
	 */
	public void setAsks(Object[][] asks) {
		this.asks = asks;
	}

}
