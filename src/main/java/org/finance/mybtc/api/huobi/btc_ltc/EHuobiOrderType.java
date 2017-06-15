/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public enum EHuobiOrderType {
	/**
	 * 买
	 */
	BUY(1),
	/**
	 * 卖
	 */
	SELL(2),
	/**
	 * 限价买
	 */
	BUY_LIMIT(1),
	/**
	 * 限价卖
	 */
	SELL_LIMIT(2),
	/**
	 * 市价买
	 */
	BUY_MARKET(3),
	/**
	 * 市价卖
	 */
	SELL_MARKET(4),

	;

	private int value;

	private EHuobiOrderType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
