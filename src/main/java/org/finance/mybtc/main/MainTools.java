/**
 * 
 */
package org.finance.mybtc.main;

import org.finance.mybtc.change.AChange;
import org.finance.mybtc.change.Huobi2Bitfinex;
import org.finance.mybtc.change.Huobi2Btce;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.core.mail.MailFactory;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class MainTools {
	
	private static Log log = Logs.get();
	
	private static boolean opt = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Huobi2Bitfinex huobi2Bitfinex = new Huobi2Bitfinex();
//		Huobi2Btce huobi2Btc = new Huobi2Btce();
		long lastSendEmail = 0;
		while (true) {
			try {
				long sleepTime = 30 * 1000l;
				AChange preChange = huobi2Bitfinex.preChange(0);
				//测试使用
				if(preChange != null && !opt){
					long now = System.currentTimeMillis();
					if(now - lastSendEmail > 10 * 60 * 1000l){
						MailFactory.getInstance().sendEmail(preChange.getClass().getSimpleName(), "preChange getWishProfit is " + preChange.getWishProfit());
						lastSendEmail = now ;
					}
				}
				if(preChange != null && opt){
					ChangeInfo execExchangeRes = preChange.execExchange();
					if(execExchangeRes !=null){
						log.info(Json.toJson(execExchangeRes,JsonFormat.compact()));
						MailFactory.getInstance().sendEmail(execExchangeRes.getRes(), Json.toJson(execExchangeRes));
						if(!execExchangeRes.isOk()){
							log.error("execExchangeRes is false ! exist");
							break;
						}
						sleepTime = 0;
					}
				}
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
//		b2lImpl.execExchange();
	}

}
