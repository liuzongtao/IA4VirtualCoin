/**
 * 
 */
package org.finance.mybtc.change;

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
public abstract class APfExchange {

	protected static Log log = Logs.get();

	private static long lastSendEmail = 0;

	public abstract AChange preChange(double totalMoney, float wishProfit);
	
	public abstract void execExchangeByType(String type);

	public boolean execExchange(double testMoney, boolean isTest, float wishProfit) {
		boolean result = false;
		AChange preChange = preChange(testMoney, wishProfit);
		// 测试使用
		if (preChange != null) {
			long now = System.currentTimeMillis();
			if (now - lastSendEmail > 10 * 60 * 1000l) {
				MailFactory.getInstance().sendEmail(preChange.getClass().getSimpleName(),
						this.getClass().getSimpleName() + " preChange getWishProfit is " + preChange.getWishProfit());
				lastSendEmail = now;
			}
		}
		if (preChange != null && !isTest) {
			ChangeInfo execExchangeRes = preChange.execExchange();
			if (execExchangeRes != null) {
				log.info(Json.toJson(execExchangeRes, JsonFormat.compact()));
				MailFactory.getInstance().sendEmail(execExchangeRes.getRes(), Json.toJson(execExchangeRes));
				if (execExchangeRes.isOk()) {
					result = true;
				} else {
					log.error("execExchangeRes is false ! exist");
				}
			}
		}
		return result;
	}
}
