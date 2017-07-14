package org.finance.mybtc.api.bitfinex2;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.finance.mybtc.api.bitfinex2.CandlestickMapReduce.CandleStick;
import org.finance.mybtc.api.bitfinex2.calls.BitfinexCall;
import org.finance.mybtc.api.bitfinex2.calls.Lendbook;
import org.finance.mybtc.api.bitfinex2.calls.Lendbook.LendbookResponse;
import org.finance.mybtc.api.bitfinex2.calls.Lends;
import org.finance.mybtc.api.bitfinex2.calls.Lends.LendsResponse;
import org.finance.mybtc.api.bitfinex2.calls.Pubticker;
import org.finance.mybtc.api.bitfinex2.calls.Pubticker.TickerResponse;
import org.finance.mybtc.api.bitfinex2.calls.Stats;
import org.finance.mybtc.api.bitfinex2.calls.Stats.StatsResponse;
import org.finance.mybtc.api.bitfinex2.calls.Trades;
import org.finance.mybtc.api.bitfinex2.calls.Trades.TradesResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Balances;
import org.finance.mybtc.api.bitfinex2.calls.auth.Balances.BalancesResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.MarginInfos;
import org.finance.mybtc.api.bitfinex2.calls.auth.MarginInfos.MarginInfosResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.NewOrder;
import org.finance.mybtc.api.bitfinex2.calls.auth.NewOrder.NewOrderResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.OrderStatus;
import org.finance.mybtc.api.bitfinex2.calls.auth.OrderStatus.OrderStatusResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Withdraw;
import org.finance.mybtc.api.bitfinex2.calls.auth.Withdraw.WithdrawResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.core.config.Configs;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class Bitfinex {
	
	private static Log log = Logs.get();

	public final HttpClient client;

	public static String apiKey;
	public static String apiSecret;

	public Bitfinex() {
		client = HttpClients.createDefault();
	}

	public Bitfinex(String apiKey, String apiSecret) {
		this.client = HttpClients.createDefault();
		Bitfinex.apiKey = apiKey;
		Bitfinex.apiSecret = apiSecret;
	}

	/* MID LEVEL FUNCTIONS */

	public void ticker(EBitfinexSymbols symbols, long refreshRate, Consumer<TradesResponse> tradeconsumer) {
		new Thread(new Ticker(this, symbols, refreshRate, tradeconsumer)).start();
	}

	public void candleTicker(EBitfinexSymbols symbols, long refreshRate, Timeframe timeframe,
			Consumer<CandleStick> candleConsumer) {
		new Thread(new Ticker(this, symbols, refreshRate, new CandlestickTicker(timeframe, candleConsumer))).start();
	}

	public void heikenAshiTicker(EBitfinexSymbols symbols, long refreshRate, Timeframe timeframe,
			Consumer<CandleStick> candleconsumer) {
		new Thread(new Ticker(this, symbols, refreshRate,
				new CandlestickTicker(timeframe, new HACandleTicker(candleconsumer)))).start();
	}

	public void historyTicker(Consumer<TradesResponse> tradeconsumer) {
		new HistoryTicker(tradeconsumer);
	}

	public void historyCandleTicker(Timeframe timeframe, Consumer<CandleStick> candleConsumer) {
		new HistoryTicker(new CandlestickTicker(timeframe, candleConsumer));
	}

	public void historyHeikenAshiTicker(Timeframe timeframe, Consumer<CandleStick> candleconsumer) {
		new HistoryTicker(new CandlestickTicker(timeframe, new HACandleTicker(candleconsumer)));
	}

	public List<CandleStick> getCandlesticks(EBitfinexSymbols symbol, Timeframe timeframe) throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol));
	}

	public List<CandleStick> getCandlesticks(EBitfinexSymbols symbol, Timeframe timeframe, int count)
			throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, count));
	}

	public List<CandleStick> getCandlesticks(EBitfinexSymbols symbol, Timeframe timeframe, long timestamp)
			throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, timestamp));
	}

	public List<CandleStick> getCandlesticks(EBitfinexSymbols symbol, Timeframe timeframe, int count, long timestamp)
			throws BitfinexCallException {
		return CandlestickMapReduce.mapReduce(timeframe, trades(symbol, count, timestamp));
	}

	public double getSentiment(EBitfinexSymbols symbol) throws BitfinexCallException {
		double amount1 = lends(symbol.getCurrency1()).get(0).getAmount_used();
		double amount2 = lends(symbol.getCurrency2()).get(0).getAmount_used();
		double spot = trades(symbol).get(0).getPrice();
		amount1 = amount1 * spot;
		return amount2 / (amount1 + amount2) * 100;
	}

	/* LOW LEVEL FUNCTIONS */

	public TickerResponse pubticker(EBitfinexSymbols symbol) throws BitfinexCallException {
		return (TickerResponse) call(new Pubticker(symbol));
	}

	public StatsResponse stats(EBitfinexSymbols symbol) throws BitfinexCallException {
		return (StatsResponse) call(new Stats(symbol));
	}

	@SuppressWarnings("unchecked")
	public List<BalancesResponse> getBalances() throws BitfinexCallException {
		return (List<BalancesResponse>) call(new Balances());
	}

	@SuppressWarnings("unchecked")
	public WithdrawResponse withdraw(String withdrawType, String wallet, float amount, String address)
			throws BitfinexCallException {
		WithdrawResponse res = null;
		List<WithdrawResponse> list = (List<WithdrawResponse>) call(
				new Withdraw(withdrawType, wallet, amount, address));
		if (list != null && list.size() > 0) {
			res = list.get(0);
		}
		return res;
	}

	public LendbookResponse lendbook(EBitfinexCurrencies currency) throws BitfinexCallException {
		return (LendbookResponse) call(new Lendbook(currency));
	}

	public LendbookResponse lendbook(EBitfinexCurrencies currency, int limit_bids, int limit_asks)
			throws BitfinexCallException {
		return (LendbookResponse) call(new Lendbook(currency, limit_bids, limit_asks));
	}

	public MarginInfosResponse marginInfos() throws BitfinexCallException {
		return (MarginInfosResponse) call(new MarginInfos());
	}

	public NewOrderResponse marginBuyMarket(EBitfinexSymbols symbol, double amount, double price)
			throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.BUY, Types.MARKET);
	}

	public NewOrderResponse marginSellMarket(EBitfinexSymbols symbol, double amount, double price)
			throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.SELL, Types.MARKET);
	}
	
	public NewOrderResponse exchangeBuyMarket(EBitfinexSymbols symbol, double amount, double price)
			throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.BUY, Types.EXCHANGE_MARKET);
	}

	public NewOrderResponse exchangeSellMarket(EBitfinexSymbols symbol, double amount, double price)
			throws BitfinexCallException {
		return newOrder(symbol, amount, price, Sides.SELL, Types.EXCHANGE_MARKET);
	}

	public NewOrderResponse newOrder(EBitfinexSymbols symbol, double amount, double price, Sides side, Types type)
			throws BitfinexCallException {
		return newOrder(symbol, amount, price, side, type, false);
	}

	public NewOrderResponse newOrder(EBitfinexSymbols symbol, double amount, double price, Sides side, Types type,
			boolean isHidden) throws BitfinexCallException {
		return (NewOrderResponse) call(new NewOrder(symbol, amount, price, side, type, isHidden));
	}

	public OrderStatusResponse orderStatus(int orderID) throws BitfinexCallException {
		return (OrderStatusResponse) call(new OrderStatus(orderID));
	}

	@SuppressWarnings("unchecked")
	public List<LendsResponse> lends(EBitfinexCurrencies currency) throws BitfinexCallException {
		return (List<LendsResponse>) call(new Lends(currency));
	}

	@SuppressWarnings("unchecked")
	public List<LendsResponse> lends(EBitfinexCurrencies currency, Date date, int count) throws BitfinexCallException {
		return (List<LendsResponse>) call(new Lends(currency, date, count));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(EBitfinexSymbols symbol) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(EBitfinexSymbols symbol, int count) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, count));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(EBitfinexSymbols symbol, long timestamp) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, timestamp));
	}

	@SuppressWarnings("unchecked")
	public List<TradesResponse> trades(EBitfinexSymbols symbol, int count, long timestamp) throws BitfinexCallException {
		return (List<TradesResponse>) call(new Trades(symbol, timestamp, count));
	}

	private Object call(BitfinexCall call) throws BitfinexCallException {
		int times = 10;
		Object parseResponse = null;
		Exception e = null;
		boolean isSuccess = false;
		for(int i = 0 ; i< times ; i++){
			try {
				parseResponse = call.parseResponse(client.execute((HttpUriRequest) call));
				isSuccess = true;
				break;
			} catch (Exception tmpException) {
				log.error(tmpException.getMessage());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					log.error(e1.getMessage());
				}
				e = tmpException;
			} 
		}
		if(!isSuccess){
			throw new BitfinexCallException("Could not send call using: " + call, e);
		}
		return parseResponse;
	}
	
	public static void main(String[] args) {
		Bitfinex bitfinex = new Bitfinex(Configs.API_CONFIG_BITFINEX.getApiKey(),Configs.API_CONFIG_BITFINEX.getApiSecret());
		try {
			List<TradesResponse> trades = bitfinex.trades(EBitfinexSymbols.LTCBTC, 10);
			System.out.println(Json.toJson(trades));
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
	}

}
