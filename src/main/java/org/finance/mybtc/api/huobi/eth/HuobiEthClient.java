package org.finance.mybtc.api.huobi.eth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.api.huobi.eth.request.CreateOrderRequest;
import org.finance.mybtc.api.huobi.eth.request.CreateWithdrawRequest;
import org.finance.mybtc.api.huobi.eth.response.Account;
import org.finance.mybtc.api.huobi.eth.response.Address;
import org.finance.mybtc.api.huobi.eth.response.ApiResponse;
import org.finance.mybtc.api.huobi.eth.response.Balance;
import org.finance.mybtc.api.huobi.eth.response.Symbol;
import org.finance.mybtc.api.huobi.eth.response.Tick;
import org.finance.mybtc.utils.JsonUtil;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.fasterxml.jackson.core.type.TypeReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * API client.
 * 
 * @author liaoxuefeng
 */
public class HuobiEthClient {

	private static Log log = Logs.get();

	static final int CONN_TIMEOUT = 5;
	static final int READ_TIMEOUT = 5;
	static final int WRITE_TIMEOUT = 5;

	static final String API_HOST = "be.huobi.com";

	static final String API_URL = "https://" + API_HOST;
	static final MediaType JSON = MediaType.parse("application/json");
	static final OkHttpClient client = createOkHttpClient();

	final String accessKeyId;
	final String accessKeySecret;
	final String assetPassword;

	/**
	 * 创建一个ApiClient实例
	 * 
	 * @param accessKeyId
	 *            AccessKeyId
	 * @param accessKeySecret
	 *            AccessKeySecret
	 */
	public HuobiEthClient(String accessKeyId, String accessKeySecret) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.assetPassword = null;
	}

	/**
	 * 创建一个ApiClient实例
	 * 
	 * @param accessKeyId
	 *            AccessKeyId
	 * @param accessKeySecret
	 *            AccessKeySecret
	 * @param assetPassword
	 *            AssetPassword
	 */
	public HuobiEthClient(String accessKeyId, String accessKeySecret, String assetPassword) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.assetPassword = assetPassword;
	}

	public Tick getKline(String symbol, String period) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", symbol);
		params.put("period", period);
		ApiResponse<Tick> resp = get("/market/kline", params, new TypeReference<ApiResponse<Tick>>() {
		});
		return resp.checkAndReturnTick();
	}

	public Tick getKline4Depth(String symbol, String type) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", symbol);
		params.put("type", type);
		ApiResponse<Tick> resp = get("/market/depth", params, new TypeReference<ApiResponse<Tick>>() {
		});
		return resp.checkAndReturnTick();
	}

	/**
	 * 查询交易对
	 * 
	 * @return List of symbols.
	 */
	public List<Symbol> getSymbols() {
		ApiResponse<List<Symbol>> resp = get("/v1/common/symbols", null,
				new TypeReference<ApiResponse<List<Symbol>>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 查询所有账户信息
	 * 
	 * @return List of accounts.
	 */
	public List<Account> getAccounts() {
		ApiResponse<List<Account>> resp = get("/v1/account/accounts", null,
				new TypeReference<ApiResponse<List<Account>>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 限价 卖
	 * 
	 * @param accountId
	 * @param sellNum
	 * @param price
	 * @return
	 */
	public String sellLimit(String accountId, String sellNum, String price) {
		return buyOrSell(accountId, sellNum, price, CreateOrderRequest.OrderType.SELL_LIMIT);
	}

	/**
	 * 限价 买
	 * 
	 * @param accountId
	 * @param buyNum
	 * @param price
	 * @return
	 */
	public String buyLimit(String accountId, String buyNum, String price) {
		return buyOrSell(accountId, buyNum, price, CreateOrderRequest.OrderType.BUY_LIMIT);
	}

	/**
	 * 市价卖
	 * 
	 * @param accountId
	 * @param sellNum
	 * @return
	 */
	public String sellMarket(String accountId, String sellNum) {
		return buyOrSell(accountId, sellNum, null, CreateOrderRequest.OrderType.SELL_MARKET);
	}

	/**
	 * 市价买
	 * 
	 * @param accountId
	 * @param buyNum
	 * @return
	 */
	public String buyMarket(String accountId, String buyNum) {
		return buyOrSell(accountId, buyNum, null, CreateOrderRequest.OrderType.BUY_MARKET);
	}

	/**
	 * 买卖操作
	 * 
	 * @param accountId
	 * @param num
	 * @param price
	 * @param type
	 * @return
	 */
	private String buyOrSell(String accountId, String num, String price, String type) {
		// create order:
		CreateOrderRequest createOrderReq = new CreateOrderRequest();
		createOrderReq.accountId = accountId;
		createOrderReq.amount = num;
		if (Strings.isNotBlank(price)) {
			createOrderReq.price = price;
		}
		createOrderReq.symbol = EHuobiSymbol.ETH.getValue();
		createOrderReq.type = type;
		Long orderId = createOrder(createOrderReq);
		// place order:
		String r = placeOrder(orderId);
		return r;
	}

	/**
	 * 创建订单（未执行)
	 * 
	 * @param request
	 *            CreateOrderRequest object.
	 * @return Order id.
	 */
	private Long createOrder(CreateOrderRequest request) {
		ApiResponse<Long> resp = post("/v1/order/orders", request, new TypeReference<ApiResponse<Long>>() {
		});
		return resp.checkAndReturn();
	}

	/**
	 * 执行订单
	 * 
	 * @param orderId
	 *            The id of created order.
	 * @return Order id.
	 */
	private String placeOrder(long orderId) {
		ApiResponse<String> resp = post("/v1/order/orders/" + orderId + "/place", null,
				new TypeReference<ApiResponse<String>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 查询当前用户的所有账户 余额信息
	 * 
	 * @param orderId
	 * @return
	 */
	public Balance getAccountBalance(long accountId) {
		ApiResponse<Balance> resp = post("/v1/account/accounts/" + accountId + "/balance", null,
				new TypeReference<ApiResponse<Balance>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 虚拟币提现
	 * 
	 * @param currency
	 * @param addressStr
	 * @param amount
	 * @return
	 */
	public long withdraw(String currency, String addressStr, String amount) {
		List<Address> addressList = getWithdrawAddress(currency);
		long addressId = 0;
		for (Address tmpAddress : addressList) {
			if (Strings.equals(tmpAddress.getAddress(), addressStr)) {
				addressId = tmpAddress.getId();
				break;
			}
		}
		if (addressId == 0) {
			return 0;
		}
		long withdrawId = createWithdraw(addressId, amount);
		if (withdrawId == 0) {
			return withdrawId;
		}
		return placeWithdraw(withdrawId);
	}

	/**
	 * 查询虚拟币提现地址
	 * 
	 * @param currency
	 * @return
	 */
	private List<Address> getWithdrawAddress(String currency) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("currency", currency);
		ApiResponse<List<Address>> resp = get("/v1/dw/withdraw-virtual/addresses", params,
				new TypeReference<ApiResponse<List<Address>>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 申请提现虚拟币
	 * 
	 * @param addressId
	 * @param amount
	 * @return
	 */
	private long createWithdraw(long addressId, String amount) {
		CreateWithdrawRequest request = new CreateWithdrawRequest();
		request.setAddressId(addressId);
		request.setAmount(amount);
		ApiResponse<Long> resp = post("/v1/dw/withdraw-virtual/create", request,
				new TypeReference<ApiResponse<Long>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 确认申请虚拟币提现
	 * 
	 * @param withdrawId
	 * @return
	 */
	private long placeWithdraw(long withdrawId) {
		ApiResponse<Long> resp = post("/v1/dw/withdraw-virtual/" + withdrawId + "/place", null,
				new TypeReference<ApiResponse<Long>>() {
				});
		return resp.checkAndReturn();
	}

	// send a GET request.
	<T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
		if (params == null) {
			params = new HashMap<>();
		}
		T instance = null;
		for (int i = 0; i < 10; i++) {
			try {
				instance = call("GET", uri, null, params, ref);
				break;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return instance;
	}

	// send a POST request.
	<T> T post(String uri, Object object, TypeReference<T> ref) {
		T instance = null;
		for (int i = 0; i < 10; i++) {
			try {
				instance = call("POST", uri, object, new HashMap<String, String>(), ref);
				break;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return instance;
	}

	// call api by endpoint.
	<T> T call(String method, String uri, Object object, Map<String, String> params, TypeReference<T> ref) {
		ApiSignature sign = new ApiSignature();
		sign.createSignature(this.accessKeyId, this.accessKeySecret, method, API_HOST, uri, params);
		try {
			Request.Builder builder = null;
			if ("POST".equals(method)) {
				RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
				builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).post(body);
			} else {
				builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).get();
			}
			if (this.assetPassword != null) {
				builder.addHeader("AuthData", authData());
			}
			Request request = builder.build();
			Response response = client.newCall(request).execute();
			String s = response.body().string();
			return JsonUtil.readValue(s, ref);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	String authData() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(this.assetPassword.getBytes(StandardCharsets.UTF_8));
		md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
		Map<String, String> map = new HashMap<>();
		map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
		try {
			return ApiSignature.urlEncode(JsonUtil.writeValue(map));
		} catch (IOException e) {
			throw new RuntimeException("Get json failed: " + e.getMessage());
		}
	}

	// Encode as "a=1&b=%20&c=&d=AAA"
	String toQueryString(Map<String, String> params) {
		return String.join("&", params.entrySet().stream().map((entry) -> {
			return entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue());
		}).collect(Collectors.toList()));
	}

	// create OkHttpClient:
	static OkHttpClient createOkHttpClient() {
		return new Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();
	}

}
