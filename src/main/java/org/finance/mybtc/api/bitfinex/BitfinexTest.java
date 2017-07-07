/**
 * 
 */
package org.finance.mybtc.api.bitfinex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.finance.mybtc.api.bitfinex.beans.BitfinexTicker;
import org.finance.mybtc.api.bitfinex.enums.EBitfinexExchangeType;
import org.finance.mybtc.api.bitfinex.enums.EBitfinexMethod;
import org.finance.mybtc.api.bitfinex.enums.EBitfinexSide;
import org.finance.mybtc.api.bitfinex.enums.EBitfinexSymbol;
import org.finance.mybtc.api.bitfinex.enums.EBitfinexWallet;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * @author zongtao liu
 *
 */
public class BitfinexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BitfinexClient client = new BitfinexClient(BitfinexConst.API_KEY,BitfinexConst.API_KEY_SECRET);
//		System.out.println(Json.toJson(client.getSymbols()));
//		for(String symbol : client.getSymbols()){
//			BitfinexTicker ticker = client.getTicker(symbol);
//			System.out.println(symbol + " == " + Json.toJson( ticker,JsonFormat.compact()));
//		}
//		
//		
//		System.out.println(Json.toJson(client.getSymbolDetails()));
		
//		System.out.println(Json.toJson(client.getAccountFees()));
//		System.out.println(Json.toJson(client.getBalances()));
//		System.out.println(Json.toJson(client.getSummary()));
//		System.out.println(Json.toJson(client.getDepositInfo(EBitfinexMethod.BTC.getValue(),EBitfinexWallet.EXCHANGE.getValue())));
//		String address = "LTNrznxKoMcF31zaX9fRagKHuBquapTPPa";
//		System.out.println(Json.toJson(client.withdraw(EBitfinexMethod.LTC.getValue(), EBitfinexWallet.EXCHANGE.getValue(), 1000, address)));
		System.out.println(Json.toJson(client.orderNew(EBitfinexSymbol.BTC_USD.getValue(), 1000, 30, EBitfinexSide.BUY.getValue(), EBitfinexExchangeType.Market.getValue())));
//		System.out.println(Json.toJson(client.ordersMulti(EBitfinexSymbol.BTC_USD.getValue(), 1000, 30, EBitfinexSide.BUY.getValue(), EBitfinexExchangeType.Market.getValue())));
//		System.out.println(Json.toJson(client.getKeyInfoMap()));
		
//		Examples examples = new  Examples(BitfinexConst.API_KEY,BitfinexConst.API_KEY_SECRET);
//		try {
//			String sendRequestV1Balances = examples.sendRequestV1Balances();
//			
//			System.out.println(sendRequestV1Balances);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			System.out.println(URLEncoder.encode("=","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}

}
