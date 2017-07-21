/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.apiManager.OkcoinFactory;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.change.impl4OkcoinAndBtce.Btc_EthChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBtce.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBtce.Eth_BtcChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBtce.Ltc_BtcChangeImpl;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class Okcoin2Btce extends APfExchange {

	@Override
	public AChange preChange(float totalMoney, float wishProfit) {
		AChange changeImpl = null;
		OkcoinFactory okcoinFactory = OkcoinFactory.getInstance();
		IVirtualCoin cnyInfo = okcoinFactory.getVirtualCoin(EOkcoinSymbols.CNY);
		if (totalMoney == 0) {
			totalMoney = cnyInfo.getCoinNum();
		}

		IVirtualCoin btcInfo = okcoinFactory.getVirtualCoin(EOkcoinSymbols.BTC);
		float[] okcoinBtcBidAndAskPrice = btcInfo.getBidAndAskPrice(EOkcoinSymbols.BTC.toString());
		float btcSellPrice = okcoinBtcBidAndAskPrice[0];
		float btcBuyPrice = okcoinBtcBidAndAskPrice[1];
		IVirtualCoin ltcInfo = okcoinFactory.getVirtualCoin(EOkcoinSymbols.LTC);
		float[] okcoinLtcbidAndAskPrice = ltcInfo.getBidAndAskPrice(EOkcoinSymbols.LTC.toString());
		float ltcSellPrice = okcoinLtcbidAndAskPrice[0];
		float ltcBuyPrice = okcoinLtcbidAndAskPrice[1];
		IVirtualCoin ethInfo = okcoinFactory.getVirtualCoin(EOkcoinSymbols.ETH);
		float[] okcoinEthBidAndAskPrice = ethInfo.getBidAndAskPrice(EOkcoinSymbols.ETH.toString());
		float ethSellPrice = okcoinEthBidAndAskPrice[0];
		float ethBuyPrice = okcoinEthBidAndAskPrice[1];

		ChangeManager changeManager = ChangeManager.getInstance();
		Map<EBTC_EPairType, Float> priceMap = changeManager.getPriceFromBTC_E();
		if (priceMap == null || priceMap.size() == 0) {
			log.error(changeManager.getClass().getSimpleName() + " priceMap is null !");
			return null;
		}

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

		log.debug(Json.toJson(profitInfo, JsonFormat.compact()));
		log.info(profitInfo.get("b2lProfit") + " , " + profitInfo.get("l2bProfit") + " , " + profitInfo.get("b2eProfit")
				+ " , " + profitInfo.get("e2bProfit"));
		if (maxProfit >= wishProfit) {
			changeImpl.setWishProfit(maxProfit);
			return changeImpl;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.change.APfExchange#execExchangeByType(java.lang.String)
	 */
	@Override
	public void execExchangeByType(String type) {
		ChangeInfo execExchange = null;
		switch (type) {
		case "b2l":
			Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
			execExchange = b2lImpl.execExchange();
			break;
		case "l2b":
			Ltc_BtcChangeImpl l2bImpl = new Ltc_BtcChangeImpl();
			execExchange = l2bImpl.execExchange();
			break;
		case "b2e":
			Btc_EthChangeImpl b2eImpl = new Btc_EthChangeImpl();
			execExchange = b2eImpl.execExchange();
			break;
		case "e2b":
			Eth_BtcChangeImpl e2bImpl = new Eth_BtcChangeImpl();
			execExchange = e2bImpl.execExchange();
			break;
		default:
			break;
		}
		if(execExchange == null){
			System.out.println("execExchange is null !");
		}else{
			System.out.println(Json.toJson(execExchange));
		}
	}
	
	

}
