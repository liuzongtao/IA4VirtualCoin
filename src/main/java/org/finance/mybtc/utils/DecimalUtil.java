/**
 * 
 */
package org.finance.mybtc.utils;

import java.math.BigDecimal;

/**
 * @author zongtao liu
 *
 */
public class DecimalUtil {
	
	public static float decimalDown(float num,int digit){
		BigDecimal b = new BigDecimal(num);
		return b.setScale(digit, BigDecimal.ROUND_DOWN).floatValue();
	}

}
