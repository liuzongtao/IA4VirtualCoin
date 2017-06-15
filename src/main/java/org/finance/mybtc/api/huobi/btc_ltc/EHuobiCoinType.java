/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public enum EHuobiCoinType {
	/**
	 * 比特币
	 */
	BTC(1),
	/**
	 * 莱特币
	 */
	LTC(2),
	
	;

	private int value;

	private EHuobiCoinType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
