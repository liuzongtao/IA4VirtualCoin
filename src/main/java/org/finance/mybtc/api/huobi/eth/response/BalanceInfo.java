/**
 * 
 */
package org.finance.mybtc.api.huobi.eth.response;

/**
 * @author zongtao liu
 *
 */
public class BalanceInfo {
	
	public static final String TYPE_TRADE = "trade";
	public static final String TYPE_FROZEN = "frozen";
	
	public static final String CURRENCY_CNY = "cny";
	public static final String CURRENCY_ETH = "eth";
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 类型:trade: 交易余额，frozen: 冻结余额
	 */
	private String type;
	
	/**
	 * 余额
	 */
	private String balance;

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	

}
