/**
 * 
 */
package org.finance.mybtc.api.btc_e.local;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.btc_e.local.response.Ticker;
import org.finance.mybtc.utils.HttpUtil;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class BTC_EClient {
	
	private static Log log = Logs.get();

	private static final String BASE_URL = "https://btc-e.com/api/3/";
	private static final String URL_TICKER = BASE_URL + "ticker/";

	public Map<String, Ticker> getTickerMap(String... pairTypes) {
		Map<String, Ticker> result = new HashMap<String, Ticker>();
		StringBuilder sb = new StringBuilder();
		for (String pairType : pairTypes) {
			if (sb.length() > 0) {
				sb.append("-");
			}
			sb.append(pairType);
		}
		//如果获取失败，则进行循环获取
		Response response = null;
		for(int i = 0 ;i < 10 ;i++){
			try {
				response = HttpUtil.get(URL_TICKER + sb.toString());
				if(response != null){
					break;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(response != null && Strings.isNotBlank(response.getContent())){
			result = Json.fromJsonAsMap(Ticker.class, response.getContent());
		}
		return result;
	}
}
