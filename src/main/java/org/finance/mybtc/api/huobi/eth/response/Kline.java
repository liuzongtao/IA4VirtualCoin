/**
 * 
 */
package org.finance.mybtc.api.huobi.eth.response;

import org.finance.mybtc.api.huobi.eth.ApiException;
import org.nutz.json.JsonField;

/**
 * @author zongtao liu
 *
 */
public class Kline {

	private String status;

	private String ch;

	private long ts;

	private Tick tick;

	@JsonField("err-code")
	private String errCode;

	@JsonField("err-msg")
	private String errMsg;

	public Tick checkAndReturn() {
		if ("ok".equals(status)) {
			return tick;
		}
		throw new ApiException(errCode, errMsg);
	}

}
