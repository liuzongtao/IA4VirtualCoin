/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;
import org.finance.mybtc.api.huobi.btc_ltc.HuobiService;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public abstract class AHuobiOldArea implements IVirtualCoin {

	protected HuobiService service = new HuobiService(API_KEY,API_SECRET);

	/**
	 * 获取个人资产信息
	 * 
	 * @return
	 */
	protected HuobiAccountInfo getAccount() {
		HuobiAccountInfo account = null;
		try {
			String accountInfo = service.getAccountInfo();
			if (Strings.isNotBlank(accountInfo)) {
				account = Json.fromJson(HuobiAccountInfo.class, accountInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

}
