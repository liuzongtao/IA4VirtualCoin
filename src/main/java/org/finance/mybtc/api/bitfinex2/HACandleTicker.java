package org.finance.mybtc.api.bitfinex2;

import java.util.function.Consumer;

import org.finance.mybtc.api.bitfinex2.CandlestickMapReduce.CandleStick;

public class HACandleTicker implements Consumer<CandleStick> {

	private CandleStick previousHACandle;
	private Consumer<CandleStick> haCandleConsumer;

	public HACandleTicker(Consumer<CandleStick> haCandleConsumer) {
		this.haCandleConsumer = haCandleConsumer;
	}
	
	@Override
	public void accept(CandleStick t) {
		CandleStick haCandle = CandlestickHeikenAshi.asHeikenAshi(t, previousHACandle);
		haCandleConsumer.accept(haCandle);
		previousHACandle = haCandle;
	}

}
