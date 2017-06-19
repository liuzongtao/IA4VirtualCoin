/**
 * 
 */
package org.finance.mybtc.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zongtao liu
 *
 */
public class DateUtil {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getCurDateTime(){
		return df.format(new Date());
	}

}
