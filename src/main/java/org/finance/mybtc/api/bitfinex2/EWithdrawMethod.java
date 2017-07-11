/**
 * 
 */
package org.finance.mybtc.api.bitfinex2;

/**
 * @author zongtao liu
 *
 */
public enum EWithdrawMethod {

	BTC("bitcoin"),
	
	LTC("litecoin"),
	
	ETH("ethereum"),
	
	MASTER("mastercoin"),
	
	;

	private String value;

	private EWithdrawMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
