/**
 * 
 */
package org.finance.mybtc.api.bitfinex.enums;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexWallet {
	
	TRADING("trading"),
	EXCHANGE("exchange"),
	DEPOSIT("deposit"),
	;
	
	
	private String value;

	private EBitfinexWallet(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
