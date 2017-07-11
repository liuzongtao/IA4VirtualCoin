package org.finance.mybtc.api.bitfinex2.calls;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.finance.mybtc.api.bitfinex2.Config;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.api.bitfinex2.util.CallUtils;

public class Stats extends HttpGet implements BitfinexCall {

	public Stats(EBitfinexSymbols symbol) {
		super(Config.baseUrl+"/stats/"+symbol);
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, StatsResponse.class);
	}
	
	public static class StatsResponse extends ArrayList<Stat> {

		private static final long serialVersionUID = -6541091815381106988L;
		
	}
	
	public static class Stat implements Serializable {
		
		private static final long serialVersionUID = -4762474386056714327L;
		
		private int period;
		private double volume;
		
		@Override
		public String toString() {
			return "Stat [period=" + period + ", volume=" + volume + "]";
		}
		
		public int getPeriod() {
			return period;
		}
		public void setPeriod(int period) {
			this.period = period;
		}
		public double getVolume() {
			return volume;
		}
		public void setVolume(double volume) {
			this.volume = volume;
		}
		
	}

}
