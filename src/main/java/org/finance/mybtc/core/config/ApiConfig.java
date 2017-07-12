/**
 * 
 */
package org.finance.mybtc.core.config;

import java.io.File;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class ApiConfig {

	private static Log log = Logs.get();

	private String withdraw_addr_btc;
	private String withdraw_addr_ltc;
	private String withdraw_addr_eth;
	private String apiKey;
	private String apiSecret;
	private String tradePwd;

	public ApiConfig(File file) {
		if (!file.exists()) {
			log.error(file.getName() + " is not exists !");
			return;
		}
		try {
			AbstractFileConfiguration config = new PropertiesConfiguration(file);
			if (config.containsKey("withdraw_addr_btc")) {
				withdraw_addr_btc = config.getString("withdraw_addr_btc");
			}

			if (config.containsKey("withdraw_addr_ltc")) {
				withdraw_addr_ltc = config.getString("withdraw_addr_ltc");
			}

			if (config.containsKey("withdraw_addr_eth")) {
				withdraw_addr_eth = config.getString("withdraw_addr_eth");
			}

			if (config.containsKey("apiKey")) {
				apiKey = config.getString("apiKey");
			}

			if (config.containsKey("apiSecret")) {
				apiSecret = config.getString("apiSecret");
			}

			if (config.containsKey("tradePwd")) {
				tradePwd = config.getString("tradePwd");
			}
			log.info(Json.toJson(this, JsonFormat.compact()));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the withdraw_addr_btc
	 */
	public String getWithdraw_addr_btc() {
		return withdraw_addr_btc;
	}

	/**
	 * @param withdraw_addr_btc
	 *            the withdraw_addr_btc to set
	 */
	public void setWithdraw_addr_btc(String withdraw_addr_btc) {
		this.withdraw_addr_btc = withdraw_addr_btc;
	}

	/**
	 * @return the withdraw_addr_ltc
	 */
	public String getWithdraw_addr_ltc() {
		return withdraw_addr_ltc;
	}

	/**
	 * @param withdraw_addr_ltc
	 *            the withdraw_addr_ltc to set
	 */
	public void setWithdraw_addr_ltc(String withdraw_addr_ltc) {
		this.withdraw_addr_ltc = withdraw_addr_ltc;
	}

	/**
	 * @return the withdraw_addr_eth
	 */
	public String getWithdraw_addr_eth() {
		return withdraw_addr_eth;
	}

	/**
	 * @param withdraw_addr_eth
	 *            the withdraw_addr_eth to set
	 */
	public void setWithdraw_addr_eth(String withdraw_addr_eth) {
		this.withdraw_addr_eth = withdraw_addr_eth;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the apiSecret
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	/**
	 * @param apiSecret
	 *            the apiSecret to set
	 */
	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	/**
	 * @return the tradePwd
	 */
	public String getTradePwd() {
		return tradePwd;
	}

	/**
	 * @param tradePwd
	 *            the tradePwd to set
	 */
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

}
