/**
 * 
 */
package org.finance.mybtc.apiManager.impl.bitfinex;

import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EWithdrawMethod;
import org.finance.mybtc.apiManager.BitfinexCoinFactory;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class BitfinexBtcImpl extends ABitfinexCoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getWithdrawType()
	 */
	@Override
	public String getWithdrawType() {
		return EWithdrawMethod.BTC.getValue();
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABitfinex#getCurrency()
	 */
	@Override
	public EBitfinexCurrencies getCurrency() {
		return EBitfinexCurrencies.BTC;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABitfinexCoin#getSellSmybol(java.lang.String)
	 */
	@Override
	public EBitfinexSymbols getSellSmybol(ESymbol toSymbol) {
		if(toSymbol == ESymbol.USD){
			return EBitfinexSymbols.BTCUSD;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABitfinexCoin#getBuySmybol(java.lang.String)
	 */
	@Override
	public EBitfinexSymbols getBuySmybol(ESymbol fromSymbol) {
		if(fromSymbol == ESymbol.USD){
			return EBitfinexSymbols.BTCUSD;
		}else if(fromSymbol == ESymbol.LTC){
			return EBitfinexSymbols.LTCBTC;
		}else if(fromSymbol == ESymbol.ETH){
			return EBitfinexSymbols.ETHBTC;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#exchange(java.lang.String, float)
	 */
	@Override
	public boolean exchange(ESymbol toSymbol, double amount) {
		boolean result = false;
		switch (toSymbol) {
		case LTC:
			IVirtualCoin ltc = BitfinexCoinFactory.getInstance().getVirtualCoin(EBitfinexCurrencies.LTC);
			result = ltc.buyMarket(ESymbol.BTC, amount);
			break;
		case ETH:
			IVirtualCoin eth = BitfinexCoinFactory.getInstance().getVirtualCoin(EBitfinexCurrencies.ETH);
			result = eth.buyMarket(ESymbol.BTC, amount);
			break;
		default:
			break;
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.impl.ABitfinexCoin#getWithdrawFees()
	 */
	@Override
	public float getWithdrawFees() {
		return Const.BITFINEX_BTC_WITHDRAW_FEE;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.BTC;
	}
	
	

}
