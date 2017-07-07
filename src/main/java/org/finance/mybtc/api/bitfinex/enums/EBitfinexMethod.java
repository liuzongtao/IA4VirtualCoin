/**
 * 
 */
package org.finance.mybtc.api.bitfinex.enums;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexMethod {

	BTC("bitcoin"),
	
	LTC("litecoin"),
	
	ETH("ethereum"),
	
	MASTER("mastercoin"),
	
	;

	private String value;

	private EBitfinexMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
