/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.btc_e.local.BTC_EClient;
import org.finance.mybtc.api.btc_e.local.response.Ticker;
import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.json.Json;

/**
 * @author zongtao liu
 *
 */
public class ChangeManager {
	
	private static volatile ChangeManager changeManager;
	
	private ChangeManager(){}
	
	public static ChangeManager getInstance(){
		if(changeManager == null){
			synchronized (ChangeManager.class) {
				if(changeManager == null){
					changeManager = new ChangeManager();
				}
			}
		}
		return changeManager;
	}
	
	public Map<EBTC_EPairType, Float> getPriceFromBTC_E(){
		BTC_EClient client = new BTC_EClient();
		Map<String, Ticker> tickerMap = client.getTickerMap(EBTC_EPairType.LTC_BTC.getValue(),EBTC_EPairType.ETH_BTC.getValue(),EBTC_EPairType.ETH_LTC.getValue());
		Map<EBTC_EPairType, Float> priceMap = new HashMap<EBTC_EPairType, Float>();
		for(String key : tickerMap.keySet()){
			Ticker ticker = tickerMap.get(key);
			float buyPrice = ticker.getBuy();
			float sellPrice = ticker.getSell();
			priceMap.put(EBTC_EPairType.valueOf(key.toUpperCase()), DecimalUtil.decimalDown(1/sellPrice, 5));
			priceMap.put(EBTC_EPairType.valueOf(reversal(key).toUpperCase()), buyPrice);
		}
		return priceMap;
	}
	
	private String reversal(String value){
		String result = value;
		if(value.contains("_")){
			String[] arr = value.split("_");
			if(arr.length == 2){
				result = arr[1] + "_" + arr[0];
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		ChangeManager m = new ChangeManager();
		Map<EBTC_EPairType, Float> map = m.getPriceFromBTC_E();
		System.out.println(Json.toJson(map));
	}

}
