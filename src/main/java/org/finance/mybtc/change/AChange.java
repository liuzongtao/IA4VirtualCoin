/**
 * 
 */
package org.finance.mybtc.change;

import org.finance.mybtc.change.bean.ChangeInfo;
import org.finance.mybtc.constant.Const;
import org.finance.mybtc.utils.DecimalUtil;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public abstract class AChange implements Ichange {
	
	private float wishProfit;

	protected static Log log = Logs.get();

	public abstract int getFromDigit();

	public abstract int getToDigit();

	public abstract float getFromWithdrawFee();

	public abstract float getOtherPfWithdrawFee();

	protected static long sleepTime = 30 * 1000l;

	@Override
	public float preChange(float totalMoney, float fromCoinBuyPrice, float toCoinSellPrice, float otherPfChangePrice) {
		log.debug("totalMoney = " + totalMoney + "; fromCoinBuyPrice = " + fromCoinBuyPrice + "; toCoinSellPrice = "
				+ toCoinSellPrice + "; otherPfChangePrice = " + otherPfChangePrice);
		if(totalMoney == 0){
			log.error("totalMoney is 0 !!!");
			return 0;
		}
		// 来源币位数
		int fromDigit = getFromDigit();
		// 购买获得的数量
		float fromCoinNum = canBuyNum(totalMoney, fromCoinBuyPrice, fromDigit);
		// 剩余钱数
		float surplusMoney = totalMoney - fromCoinBuyPrice * fromCoinNum;
		// 实际到手的数量
		fromCoinNum = DecimalUtil.decimalDown(fromCoinNum * (1 - Const.HUOBI_TRADE_FEE_BUY), fromDigit);
		// 其他平台 收到的虚拟币的数量
		fromCoinNum = fromCoinNum - getFromWithdrawFee();
		// 终止币位数
		int toDigit = getToDigit();
		// 其他平台 的虚拟币数量
		float toCoinNum = canBuyNum(fromCoinNum, otherPfChangePrice, toDigit);
		// 火币网收到虚拟币数量
		toCoinNum = toCoinNum - getOtherPfWithdrawFee();
		// 火币网虚拟币币兑现后资金
		float newMoney = toCoinNum * toCoinSellPrice * (1 - Const.HUOBI_TRADE_FEE_SELL);
		// 计算利润
		float profit = (newMoney + surplusMoney - totalMoney) / totalMoney;
		profit = DecimalUtil.decimalDown(profit * 100, 2);
		return profit;
	}

	public float canBuyNum(float amount, float price, int digit) {
		return DecimalUtil.decimalDown(amount / price, digit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.Ichange#execExchange()
	 */
	@Override
	public ChangeInfo execExchange() {
		return null;
	}

	/**
	 * @return the wishProfit
	 */
	public float getWishProfit() {
		return wishProfit;
	}

	/**
	 * @param wishProfit the wishProfit to set
	 */
	public void setWishProfit(float wishProfit) {
		this.wishProfit = wishProfit;
	}
}
