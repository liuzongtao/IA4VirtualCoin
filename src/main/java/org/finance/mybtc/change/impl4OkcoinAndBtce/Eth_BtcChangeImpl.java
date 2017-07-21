/**
 * 
 */
package org.finance.mybtc.change.impl4OkcoinAndBtce;

import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.api.okCoin.EOkcoinSymbols;
import org.finance.mybtc.apiManager.BTC_ECoinFactory;
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
		return Const.BTC_E_BTC_WITHDRAW_FEE;
	}
	
	/* (non-Javadoc)
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

		// btc-e工厂
		BTC_ECoinFactory btceFactory = BTC_ECoinFactory.getInstance();
		IVirtualCoin btceEth = btceFactory.getVirtualCoin(EBTC_ESymbol.ETH);
		IVirtualCoin btceBtc = btceFactory.getVirtualCoin(EBTC_ESymbol.BTC);

		// 提币地址
		String btc_eWithdrawAdd = Configs.API_CONFIG_BTC_E.getWithdraw_addr_eth();
		String okcoinWithdrawAdd = Configs.API_CONFIG_OKCOIN.getWithdraw_addr_btc();

		ChangeInfo info = baseExecExchange(okcoinCny, okcoinEth, btceEth, btceBtc, okcoinBtc, okcoinWithdrawAdd,
				btc_eWithdrawAdd);

		return info;
	}

}
