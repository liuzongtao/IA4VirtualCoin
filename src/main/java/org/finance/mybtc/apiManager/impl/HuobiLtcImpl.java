/**
 * 
 */
package org.finance.mybtc.apiManager.impl;

import org.finance.mybtc.api.huobi.btc_ltc.EHuobiCoinType;
import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;
import org.finance.mybtc.api.huobi.btc_ltc.WithdrawCoinResult;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class HuobiLtcImpl extends AHuobiBtcLtcCoin {

	private static final String WITHDRAW_ADDR = "LQrjfk6a8Scax3c7zZKVwqVDHHhhktGYRu";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public float getCoinNum() {
		float coinNum = 0;
		try {
			HuobiAccountInfo account = getAccount();
			coinNum = account.getAvailable_ltc_display();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(float)
	 */
	@Override
	public boolean withdrawCoin(float amount) {
		WithdrawCoinResult result = withdrawCoin(amount, WITHDRAW_ADDR, Const.HUOBI_LTC_WITHDRAW_FEE);
		if (result != null && result.getCode() == 200) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.impl.ABaseHuobiCoin#getCoinType()
	 */
	@Override
	public EHuobiCoinType getCoinType() {
		return EHuobiCoinType.LTC;
	}

}
