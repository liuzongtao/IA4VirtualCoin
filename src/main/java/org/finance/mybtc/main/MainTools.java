/**
 * 
 */
package org.finance.mybtc.main;

import org.finance.mybtc.change.Huobi2Bitfinex;
import org.finance.mybtc.change.Huobi2Btce;

/**
 * @author zongtao liu
 *
 */
public class MainTools {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Huobi2Bitfinex huobi2Bitfinex = new Huobi2Bitfinex();
		Huobi2Btce huobi2Btc = new Huobi2Btce();
		while (true) {
			try {
				long sleepTime = 30 * 1000l;
				boolean execExchange1 = huobi2Btc.execExchange(30000, true);
				boolean execExchange2 = huobi2Bitfinex.execExchange(30000, true);
				if (execExchange1 || execExchange2) {
					sleepTime = 0;
				}
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
