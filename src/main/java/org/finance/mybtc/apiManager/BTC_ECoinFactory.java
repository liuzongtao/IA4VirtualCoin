/**
 * 
 */
package org.finance.mybtc.apiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.impl.ABTC_ECoin;
import org.finance.mybtc.apiManager.impl.BTC_EBtcImpl;
import org.finance.mybtc.apiManager.impl.BTC_EEthImpl;
import org.finance.mybtc.apiManager.impl.BTC_ELtcImpl;

/**
 * @author zongtao liu
 *
 */
public class BTC_ECoinFactory {

	private static volatile BTC_ECoinFactory btc_eCoinFactory;

	private Map<EBTC_ESymbol, ABTC_ECoin> coinMap = new HashMap<EBTC_ESymbol, ABTC_ECoin>();

	private BTC_ECoinFactory() {
		init();
	}

	public static BTC_ECoinFactory getInstance() {
		if (btc_eCoinFactory == null) {
			synchronized (BTC_ECoinFactory.class) {
				if (btc_eCoinFactory == null) {
					btc_eCoinFactory = new BTC_ECoinFactory();
				}
			}
		}
		return btc_eCoinFactory;
	}

	private void init() {
		coinMap.put(EBTC_ESymbol.BTC, new BTC_EBtcImpl());
		coinMap.put(EBTC_ESymbol.LTC, new BTC_ELtcImpl());
		coinMap.put(EBTC_ESymbol.ETH, new BTC_EEthImpl());
	}

	/**
	 * 获取虚拟货币
	 * 
	 * @param symbol
	 * @return
	 */
	public ABTC_ECoin getVirtualCoin(EBTC_ESymbol symbol) {
		return coinMap.get(symbol);
	}

	public List<ABTC_ECoin> getVirtualCoinList() {
		List<ABTC_ECoin> list = new ArrayList<ABTC_ECoin>(coinMap.values());
		return list;
	}

	public Map<EBTC_ESymbol, ABTC_ECoin> getVirtualCoinMap() {
		return coinMap;
	}

}
