/**
 * 
 */
package org.finance.mybtc.change;

import java.util.HashMap;
import java.util.Map;

import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.apiManager.BitfinexCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.apiManager.OkcoinFactory;
import org.finance.mybtc.apiManager.impl.bitfinex.ABitfinexCoin;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.change.impl4OkcoinAndBitfinex.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBitfinex.Ltc_BtcChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBitfinex.Btc_EthChangeImpl;
import org.finance.mybtc.change.impl4OkcoinAndBitfinex.Eth_BtcChangeImpl;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class Okcoin2Bitfinex extends APfExchange {

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

		// 获取bitfinex的价钱
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		ABitfinexCoin bitfinexBtcInfo = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		float[] bitfinexLtcBtcBidAndAskPrice = bitfinexBtcInfo.getBidAndAskPrice(EBitfinexSymbols.LTCBTC);
		if (bitfinexLtcBtcBidAndAskPrice == null) {
			log.error("bitfinexBtcBidAndAskPrice is null");
			return null;
		}
		float bitfinexBtc2LtcPrice = bitfinexLtcBtcBidAndAskPrice[0];
		float bitfinexLtc2BtcPrice = 0;
		if (bitfinexLtcBtcBidAndAskPrice[1] != 0) {
			bitfinexLtc2BtcPrice = DecimalUtil.decimalDown(1 / bitfinexLtcBtcBidAndAskPrice[1], 5);
		}

		float[] bitfinexEthBtcBidAndAskPrice = bitfinexBtcInfo.getBidAndAskPrice(EBitfinexSymbols.ETHBTC);
		if (bitfinexEthBtcBidAndAskPrice == null) {
			log.error("bitfinexBtcBidAndAskPrice is null");
			return null;
		}
		float bitfinexBtc2EthPrice = bitfinexEthBtcBidAndAskPrice[0];
		float bitfinexEth2BtcPrice = 0;
		if (bitfinexEthBtcBidAndAskPrice[1] != 0) {
			bitfinexEth2BtcPrice = DecimalUtil.decimalDown(1 / bitfinexEthBtcBidAndAskPrice[1], 5);
		}

		Map<String, Object> profitInfo = new HashMap<String, Object>();
		float maxProfit = -10;

		Btc_LtcChangeImpl b2lImpl = new Btc_LtcChangeImpl();
		float b2lProfit = b2lImpl.preChange(totalMoney, btcBuyPrice, ltcSellPrice, bitfinexBtc2LtcPrice);
		profitInfo.put("b2lProfit", b2lProfit);
		if (b2lProfit > maxProfit) {
			changeImpl = b2lImpl;
			maxProfit = b2lProfit;
		}

		Ltc_BtcChangeImpl l2bimpl = new Ltc_BtcChangeImpl();
		float l2bProfit = l2bimpl.preChange(totalMoney, ltcBuyPrice, btcSellPrice, bitfinexLtc2BtcPrice);
		profitInfo.put("l2bProfit", l2bProfit);
		if (l2bProfit > maxProfit) {
			changeImpl = l2bimpl;
			maxProfit = l2bProfit;
		}

		Btc_EthChangeImpl b2eImpl = new Btc_EthChangeImpl();
		float b2eProfit = b2eImpl.preChange(totalMoney, btcBuyPrice, ethSellPrice, bitfinexBtc2EthPrice);
		profitInfo.put("b2eProfit", b2eProfit);
		if (b2eProfit > maxProfit) {
			changeImpl = b2eImpl;
			maxProfit = b2eProfit;
		}

		Eth_BtcChangeImpl e2bimpl = new Eth_BtcChangeImpl();
		float e2bProfit = e2bimpl.preChange(totalMoney, ethBuyPrice, btcSellPrice, bitfinexEth2BtcPrice);
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
