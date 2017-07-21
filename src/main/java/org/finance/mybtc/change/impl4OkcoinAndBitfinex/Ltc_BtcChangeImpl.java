/**
 * 
 */
package org.finance.mybtc.change.impl4OkcoinAndBitfinex;

import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.apiManager.BitfinexCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.apiManager.OkcoinFactory;
import org.finance.mybtc.change.AOkcoinChange;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.constant.Const;
import org.finance.mybtc.core.config.Configs;

/**
 * @author zongtao liu
 *
 */
public class Ltc_BtcChangeImpl extends AOkcoinChange {

	@Override
	public int getFromDigit() {
		return Const.LTC_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.BTC_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_LTC_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BITFINEX_BTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.Ichange#execExchange()
	 */
	@Override
	public ChangeInfo execExchange() {
		OkcoinFactory okcoinFactory = OkcoinFactory.getInstance();
		// 火币网工厂
		IVirtualCoin okcoinCny = okcoinFactory.getVirtualCoin(EOkcoinSymbols.CNY);
		IVirtualCoin okcoinLtc = okcoinFactory.getVirtualCoin(EOkcoinSymbols.LTC);
		IVirtualCoin okcoinBtc = okcoinFactory.getVirtualCoin(EOkcoinSymbols.BTC);

		// btc-e工厂
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		IVirtualCoin bitfinexLtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.LTC);
		IVirtualCoin bitfinexBtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);

		// 提币地址
		String bitfinexWithdrawAdd = Configs.API_CONFIG_BITFINEX.getWithdraw_addr_ltc();
		String okcoinWithdrawAdd = Configs.API_CONFIG_OKCOIN.getWithdraw_addr_btc();

		ChangeInfo info = baseExecExchange(okcoinCny, okcoinLtc, bitfinexLtc, bitfinexBtc, okcoinBtc,okcoinWithdrawAdd, bitfinexWithdrawAdd);
		
		return info;
	}

}
