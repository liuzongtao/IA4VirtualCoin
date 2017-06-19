/**
 * 
 */
package org.finance.mybtc.main;

import java.util.Date;
import java.util.Map;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.HuobiCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.change.ChangeManager;
import org.finance.mybtc.change.impl.Btc_EthChangeImpl;
import org.finance.mybtc.change.impl.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl.Eth_BtcChangeImpl;
import org.finance.mybtc.change.impl.Eth_LtcChangeImpl;
import org.finance.mybtc.change.impl.Ltc_BtcChangeImpl;
import org.finance.mybtc.change.impl.Ltc_EthChangeImpl;
import org.finance.mybtc.utils.DateUtil;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class MainTools {
	
	private static Log log = Logs.get();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// List<IVirtualCoin> virtualCoinList =
		// HuobiCoinFactory.getInstance().getVirtualCoinList();
		// for (IVirtualCoin coin : virtualCoinList) {
		// System.out.println("=========" + coin.getClass().getSimpleName());
		// System.out.println("coin num = " + coin.getCoinNum());
		// float[] arr = coin.getBidAndAskPrice();
		// if(arr != null && arr.length >= 2){
		// System.out.println("ask price == " + arr[1]);
		// System.out.println("bid price == " + arr[0]);
		// }
		// }
		//
		// List<ABTC_ECoin> list =
		// BTC_ECoinFactory.getInstance().getVirtualCoinList();
		// for (ABTC_ECoin coin : list) {
		// System.out.println("=========" + coin.getClass().getSimpleName());
		// System.out.println("coin num = " + coin.getCoinNum());
		// float[] arr = coin.getBidAndAskPrice();
		// System.out.println("ask price == " + arr[1]);
		// System.out.println("bid price == " + arr[0]);
		// }

		while (true) {
			int result = test();
			long time = 60 * 1000l;
			if (result == 1) {
				time = 30 * 60 * 1000l;
			}
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static int test() {
		int result = 0;
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		IVirtualCoin cnyInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		float totalMoney = 20000;// cnyInfo.getCoinNum();

		IVirtualCoin btcInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.BTC);
		float btcSellPrice = btcInfo.getBidAndAskPrice()[0];
		float btcBuyPrice = btcInfo.getBidAndAskPrice()[1];
		IVirtualCoin ltcInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.LTC);
		float ltcSellPrice = ltcInfo.getBidAndAskPrice()[0];
		float ltcBuyPrice = ltcInfo.getBidAndAskPrice()[1];
		IVirtualCoin ethInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.ETH);
		float ethSellPrice = ethInfo.getBidAndAskPrice()[0];
		float ethBuyPrice = ethInfo.getBidAndAskPrice()[1];

		ChangeManager changeManager = ChangeManager.getInstance();
		Map<EBTC_EPairType, Float> priceMap = changeManager.getPriceFromBTC_E();

		float maxProfit = 0;

		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
		float b2lProfit = b2lImpl.preChange(totalMoney, btcBuyPrice, ltcSellPrice,
				priceMap.get(EBTC_EPairType.BTC_LTC));
		if (b2lProfit > maxProfit) {
			maxProfit = b2lProfit;
		}

		Ltc_BtcChangeImpl l2bimpl = new Ltc_BtcChangeImpl();
		float l2bProfit = l2bimpl.preChange(totalMoney, ltcBuyPrice, btcSellPrice,
				priceMap.get(EBTC_EPairType.LTC_BTC));
		if (l2bProfit > maxProfit) {
			maxProfit = l2bProfit;
		}

		Btc_EthChangeImpl b2eImpl = new Btc_EthChangeImpl();
		float b2eProfit = b2eImpl.preChange(totalMoney, btcBuyPrice, ethSellPrice,
				priceMap.get(EBTC_EPairType.BTC_ETH));
		if (b2eProfit > maxProfit) {
			maxProfit = b2eProfit;
		}

		Eth_BtcChangeImpl e2bimpl = new Eth_BtcChangeImpl();
		float e2bProfit = e2bimpl.preChange(totalMoney, ethBuyPrice, btcSellPrice,
				priceMap.get(EBTC_EPairType.ETH_BTC));
		if (e2bProfit > maxProfit) {
			maxProfit = e2bProfit;
		}

		Ltc_EthChangeImpl l2eImpl = new Ltc_EthChangeImpl();
		float l2eProfit = l2eImpl.preChange(totalMoney, ltcBuyPrice, ethSellPrice,
				priceMap.get(EBTC_EPairType.LTC_ETH));
		if (l2eProfit > maxProfit) {
			maxProfit = l2eProfit;
		}

		Eth_LtcChangeImpl e2limpl = new Eth_LtcChangeImpl();
		float e2lProfit = e2limpl.preChange(totalMoney, ethBuyPrice, ltcSellPrice,
				priceMap.get(EBTC_EPairType.ETH_LTC));
		if (e2lProfit > maxProfit) {
			maxProfit = e2lProfit;
		}

//		float purpose = 3;
//		if (maxProfit > purpose) {
//			log.info(new Date() + " ; maxProfit == " + maxProfit);
//			log.info(new Date() + " ; b2lProfit == " + b2lProfit + " ; l2bProfit == " + l2bProfit
//					+ " ; b2eProfit == " + b2eProfit + " ; e2bProfit == " + e2bProfit + " ; l2eProfit == " + l2eProfit
//					+ " ; l2ebProfit == " + e2lProfit);
//			result = 1;
//		}
		
		log.info(DateUtil.getCurDateTime() + " ; " + b2lProfit + " ; " + l2bProfit + " ; " + b2eProfit + " ; " + e2bProfit + " ; "
				+ l2eProfit + " ; " + e2lProfit);
		return result;
	}

}
