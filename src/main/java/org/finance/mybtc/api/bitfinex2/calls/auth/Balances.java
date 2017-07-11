package org.finance.mybtc.api.bitfinex2.calls.auth;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.api.bitfinex2.util.CallUtils;

import com.google.gson.reflect.TypeToken;

public class Balances extends HttpBitfinex {

	public Balances() {
		super("/balances");
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		Type type = new TypeToken<List<BalancesResponse>>() {
		}.getType();
		return CallUtils.parseResponse(response, type);
	}

	public static class BalancesResponse implements Serializable {

		private static final long serialVersionUID = -8211497276524489355L;

		/**
		 * “trading”, “deposit” or “exchange”
		 */
		private String type;

		/**
		 * Currency
		 */
		private String currency;

		/**
		 * How much balance of this currency in this wallet
		 */
		private float amount;

		/**
		 * How much X there is in this wallet that is available to trade
		 */
		private float available;

		@Override
		public String toString() {
			return "BalancesResponse [type=" + type + ", currency=" + currency + ", amount=" + amount + ", available="
					+ available + "]";
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return the currency
		 */
		public String getCurrency() {
			return currency;
		}

		/**
		 * @param currency
		 *            the currency to set
		 */
		public void setCurrency(String currency) {
			this.currency = currency;
		}

		/**
		 * @return the amount
		 */
		public float getAmount() {
			return amount;
		}

		/**
		 * @param amount
		 *            the amount to set
		 */
		public void setAmount(float amount) {
			this.amount = amount;
		}

		/**
		 * @return the available
		 */
		public float getAvailable() {
			return available;
		}

		/**
		 * @param available
		 *            the available to set
		 */
		public void setAvailable(float available) {
			this.available = available;
		}
	}

}
