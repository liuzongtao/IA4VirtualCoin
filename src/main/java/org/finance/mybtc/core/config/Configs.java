/**
 * 
 */
package org.finance.mybtc.core.config;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public class Configs {

	public static ApiConfig API_CONFIG_HUOBI;
	public static ApiConfig API_CONFIG_BTC_E;
	public static ApiConfig API_CONFIG_BITFINEX;
	public static ApiConfig API_CONFIG_OKCOIN;

	static {
		String newPath = "configs";
		// 加载log4j配置文件
		String log4jFileName = "log4j.properties";
		File log4jFile = Files.findFile(log4jFileName);
		if (!log4jFile.exists()) {
			log4jFile = Files.findFile(newPath + File.separator + "log4j.properties");
		}
		if (log4jFile.exists()) {
			PropertyConfigurator.configure(log4jFile.getAbsolutePath());
		}

		// 加载火币网
		String apiHuobiName = "api_huobi.properties";
		API_CONFIG_HUOBI = new ApiConfig(Files.findFile(apiHuobiName));
		if (Strings.isBlank(API_CONFIG_HUOBI.getApiKey())) {
			API_CONFIG_HUOBI = new ApiConfig(Files.findFile(newPath + File.separator + apiHuobiName));
		}
		// 加载btc-e
		String apiBtceName = "api_btc-e.properties";
		API_CONFIG_BTC_E = new ApiConfig(Files.findFile(apiBtceName));
		if (Strings.isBlank(API_CONFIG_BTC_E.getApiKey())) {
			API_CONFIG_BTC_E = new ApiConfig(Files.findFile(newPath + File.separator + apiBtceName));
		}
		// 加载bitfinex
		String apiBitfinexName = "api_bitfinex.properties";
		API_CONFIG_BITFINEX = new ApiConfig(Files.findFile(apiBitfinexName));
		if (Strings.isBlank(API_CONFIG_BITFINEX.getApiKey())) {
			API_CONFIG_BITFINEX = new ApiConfig(Files.findFile(newPath + File.separator + apiBitfinexName));
		}
		// 加载okcoin
		String apiOkcoinName = "api_okcoin.properties";
		API_CONFIG_OKCOIN = new ApiConfig(Files.findFile(apiOkcoinName));
		if (Strings.isBlank(API_CONFIG_OKCOIN.getApiKey())) {
			API_CONFIG_OKCOIN = new ApiConfig(Files.findFile(newPath + File.separator + apiOkcoinName));
		}
	}

	public static File getAppConfigFile() {
		File file = Files.findFile("app.properties");
		if (!file.exists()) {
			file = Files.findFile("configs/app.properties");
		}
		return file;
	}

}
