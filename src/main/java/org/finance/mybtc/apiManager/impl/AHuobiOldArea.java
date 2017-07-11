/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;
import org.finance.mybtc.api.huobi.btc_ltc.HuobiService;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.configs.Configs;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public abstract class AHuobiOldArea implements IVirtualCoin {

	protected HuobiService service = new HuobiService(Configs.API_CONFIG_HUOBI.getApiKey(),Configs.API_CONFIG_HUOBI.getApiSecret());

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

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#exchange(java.lang.String, float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, float amount) {
		return false;
	}
	
	

}
