/*
 * Huobi.com Inc.
 *Copyright (c) 2014 火币天下网络技术有限公司. 
 *All Rights Reserved
 */
package org.finance.mybtc.api.huobi.btc_ltc;

import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.configs.Configs;

/**
 * @author yanjg 2014年11月22日
 */
public class HuobiMain {

	public static void main(String[] args) {
		HuobiService service = new HuobiService(Configs.API_CONFIG_HUOBI.getApiKey(), Configs.API_CONFIG_HUOBI.getApiSecret());
		try {
			// // 提交限价单接口 1btc 2ltc
			// System.out.println(service.buy(1, "2281.52", "0.001", null,
			// null));
			// // 提交市价单接口
			// System.out.println(service.buyMarket(1, "2", null, null));
			// // 取消订单接口
			// System.out.println(service.cancelOrder(1, 160801));
			// 获取账号详情
			System.out.println(service.getAccountInfo());
			// // 查询个人最新10条成交订单
			// System.out.println(service.getNewDealOrders(1));
			// // 根据trade_id查询oder_id
			// System.out.println(service.getOrderIdByTradeId(1, 2));
			// // 获取所有正在进行的委托
			// System.out.println(service.getOrders(2));
			// // 获取订单详情
			// System.out.println(service.getOrderInfo(1, 160802));
			// // 市价卖出
			// System.out.println(service.sell(1, "2555.52", "0.1", null,
			// null));
			// // 市价卖出
			// System.out.println(service.sellMarket(1, "2", null, null));

			// System.out.println(Json.toJson(service.getActualTickerInfo(EHuobiCoinType.BTC)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
