package org.finance.mybtc.api.bitfinex2.exceptions;

public class BitfinexCallException extends BitfinexException {

	private static final long serialVersionUID = -6445025699501909020L;

	public BitfinexCallException() {
	}

	public BitfinexCallException(String message) {
		super(message);
	}

	public BitfinexCallException(Throwable cause) {
		super(cause);
	}

	public BitfinexCallException(String message, Throwable cause) {
		super(message, cause);
	}

	public BitfinexCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
