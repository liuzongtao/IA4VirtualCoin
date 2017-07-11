package org.finance.mybtc.api.bitfinex2.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.finance.mybtc.api.bitfinex2.Config;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class CallUtils {

	private static Log log = Logs.get();
	
	public static URIBuilder createUriBuilder(String path) {
		return new URIBuilder().setScheme(Config.scheme).setHost(Config.host).setPath(Config.basePath + path);
	}

	public static <R> R parseResponse(HttpResponse response, Type responseType) throws BitfinexCallException {
		try {
			InputStream stream = response.getEntity().getContent();
			String content = IOUtils.toString(stream);
			log.debug("Response is " + content);
			stream.close();
			return new Gson().fromJson(content, responseType);
		} catch (JsonSyntaxException e) {
			throw new BitfinexCallException("Could not parse the response as JSon", e);
		} catch (JsonIOException e) {
			throw new BitfinexCallException("Could not read the response", e);
		} catch (IllegalStateException e) {
			throw new BitfinexCallException("Could not create the content stream", e);
		} catch (IOException e) {
			throw new BitfinexCallException("Could not create the stream", e);
		}
	}

}
