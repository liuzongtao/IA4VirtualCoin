/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EWithdrawMethod;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class BitfinexEthImpl extends ABitfinexCoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getSmybol()
	 */
	@Override
	public EBitfinexSymbols getSmybol() {
		return EBitfinexSymbols.ETHUSD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EWithdrawMethod.ETH.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getCurrency()
	 */
	@Override
	public EBitfinexCurrencies getCurrency() {
		return EBitfinexCurrencies.ETH;
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
			return EBitfinexSymbols.ETHUSD;
		} else if (toSymbol == ESymbol.BTC) {
			return EBitfinexSymbols.ETHBTC;
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
			return EBitfinexSymbols.ETHUSD;
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
	public boolean exchange(ESymbol toSymbol, float amount) {
		boolean result = false;
		switch (toSymbol) {
		case BTC:
			result = sellMarket(ESymbol.BTC, amount);
			break;
		case LTC:
			// 卖出eth,买入ltc
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
		return Const.BITFINEX_ETH_WITHDRAW_FEE;
	}

}
