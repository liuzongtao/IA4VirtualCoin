package org.finance.mybtc.api.bitfinex2;

public enum EBitfinexCurrencies {
	
	BTC,USD,LTC,ETH;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
