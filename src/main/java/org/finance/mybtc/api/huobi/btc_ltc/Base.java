package org.finance.mybtc.api.huobi.btc_ltc;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.finance.mybtc.utils.EncryptUtil;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 *
 */
public abstract class Base {

	protected static Log log = Logs.get();
	// 火币现货配置信息
	public static String HUOBI_API_URL = "https://api.huobi.com/apiv3";

	// bitvc现货，期货共用accessKey,secretKey配置信息
	public static String BITVC_ACCESS_KEY = "";
	public static String BITVC_SECRET_KEY = "";

	protected static int success = 200;

	public String post(Map<String, Object> map, String url) throws Exception {
		log.debug("request map is " + Json.toJson(map, JsonFormat.compact()));
		ResponseHandler<String> handler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				int code = response.getStatusLine().getStatusCode();
				if (success == code) {
					String result = EntityUtils.toString(response.getEntity(), "utf-8");
					log.debug("response is " + result);
					return result;
				}
				log.infof("response code {}", code);
				return null;
			}
		};
		int times = 10;
		String post = null;
		boolean isOk = false;
		Exception e = null;
		for (int i = 0; i < times; i++) {
			try {
				post = HttpUtil.post(url, map, handler);
				isOk = true;
				break;
			} catch (Exception tmpException) {
				log.error(e.getMessage());
				try {
					Thread.sleep(100);
				} catch (Exception e2) {
					log.error(e2.getMessage());
				}
				e = tmpException;
			}
		}
		if (!isOk) {
			throw e;
		}
		return post;
	}

	public long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	public String sign(TreeMap<String, Object> map) {
		StringBuilder inputStr = new StringBuilder();
		for (Map.Entry<String, Object> me : map.entrySet()) {
			inputStr.append(me.getKey()).append("=").append(me.getValue()).append("&");
		}
		String md5Str = EncryptUtil.MD5(inputStr.substring(0, inputStr.length() - 1)).toLowerCase();
		return md5Str;
	}

}
