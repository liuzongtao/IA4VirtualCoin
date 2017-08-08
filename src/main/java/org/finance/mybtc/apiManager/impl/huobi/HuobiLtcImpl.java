/**
 * 
 */
package org.finance.mybtc.apiManager.impl.huobi;

import org.finance.mybtc.api.huobi.btc_ltc.EHuobiCoinType;
import org.finance.mybtc.api.huobi.btc_ltc.HuobiAccountInfo;
import org.finance.mybtc.api.huobi.btc_ltc.WithdrawCoinResult;
import org.finance.mybtc.apiManager.ESymbol;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class HuobiLtcImpl extends AHuobiBtcLtcCoin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getCoinNum()
	 */
	@Override
	public double getCoinNum() {
		double coinNum = 0;
		try {
			HuobiAccountInfo account = getAccount();
			if(account != null){
				coinNum = account.getAvailable_ltc_display();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coinNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#withdrawCoin(double)
	 */
	@Override
	public boolean withdrawCoin(double amount, String address) {
		WithdrawCoinResult result = withdrawCoin(amount - Const.HUOBI_LTC_WITHDRAW_FEE, address, Const.HUOBI_LTC_WITHDRAW_FEE);
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
	
	/* (non-Javadoc)
	 * @see org.finance.mybtc.apiManager.IVirtualCoin#getESymbol()
	 */
	@Override
	public ESymbol getESymbol() {
		return ESymbol.LTC;
	}

}
