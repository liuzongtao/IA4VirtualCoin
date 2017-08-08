/**
 * 
 */
package org.finance.mybtc.main;

import org.finance.mybtc.change.APfExchange;
import org.finance.mybtc.change.EPfType;
import org.finance.mybtc.change.Huobi2Bitfinex;
import org.finance.mybtc.change.Huobi2Btce;
import org.finance.mybtc.change.Okcoin2Bitfinex;
import org.finance.mybtc.change.Okcoin2Btce;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class Test4Allpf {

	private static Log log = Logs.get();

	public static void main(String[] args) {
		log.debug("args is " + Json.toJson(args, JsonFormat.compact()));
		String pf = EPfType.okcoin2bitfinex.toString();
		if (args.length > 0) {
			pf = args[0].toLowerCase();
		}
		String type = "b2l";
		if (args.length > 1) {
			type = args[1].toLowerCase();
		}
		// 运行
		run(pf, type);
	}

	public static void run(String pf, String type) {
		log.debug("pf == " + pf + " ; type == " + type);
		EPfType pfType = EPfType.valueOf(pf);
		APfExchange pfexchange = null;
		switch (pfType) {
		case huobi2btce:
			pfexchange = new Huobi2Btce();
			break;
		case huobi2bitfinex:
			pfexchange = new Huobi2Bitfinex();
			break;
		case okcoin2btce:
			pfexchange = new Okcoin2Btce();
			break;
		case okcoin2bitfinex:
			pfexchange = new Okcoin2Bitfinex();
			break;
		default:
			break;
		}
		if (pfexchange != null) {
			log.info("APfExchange is " + pfexchange.getClass().getSimpleName());
			pfexchange.execExchangeByType(type);
		} else {
			log.error("pfexchange is null !! ");
		}
	}

}
