/**
 * 
 */
package org.finance.mybtc.change;

import org.finance.mybtc.apiManager.IVirtualCoin;
import org.finance.mybtc.change.bean.ChangeInfo;
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

	public abstract float getBuyFee();

	public abstract float getSellFee();

	protected static long sleepTime = 30 * 1000l;

	@Override
	public float preChange(double totalMoney, double fromCoinBuyPrice, double toCoinSellPrice,
			double otherPfChangePrice) {
		log.debug("totalMoney = " + totalMoney + "; fromCoinBuyPrice = " + fromCoinBuyPrice + "; toCoinSellPrice = "
				+ toCoinSellPrice + "; otherPfChangePrice = " + otherPfChangePrice);
		if (totalMoney == 0) {
			log.error("totalMoney is 0 !!!");
			return 0;
		}
		// 来源币位数
		int fromDigit = getFromDigit();
		// 购买获得的数量
		double fromCoinNum = canBuyNum(totalMoney, fromCoinBuyPrice, fromDigit);
		// 剩余钱数
		double surplusMoney = totalMoney - fromCoinBuyPrice * fromCoinNum;
		// 实际到手的数量
		fromCoinNum = fromCoinNum * (1 - getBuyFee());
		// 其他平台 收到的虚拟币的数量
		fromCoinNum = fromCoinNum - getFromWithdrawFee();
		// 终止币位数
		int toDigit = getToDigit();
		// 其他平台 的虚拟币数量
		double toCoinNum = canBuyNum(fromCoinNum, otherPfChangePrice, toDigit);
		// 火币网收到虚拟币数量
		toCoinNum = toCoinNum - getOtherPfWithdrawFee();
		// 火币网虚拟币币兑现后资金
		double newMoney = toCoinNum * toCoinSellPrice * (1 - getSellFee());
		// 计算利润
		double profit = (newMoney + surplusMoney - totalMoney) / totalMoney;
		profit = profit * 100;
		return Float.valueOf(String.valueOf(profit));
	}

	public double canBuyNum(double amount, double price, int digit) {
		return amount / price;
		// return DecimalUtil.decimalDown(amount / price, digit);
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
	 * @param wishProfit
	 *            the wishProfit to set
	 */
	public void setWishProfit(float wishProfit) {
		this.wishProfit = wishProfit;
	}

	public ChangeInfo baseExecExchange(IVirtualCoin fromTotal, IVirtualCoin fromCoin, IVirtualCoin toOtherCoin,
			IVirtualCoin fromOtherCoin, IVirtualCoin toCoin, String withdrawAdd, String otherWithdrawAdd) {
		int mySleepTime = 1000;
		ChangeInfo info = new ChangeInfo();
		double fromTotalNum = fromTotal.getCoinNum();
		info.addInfo("fromTotalNum", fromTotalNum);
		// 获取当前平台 转换前币种 数量
		double fromCoinNum = fromCoin.getCoinNum();
		info.addInfo("fromCoinNum", fromCoinNum);
		// 购买当前平台 转换前币种
		boolean fromBuy = fromCoin.buyMarket(null, fromTotalNum);
		if (!fromBuy) {
			info.addErrorInfo("fromBuy is " + fromBuy);
			return info;
		}
		log.debug("fromBuy is " + fromBuy);
		sleep(mySleepTime * 60);
		double fromCoinNum2 = fromCoin.getCoinNum();
		info.addInfo("fromCoinNum2", fromCoinNum2);
		double fromWithdrawNum = fromCoinNum2 - fromCoinNum;
		// 进行提币
		boolean fromWithdrawRes = fromCoin.withdrawCoin(fromWithdrawNum, otherWithdrawAdd);
		int fromWithdrawResStep = 0;
		while (!fromWithdrawRes) {
			log.error("fromWithdrawRes is " + fromWithdrawRes + " == " + fromWithdrawResStep + " == ");
			sleep(mySleepTime);
			fromWithdrawRes = fromCoin.withdrawCoin(fromWithdrawNum, otherWithdrawAdd);
			fromWithdrawResStep++;
		}
		log.debug("fromWithdrawRes is " + fromWithdrawRes + " , runnum == " + fromWithdrawResStep);

		double toOtherCoinNum = toOtherCoin.getCoinNum();
		info.addInfo("toOtherCoinNum", toOtherCoinNum);
		// 查询另一平台是否收到转换前币种
		double addToOtherCoinNum = 0;
		double toOtherCoinNum2 = 0;
		while (addToOtherCoinNum <= 0) {
			sleep(sleepTime);
			toOtherCoinNum2 = toOtherCoin.getCoinNum();
			log.debug("toOtherCoinNum2 == " + toOtherCoinNum2);
			addToOtherCoinNum = toOtherCoinNum2 - toOtherCoinNum;
			if (addToOtherCoinNum > 0) {
				info.addInfo("addToOtherCoinNum", addToOtherCoinNum);
			}
		}

		// 在另一平台进行 币种转换
		boolean otherCoinExchangeRes = toOtherCoin.exchange(fromOtherCoin.getESymbol(), toOtherCoinNum2);
		int otherCoinExchangeResStep = 0;
		while (!otherCoinExchangeRes) {
			log.error("otherCoinExchangeRes is " + otherCoinExchangeRes + " == " + otherCoinExchangeResStep + " == ");
			sleep(mySleepTime);
			otherCoinExchangeRes = toOtherCoin.exchange(fromOtherCoin.getESymbol(), toOtherCoinNum2);
			otherCoinExchangeResStep++;
		}
		log.debug("otherCoinExchangeRes is " + otherCoinExchangeRes + " , runnum == " + otherCoinExchangeResStep);
		// 获取另一平台 转换后币种的数量
		sleep(mySleepTime * 60);
		toOtherCoinNum = toOtherCoin.getCoinNum();
		int waitExchangeStep = 0;
		while (!(toOtherCoinNum >= 0 && toOtherCoinNum < 0.0001)) {
			sleep(mySleepTime);
			toOtherCoinNum = toOtherCoin.getCoinNum();
			log.debug("toOtherCoinNum is " + toOtherCoinNum + " , runnum == " + waitExchangeStep);
			waitExchangeStep++;
		}
		double fromOtherCoinNum = fromOtherCoin.getCoinNum();
		info.addInfo("fromOtherCoinNum", fromOtherCoinNum);

		// 提取转换后币种 到 当前平台
		double otherWithdrawNum = fromOtherCoinNum;
		boolean otherWithdrawRes = fromOtherCoin.withdrawCoin(otherWithdrawNum, withdrawAdd);
		int otherWithdrawResStep = 0;
		while (!otherWithdrawRes) {
			log.error("otherWithdrawRes is " + otherWithdrawRes + " == " + otherWithdrawResStep + " == ");
			sleep(mySleepTime);
			otherWithdrawRes = fromOtherCoin.withdrawCoin(otherWithdrawNum, withdrawAdd);
			otherWithdrawResStep++;
		}
		log.debug("otherWithdrawRes is " + otherWithdrawRes + " , runnum == " + otherWithdrawResStep);

		// 查询 当前平台 是否收到 转换后的币种
		double toCoinNum = toCoin.getCoinNum();
		info.addInfo("toCoinNum", toCoinNum);
		double addToCoinNum = 0;
		double toCoinNum2 = 0;
		while (addToCoinNum <= 0) {
			sleep(sleepTime);
			toCoinNum2 = toCoin.getCoinNum();
			log.debug("toCoinNum2 == " + toCoinNum2);
			addToCoinNum = toCoinNum2 - toCoinNum;
			if (addToCoinNum > 0) {
				info.addInfo("addToCoinNum", addToCoinNum);
			}
		}
		// 在当前平台 卖掉已经收到的 转换后的币种
		boolean toCoinSellRes = toCoin.sellMarket(null, addToCoinNum);
		int toCoinSellResStep = 0;
		while (!toCoinSellRes) {
			log.error("toCoinSellRes is " + toCoinSellRes + " == " + toCoinSellResStep + " == ");
			sleep(mySleepTime);
			toCoinSellRes = toCoin.sellMarket(null, addToCoinNum);
			toCoinSellResStep++;
		}
		log.debug("toCoinSellRes is " + toCoinSellRes + " , runnum == " + toCoinSellResStep);
		// 获取 当前平台 卖完转换后币种 的数量
		sleep(mySleepTime);
		double fromTotalNum2 = fromTotal.getCoinNum();
		info.addInfo("fromTotalNum2", fromTotalNum2);
		// 计算盈利情况
		String infoStr = getInfoStr(fromTotal, fromCoin, toOtherCoin, fromOtherCoin, toCoin,
				String.valueOf(fromTotalNum2 - fromTotalNum));
		info.addInfo(infoStr);
		return info;
	}

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 获取info字符串
	 * 
	 * @param fromTotal
	 * @param fromCoin
	 * @param toOtherCoin
	 * @param fromOtherCoin
	 * @param toCoin
	 * @param info
	 * @return
	 */
	private String getInfoStr(IVirtualCoin fromTotal, IVirtualCoin fromCoin, IVirtualCoin toOtherCoin,
			IVirtualCoin fromOtherCoin, IVirtualCoin toCoin, String info) {
		String infoStr = fromCoin.getClass().getSimpleName() + " -> " + toOtherCoin.getClass().getSimpleName() + " -> "
				+ fromOtherCoin.getClass().getSimpleName() + " -> " + toCoin.getClass().getSimpleName()
				+ " : profit is " + info;
		return infoStr;

	}
}
