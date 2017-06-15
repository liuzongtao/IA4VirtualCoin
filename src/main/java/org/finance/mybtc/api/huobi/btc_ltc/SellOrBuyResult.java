/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public class SellOrBuyResult {

	public static final String RESULT_SUCCESS = "success";

	/**
	 * 成功状态 success
	 */
	private String result;

	/**
	 * 委托id
	 */
	private String id;

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
