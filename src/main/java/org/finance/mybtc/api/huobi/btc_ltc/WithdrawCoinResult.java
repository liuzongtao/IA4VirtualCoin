/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public class WithdrawCoinResult {

	/**
	 * 正常200，失败时为错误代码
	 */
	private int code;

	/**
	 * 正常OK，失败时为提示信息
	 */
	private String message;

	/**
	 * 提币申请id
	 */
	private String withdraw_coin_id;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the withdraw_coin_id
	 */
	public String getWithdraw_coin_id() {
		return withdraw_coin_id;
	}

	/**
	 * @param withdraw_coin_id
	 *            the withdraw_coin_id to set
	 */
	public void setWithdraw_coin_id(String withdraw_coin_id) {
		this.withdraw_coin_id = withdraw_coin_id;
	}

}
