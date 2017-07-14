/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.btc_e.official.EBTC_EPairType;
import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;

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

}
