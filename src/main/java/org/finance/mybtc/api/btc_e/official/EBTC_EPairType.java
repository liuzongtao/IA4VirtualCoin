/**
 * 
 */
package org.finance.mybtc.api.btc_e.official;

/**
 * @author zongtao liu
 *
 */
public enum EBTC_EPairType {
	
	BTC_USD("btc_usd"),

	LTC_USD("ltc_usd"),

	ETH_USD("eth_usd"),

	LTC_BTC("ltc_btc"),
	
	ETH_BTC("eth_btc"),
	
	ETH_LTC("eth_ltc"),
	;

	private String value;

	private EBTC_EPairType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
