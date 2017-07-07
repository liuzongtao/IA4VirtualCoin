/**
 * 
 */
package org.finance.mybtc.api.bitfinex.enums;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexSymbol {
	
	BTC_USD("btcusd"),
	LTC_USD("ltcusd"),
	ETH_USD("ethusd"),
	LTC_BTC("ltcbtc"),
	ETH_BTC("ethbtc"),
	;
	
	private String value;

	private EBitfinexSymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
