/**
 * 
 */
package org.finance.mybtc.api.bitfinex.enums;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexSide {
	
	BUY("buy"),
	SELL("sell");
	
	private String value;

	private EBitfinexSide(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
