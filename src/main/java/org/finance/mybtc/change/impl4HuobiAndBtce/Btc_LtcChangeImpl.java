/**
 * 
 */
package org.finance.mybtc.change.impl4HuobiAndBtce;

import org.finance.mybtc.api.btc_e.official.EBTC_ESymbol;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.BTC_ECoinFactory;
import org.finance.mybtc.apiManager.HuobiCoinFactory;
import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.change.AHuobiChange;
import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.constant.Const;
import org.finance.mybtc.core.config.Configs;

/**
 * @author zongtao liu
 *
 */
public class Btc_LtcChangeImpl extends AHuobiChange {

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
		return Const.HUOBI_BTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.AChange#getOtherWithdrawFee()
	 */
	@Override
	public float getOtherPfWithdrawFee() {
		return Const.BTC_E_LTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.Ichange#execExchange()
	 */
	@Override
	public ChangeInfo execExchange() {
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		// 火币网工厂
		IVirtualCoin huobiCny = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		IVirtualCoin huobiBtc = huobiFactory.getVirtualCoin(EHuobiSymbol.BTC);
		IVirtualCoin huobiLtc = huobiFactory.getVirtualCoin(EHuobiSymbol.LTC);

		// btc-e工厂
		BTC_ECoinFactory btceFactory = BTC_ECoinFactory.getInstance();
		// 获取当前btce比特币的数量
		IVirtualCoin btceBtc = btceFactory.getVirtualCoin(EBTC_ESymbol.BTC);
		IVirtualCoin btceLtc = btceFactory.getVirtualCoin(EBTC_ESymbol.LTC);

		// 提币地址
		String btc_eWithdrawAdd = Configs.API_CONFIG_BTC_E.getWithdraw_addr_btc();
		String huobiWithdrawAdd = Configs.API_CONFIG_HUOBI.getWithdraw_addr_ltc();

		ChangeInfo info = baseExecExchange(huobiCny, huobiBtc, btceBtc, btceLtc, huobiLtc, huobiWithdrawAdd,
				btc_eWithdrawAdd);

		return info;
	}

}
