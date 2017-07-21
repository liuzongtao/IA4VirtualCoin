/**
 * 
 */
package org.finance.mybtc.apiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.apiManager.impl.okcoin.OkcoinBtcImpl;
import org.finance.mybtc.apiManager.impl.okcoin.OkcoinCnyImpl;
import org.finance.mybtc.apiManager.impl.okcoin.OkcoinEthImpl;
import org.finance.mybtc.apiManager.impl.okcoin.OkcoinLtcImpl;

/**
 * @author zongtao liu
 *
 */
public class OkcoinFactory {

	private static volatile OkcoinFactory okcoinFactory;

	private Map<EOkcoinSymbols, IVirtualCoin> coinMap = new HashMap<EOkcoinSymbols, IVirtualCoin>();

	private OkcoinFactory() {
		init();
	}

	public static OkcoinFactory getInstance() {
		if (okcoinFactory == null) {
			synchronized (HuobiCoinFactory.class) {
				if (okcoinFactory == null) {
					okcoinFactory = new OkcoinFactory();
				}
			}
		}
		return okcoinFactory;
	}

	private void init() {
		coinMap.put(EOkcoinSymbols.BTC, new OkcoinBtcImpl());
		coinMap.put(EOkcoinSymbols.LTC, new OkcoinLtcImpl());
		coinMap.put(EOkcoinSymbols.ETH, new OkcoinEthImpl());
		coinMap.put(EOkcoinSymbols.CNY, new OkcoinCnyImpl());
	}

	/**
	 * 获取虚拟货币
	 * 
	 * @param symbol
	 * @return
	 */
	public IVirtualCoin getVirtualCoin(EOkcoinSymbols symbol) {
		return coinMap.get(symbol);
	}

	public List<IVirtualCoin> getVirtualCoinList() {
		List<IVirtualCoin> list = new ArrayList<IVirtualCoin>(coinMap.values());
		return list;
	}

	public Map<EOkcoinSymbols, IVirtualCoin> getVirtualCoinMap() {
		return coinMap;
	}

}
