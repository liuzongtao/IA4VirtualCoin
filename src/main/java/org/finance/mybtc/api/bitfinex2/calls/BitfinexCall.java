package org.finance.mybtc.api.bitfinex2.calls;

import org.apache.http.HttpResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;

public interface BitfinexCall {
	
	public Object parseResponse(HttpResponse response) throws BitfinexCallException;

}
