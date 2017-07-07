/**
 * 
 */
package org.finance.mybtc.api.bitfinex.enums;

/**
 * @author zongtao liu
 *
 */
public enum EBitfinexExchangeType {
	
	Market("market"),
	Limit("limit"),
	Stop("stop"),
	Trailing_stop("trailing-stop"),
	Fill_or_kill("fill-or-kill"),
	Exchange_market("exchange market"),
	Exchange_limit("exchange limit"),
	Exchange_Stop("exchange stop"),
	Exchange_trailing_stop("exchange trailing-stop"),
	Exchange_fill_or_kill("exchange fill-or-kill"),
	;
	private String value;

	private EBitfinexExchangeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
