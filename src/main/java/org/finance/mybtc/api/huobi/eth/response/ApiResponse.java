package org.finance.mybtc.api.huobi.eth.response;

import org.finance.mybtc.api.huobi.eth.ApiException;

public class ApiResponse<T> {

	public String status;
	private String ch;
	private long ts;
	public String errCode;
	public String errMsg;
	public T data;
	public T tick;

	public T checkAndReturn() {
		if ("ok".equals(status)) {
			return data;
		}
		throw new ApiException(errCode, errMsg);
	}

	public T checkAndReturnTick() {
		if ("ok".equals(status)) {
			return tick;
		}
		throw new ApiException(errCode, errMsg);
	}
}
