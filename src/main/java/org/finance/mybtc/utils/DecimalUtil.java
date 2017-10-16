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
	
	public static double decimalDown(double num,int digit){
		BigDecimal b = new BigDecimal(num);
		return b.setScale(digit, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	public static float decimalDown(String val,int digit){
		BigDecimal b = new BigDecimal(val);
		return b.setScale(digit, BigDecimal.ROUND_DOWN).floatValue();
	}
	
	public static void main(String[] args) {
		System.out.println(decimalDown(10.2360000123d,8));
	}

}
