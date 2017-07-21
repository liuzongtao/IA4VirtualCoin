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
public class Btc_LtcChangeImpl extends AOkcoinChange {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getFromDigit()
	 */
	@Override
	public int getFromDigit() {
		return Const.BTC_DIGIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getToDigit()
	 */
	@Override
	public int getToDigit() {
		return Const.LTC_DIGIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getFromWithdrawFee()
	 */
	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_BTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getOtherWithdrawFee()
	 */
	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BITFINEX_LTC_WITHDRAW_FEE;
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
		IVirtualCoin okcoinBtc = okcoinFactory.getVirtualCoin(EOkcoinSymbols.BTC);
		IVirtualCoin okcoinLtc = okcoinFactory.getVirtualCoin(EOkcoinSymbols.LTC);

		// btc-e工厂
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		// 获取当前btce比特币的数量
		IVirtualCoin bitfinexBtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		IVirtualCoin bitfinexLtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.LTC);

		// 提币地址
		String bitfinexWithdrawAdd = Configs.API_CONFIG_BITFINEX.getWithdraw_addr_btc();
		String okcoinWithdrawAdd = Configs.API_CONFIG_OKCOIN.getWithdraw_addr_ltc();

		ChangeInfo info = baseExecExchange(okcoinCny, okcoinBtc, bitfinexBtc, bitfinexLtc, okcoinLtc, okcoinWithdrawAdd,
				bitfinexWithdrawAdd);

		return info;
	}

}
