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
public class BTC_EBtcImpl extends ABTC_ECoin {
	
	private static final String WITHDRAW_ADDR = "1MmmNsCEgzqbErAdezceBxaAAsTvmknNqT";

	private static final float WITHDRAW_FEE = 0.001f;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount) {
		// TODO Auto-generated method stub
		return false;
	}

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

}
