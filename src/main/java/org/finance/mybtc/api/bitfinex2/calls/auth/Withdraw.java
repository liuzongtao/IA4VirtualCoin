package org.finance.mybtc.api.bitfinex2.calls.auth;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.api.bitfinex2.util.CallUtils;

import com.google.gson.reflect.TypeToken;

public class Withdraw extends HttpBitfinex {

	public Withdraw(String withdrawType, String wallet, float amount, String address) {
		super("/withdraw");
		addHeader("withdraw_type", withdrawType);
		addHeader("walletselected", wallet);
		addHeader("amount", String.valueOf(amount));
		addHeader("address", address);
		updatePayload();
	}

	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		Type type = new TypeToken<List<WithdrawResponse>>() {
		}.getType();
		return CallUtils.parseResponse(response, type);
	}

	public static class WithdrawResponse implements Serializable {

		private static final long serialVersionUID = -8211497276524489356L;

		/**
		 * “success” or “error”
		 */
		private String status;

		/**
		 * Success or error message
		 */
		private String message;

		/**
		 * ID of the withdrawal (0 if unsuccessful)
		 */
		private long withdrawal_id;

		@Override
		public String toString() {
			return "WithdrawResponse [status=" + status + ", message=" + message + ", withdrawal_id=" + withdrawal_id
					+ "]";
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status
		 *            the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message
		 *            the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * @return the withdrawal_id
		 */
		public long getWithdrawal_id() {
			return withdrawal_id;
		}

		/**
		 * @param withdrawal_id
		 *            the withdrawal_id to set
		 */
		public void setWithdrawal_id(long withdrawal_id) {
			this.withdrawal_id = withdrawal_id;
		}

	}

}
