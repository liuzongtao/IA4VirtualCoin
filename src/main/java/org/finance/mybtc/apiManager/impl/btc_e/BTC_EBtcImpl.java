/**
 * 
 */
package org.finance.mybtc.apiManager.impl.btc_e;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.apiManager.BTC_ECoinFactory;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;

/**
 * @author zongtao liu
 *
 */
public class BTC_EBtcImpl extends ABTC_ECoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getSmybol()
	 */
	@Override
	public EBTC_ESymbol getSmybol() {
		return EBTC_ESymbol.BTC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getPair()
	 */
	@Override
	public EBTC_EPairType getPair() {
		return EBTC_EPairType.BTC_USD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EBTC_ESymbol.BTC.getValue().toUpperCase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#exchange(java.lang.String,
	 * float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, double amount) {
		boolean result = false;
		switch (toSymbol) {
		case LTC:
			IVirtualCoin ltc = BTC_ECoinFactory.getInstance().getVirtualCoin(EBTC_ESymbol.LTC);
			result = ltc.buyMarket(ESymbol.BTC, amount);
			break;
		case ETH:
			IVirtualCoin eth = BTC_ECoinFactory.getInstance().getVirtualCoin(EBTC_ESymbol.ETH);
			result = eth.buyMarket(ESymbol.BTC, amount);
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
	 * org.finance.mybtc.apiManager.impl.ABTC_ECoin#getSellSmybol(org.finance.
	 * mybtc.apiManager.ESymbol)
	 */
	@Override
	public EBTC_EPairType getSellSmybol(ESymbol toSymbol) {
		if (toSymbol == ESymbol.USD) {
			return EBTC_EPairType.BTC_USD;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.finance.mybtc.apiManager.impl.ABTC_ECoin#getBuySmybol(org.finance.
	 * mybtc.apiManager.ESymbol)
	 */
	@Override
	public EBTC_EPairType getBuySmybol(ESymbol fromSymbol) {
		if (fromSymbol == ESymbol.USD) {
			return EBTC_EPairType.BTC_USD;
		} else if (fromSymbol == ESymbol.LTC) {
			return EBTC_EPairType.LTC_BTC;
		} else if (fromSymbol == ESymbol.ETH) {
			return EBTC_EPairType.ETH_BTC;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.BTC;
	}

}
