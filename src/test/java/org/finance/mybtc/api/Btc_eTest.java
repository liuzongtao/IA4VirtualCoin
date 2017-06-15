/**
 * 
 */
package org.finance.mybtc.api;

import static org.junit.Assert.fail;

import org.finance.mybtc.api.btc_e.official.TradeApi;
import org.junit.Test;

/**
 * @author zongtao liu
 *
 */
public class Btc_eTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLastPrice() {
		TradeApi ta = new TradeApi();
		ta.ticker.addPair("btc_usd"); // add btc_usd pair to ticker method
		//ta.ticker.addPair("ltc_usd");
		ta.ticker.runMethod();// run ticker API method with added parametres
		while (ta.ticker.hasNextPair()) {
			/***
			 * in this case, switch to first pairs, i.e. btc_usd, for the next
			 * iteration it will be ltc_usd
			 */
			ta.ticker.switchNextPair();
			/**
			 * print last price for current pair
			 */
			System.out.println(ta.ticker.getCurrentLast());
		}
	}

	@Test
	public void testGetActiveOrders() {
		TradeApi ta = new TradeApi();
		ta.depth.addPair("btc_usd");
		ta.depth.setLimit(100);
		ta.depth.runMethod();
		/***
		 * now instead switchNextPair we can use this method, but switchNextPair
		 * works too
		 */
		ta.depth.setCurrentPair("btc_usd");
		while (ta.depth.hasNextAsk()) {
			/**
			 * switch to next ask. In this case we can switch pairs and can
			 * switch asks and bids for each pair
			 */
			ta.depth.switchNextAsk();
			System.out.println(ta.depth.getCurrentAskPrice());
		}
	}

	@Test
	public void testGetMyBalance() throws Exception {
		String aKey = "BKE24I2F-QM4N55Z2-Y7GCI2LQ-Z0ZPYUCO-VESDJ6HT"; // API-key
		String aSecret = "c182c720dcac587750edf8bf713ec53d8d62c898e1442565a503c0d22fda2e22"; // SECRET-key
		/**
		 * we can use constructor, we can use empty constructor and after that
		 * t.setKeys(...) and so on.
		 */
		TradeApi t = new TradeApi(aKey, aSecret);
		t.getInfo.runMethod();
		/**
		 * get USD balance. We can get currency list with getCurrencyList()
		 */
		System.out.println(t.getInfo.getInfo());
//		System.out.println(t.getInfo.getBalance("usd"));
	}

}
