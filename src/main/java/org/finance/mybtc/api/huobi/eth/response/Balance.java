/**
 * 
 */
package org.finance.mybtc.api.huobi.eth.response;

import java.util.List;

/**
 * @author zongtao liu
 *
 */
public class Balance {

	public static final String STATE_WORKING = "working";
	public static final String STATE_LOCK = "lock";

	/**
	 * 账户 ID
	 */
	private long id;

	/**
	 * 账户状态 working：正常 lock：账户被锁定
	 */
	private String state;

	/**
	 * 账户类型 spot：现货账户
	 */
	private String type;

	/**
	 * 子账户数组
	 */
	private List<BalanceInfo> list;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the list
	 */
	public List<BalanceInfo> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<BalanceInfo> list) {
		this.list = list;
	}

}
