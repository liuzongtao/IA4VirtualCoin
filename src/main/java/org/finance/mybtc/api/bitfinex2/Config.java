package org.finance.mybtc.api.bitfinex2;

public abstract class Config {

	public static String scheme = BitfinexConstant.scheme;
	public static String host = BitfinexConstant.host;
	public static String basePath = BitfinexConstant.version;
	public static String baseUrl = scheme + "://" + host + basePath;

}
