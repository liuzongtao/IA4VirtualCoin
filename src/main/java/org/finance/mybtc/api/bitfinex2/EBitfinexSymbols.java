package org.finance.mybtc.api.bitfinex2;
import static org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies.*;

public enum EBitfinexSymbols {

	BTCUSD(BTC, USD), LTCUSD(LTC, USD), LTCBTC(LTC, BTC),ETHUSD(ETH,USD),ETHBTC(ETH,BTC);

	private EBitfinexCurrencies currency1;
	private EBitfinexCurrencies currency2;
	
	private EBitfinexSymbols(EBitfinexCurrencies currency1, EBitfinexCurrencies currency2) {
		this.currency1 = currency1;
		this.currency2 = currency2;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	public EBitfinexCurrencies getCurrency1() {
		return currency1;
	}

	public void setCurrency1(EBitfinexCurrencies currency1) {
		this.currency1 = currency1;
	}

	public EBitfinexCurrencies getCurrency2() {
		return currency2;
	}

	public void setCurrency2(EBitfinexCurrencies currency2) {
		this.currency2 = currency2;
	}
	
}
