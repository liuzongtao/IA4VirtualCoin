/**
 * 
 */
package org.finance.mybtc.api.btc_e.official;

/**
 * @author zongtao liu
 *
 */
public enum EBTC_ESymbol {

	BTC("btc"),

	LTC("ltc"),

	ETH("eth"),

	USD("usd"),;

	private String value;

	private EBTC_ESymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
