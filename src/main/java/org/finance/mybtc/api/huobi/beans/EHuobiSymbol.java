/**
 * 
 */
package org.finance.mybtc.api.huobi.beans;

/**
 * @author zongtao liu
 *
 */
public enum EHuobiSymbol {
	
	BTC("btccny"),
	
	LTC("ltccny"),
	
	ETH("ethcny"),
	
	CNY_OLD("cny4old"),
	
	CNY_NEW("cny4new"),
	
	;
	
	private String value;
	
	private EHuobiSymbol(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
