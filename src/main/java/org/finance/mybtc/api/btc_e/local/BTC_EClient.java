/**
 * 
 */
package org.finance.mybtc.api.btc_e.local;

import java.util.Map;

import org.finance.mybtc.api.btc_e.local.response.Ticker;
import org.finance.mybtc.utils.HttpUtil;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public class BTC_EClient {

	private static final String BASE_URL = "https://btc-e.com/api/3/";
	private static final String URL_TICKER = BASE_URL + "ticker/";

	public Map<String, Ticker> getTickerMap(String... pairTypes) {
		Map<String, Ticker> result = null;
		StringBuilder sb = new StringBuilder();
		for (String pairType : pairTypes) {
			if (sb.length() > 0) {
				sb.append("-");
			}
			sb.append(pairType);
		}
		Response response = HttpUtil.get(URL_TICKER + sb.toString());
		if (Strings.isNotBlank(response.getContent())) {
			result = Json.fromJsonAsMap(Ticker.class, response.getContent());
		}
		return result;
	}
}
