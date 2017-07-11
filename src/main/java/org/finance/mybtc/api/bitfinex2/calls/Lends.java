package org.finance.mybtc.api.bitfinex2.calls;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.finance.mybtc.api.bitfinex2.Config;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.api.bitfinex2.util.CallUtils;

import com.google.gson.reflect.TypeToken;

public class Lends extends HttpGet implements BitfinexCall {

	public Lends(EBitfinexCurrencies currency) {
		super(Config.baseUrl+"/lends/"+currency);
	}
	
	public Lends(EBitfinexCurrencies currency, Date timestamp, int limit_lends) throws BitfinexCallException {
		super(getUri(currency, timestamp.getTime()/1000, limit_lends));
	}
	
	private static URI getUri(EBitfinexCurrencies currency, long unix_timestamp, int limit_lends) throws BitfinexCallException {
		try {
			return CallUtils.createUriBuilder("/lends/"+currency).addParameter("timestamp", unix_timestamp+"").addParameter("limit_lends", limit_lends+"").build();
		} catch (URISyntaxException e) {
			throw new BitfinexCallException("Could not create the uri", e);
		}
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, new TypeToken<List<LendsResponse>>() {}.getType());
	}
	
	public static class LendsResponse implements Serializable {

		private static final long serialVersionUID = -2864654317898673726L;

		double rate;
		double amount_lent;
		double amount_used;
		long timestamp;
		
		@Override
		public String toString() {
			return "LendsResponse [rate=" + rate + ", amount_lent=" + amount_lent + ", amount_used=" + amount_used
					+ ", timestamp=" + timestamp + "]";
		}
		
		public double getRate() {
			return rate;
		}
		public void setRate(double rate) {
			this.rate = rate;
		}
		
		public double getAmount_lent() {
			return amount_lent;
		}
		public void setAmount_lent(double amount_lent) {
			this.amount_lent = amount_lent;
		}
		
		public double getAmount_used() {
			return amount_used;
		}
		public void setAmount_used(double amount_used) {
			this.amount_used = amount_used;
		}

		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		
	}
	
}
