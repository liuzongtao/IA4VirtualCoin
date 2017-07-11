/**
 * 
 */
package org.finance.mybtc.main;

import org.finance.mybtc.change.AChange;
import org.finance.mybtc.change.Huobi2Bitfinex;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class MainTools {
	
	private static Log log = Logs.get();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Huobi2Bitfinex huobi2Bitfinex = new Huobi2Bitfinex();
		while (true) {
			try {
				long sleepTime = 30 * 1000l;
				AChange preChange = huobi2Bitfinex.preChange(20000);
//				if(preChange != null){
//					boolean execExchangeRes = preChange.execExchange();
//					if(!execExchangeRes){
//						log.error("execExchangeRes is false ! exist");
//						break;
//					}
//					sleepTime = 0;
//				}
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
