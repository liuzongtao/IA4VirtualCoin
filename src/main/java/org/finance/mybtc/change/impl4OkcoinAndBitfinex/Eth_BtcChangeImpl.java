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
public class Eth_BtcChangeImpl extends AOkcoinChange {

	@Override
	public int getFromDigit() {
		return Const.ETH_DIGIT;
	}

	@Override
	public int getToDigit() {
		return Const.BTC_DIGIT;
	}

	@Override
	public float getFromWithdrawFee() {
		return Const.OKCOIN_ETH_WITHDRAW_FEE;
	}

	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BITFINEX_BTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AOkcoinChange#getBuyFee()
	 */
	@Override
	public float getBuyFee() {
		return Const.OKCOIN_TRADE_FEE_BUY_ETH;
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
		IVirtualCoin okcoinEth = okcoinFactory.getVirtualCoin(EOkcoinSymbols.ETH);
		IVirtualCoin okcoinBtc = okcoinFactory.getVirtualCoin(EOkcoinSymbols.BTC);

		// bitfinex工厂
		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		// 获取当前btce比特币的数量
		IVirtualCoin bitfinexEth = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.ETH);
		IVirtualCoin bitfinexBtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);

		// 提币地址
		String btc_eWithdrawAdd = Configs.API_CONFIG_BITFINEX.getWithdraw_addr_eth();
		String okcoinWithdrawAdd = Configs.API_CONFIG_OKCOIN.getWithdraw_addr_btc();

		ChangeInfo info = baseExecExchange(okcoinCny, okcoinEth, bitfinexEth, bitfinexBtc, okcoinBtc, okcoinWithdrawAdd,
				btc_eWithdrawAdd);

		return info;
	}

}
