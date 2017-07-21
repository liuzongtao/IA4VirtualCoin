/**
 * 
 */
package org.finance.mybtc.change.impl4HuobiAndBitfinex;

import org.finance.mybtc.api.bitfinex2.EBitfinexCurrencies;
import org.finance.mybtc.api.huobi.beans.EHuobiSymbol;
import org.finance.mybtc.apiManager.BitfinexCoinFactory;
import org.finance.mybtc.apiManager.ESymbol;
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
		return Const.BITFINEX_LTC_WITHDRAW_FEE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.finance.mybtc.change.Ichange#execExchange()
	 */
	@Override
	public ChangeInfo execExchange() {
		ChangeInfo info = new ChangeInfo();
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		// 获取当前人民币数量
		IVirtualCoin huobiCny = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		float huobiCnyNum = huobiCny.getCoinNum();
		info.addInfo("huobiCnyNum", huobiCnyNum);
		// 获取当前比特币数量
		IVirtualCoin huobiBtc = huobiFactory.getVirtualCoin(EHuobiSymbol.BTC);
		float huobiBtcNum = huobiBtc.getCoinNum();
		info.addInfo("huobiBtcNum", huobiBtcNum);
		// 购买比特币
		boolean huobiBuyBtc = huobiBtc.buyMarket(null, huobiCnyNum);
		if (!huobiBuyBtc) {
			info.addErrorInfo("huobiBuyBtc is " + huobiBuyBtc);
			return info;
		}
		float huobiBtcNum2 = huobiBtc.getCoinNum();
		info.addInfo("huobiBtcNum2", huobiBtcNum2);
		// 进行提币
		boolean huobiWithdrawBtcRes = huobiBtc.withdrawCoin((huobiBtcNum2 - huobiBtcNum),
				Configs.API_CONFIG_BITFINEX.getWithdraw_addr_btc());
		if (!huobiWithdrawBtcRes) {
			info.addErrorInfo("huobiWithdrawBtcRes is " + huobiWithdrawBtcRes);
			return info;
		}
		log.debug("huobiWithdrawBtcRes is " + huobiWithdrawBtcRes);

		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		// 获取当前bitfinex比特币的数量
		IVirtualCoin bitfinexBtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		float bitfinexBtcNum = bitfinexBtc.getCoinNum();
		info.addInfo("bitfinexBtcNum", bitfinexBtcNum);
		// 查询bitfinex是否收到比特币
		float addBitfinexBtcNum = 0;
		float bitfinexBtcNum2 = 0;
		while (addBitfinexBtcNum <= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bitfinexBtcNum2 = bitfinexBtc.getCoinNum();
			log.debug("bitfinexBtcNum2 == " + bitfinexBtcNum2);
			addBitfinexBtcNum = bitfinexBtcNum2 - bitfinexBtcNum;
			if (addBitfinexBtcNum > 0) {
				info.addInfo("addBitfinexBtcNum", addBitfinexBtcNum);
			}
		}

		// 通过比特币转换到莱特币
		boolean bitfinexBtc2LtcRes = bitfinexBtc.exchange(ESymbol.BTC, bitfinexBtcNum2);
		if (!bitfinexBtc2LtcRes) {
			info.addErrorInfo("bitfinexBtc2LtcRes is " + bitfinexBtc2LtcRes);
			return info;
		}
		log.debug("bitfinexBtc2LtcRes is " + bitfinexBtc2LtcRes);
		// 获取莱特币的数量
		IVirtualCoin bitfinexLtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.LTC);
		float bitfinexLtcNum = bitfinexLtc.getCoinNum();
		info.addInfo("bitfinexLtcNum", bitfinexLtcNum);
		// 提取莱特币到火币网
		boolean bitfinexWithdrawLtcRes = bitfinexBtc.withdrawCoin(bitfinexLtcNum,
				Configs.API_CONFIG_HUOBI.getWithdraw_addr_ltc());
		if (!bitfinexWithdrawLtcRes) {
			info.addErrorInfo("bitfinexWithdrawLtcRes is " + bitfinexWithdrawLtcRes);
			return info;
		}
		log.debug("bitfinexWithdrawLtcRes is " + bitfinexWithdrawLtcRes);

		// 查询火币网是否收到莱特币
		IVirtualCoin huobiLtc = huobiFactory.getVirtualCoin(EHuobiSymbol.LTC);
		float huobiLtcNum = huobiLtc.getCoinNum();
		info.addInfo("huobiLtcNum", huobiLtcNum);
		float addhuobiLtcNum = 0;
		float huobiLtcNum2 = 0;
		while (addhuobiLtcNum <= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			huobiLtcNum2 = huobiLtc.getCoinNum();
			log.debug("huobiLtcNum2 == " + huobiLtcNum2);
			addhuobiLtcNum = huobiLtcNum2 - huobiLtcNum;
			if (addhuobiLtcNum > 0) {
				info.addInfo("addhuobiLtcNum", addhuobiLtcNum);
			}
		}
		// 卖掉已经收到的莱特币
		boolean huobiLtcSellRes = huobiLtc.sellMarket(null, addhuobiLtcNum);
		if (!huobiLtcSellRes) {
			info.addErrorInfo("huobiLtcSellRes is " + huobiLtcSellRes);
			return info;
		}
		log.debug("huobiLtcSellRes is " + huobiLtcSellRes);
		// 获取卖完后人民币的数量
		float huobiCnyNum2 = huobiCny.getCoinNum();
		info.addInfo("huobiCnyNum2", huobiCnyNum2);
		// 计算盈利情况
		info.addInfo("huobiBtc -> bitfinexBtc -> bitfinexLtc -> huobiLtc : profit is " + (huobiCnyNum2 - huobiCnyNum));
		return info;
	}

}
