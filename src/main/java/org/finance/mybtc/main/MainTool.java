/**
 * 
 */
package org.finance.mybtc.main;

import java.util.ArrayList;
import java.util.List;

import org.finance.mybtc.change.APfExchange;
import org.finance.mybtc.change.EPfType;
import org.finance.mybtc.change.PfFactory;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author zongtao liu
 *
 */
public class MainTool {

	private static Log log = Logs.get();

	public static void main(String[] args) {
		log.debug("args is " + Json.toJson(args, JsonFormat.compact()));
		String pf = getPfInfo(EPfType.okcoin2btce);
		if (args.length > 0) {
			pf = args[0].toLowerCase();
		}
		float num = 0;
		if (args.length > 1) {
			num = Float.valueOf(args[1]);
		}
		boolean isTest = false;
		if (args.length > 2) {
			isTest = Boolean.valueOf(args[2]);
		}
		float wishProfit = 1.5f;
		if (args.length > 3) {
			wishProfit = Float.valueOf(args[3]);
		}

		run(getTypeList(pf), num, isTest, wishProfit);
	}

	public static String getPfInfo(EPfType... ePfTypes) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (EPfType type : ePfTypes) {
			if (index != 0) {
				sb.append(",");
			}
			sb.append(type.toString());
			index++;
		}
		return sb.toString();
	}

	public static List<EPfType> getTypeList(String pfInfoStr) {
		List<EPfType> typeList = new ArrayList<EPfType>();
		String regex = ",";
		if (pfInfoStr.contains(regex)) {
			String[] pfInfoArr = pfInfoStr.split(regex);
			for (String pfInfo : pfInfoArr) {
				try {
					EPfType type = EPfType.valueOf(pfInfo);
					typeList.add(type);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		} else {
			try {
				EPfType type = EPfType.valueOf(pfInfoStr);
				typeList.add(type);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return typeList;
	}

	public static void run(List<EPfType> typeList, float num, boolean isTest, float wishProfit) {
		if (typeList == null || typeList.size() == 0) {
			log.error("typeList is null or size is 0 !");
			return ;
		}
		log.debug("num == " + num + " ;isTest == " + isTest + " ;wishProfit == " + wishProfit);
		List<APfExchange> exchangeList = new ArrayList<APfExchange>();
		for (EPfType type : typeList) {
			APfExchange pfExchange = PfFactory.getInstance().getPfExchange(type);
			if (pfExchange == null) {
				log.error("pfExchange is null ; type == " + type);
			} else {
				exchangeList.add(pfExchange);
				log.debug("pfExchange is " + pfExchange.getClass().getSimpleName());
			}
		}
		while (true) {
			try {
				long sleepTime = 30 * 1000l;
				for (APfExchange pfExchange : exchangeList) {
					boolean execExchange = pfExchange.execExchange(num, isTest, wishProfit);
					if (execExchange) {
						sleepTime = 0;
					}
				}
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
