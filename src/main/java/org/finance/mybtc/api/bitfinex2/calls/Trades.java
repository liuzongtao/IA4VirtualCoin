package org.finance.mybtc.api.bitfinex2.calls;

import java.io.Serializable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.finance.mybtc.api.bitfinex2.Config;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.api.bitfinex2.util.CallUtils;

import com.google.gson.reflect.TypeToken;

public class Trades extends HttpGet implements BitfinexCall {

	public Trades(EBitfinexSymbols symbols) {
		super(Config.baseUrl+"/trades/"+symbols);
	}

	public Trades(EBitfinexSymbols symbols, long timestamp) {
		super(Config.baseUrl+"/trades/"+symbols+"?timestamp="+timestamp);
	}

	public Trades(EBitfinexSymbols symbols, int count) {
		super(Config.baseUrl+"/trades/"+symbols+"?limit_trades="+count);
	}

	public Trades(EBitfinexSymbols symbols, long timestamp, int count) {
		super(Config.baseUrl+"/trades/"+symbols+"?timestamp="+timestamp+"&limit_trades="+count);
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, new TypeToken<List<TradesResponse>>() {}.getType());
	}
	
	public static class TradesResponse implements Serializable {

		private static final long serialVersionUID = -4037190389844866185L;
		
		private int tid;
		private long timestamp;
		private double price;
		private double amount;
		private String exchange;
		private String type;
		
		@Override
		public String toString() {
			return "TradesResponse [tid=" + tid + ", timestamp=" + timestamp + ", price=" + price + ", amount=" + amount
					+ ", exchange=" + exchange + ", type=" + type + "]";
		}
		
		public int getTid() {
			return tid;
		}
		public void setTid(int tid) {
			this.tid = tid;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getExchange() {
			return exchange;
		}
		public void setExchange(String exchange) {
			this.exchange = exchange;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		
	}
	
}
