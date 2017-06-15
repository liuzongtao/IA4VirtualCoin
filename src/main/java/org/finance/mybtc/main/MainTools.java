/**
 * 
 */
package org.finance.mybtc.main;

import java.util.List;

import org.finance.mybtc.apiManager.BTC_ECoinFactory;
import org.finance.mybtc.apiManager.HuobiCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.apiManager.impl.ABTC_ECoin;

/**
 * @author zongtao liu
 *
 */
public class MainTools {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<IVirtualCoin> virtualCoinList = HuobiCoinFactory.getInstance().getVirtualCoinList();
		for (IVirtualCoin coin : virtualCoinList) {
			System.out.println("=========" + coin.getClass().getSimpleName());
			System.out.println("coin num = " + coin.getCoinNum());
			float[] arr = coin.getBidAndAskPrice();
			if(arr != null && arr.length >= 2){
				System.out.println("ask price == " + arr[1]);
				System.out.println("bid price == " + arr[0]);
			}
		}
		
		List<ABTC_ECoin> list = BTC_ECoinFactory.getInstance().getVirtualCoinList();
		for (ABTC_ECoin coin : list) {
			System.out.println("=========" + coin.getClass().getSimpleName());
			System.out.println("coin num = " + coin.getCoinNum());
			float[] arr = coin.getBidAndAskPrice();
			System.out.println("ask price == " + arr[1]);
			System.out.println("bid price == " + arr[0]);
		}
	}
	
	

}
