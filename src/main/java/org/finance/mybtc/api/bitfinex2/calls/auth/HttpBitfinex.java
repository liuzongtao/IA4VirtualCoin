package org.finance.mybtc.api.bitfinex2.calls.auth;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.finance.mybtc.api.bitfinex2.Bitfinex;
import org.finance.mybtc.api.bitfinex2.Config;
import org.finance.mybtc.api.bitfinex2.calls.BitfinexCall;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.gson.Gson;

public abstract class HttpBitfinex extends HttpPost implements BitfinexCall {
	
	private static Log log = Logs.get();

	public HttpBitfinex(String request) {
		super(Config.baseUrl + request);
		addHeader("request", Config.basePath + request);
		addHeader("nonce", System.currentTimeMillis() + "");
		updatePayload();
		int timeout = 10 * 1000;
		setConfig(RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
				.setSocketTimeout(timeout).build());
	}

	public void updatePayload() {
		try {
			Map<String, String> headers = new HashMap<String, String>();
			for (Header header : getAllHeaders()) {
				headers.put(header.getName(), header.getValue());
			}
			byte[] payload = new Base64().encode(new Gson().toJson(headers).getBytes("UTF-8"));
			Mac mac = HmacUtils.getHmacSha384(Bitfinex.apiSecret.getBytes("UTF-8"));
			byte[] signature = mac.doFinal(payload);
			addHeader("X-BFX-APIKEY", Bitfinex.apiKey);
			addHeader("X-BFX-PAYLOAD", new String(payload));
			addHeader("X-BFX-SIGNATURE", Hex.encodeHexString(signature));

			headers.clear();
			for (Header header : getAllHeaders()) {
				headers.put(header.getName(), header.getValue());
			}
			log.info("headers == " + new Gson().toJson(headers));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getNonce() {
		return System.currentTimeMillis() + "";
	}

}
