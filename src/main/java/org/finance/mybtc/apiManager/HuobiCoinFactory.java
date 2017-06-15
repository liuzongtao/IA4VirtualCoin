/**
 * 
 */
package org.finance.mybtc.apiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.impl.HuobiBtcImpl;
import org.finance.mybtc.apiManager.impl.HuobiCny4NewAreaImpl;
import org.finance.mybtc.apiManager.impl.HuobiCny4OldAreaImpl;
import org.finance.mybtc.apiManager.impl.HuobiEthImpl;
import org.finance.mybtc.apiManager.impl.HuobiLtcImpl;

/**
 * @author zongtao liu
 *
 */
public class HuobiCoinFactory {

	private static volatile HuobiCoinFactory huobiCoinFactory;

	private Map<EHuobiSymbol, IVirtualCoin> coinMap = new HashMap<EHuobiSymbol, IVirtualCoin>();

	private HuobiCoinFactory() {
		init();
	}

	public static HuobiCoinFactory getInstance() {
		if (huobiCoinFactory == null) {
			synchronized (HuobiCoinFactory.class) {
				if (huobiCoinFactory == null) {
					huobiCoinFactory = new HuobiCoinFactory();
				}
			}
		}
		return huobiCoinFactory;
	}

	private void init() {
		coinMap.put(EHuobiSymbol.BTC, new HuobiBtcImpl());
		coinMap.put(EHuobiSymbol.LTC, new HuobiLtcImpl());
		coinMap.put(EHuobiSymbol.CNY_OLD, new HuobiCny4OldAreaImpl());
		coinMap.put(EHuobiSymbol.ETH, new HuobiEthImpl());
		coinMap.put(EHuobiSymbol.CNY_NEW, new HuobiCny4NewAreaImpl());
	}

	/**
	 * 获取虚拟货币
	 * 
	 * @param symbol
	 * @return
	 */
	public IVirtualCoin getVirtualCoin(EHuobiSymbol symbol) {
		return coinMap.get(symbol);
	}

	public List<IVirtualCoin> getVirtualCoinList() {
		List<IVirtualCoin> list = new ArrayList<IVirtualCoin>(coinMap.values());
		return list;
	}

	public Map<EHuobiSymbol, IVirtualCoin> getVirtualCoinMap() {
		return coinMap;
	}

}
