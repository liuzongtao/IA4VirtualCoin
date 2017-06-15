/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import java.util.List;

import org.finance.mybtc.api.huobi.eth.HuobiEthClient;
import org.finance.mybtc.api.huobi.eth.response.Account;
import org.finance.mybtc.apiManager.IVirtualCoin;

/**
 * @author zongtao liu
 *
 */
public abstract class AHuobiNewArea implements IVirtualCoin {
	
	protected HuobiEthClient client = new HuobiEthClient(API_KEY, API_SECRET);
	

	/**
	 * 获取账号id
	 * @return
	 */
	protected long getAccountId() {
		long accountId = 0;
		List<Account> accounts = client.getAccounts();
		if (accounts != null && accounts.size() > 0) {
			Account account = accounts.get(0);
			if (account.state == Account.STATE_WORKING) {
				accountId = account.id;
			}
		}
		return accountId;
	}

}
