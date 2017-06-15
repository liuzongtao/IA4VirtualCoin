/**
 * 
 */
package org.finance.mybtc.api.huobi.btc_ltc;

/**
 * @author zongtao liu
 *
 */
public enum EHuobiStepType {

	/***
	 * 合并深度0
	 */
	STEP_0("step0"),
	/***
	 * 合并深度1
	 */
	STEP_1("step1"),
	/***
	 * 合并深度2
	 */
	STEP_2("step2"),
	/***
	 * 合并深度3
	 */
	STEP_3("step3"),
	/***
	 * 合并深度4
	 */
	STEP_4("step4"),
	/***
	 * 合并深度5
	 */
	STEP_5("step5"),
	;

	private String value;

	private EHuobiStepType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
