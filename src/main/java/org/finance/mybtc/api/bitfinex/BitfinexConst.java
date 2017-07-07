/**
 * 
 */
package org.finance.mybtc.api.bitfinex;

/**
 * @author zongtao liu
 *
 */
public class BitfinexConst {
	
	public static final String API_KEY = "si9S102S7i0qg7OurYKpW9wlkEvnACFVtkE3U3NUMWC";

	public static final String API_KEY_SECRET = "sUSwomEl0DQNaFw5vjyWkqP746UHLtR9WfI4Hes8Wq4";
	
	public static final String URL_API_BASE = "https://api.bitfinex.com";
	
	public static final String VERSION = "/v1";
	
	public static final String URL_TICKER = URL_API_BASE + VERSION + "/pubticker/%s";
	
	public static final String URL_SYMBOLS = URL_API_BASE + VERSION  + "/symbols";
	
	public static final String URL_SYMBOL_DETAILS = URL_API_BASE + VERSION  + "/symbols_details";
	
	public static final String URL_ACCOUNT_INFO = URL_API_BASE + VERSION  + "/account_infos";
	
	public static final String URL_ACCOUNT_FEES = URL_API_BASE + VERSION  + "/account_fees";
	
	public static final String URL_BALANCES = URL_API_BASE + VERSION  + "/balances";
	
	public static final String URL_WITHDRAW = URL_API_BASE + VERSION  + "/withdraw";
	
	public static final String URL_DEPOSIT = URL_API_BASE + VERSION  + "/deposit/new";
	
	public static final String URL_SUMMARY = URL_API_BASE + VERSION  + "/summary";
	
	public static final String URL_ORDER_NEW = URL_API_BASE + VERSION  + "/order/new";
	
	public static final String URL_ORDER_MULTI = URL_API_BASE + VERSION  + "/order/new/multi";
	
	public static final String URL_KEY_INFO = URL_API_BASE + VERSION  + "/key_info";

}
