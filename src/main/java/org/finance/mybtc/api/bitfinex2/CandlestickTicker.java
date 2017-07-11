package org.finance.mybtc.api.bitfinex2;

import java.util.function.Consumer;

import org.finance.mybtc.api.bitfinex2.CandlestickMapReduce.CandleStick;
import org.finance.mybtc.api.bitfinex2.calls.Trades.TradesResponse;

public class CandlestickTicker implements Consumer<TradesResponse> {
	
	private CandleStick candlestick;
	private Timeframe timeframe;
	private Consumer<CandleStick> candleConsumer;
	
	public CandlestickTicker(Timeframe timeframe, Consumer<CandleStick> candleConsumer) {
		this.timeframe = timeframe;
		this.candleConsumer = candleConsumer;
	}
	
	@Override
	public void accept(TradesResponse t) {
		if(candlestick == null) {
			candlestick = new CandleStick(t, timeframe);
		} else {
			CandleStick newCandle = new CandleStick(t, timeframe);
			if(newCandle.getIndex().getPositionInChart() == candlestick.getIndex().getPositionInChart()) {
				candlestick = CandlestickMapReduce.merge(candlestick, newCandle);
			} else {
				candleConsumer.accept(candlestick);
				candlestick = newCandle;
			}
		}
	}
		
}
