package org.finance.mybtc.api.bitfinex2;

import java.util.List;
import java.util.function.Consumer;

import org.finance.mybtc.api.bitfinex2.calls.Trades.TradesResponse;

public class Ticker implements Runnable {

	private Bitfinex bitfinex;
	private EBitfinexSymbols symbol;
	private long refreshRate;
	private Consumer<TradesResponse> tradeConsumer;
	
	private long lastChecked;
	
	public Ticker(Bitfinex bitfinex, EBitfinexSymbols symbol, long refreshRate, Consumer<TradesResponse> tradeConsumer) {
		this.bitfinex = bitfinex;
		this.symbol = symbol;
		this.refreshRate = refreshRate;
		this.tradeConsumer = tradeConsumer;
		
		lastChecked = System.currentTimeMillis()/1000;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				List<TradesResponse> trades = bitfinex.trades(symbol, lastChecked);
				lastChecked = System.currentTimeMillis()/1000;
				trades.forEach(tradeConsumer);
				Thread.sleep(refreshRate);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
