/**
 * 
 */
package org.finance.mybtc.apiManager.impl.bitfinex;

import java.util.List;

import org.finance.mybtc.api.bitfinex2.Bitfinex;
import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.bitfinex2.EBitfinexSymbols;
import org.finance.mybtc.api.bitfinex2.EBitfinexWalletType;
import org.finance.mybtc.api.bitfinex2.calls.Pubticker.TickerResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Balances.BalancesResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.NewOrder.NewOrderResponse;
import org.finance.mybtc.api.bitfinex2.calls.auth.Withdraw.WithdrawResponse;
import org.finance.mybtc.api.bitfinex2.exceptions.BitfinexCallException;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.core.config.Configs;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public abstract class ABitfinexCoin implements IVirtualCoin {

	private static Log log = Logs.get();

	public abstract EBitfinexSymbols getSellSmybol(ESymbol toSymbol);

	public abstract EBitfinexSymbols getBuySmybol(ESymbol fromSymbol);

	public abstract EBitfinexCurrencies getCurrency();

	public abstract String getWithdrawType();

	public abstract float getWithdrawFees();

	protected static Bitfinex bitfinex = new Bitfinex(Configs.API_CONFIG_BITFINEX.getApiKey(),
			Configs.API_CONFIG_BITFINEX.getApiSecret());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getBidAndAskPrice()
	 */
	@Override
	public double[] getBidAndAskPrice(String pair) {
		return getBidAndAskPrice(EBitfinexSymbols.getEBitfinexSymbols(pair));
	}

	public double[] getBidAndAskPrice(EBitfinexSymbols symbol) {
		double[] result = null;
		try {
			TickerResponse pubticker = bitfinex.pubticker(symbol);
			if (pubticker != null) {
				result = new double[2];
				result[0] = Double.valueOf(pubticker.getBid() + "");
				result[1] = Double.valueOf(pubticker.getAsk() + "");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public double getCoinNum() {
		float coinNum = 0;
		BalancesResponse balance = getBalancesResponse();
		if (balance != null) {
			coinNum = balance.getAmount();
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#buyMarket(float)
	 */
	@Override
	public boolean buyMarket(ESymbol fromSymbol, double amount) {
		boolean result = false;
		try {
			double[] bidAndAskPrice = getBidAndAskPrice(getBuySmybol(fromSymbol));
			double buyAmount = DecimalUtil.decimalDown(amount / bidAndAskPrice[1], 8);
			NewOrderResponse marginBuyMarket = bitfinex.exchangeBuyMarket(getBuySmybol(fromSymbol), buyAmount,
					1 / bidAndAskPrice[1]);
			if (marginBuyMarket == null) {
				log.error("marginBuyMarket is null !");
			} else {
				if (marginBuyMarket.getOrder_id() != 0) {
					result = true;
				} else {
					log.error(marginBuyMarket.toString());
				}
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#sellMarket(float)
	 */
	@Override
	public boolean sellMarket(ESymbol toSymbol, double amount) {
		boolean result = false;
		try {
			double[] bidAndAskPrice = getBidAndAskPrice(getSellSmybol(toSymbol));
			NewOrderResponse marginSellMarket = bitfinex.exchangeSellMarket(getSellSmybol(toSymbol), amount,
					bidAndAskPrice[0]);
			if (marginSellMarket == null) {
				log.error("marginSellMarket is null !");
			} else {
				if (marginSellMarket.getOrder_id() != 0) {
					result = true;
				} else {
					log.error(marginSellMarket.toString());
				}
			}
		} catch (BitfinexCallException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(double amount, String address) {
		boolean result = false;
		try {
			WithdrawResponse withdraw = bitfinex.withdraw(getWithdrawType(), EBitfinexWalletType.EXCHANGE.getValue(),
					amount - getWithdrawFees(), address);
			if (withdraw != null && Strings.equals(withdraw.getStatus(), "success")) {
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
