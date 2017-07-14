/**
 * 
 */
package org.finance.mybtc.main;

import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Ltc_BtcChangeImpl;
import org.nutz.json.Json;

/**
 * @author zongtao liu
 *
 */
public class ExeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
//		b2lImpl.execExchange();
		
		Ltc_BtcChangeImpl l2bImpl = new Ltc_BtcChangeImpl();
		ChangeInfo execExchange = l2bImpl.execExchange();
		System.out.println(Json.toJson(execExchange));
		
//		BTC_ELtcImpl l = new BTC_ELtcImpl();
//		boolean withdrawCoin = l.withdrawCoin(100f, Configs.API_CONFIG_HUOBI.getWithdraw_addr_ltc());
//		System.out.println(withdrawCoin);

	}

}
