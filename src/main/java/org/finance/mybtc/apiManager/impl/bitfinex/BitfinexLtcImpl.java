/**
 * 
 */
package org.finance.mybtc.apiManager.impl.bitfinex;

import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EWithdrawMethod;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class BitfinexLtcImpl extends ABitfinexCoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EWithdrawMethod.LTC.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getCurrency()
	 */
	@Override
	public EBitfinexCurrencies getCurrency() {
		return EBitfinexCurrencies.LTC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.impl.ABitfinexCoin#getSellSmybol(java.lang.
	 * String)
	 */
	@Override
	public EBitfinexSymbols getSellSmybol(ESymbol toSymbol) {
		if (toSymbol == ESymbol.USD) {
			return EBitfinexSymbols.LTCUSD;
		} else if (toSymbol == ESymbol.BTC) {
			return EBitfinexSymbols.LTCBTC;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.impl.ABitfinexCoin#getBuySmybol(java.lang.
	 * String)
	 */
	@Override
	public EBitfinexSymbols getBuySmybol(ESymbol fromSymbol) {
		if (fromSymbol == ESymbol.USD) {
			return EBitfinexSymbols.LTCUSD;
		} else if (fromSymbol == ESymbol.BTC) {
			return EBitfinexSymbols.LTCBTC;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.IVirtualCoin#exchange(org.finance.mybtc.
	 * apiManager.ESymbol, float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, double amount) {
		boolean result = false;
		switch (toSymbol) {
		case BTC:
			result = sellMarket(ESymbol.BTC, amount);
			break;
		case ETH:
			// 卖掉ltc买入eth
			break;
		default:
			break;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinexCoin#getWithdrawFees()
	 */
	@Override
	public float getWithdrawFees() {
		return Const.BITFINEX_LTC_WITHDRAW_FEE;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.LTC;
	}

}
