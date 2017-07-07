/**
 * 
 */
package org.finance.mybtc.api.bitfinex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.finance.mybtc.api.bitfinex.beans.BitfinexAccountFees;
import org.finance.mybtc.api.bitfinex.beans.BitfinexAccountInfo;
import org.finance.mybtc.api.bitfinex.beans.BitfinexBalance;
import org.finance.mybtc.api.bitfinex.beans.BitfinexDepositRes;
import org.finance.mybtc.api.bitfinex.beans.BitfinexKeyInfo;
import org.finance.mybtc.api.bitfinex.beans.BitfinexMultiOrdersRes;
import org.finance.mybtc.api.bitfinex.beans.BitfinexOrderRes;
import org.finance.mybtc.api.bitfinex.beans.BitfinexSummary;
import org.finance.mybtc.api.bitfinex.beans.BitfinexSymbolDetail;
import org.finance.mybtc.api.bitfinex.beans.BitfinexTicker;
import org.finance.mybtc.api.bitfinex.beans.BitfinexWithdrawRes;
import org.finance.mybtc.utils.HttpUtil;
import org.nutz.http.Header;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.gson.Gson;

/**
 * @author zongtao liu
 *
 */
/**
 * @author zongtao liu
 *
 */
public class BitfinexClient {

	private static Log log = Logs.get();

	private static final String ALGORITHM_HMACSHA384 = "HmacSHA384";

	private String apiKey = "";
	private String apiKeySecret = "";

	/**
	 * 
	 */
	public BitfinexClient() {
	}

	/**
	 * @param apiKey
	 * @param apiKeySecret
	 */
	public BitfinexClient(String apiKey, String apiKeySecret) {
		this.apiKey = apiKey;
		this.apiKeySecret = apiKeySecret;
	}

	/**
	 * The ticker is a high level overview of the state of the market. It shows
	 * you the current best bid and ask, as well as the last trade price. It
	 * also includes information such as daily volume and how much the price has
	 * moved over the last day
	 * 
	 * @param symbol
	 * @return
	 */
	public BitfinexTicker getTicker(String symbol) {
		if (Strings.isBlank(symbol)) {
			return null;
		}
		BitfinexTicker ticker = null;
		String url = String.format(BitfinexConst.URL_TICKER, symbol);
		Response response = HttpUtil.get(url);
		if (response != null && response.isOK()) {
			ticker = Json.fromJson(BitfinexTicker.class, response.getContent());
		}
		return ticker;
	}

	public List<String> getSymbols() {
		List<String> list = null;
		Response response = HttpUtil.get(BitfinexConst.URL_SYMBOLS);
		if (response != null && response.isOK()) {
			list = Json.fromJsonAsList(String.class, response.getContent());
		}
		return list;
	}

	/**
	 * Get a list of valid symbol IDs and the pair details.
	 * 
	 * @return
	 */
	public List<BitfinexSymbolDetail> getSymbolDetails() {
		List<BitfinexSymbolDetail> list = null;
		Response response = HttpUtil.get(BitfinexConst.URL_SYMBOL_DETAILS);
		if (response != null && response.isOK()) {
			list = Json.fromJsonAsList(BitfinexSymbolDetail.class, response.getContent());
		}
		return list;
	}

	public List<BitfinexAccountInfo> getAccountInfo() {
		return getObjList(BitfinexConst.URL_ACCOUNT_INFO, BitfinexAccountInfo.class,null);
	}
	
	/**
	 * See your balances
	 * @return
	 */
	public List<BitfinexBalance> getBalances() {
		return getObjList(BitfinexConst.URL_BALANCES, BitfinexBalance.class,null);
	}
	
	public BitfinexAccountFees getAccountFees() {
		return getObj(BitfinexConst.URL_ACCOUNT_FEES, BitfinexAccountFees.class,null);
	}
	
	public BitfinexSummary getSummary(){
		return getObj(BitfinexConst.URL_SUMMARY, BitfinexSummary.class,null);
	}
	
	public BitfinexDepositRes getDepositInfo(String method,String wallet){
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", method);
		params.put("wallet_name", wallet);
		return getObj(BitfinexConst.URL_DEPOSIT, BitfinexDepositRes.class,params);
	}
	
	public Map<String, BitfinexKeyInfo> getKeyInfoMap(){
		return getObjMap(BitfinexConst.URL_KEY_INFO, BitfinexKeyInfo.class, null);
	}
	
	
	/**
	 * Allow you to request a withdrawal from one of your wallet.
	 * @param withdrawType
	 * can be one of the following ['bitcoin', 'litecoin', 'ethereum', 'ethereumc', 'mastercoin', 'zcash', 'monero', 'wire', 'dash', 'ripple']
	 * @param wallet
	 * The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
	 * @param amount
	 * Amount to withdraw.
	 * @param address Destination address for withdrawal.
	 * @return
	 */
	public BitfinexWithdrawRes withdraw(String withdrawType,String wallet,float amount,String address){
		Map<String, String> params = new HashMap<String, String>();
		params.put("withdraw_type", withdrawType);
		params.put("walletselected", wallet);
		params.put("amount", String.valueOf(amount));
		params.put("address", address);
		List<BitfinexWithdrawRes> objList = getObjList(BitfinexConst.URL_WITHDRAW, BitfinexWithdrawRes.class,params);
		BitfinexWithdrawRes res = null;
		if(objList != null && objList.size() > 0){
			res = objList.get(0);
		}
		return res;
	}
	
	public BitfinexOrderRes orderNew(String symbol,float amount,float price,String side,String type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", "btcusd");
		params.put("amount", String.valueOf(amount));
		params.put("price", String.valueOf(price));
		params.put("side", side);
		params.put("type", type);
		params.put("exchange", "bitfinex");
		params.put("isHidden", String.valueOf(false));
//		params.put("is_postonly", String.valueOf(false));
//		params.put("use_all_available", 0);
//		params.put("ocoorder", String.valueOf(false));
//		params.put("buy_price_oco", 0);
//		params.put("sell_price_oco", 0);
		return getObj(BitfinexConst.URL_ORDER_NEW, BitfinexOrderRes.class, params);
	}
	
	public BitfinexMultiOrdersRes ordersMulti(String symbol,float amount,float price,String side,String type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", "BTCUSD");
		params.put("amount", String.valueOf(amount));
		params.put("price", String.valueOf(price));
		params.put("side", side);
		params.put("type", type);
		params.put("exchange", "bitfinex");
		return getObj(BitfinexConst.URL_ORDER_MULTI, BitfinexMultiOrdersRes.class, params);
	}
	
	
	
	private <T> List<T> getObjList(String url,Class<T> clazz,Map<String, String> params) {
		List<T> list = null;
		String urlPath = url.replace(BitfinexConst.URL_API_BASE, "");
		Response response = HttpUtil.httpsPost(url, getNewParams(params), createHeader(urlPath,params));
		if(response == null){
			log.error("response is null !");
			return null;
		}
		if (response.isOK()) {
			list = Json.fromJsonAsList(clazz, response.getContent());
		}else{
			log.error(response.getStatus() + " : " + response.getDetail());
		}
		return list;
	}
	
	private <T> T getObj(String url,Class<T> clazz,Map<String, String> params){
		T obj = null;
		String urlPath = url.replace(BitfinexConst.URL_API_BASE, "");
		Response response = HttpUtil.httpsPost(url, getNewParams(params), createHeader(urlPath,params));
		if(response == null){
			log.error("response is null !");
			return null;
		}
		if (response.isOK()) {
			obj = Json.fromJson(clazz, response.getContent());
		}else{
			log.error(response.getStatus() + " : " + response.getDetail());
		}
		return obj;
	}
	
	private <T> Map<String,T> getObjMap(String url,Class<T> clazz,Map<String, String> params){
		Map<String,T> objMap = null;
		String urlPath = url.replace(BitfinexConst.URL_API_BASE, "");
		Response response = HttpUtil.httpsPost(url, getNewParams(params), createHeader(urlPath,params));
		if(response == null){
			log.error("response is null !");
			return null;
		}
		if (response.isOK()) {
			objMap = Json.fromJsonAsMap(clazz, response.getContent());
		}else{
			log.error(response.getStatus() + " : " + response.getDetail());
		}
		return objMap;
	}
	
	private Map<String, Object> getNewParams(Map<String, String> params){
		Map<String, Object> newParams = new HashMap<String, Object>();
		for(Entry<String, String> entry : params.entrySet()){
			newParams.put(entry.getKey(), entry.getValue());
		}
		return newParams;
	}

	private Header createHeader(String urlPath,Map<String, String> params) {
		Header header = Header.create();
		try {
//			Map<String, String> headerMap = new HashMap<String, String>();
//			headerMap.put("Content-Typ", "application/json");
//			headerMap.put("Accept", "application/json");
			Map<String, String> headerMap = new HashMap<String, String>();
			if (Strings.isNotBlank(apiKey) && Strings.isNotBlank(apiKeySecret)) {
				
				createBaseHear(urlPath, headerMap);
				if(params!= null && params.size() > 0){
					headerMap.putAll(params);
					createBaseHear(urlPath, headerMap);
				}
				System.out.println("2===" + new Gson().toJson(headerMap));
//				System.out.println("2===" + new Gson().toJson(headerMap, JsonFormat.compact()));
			}
			
			params.put("Content-Typ", "application/json");
			params.put("Accept", "application/json");
			
			header.addAll(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return header;
	}
	
	private void createBaseHear(String urlPath,Map<String, String> params){
		try {
			params.put("request", urlPath);
			params.put("nonce", "1499417695350");
//			params.put("nonce", String.valueOf(System.currentTimeMillis()));
			String jsonEncode = Json.toJson(params, JsonFormat.compact());
			System.out.println("1===" + jsonEncode);
			byte[] payload = new Base64().encode(jsonEncode.getBytes("UTF-8"));
//			String payload = Base64.encodeToString(jsonEncode.getBytes("UTF-8"), false);
//			String signature = hmacDigest(payload, apiKeySecret, ALGORITHM_HMACSHA384);
			String signature = hmacDigest(payload, apiKeySecret, ALGORITHM_HMACSHA384);
			params.put("X-BFX-APIKEY", apiKey);
			params.put("X-BFX-PAYLOAD", new String(payload));
			params.put("X-BFX-SIGNATURE", signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String hmacDigest(byte[] msg, String keyString, String algo) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg);
//			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (UnsupportedEncodingException e) {
			log.error("Exception: " + e.getMessage());
		} catch (InvalidKeyException e) {
			log.error("Exception: " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("Exception: " + e.getMessage());
		}
		return digest;
	}

}
