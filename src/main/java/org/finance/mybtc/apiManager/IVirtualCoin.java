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
	public double[] getBidAndAskPrice(String pair);

	/**
	 * 获取当前拥有的数量
	 * 
	 * @return
	 */
	public double getCoinNum();

	/**
	 * 市价买入
	 * 
	 * @param amount
	 * @return
	 */
	public boolean buyMarket(ESymbol fromSymbol,double amount);

	/**
	 * 市价卖出
	 * 
	 * @param amount
	 * @return
	 */
	public boolean sellMarket(ESymbol toSymbol,double amount);

	/**
	 * 提币
	 * 
	 * @param amount
	 * @return
	 */
	public boolean withdrawCoin(double amount,String address);
	
	
	/**
	 * 进行兑换
	 * @param toSymbol
	 * @param amount
	 * @return
	 */
	public boolean exchange(ESymbol toSymbol,double amount);
	
	/**
	 * @return
	 */
	public ESymbol getESymbol();
}
