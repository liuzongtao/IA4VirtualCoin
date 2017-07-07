/**
 * 
 */
package org.finance.mybtc.api.bitfinex.beans;

import java.util.List;

/**
 * @author zongtao liu
 *
 */
public class BitfinexSummary {
	
	/**
	 * Trading volumes for any currency for the last 30 days
	 */
	private List<BitfinexTradeVol> trade_vol_30d;
	/**
	 *Funding profits for any currency for the last 30 days 
	 */
	private List<BitfinexFundingProfit> funding_profit_30d;
	/**
	 * Your current fees for maker orders (limit orders not marketable, in percent)
	 */
	private float maker_fee;
	/**
	 * Your current fees for taker orders (marketable order, in percent)
	 */
	private float taker_fee;

}
