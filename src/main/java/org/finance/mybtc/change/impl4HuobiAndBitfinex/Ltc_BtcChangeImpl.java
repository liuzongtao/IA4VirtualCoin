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
import org.finance.mybtc.change.AChange;
import org.finance.mybtc.configs.Configs;
import org.finance.mybtc.constant.Const;

/**
 * @author zongtao liu
 *
 */
public class Ltc_BtcChangeImpl extends AChange {

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
		return Const.HUOBI_LTC_WITHDRAW_FEE;
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
	public boolean execExchange() {
		HuobiCoinFactory huobiFactory = HuobiCoinFactory.getInstance();
		// 获取当前人民币数量
		IVirtualCoin huobiCny = huobiFactory.getVirtualCoin(EHuobiSymbol.CNY_OLD);
		float huobiCnyNum = huobiCny.getCoinNum();
		log.debug("huobiCnyNum == " + huobiCnyNum);
		// 获取当前莱特币数量
		IVirtualCoin huobiLtc = huobiFactory.getVirtualCoin(EHuobiSymbol.LTC);
		float huobiLtcNum = huobiLtc.getCoinNum();
		log.debug("huobiLtcNum == " + huobiLtcNum);
		// 购买莱特币
		boolean huobiBuyLtc = huobiLtc.buyMarket(null, huobiCnyNum);
		if (!huobiBuyLtc) {
			log.error("huobiBuyLtc is " + huobiBuyLtc);
			return false;
		}
		float huobiLtcNum2 = huobiLtc.getCoinNum();
		log.debug("huobiLtcNum2 == " + huobiLtcNum2);
		// 进行提币
		boolean huobiWithdrawLtcRes = huobiLtc.withdrawCoin((huobiLtcNum2 - huobiLtcNum),
				Configs.API_CONFIG_BITFINEX.getWithdraw_addr_ltc());
		if (!huobiWithdrawLtcRes) {
			log.error("huobiWithdrawLtcRes is " + huobiWithdrawLtcRes);
			return false;
		}
		log.debug("huobiWithdrawLtcRes is " + huobiWithdrawLtcRes);

		BitfinexCoinFactory bitfinexFactory = BitfinexCoinFactory.getInstance();
		// 获取当前bitfinex莱特币的数量
		IVirtualCoin bitfinexLtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.LTC);
		float bitfinexLtcNum = bitfinexLtc.getCoinNum();
		log.debug("bitfinexLtcNum == " + bitfinexLtcNum);
		//查询bitfinex是否收到莱特币
		float addBitfinexLtcNum = 0;
		float bitfinexLtcNum2 = 0;
		while (addBitfinexLtcNum <= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bitfinexLtcNum2 = bitfinexLtc.getCoinNum();
			log.debug("bitfinexLtcNum2 == " + bitfinexLtcNum2);
			addBitfinexLtcNum = bitfinexLtcNum2 - bitfinexLtcNum;
			if (addBitfinexLtcNum > 0) {
				log.debug("bitfinex receive the ltc , num = " + addBitfinexLtcNum);
			}
		}
		// 通过莱特币转换到比特币
		boolean bitfinexLtc2BtcRes = bitfinexLtc.exchange(ESymbol.BTC, bitfinexLtcNum2);
		if (!bitfinexLtc2BtcRes) {
			log.error("bitfinexLtc2BtcRes is " + bitfinexLtc2BtcRes);
			return false;
		}
		log.debug("bitfinexLtc2BtcRes is " + bitfinexLtc2BtcRes);
		// 获取比特币的数量
		IVirtualCoin bitfinexBtc = bitfinexFactory.getVirtualCoin(EBitfinexCurrencies.BTC);
		float bitfinexBtcNum = bitfinexBtc.getCoinNum();
		log.debug("bitfinexBtcNum == " + bitfinexBtcNum);// 0.16
		// 提取比特币到火币网
		boolean bitfinexWithdrawBtcRes = bitfinexBtc.withdrawCoin(bitfinexBtcNum,
				Configs.API_CONFIG_HUOBI.getWithdraw_addr_btc());
		if (!bitfinexWithdrawBtcRes) {
			log.error("bitfinexWithdrawBtcRes is " + bitfinexWithdrawBtcRes);
			return false;
		}
		log.debug("bitfinexWithdrawBtcRes is " + bitfinexWithdrawBtcRes);

		// 查询火币网是否收到比特币
		IVirtualCoin huobiBtc = huobiFactory.getVirtualCoin(EHuobiSymbol.BTC);
		float huobiBtcNum = huobiBtc.getCoinNum();
		log.debug("huobiBtcNum == " + huobiBtcNum);
		float addhuobiBtcNum = 0;
		float huobiBtcNum2 = 0;
		while (addhuobiBtcNum <= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			huobiBtcNum2 = bitfinexLtc.getCoinNum();
			log.debug("huobiBtcNum2 == " + huobiBtcNum2);
			addhuobiBtcNum = huobiBtcNum2 - huobiBtcNum;
			if (addhuobiBtcNum > 0) {
				log.debug("huobi receive the btc , num = " + addhuobiBtcNum);
			}
		}
		// 卖掉已经收到的比特币
		boolean huobiBtcSellRes = huobiBtc.sellMarket(null, addhuobiBtcNum);
		if (!huobiBtcSellRes) {
			log.error("huobiBtcSellRes is " + huobiBtcSellRes);
			return false;
		}
		log.debug("huobiBtcSellRes is " + huobiBtcSellRes);
		// 获取卖完后人民币的数量
		float huobiCnyNum2 = huobiCny.getCoinNum();
		log.debug("huobiCnyNum2 == " + huobiCnyNum2);
		// 计算盈利情况
		log.debug("huobiLtc -> bitfinexLtc -> bitfinexBtc -> huobiBtc : profit is " + (huobiCnyNum2 - huobiCnyNum));
		return true;
	}

}
