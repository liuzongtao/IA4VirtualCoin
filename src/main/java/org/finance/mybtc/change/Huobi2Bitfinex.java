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
import org.finance.mybtc.apiManager.impl.ABitfinexCoin;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Btc_LtcChangeImpl;
import org.finance.mybtc.change.impl4HuobiAndBitfinex.Ltc_BtcChangeImpl;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class Huobi2Bitfinex extends APfExchange {

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

		// 获取bitfinex的价钱
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		ABitfinexCoin bitfinexBtcInfo = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		float[] bitfinexBtcBidAndAskPrice = bitfinexBtcInfo.getBidAndAskPrice(EBitfinexSymbols.LTCBTC);
		if (bitfinexBtcBidAndAskPrice == null) {
			log.error("bitfinexBtcBidAndAskPrice is null");
			return null;
		}
		float bitfinexBtcSellPrice = bitfinexBtcBidAndAskPrice[0];
		float bitfinexBtcBuyPrice = 0;
		if (bitfinexBtcBidAndAskPrice[1] != 0) {
			bitfinexBtcBuyPrice = DecimalUtil.decimalDown(1 / bitfinexBtcBidAndAskPrice[1], 5);
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

		log.info(profitInfo.get("b2lProfit") + " , " + profitInfo.get("l2bProfit"));
		if (maxProfit >= 2) {
			changeImpl.setWishProfit(maxProfit);
			return changeImpl;
		}
		return null;
	}

}
