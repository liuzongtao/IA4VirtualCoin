package org.finance.mybtc.api.bitfinex2;

public enum Sides {
	
	BUY, SELL;
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
