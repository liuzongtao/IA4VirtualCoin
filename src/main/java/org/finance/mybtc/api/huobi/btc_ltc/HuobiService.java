/*
 * Huobi.com Inc.
 *Copyright (c) 2014 火币天下网络技术有限公司.
 *All Rights Reserved
 */
package org.finance.mybtc.api.huobi.btc_ltc;

import java.math.BigDecimal;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.finance.mybtc.api.huobi.beans.ActualTickerBean;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author yanjg 2014年11月22日
 */
public class HuobiService extends Base {

	private static final String BUY = "buy";
	private static final String BUY_MARKET = "buy_market";
	private static final String CANCEL_ORDER = "cancel_order";
	private static final String ACCOUNT_INFO = "get_account_info";
	private static final String NEW_DEAL_ORDERS = "get_new_deal_orders";
	private static final String ORDER_ID_BY_TRADE_ID = "get_order_id_by_trade_id";
	private static final String GET_ORDERS = "get_orders";
	private static final String ORDER_INFO = "order_info";
	private static final String SELL = "sell";
	private static final String SELL_MARKET = "sell_market";
	private static final String WITHDRAW_COIN = "withdraw_coin";
	private static final String LOAN_AVAILABLE = "get_loan_available";
	
	private static final String MARKET_BTC_URL = "http://api.huobi.com/staticmarket/ticker_btc_json.js";
	private static final String MARKET_LTC_URL = "http://api.huobi.com/staticmarket/ticker_ltc_json.js";
	
	final String accessKeyId;
	final String accessKeySecret;

	/**
	 * 创建一个ApiClient实例
	 * 
	 * @param accessKeyId
	 *            AccessKeyId
	 * @param accessKeySecret
	 *            AccessKeySecret
	 */
	public HuobiService(String accessKeyId, String accessKeySecret) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 下单接口
	 * 
	 * @param coinType
	 * @param price
	 * @param amount
	 * @param tradePassword
	 * @param tradeid
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String buy(EHuobiCoinType coinType, String price, String amount, String tradePassword, Integer tradeid)
			throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", BUY);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("price", price);
		paraMap.put("amount", amount);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(tradePassword)) {
			paraMap.put("trade_password", tradePassword);
		}
		if (null != tradeid) {
			paraMap.put("trade_id", tradeid);
		}
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 提交市价单接口
	 * 
	 * @param coinType
	 * @param amount
	 * @param tradePassword
	 * @param tradeid
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String buyMarket(EHuobiCoinType coinType, String amount, String tradePassword, Integer tradeid) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", BUY_MARKET);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("amount", amount);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(tradePassword)) {
			paraMap.put("trade_password", tradePassword);
		}
		if (null != tradeid) {
			paraMap.put("trade_id", tradeid);
		}
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 撤销订单
	 * 
	 * @param coinType
	 * @param id
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String cancelOrder(EHuobiCoinType coinType, long id) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", CANCEL_ORDER);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("id", id);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 获取账号详情
	 * 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String getAccountInfo() throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", ACCOUNT_INFO);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 查询个人最新10条成交订单
	 * 
	 * @param coinType
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String getNewDealOrders(EHuobiCoinType coinType) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", NEW_DEAL_ORDERS);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 根据trade_id查询oder_id
	 * 
	 * @param coinType
	 * @param tradeid
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String getOrderIdByTradeId(EHuobiCoinType coinType, long tradeid) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", ORDER_ID_BY_TRADE_ID);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("trade_id", tradeid);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 获取所有正在进行的委托
	 * 
	 * @param coinType
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String getOrders(EHuobiCoinType coinType) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", GET_ORDERS);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 获取订单详情
	 * 
	 * @param coinType
	 * @param id
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String getOrderInfo(EHuobiCoinType coinType, long id) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", ORDER_INFO);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("id", id);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 限价卖出
	 * 
	 * @param coinType
	 * @param price
	 * @param amount
	 * @param tradePassword
	 * @param tradeid
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String sell(EHuobiCoinType coinType, String price, String amount, String tradePassword, Integer tradeid)
			throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", SELL);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("price", price);
		paraMap.put("amount", amount);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(tradePassword)) {
			paraMap.put("trade_password", tradePassword);
		}
		if (null != tradeid) {
			paraMap.put("trade_id", tradeid);
		}
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 市价卖出
	 * 
	 * @param coinType
	 * @param amount
	 * @param tradePassword
	 * @param tradeid
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public String sellMarket(EHuobiCoinType coinType, String amount, String tradePassword, Integer tradeid) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", SELL_MARKET);
		paraMap.put("created", getTimestamp());
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("amount", amount);
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(tradePassword)) {
			paraMap.put("trade_password", tradePassword);
		}
		if (null != tradeid) {
			paraMap.put("trade_id", tradeid);
		}
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 提币BTC/LTC
	 * 
	 * @param coinType
	 * @param withdrawAddr
	 * @param withdrawAmount
	 * @param tradePwd
	 * @param market
	 * @param withdrawFee
	 * @return
	 * @throws Exception
	 */
	public String withdrawCoin(EHuobiCoinType coinType, String withdrawAddr, float withdrawAmount,String tradePwd,  String market,float withdrawFee)
			throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", WITHDRAW_COIN);
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("coin_type", coinType.getValue());
		paraMap.put("created", getTimestamp());
		paraMap.put("withdraw_address", withdrawAddr);
		paraMap.put("withdraw_amount", getWithdrawAmount(coinType, withdrawAmount));
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(tradePwd)) {
			paraMap.put("trade_password", tradePwd);
		}
		if (StringUtils.isNotEmpty(market)) {
			paraMap.put("market", market);
		}
		if(withdrawFee > 0){
			paraMap.put("withdraw_fee", getWithdrawFee(coinType, withdrawFee));
		}
		return post(paraMap, HUOBI_API_URL);
	}

	/**
	 * 获取提币数量
	 * @param coinType
	 * @param withdrawAmount
	 * @return
	 */
	private float getWithdrawAmount(EHuobiCoinType coinType, float withdrawAmount) {
		if (coinType == EHuobiCoinType.BTC) {
			withdrawAmount = DecimalUtil.decimalDown(withdrawAmount, 2);
		} else if (coinType == EHuobiCoinType.LTC) {
			withdrawAmount = DecimalUtil.decimalDown(withdrawAmount, 1);
		}
		return withdrawAmount;
	}
	
	/**
	 * 获取网络转账手续费
	 * @param coinType
	 * @param withdrawFee
	 * @return
	 */
	private float getWithdrawFee(EHuobiCoinType coinType, float withdrawFee) {
		if(withdrawFee <= 0){
			return 0;
		}
		if (coinType == EHuobiCoinType.BTC) {
			if(withdrawFee > 0.01){
				withdrawFee = 0.01f;
			}else if(withdrawFee < 0.0001){
				withdrawFee = 0.0001f;
			}
		} else if (coinType == EHuobiCoinType.LTC) {
			withdrawFee = 0.001f;
		}else{
			withdrawFee = 0;
		}
		return withdrawFee;
	}

	/**
	 * 查询可申请杠杆额度
	 * 
	 * @param market
	 * @return
	 * @throws Exception
	 */
	public String getLoanAvailable(String market) throws Exception {
		TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
		paraMap.put("method", LOAN_AVAILABLE);
		paraMap.put("access_key", accessKeyId);
		paraMap.put("secret_key", accessKeySecret);
		paraMap.put("created", getTimestamp());
		String md5 = sign(paraMap);
		paraMap.remove("secret_key");
		paraMap.put("sign", md5);
		if (StringUtils.isNotEmpty(market)) {
			paraMap.put("market", market);
		}
		return post(paraMap, HUOBI_API_URL);
	}
	
	/**
	 * 实时行情图
	 * @param coinType
	 * @return
	 */
	public ActualTickerBean getActualTickerInfo(EHuobiCoinType coinType){
		String url = null;
		if(coinType == EHuobiCoinType.BTC){
			url = MARKET_BTC_URL;
		}else if(coinType == EHuobiCoinType.LTC){
			url = MARKET_LTC_URL;
		}
		if(url == null){
			return null;
		}
		ActualTickerBean result = null;
		Response response = Http.get(url);
		String content = response.getContent();
		if(Strings.isNotBlank(content)){
			result = Json.fromJson(ActualTickerBean.class, content);
		}
		return result;
	}

}
