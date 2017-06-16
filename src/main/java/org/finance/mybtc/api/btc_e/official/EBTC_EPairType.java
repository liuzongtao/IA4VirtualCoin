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
	
	BTC_LTC("btc_ltc"),
	
	ETH_BTC("eth_btc"),
	
	BTC_ETH("btc_eth"),
	
	ETH_LTC("eth_ltc"),
	
	LTC_ETH("ltc_eth"),
	;

	private String value;

	private EBTC_EPairType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
