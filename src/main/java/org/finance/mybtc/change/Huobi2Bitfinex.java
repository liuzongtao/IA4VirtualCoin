/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.BitfinexCoinFactory;
import org.finance.mybtc.apiManager.HuobiCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.apiManager.impl.bitfinex.ABitfinexCoin;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Btc_EthChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Eth_BtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Ltc_BtcChangeImpl;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class Huobi2Bitfinex extends APfExchange {

	@Override
	public AChange preChange(double totalMoney, float wishProfit) {
		AChange changeImpl = null;
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		IVirtualCoin cnyInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		if (totalMoney == 0) {
			totalMoney = cnyInfo.getCoinNum();
		}

		IVirtualCoin btcInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.BTC);
		double[] huobiBtcBidAndAskPrice = btcInfo.getBidAndAskPrice(EHuobiSymbol.BTC.toString());
		double btcSellPrice = huobiBtcBidAndAskPrice[0];
		double btcBuyPrice = huobiBtcBidAndAskPrice[1];
		IVirtualCoin ltcInfo = huobiFactory.getVirtualCoin(EHuobiSymbol.LTC);
		double[] huobiLtcbidAndAskPrice = ltcInfo.getBidAndAskPrice(EHuobiSymbol.LTC.toString());
		double ltcSellPrice = huobiLtcbidAndAskPrice[0];
		double ltcBuyPrice = huobiLtcbidAndAskPrice[1];

		// 获取bitfinex的价钱
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		ABitfinexCoin bitfinexBtcInfo = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		double[] bitfinexBtcBidAndAskPrice = bitfinexBtcInfo.getBidAndAskPrice(EBitfinexSymbols.LTCBTC);
		if (bitfinexBtcBidAndAskPrice == null) {
			log.error("bitfinexBtcBidAndAskPrice is null");
			return null;
		}
		double bitfinexBtcSellPrice = bitfinexBtcBidAndAskPrice[0];
		double bitfinexBtcBuyPrice = 0;
		if (bitfinexBtcBidAndAskPrice[1] != 0) {
			bitfinexBtcBuyPrice = 1 / bitfinexBtcBidAndAskPrice[1];
		}

		Map<String, Object> profitInfo = new HashMap<String, Object>();
		float maxProfit = -10;

		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
		float b2lProfit = b2lImpl.preChange(totalMoney, btcBuyPrice, ltcSellPrice, bitfinexBtcSellPrice);
		profitInfo.put("b2lProfit", b2lProfit);
		if (b2lProfit > maxProfit) {
			changeImpl = b2lImpl;
			maxProfit = b2lProfit;
		}

		Ltc_BtcChangeImpl l2bimpl = new Ltc_BtcChangeImpl();
		float l2bProfit = l2bimpl.preChange(totalMoney, ltcBuyPrice, btcSellPrice, bitfinexBtcBuyPrice);
		profitInfo.put("l2bProfit", l2bProfit);
		if (l2bProfit > maxProfit) {
			changeImpl = l2bimpl;
			maxProfit = l2bProfit;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.change.APfExchange#execExchangeByType(java.lang.String)
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
		if (execExchange == null) {
			System.out.println("execExchange is null !");
		} else {
			System.out.println(Json.toJson(execExchange));
		}

	}

}
