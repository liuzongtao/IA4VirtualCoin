/**
 * 
 */
package org.finance.mybtc.utils;

import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Cookie;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public class HttpUtil {

	public static Response httpsPost(String url) {
		return https(url, null, null, METHOD.POST, null);
	}

	public static Response httpsPost(String url, Map<String, Object> params) {
		return https(url, params, null, METHOD.POST, null);
	}

	public static Response httpsPost(String url, Map<String, Object> params, Header header) {
		return https(url, params, null, METHOD.POST, header);
	}

	public static Response httpsPost(String url, Map<String, Object> params, Cookie cookie) {
		return https(url, params, cookie, METHOD.POST);
	}

	public static Response get(String url) {
		if (Strings.isBlank(url) || !url.contains("http")) {
			return null;
		}
		if (url.startsWith("https")) {
			return httpsGet(url);
		} else {
			return Http.get(url);
		}
	}

	public static Response post(String url) {
		if (Strings.isBlank(url) || !url.contains("http")) {
			return null;
		}
		if (url.startsWith("https")) {
			return httpsPost(url);
		} else {
			return Http.post2(url, null, 3000);
		}
	}

	public static Response httpsGet(String url) {
		return httpsGet(url, null, null);
	}

	public static Response httpsGet(String url, Map<String, Object> params, Cookie cookie) {
		return https(url, params, cookie, METHOD.GET);
	}

	private static Response https(String url, Map<String, Object> params, Cookie cookie, METHOD method) {
		return https(url, params, cookie, method, null);
	}

	private static Response https(String url, Map<String, Object> params, Cookie cookie, METHOD method, Header header) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		if (header == null) {
			header = Header.create();
		}
		Request req = Request.create(url, method, params, header);
		Response response = null;
		try {
			Sender sender = Sender.create(req);
			sender.setSSLSocketFactory(Http.nopSSLSocketFactory());
			if (cookie != null) {
				sender.setInterceptor(cookie);
			}
			response = sender.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
