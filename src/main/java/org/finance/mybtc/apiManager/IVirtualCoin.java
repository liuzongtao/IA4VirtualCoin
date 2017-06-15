/**
 * 
 */
package org.finance.mybtc.apiManager;

/**
 * @author zongtao liu
 *
 */
public interface IVirtualCoin {
	
	public static final String API_KEY = "8f06a5ef-378779fd-aced6973-a6b4e";
	public static final String API_SECRET = "64a03733-5fc15c34-3d8e44e7-afb04";
	
	public static final String BTC_E_API_KEY = "BKE24I2F-QM4N55Z2-Y7GCI2LQ-Z0ZPYUCO-VESDJ6HT"; // API-key
	public static final String BTC_E_API_SECRET = "c182c720dcac587750edf8bf713ec53d8d62c898e1442565a503c0d22fda2e22"; // SECRET-key

	public static final String TRADE_PWD = "Aayueya123";

	/***
	 * 获取卖出价格 、 买入价格
	 * 
	 * @return
	 */
	public float[] getBidAndAskPrice();

	/**
	 * 获取当前拥有的数量
	 * 
	 * @return
	 */
	public float getCoinNum();

	/**
	 * 市价买入
	 * 
	 * @param amount
	 * @return
	 */
	public boolean buyMarket(float amount);

	/**
	 * 市价卖出
	 * 
	 * @param amount
	 * @return
	 */
	public boolean sellMarket(float amount);

	/**
	 * 提币
	 * 
	 * @param amount
	 * @return
	 */
	public boolean withdrawCoin(float amount);
}
