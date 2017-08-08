/**
 * 
 */
package org.finance.mybtc.apiManager.impl.btc_e;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.apiManager.ESymbol;

/**
 * @author zongtao liu
 *
 */
public class BTC_ELtcImpl extends ABTC_ECoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getSmybol()
	 */
	@Override
	public EBTC_ESymbol getSmybol() {
		return EBTC_ESymbol.LTC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getPair()
	 */
	@Override
	public EBTC_EPairType getPair() {
		return EBTC_EPairType.LTC_USD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EBTC_ESymbol.LTC.getValue().toUpperCase();
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
	 * @see
	 * org.finance.mybtc.apiManager.impl.ABitfinexCoin#getSellSmybol(java.lang.
	 * String)
	 */
	@Override
	public EBTC_EPairType getSellSmybol(ESymbol toSymbol) {
		if (toSymbol == ESymbol.USD) {
			return EBTC_EPairType.LTC_USD;
		} else if (toSymbol == ESymbol.BTC) {
			return EBTC_EPairType.LTC_BTC;
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
	public EBTC_EPairType getBuySmybol(ESymbol fromSymbol) {
		if (fromSymbol == ESymbol.USD) {
			return EBTC_EPairType.LTC_USD;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.LTC;
	}

}
