/**
 * 
 */
package org.finance.mybtc.configs;

import org.nutz.lang.Files;

/**
 * @author zongtao liu
 *
 */
public class Configs {

	public static ApiConfig API_CONFIG_HUOBI = new ApiConfig(Files.findFile("api_huobi.properties"));
	public static ApiConfig API_CONFIG_BTC_E = new ApiConfig(Files.findFile("api_btc-e.properties"));
	public static ApiConfig API_CONFIG_BITFINEX = new ApiConfig(Files.findFile("api_bitfinex.properties"));

}
