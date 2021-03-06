/**
 * 
 */
package org.finance.mybtc.apiManager;

/**
 * @author zongtao liu
 *
 */
public enum ESymbol {

	BTC("btc"), LTC("ltc"), ETH("eth"), USD("usd"), CNY("cny"),;

	private String value;

	private ESymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
