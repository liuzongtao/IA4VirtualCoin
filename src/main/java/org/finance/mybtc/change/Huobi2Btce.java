/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.HuobiCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.change.impl4HuobiAndBtce.Btc_EthChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBtce.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBtce.Eth_BtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBtce.Eth_LtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBtce.Ltc_BtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBtce.Ltc_EthChangeImpl;

/**
 * @author zongtao liu
 *
 */
public class Huobi2Btce extends APfExchange {

	@Override
	public AChange preChange(float totalMoney) {
		AChange changeImpl = null;
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		IVirtualCoin cnyInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		if (totalMoney == 0) {
			totalMoney = cnyInfo.getCoinNum();
		}

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

		Map<String, Object> profitInfo = new HashMap<String, Object>();
		float maxProfit = -10;

		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
		float b2lProfit = b2lImpl.preChange(totalMoney, btcBuyPrice, ltcSellPrice,
				priceMap.get(EBTC_EPairType.BTC_LTC));
		profitInfo.put("b2lProfit", b2lProfit);
		if (b2lProfit > maxProfit) {
			changeImpl = b2lImpl;
			maxProfit = b2lProfit;
		}

		Ltc_BtcChangeImpl l2bimpl = new Ltc_BtcChangeImpl();
		float l2bProfit = l2bimpl.preChange(totalMoney, ltcBuyPrice, btcSellPrice,
				priceMap.get(EBTC_EPairType.LTC_BTC));
		profitInfo.put("l2bProfit", l2bProfit);
		if (l2bProfit > maxProfit) {
			changeImpl = l2bimpl;
			maxProfit = l2bProfit;
		}

		Btc_EthChangeImpl b2eImpl = new Btc_EthChangeImpl();
		float b2eProfit = b2eImpl.preChange(totalMoney, btcBuyPrice, ethSellPrice,
				priceMap.get(EBTC_EPairType.BTC_ETH));
		profitInfo.put("b2eProfit", b2eProfit);
		if (b2eProfit > maxProfit) {
			changeImpl = b2eImpl;
			maxProfit = b2eProfit;
		}

		Eth_BtcChangeImpl e2bimpl = new Eth_BtcChangeImpl();
		float e2bProfit = e2bimpl.preChange(totalMoney, ethBuyPrice, btcSellPrice,
				priceMap.get(EBTC_EPairType.ETH_BTC));
		profitInfo.put("e2bProfit", e2bProfit);
		if (e2bProfit > maxProfit) {
			changeImpl = e2bimpl;
			maxProfit = e2bProfit;
		}

		Ltc_EthChangeImpl l2eImpl = new Ltc_EthChangeImpl();
		float l2eProfit = l2eImpl.preChange(totalMoney, ltcBuyPrice, ethSellPrice,
				priceMap.get(EBTC_EPairType.LTC_ETH));
		profitInfo.put("l2eProfit", l2eProfit);
		if (l2eProfit > maxProfit) {
			changeImpl = l2eImpl;
			maxProfit = l2eProfit;
		}

		Eth_LtcChangeImpl e2limpl = new Eth_LtcChangeImpl();
		float e2lProfit = e2limpl.preChange(totalMoney, ethBuyPrice, ltcSellPrice,
				priceMap.get(EBTC_EPairType.ETH_LTC));
		profitInfo.put("e2lProfit", e2lProfit);
		if (e2lProfit > maxProfit) {
			changeImpl = e2limpl;
			maxProfit = e2lProfit;
		}

		log.info(profitInfo.get("b2lProfit") + " , " + profitInfo.get("l2bProfit"));
		if (maxProfit >= 2) {
			changeImpl.setWishProfit(maxProfit);
			return changeImpl;
		}
		return null;
	}

}
