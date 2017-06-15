/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import java.util.List;

import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.api.huobi.btc_ltc.EHuobiStepType;
import org.finance.mybtc.api.huobi.eth.response.Balance;
import org.finance.mybtc.api.huobi.eth.response.BalanceInfo;
import org.finance.mybtc.api.huobi.eth.response.Symbol;
import org.finance.mybtc.api.huobi.eth.response.Tick;
import org.nutz.lang.Strings;

/**
 * @author zongtao liu
 *
 */
public class HuobiEthImpl extends AHuobiNewArea {

	private static final String WITHDRAW_ADDR = "0x8c8ab6567b9c60298592932a3d53ee5c0714a47d";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public float[] getBidAndAskPrice() {
		float[] result = new float[2];
		Tick tick = getTick();
		if (tick != null) {
			Object[][] bids = tick.getBids();
			if (bids.length > 0) {
				result[0] = Float.valueOf(String.valueOf(bids[0][0]));
			}
			Object[][] asks = tick.getAsks();
			if (asks.length > 0) {
				result[1] = Float.valueOf(String.valueOf(asks[0][0]));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		long accountId = getAccountId();
		if (accountId == 0) {
			return 0;
		}
		Balance accountBalance = client.getAccountBalance(accountId);
		if (accountBalance != null && accountBalance.getState() == Balance.STATE_WORKING) {
			List<BalanceInfo> list = accountBalance.getList();
			for (BalanceInfo tmpBalanceInfo : list) {
				if (Strings.equals(BalanceInfo.CURRENCY_ETH, tmpBalanceInfo.getCurrency())
						&& Strings.equals(BalanceInfo.TYPE_TRADE, tmpBalanceInfo.getType())) {
					coinNum = Float.valueOf(tmpBalanceInfo.getBalance());
				}
			}
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(float amount) {
		long accountId = getAccountId();
		if (accountId == 0) {
			return false;
		}
		String orderId = client.buyMarket(String.valueOf(accountId), String.valueOf(amount));
		if (Strings.isNotBlank(orderId)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(float amount) {
		long accountId = getAccountId();
		if (accountId == 0) {
			return false;
		}
		String orderId = client.sellMarket(String.valueOf(accountId), String.valueOf(amount));
		if (Strings.isNotBlank(orderId)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount) {
		long withdrawId = client.withdraw(BalanceInfo.CURRENCY_ETH, WITHDRAW_ADDR, String.valueOf(amount));
		if (withdrawId > 0) {
			return true;
		}
		return false;
	}

	private String getSymbol() {
		String symbol = null;
		List<Symbol> symbols = client.getSymbols();
		for (Symbol tmpSymbol : symbols) {
			if (Strings.equals(tmpSymbol.symbol, EHuobiSymbol.ETH.getValue())) {
				symbol = tmpSymbol.symbol;
				break;
			}
		}
		return symbol;
	}

	private Tick getTick() {
		Tick tick = client.getKline4Depth(EHuobiSymbol.ETH.getValue(), EHuobiStepType.STEP_0.getValue());
		return tick;
	}

}
