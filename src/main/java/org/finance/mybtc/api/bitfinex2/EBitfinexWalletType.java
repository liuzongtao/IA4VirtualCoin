/**
 * 
 */
package org.finance.mybtc.api.bitfinex2;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexWalletType {
	
	TRADING("trading"),
	EXCHANGE("exchange"),
	DEPOSIT("deposit"),
	;
	
	
	private String value;

	private EBitfinexWalletType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
