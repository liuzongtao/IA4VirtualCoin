/**
 * 
 */
package org.finance.mybtc.apiManager;

/**
 * @author zongtao liu
 *
 */
public interface IVirtualCoin {
	
	/***
	 * 获取卖出价格 、 买入价格
	 * 
	 * @return
	 */
	public float[] getBidAndAskPrice(String pair);

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
	public boolean buyMarket(ESymbol fromSymbol,float amount);

	/**
	 * 市价卖出
	 * 
	 * @param amount
	 * @return
	 */
	public boolean sellMarket(ESymbol toSymbol,float amount);

	/**
	 * 提币
	 * 
	 * @param amount
	 * @return
	 */
	public boolean withdrawCoin(float amount,String address);
	
	
	/**
	 * 进行兑换
	 * @param toSymbol
	 * @param amount
	 * @return
	 */
	public boolean exchange(ESymbol toSymbol,float amount);
	
	/**
	 * @return
	 */
	public ESymbol getESymbol();
}
