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
public class BTC_EEthImpl extends ABTC_ECoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getSmybol()
	 */
	@Override
	public EBTC_ESymbol getSmybol() {
		return EBTC_ESymbol.ETH;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getPair()
	 */
	@Override
	public EBTC_EPairType getPair() {
		return EBTC_EPairType.ETH_USD;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EBTC_ESymbol.ETH.getValue().toUpperCase();
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getSellSmybol(org.finance.mybtc.apiManager.ESymbol)
	 */
	@Override
	public EBTC_EPairType getSellSmybol(ESymbol toSymbol) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABTC_ECoin#getBuySmybol(org.finance.mybtc.apiManager.ESymbol)
	 */
	@Override
	public EBTC_EPairType getBuySmybol(ESymbol toSymbol) {
		// TODO Auto-generated method stub
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
			// 卖掉ltc买入eth
			break;
		default:
			break;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.ETH;
	}

}
