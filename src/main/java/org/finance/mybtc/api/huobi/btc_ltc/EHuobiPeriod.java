/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public enum EHuobiPeriod {

	/**
	 * 1分钟
	 */
	MIN_1("1min"),
	/**
	 * 5分钟
	 */
	MIN_5("5min"),
	/**
	 * 15分钟
	 */
	MIN_15("15min"),
	/**
	 * 30分钟
	 */
	MIN_30("30min"),
	/**
	 * 60分钟
	 */
	MIN_60("60min"),
	/**
	 * 1天
	 */
	DAY_1("1day"),
	/**
	 * 1个月
	 */
	MON_1("1mon"),
	/**
	 * 1周
	 */
	WEEK_1("1week"),
	/**
	 * 1年
	 */
	YEAR_1("1year"),
	
	;

	private String value;

	private EHuobiPeriod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
