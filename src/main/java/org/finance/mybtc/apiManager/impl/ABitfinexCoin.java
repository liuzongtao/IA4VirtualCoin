/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import java.util.List;

import org.finance.mybtc.api.bitfinex2.Bitfinex;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EBitfinexWalletType;
import org.finance.mybtc.api.bitfinex2.calls.Pubticker.TickerResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Balances.BalancesResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.NewOrder.NewOrderResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Withdraw.WithdrawResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.configs.Configs;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public abstract class ABitfinexCoin implements IVirtualCoin{
	
	private static Log log = Logs.get();
	
	public abstract EBitfinexSymbols getSmybol();
	public abstract EBitfinexSymbols getSellSmybol(ESymbol toSymbol);
	public abstract EBitfinexSymbols getBuySmybol(ESymbol fromSymbol);
	public abstract EBitfinexCurrencies getCurrency();
	public abstract String getWithdrawType();
	public abstract float getWithdrawFees();
	
	protected static Bitfinex bitfinex = new Bitfinex(Configs.API_CONFIG_BITFINEX.getApiKey(),Configs.API_CONFIG_BITFINEX.getApiSecret());

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public float[] getBidAndAskPrice() {
		return getBidAndAskPrice(getSmybol());
	}
	
	public float[] getBidAndAskPrice(EBitfinexSymbols symbol) {
		float[] result = new float[2];
		try {
			TickerResponse pubticker = bitfinex.pubticker(symbol);
			if (pubticker != null) {
				result[0] = Float.valueOf(pubticker.getBid() + "");
				result[1] = Float.valueOf(pubticker.getAsk() + "");
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		BalancesResponse balance = getBalancesResponse();
		if (balance != null) {
			coinNum = balance.getAvailable();
		}
		return coinNum;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol,float amount) {
		boolean result = false;
		try {
			float[] bidAndAskPrice = getBidAndAskPrice();
			NewOrderResponse marginBuyMarket = bitfinex.exchangeBuyMarket(getBuySmybol(fromSymbol), amount, bidAndAskPrice[1]);
			if(marginBuyMarket == null){
				log.error("marginBuyMarket is null !");
			}else {
				if(marginBuyMarket.getOrder_id() != 0){
					result = true;
				}else{
					log.error(marginBuyMarket.toString());
				}
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol,float amount) {
		boolean result = false;
		try {
			float[] bidAndAskPrice = getBidAndAskPrice();
			NewOrderResponse marginSellMarket = bitfinex.exchangeSellMarket(getSellSmybol(toSymbol), Double.valueOf(amount+""), bidAndAskPrice[0]);
			if(marginSellMarket == null){
				log.error("marginSellMarket is null !");
			}else {
				if(marginSellMarket.getOrder_id() != 0){
					result = true;
				}else{
					log.error(marginSellMarket.toString());
				}
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount,String address) {
		boolean result = false;
		try {
			WithdrawResponse withdraw = bitfinex.withdraw(getWithdrawType(), EBitfinexWalletType.EXCHANGE.getValue(), amount - getWithdrawFees(), address);
			if(withdraw != null && Strings.equals(withdraw.getStatus(), "success")){
				result = true;
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private BalancesResponse getBalancesResponse() {
		BalancesResponse curBalance = null;
		try {
			List<BalancesResponse> balances = bitfinex.getBalances();
			if (balances != null && balances.size() > 0) {
				for (BalancesResponse balance : balances) {
					if (Strings.equals(balance.getType(), EBitfinexWalletType.EXCHANGE.getValue())
							&& Strings.equals(balance.getCurrency(), getCurrency().toString())) {
						curBalance = balance;
						break;
					}
				}
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return curBalance;
	}
	
	

}
