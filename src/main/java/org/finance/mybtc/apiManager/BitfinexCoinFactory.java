/**
 * 
 */
package org.finance.mybtc.apiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.apiManager.impl.bitfinex.ABitfinexCoin;
import org.finance.mybtc.apiManager.impl.bitfinex.BitfinexBtcImpl;
import org.finance.mybtc.apiManager.impl.bitfinex.BitfinexEthImpl;
import org.finance.mybtc.apiManager.impl.bitfinex.BitfinexLtcImpl;

/**
 * @author zongtao liu
 *
 */
public class BitfinexCoinFactory {

	private static volatile BitfinexCoinFactory bitfinexCoinFactory;

	private Map<EBitfinexCurrencies, ABitfinexCoin> coinMap = new HashMap<EBitfinexCurrencies, ABitfinexCoin>();

	private BitfinexCoinFactory() {
		init();
	}

	public static BitfinexCoinFactory getInstance() {
		if (bitfinexCoinFactory == null) {
			synchronized (BitfinexCoinFactory.class) {
				if (bitfinexCoinFactory == null) {
					bitfinexCoinFactory = new BitfinexCoinFactory();
				}
			}
		}
		return bitfinexCoinFactory;
	}

	private void init() {
		coinMap.put(EBitfinexCurrencies.BTC, new BitfinexBtcImpl());
		coinMap.put(EBitfinexCurrencies.LTC, new BitfinexLtcImpl());
		coinMap.put(EBitfinexCurrencies.ETH, new BitfinexEthImpl());
	}

	/**
	 * 获取虚拟货币
	 * 
	 * @param symbol
	 * @return
	 */
	public ABitfinexCoin getVirtualCoin(EBitfinexCurrencies symbol) {
		return coinMap.get(symbol);
	}

	public List<ABitfinexCoin> getVirtualCoinList() {
		List<ABitfinexCoin> list = new ArrayList<ABitfinexCoin>(coinMap.values());
		return list;
	}

	public Map<EBitfinexCurrencies, ABitfinexCoin> getVirtualCoinMap() {
		return coinMap;
	}

}
