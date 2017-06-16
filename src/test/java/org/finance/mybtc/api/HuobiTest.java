/**
 * 
 */
package org.finance.mybtc.api;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.api.huobi.eth.HuobiEthClient;
import org.finance.mybtc.api.huobi.eth.request.CreateOrderRequest;
import org.finance.mybtc.api.huobi.eth.response.Account;
import org.finance.mybtc.utils.JsonUtil;
import org.junit.Test;
import org.nutz.json.Json;

/**
 * @author zongtao liu
 *
 */
public class HuobiTest {

	static final String API_KEY = "8f06a5ef-378779fd-aced6973-a6b4e";
	static final String API_SECRET = "64a03733-5fc15c34-3d8e44e7-afb04";

	@Test
	public void apiSample() {
		// create ApiClient using your api key and api secret:
		HuobiEthClient client = new HuobiEthClient(API_KEY, API_SECRET);
		// get symbol list:
//		print(client.getSymbols());
		print(client.getKline4Depth(EHuobiSymbol.ETH.getValue(), "step0"));
		// get accounts:
//		List<Account> accounts = client.getAccounts();
//		print(accounts);
//		if (!accounts.isEmpty()) {
//			// find account id:
//			Account account = accounts.get(0);
//			long accountId = account.id;
			// create order:
//			CreateOrderRequest createOrderReq = new CreateOrderRequest();
//			createOrderReq.accountId = String.valueOf(accountId);
//			createOrderReq.amount = "0.02";
//			createOrderReq.price = "1100.99";
//			createOrderReq.symbol = "ethcny";
//			createOrderReq.type = CreateOrderRequest.OrderType.BUY_LIMIT;
//			Long orderId = client.createOrder(createOrderReq);
//			print(orderId);
//			// place order:
//			String r = client.placeOrder(orderId);
//			print(r);
//		}
	}

	private void print(Object obj) {
		try {
			System.out.println(JsonUtil.writeValue(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
