/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.apiManager.HuobiCoinFactory;

/**
 * @author zongtao liu
 *
 */
public class PfFactory {

	private static volatile PfFactory factory;

	private Map<EPfType, APfExchange> map = new HashMap<EPfType, APfExchange>();

	private PfFactory() {
		init();
	}

	public static PfFactory getInstance() {
		if (factory == null) {
			synchronized (HuobiCoinFactory.class) {
				if (factory == null) {
					factory = new PfFactory();
				}
			}
		}
		return factory;
	}

	private void init() {
		map.put(EPfType.huobi2bitfinex, new Huobi2Bitfinex());
		map.put(EPfType.huobi2btce, new Huobi2Btce());
		map.put(EPfType.okcoin2bitfinex, new Okcoin2Bitfinex());
		map.put(EPfType.okcoin2btce, new Okcoin2Btce());
	}
	
	public APfExchange getPfExchange(EPfType type){
		if(map.containsKey(type)){
			return map.get(type);
		}
		return null;
	}

}
