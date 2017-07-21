/**
 * 
 */
package org.finance.mybtc.api.okCoin;

/**
 * @author zongtao liu
 *
 */
public enum EOkcoinSymbols {

	BTC("btc_cny"),

	LTC("ltc_cny"),

	ETH("eth_cny"),
	
	CNY("cny"),

	;

	private String value;

	private EOkcoinSymbols(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
