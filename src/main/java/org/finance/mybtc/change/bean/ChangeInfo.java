/**
 * 
 */
package org.finance.mybtc.change.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zongtao liu
 *
 */
public class ChangeInfo {
	
	private static final int RES_SUCCESS = 1;
	
	private int res = RES_SUCCESS;
	
	private Map<String, Object> info;
	
	public void addInfo(String key,Object value){
		if(info == null){
			info = new HashMap<String, Object>();
		}
		info.put(key, value);
	}
	
	public void addInfo(Object value){
		addInfo("message", value);
	}
	
	public void addErrorInfo(Object value){
		addInfo(value);
		res = 0;
	}
	
	public boolean isOk(){
		if(res == RES_SUCCESS){
			return true;
		}
		return false;
	}
	
	public String getRes(){
		if(res == RES_SUCCESS){
			return "success";
		}
		return "fail";
	}

}
