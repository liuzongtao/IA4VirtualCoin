/**
 * 
 */
package org.finance.mybtc.api.okCoin.beans;

/**
 * @author zongtao liu
 *
 */
public class OkcoinUserInfo extends OkcoinResponse {

	private OkcoinFundsInfo info;

	/**
	 * @return the info
	 */
	public OkcoinFundsInfo getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(OkcoinFundsInfo info) {
		this.info = info;
	}

}
