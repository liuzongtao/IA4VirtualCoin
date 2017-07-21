package org.finance.mybtc.api.btc_e.official;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class PrivateBaseClass extends CommonClass {
    protected boolean success;
    protected HashMap<String, String> paramsMap;
    protected StringBuilder paramsBuf;
    protected Iterator<String> it;
    protected PrivateNetwork network;

    PrivateBaseClass() {
        paramsMap = new HashMap<String, String>();
        paramsBuf = new StringBuilder();
    }

    /**
     * Send request.
     *
     * @return TRUE if positive answer is received. FALSE if has ANY trouble. If
     * getErrorMessage().length()==0 it's network troubles.
     */
    protected synchronized boolean runMethod() {
        return false;
    }

    protected String getParams() {
        paramsBuf.delete(0, paramsBuf.length());
        Set<Map.Entry<String, String>> set = paramsMap.entrySet();
        for (Map.Entry<String, String> me : set) {
            paramsBuf.append(me.getKey()).append("=");
            paramsBuf.append(me.getValue()).append("&");
        }
        return paramsBuf.toString();
    }

    /**
     * You can set data for object manually, if need
     *
     * @param json in JSON format
     * @return true if this data success=1
     */
    public synchronized boolean setData(String json) {
        InputStream stream = new ByteArrayInputStream(json.getBytes());
        setData(stream);
        return success;
    }

    protected void setData(InputStream json) {
    }

    /**
     * Reset parameters, such as count, pair, type and so on
     */
    public synchronized void resetParams() {
        paramsMap.clear();
    }

    public synchronized boolean isSuccess() {
        return success;
    }

    /**
     * Error message from server if success=0 and it's not connection trouble.
     *
     * @return error message
     */
    public synchronized String getErrorMessage() {
        return rootNode.path("error").asText();
    }
    
    public String convertStreamToString(InputStream is) {
    	if(is == null){
    		return "";
    	}
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			int index = 0;
			while ((line = reader.readLine()) != null) {
				if (index != 0) {
					sb.append("\n");
				}
				sb.append(line);
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
