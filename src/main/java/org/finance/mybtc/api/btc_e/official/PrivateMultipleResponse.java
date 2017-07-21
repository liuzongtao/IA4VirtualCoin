package org.finance.mybtc.api.btc_e.official;

import java.io.InputStream;

abstract class PrivateMultipleResponse extends PrivateBaseClass {
    protected String current_position;

    protected synchronized void setData(InputStream json) {
        try {
            rootNode = null;
            it = null;
            localTimestamp = 0;
            success = false;
            String jsonStr = convertStreamToString(json);
            if(jsonStr.startsWith("{") || jsonStr.startsWith("[")){
            	log.debug("json is  " + jsonStr);
            	rootNode = mapper.readTree(jsonStr);
                if (rootNode.path("success").toString().equals("1")) {
                    it = rootNode.path("return").fieldNames();
                    if (it != null) {
                        localTimestamp = System.currentTimeMillis();
                        success = true;
                        return;
                    }
                } else if (getErrorMessage().length() != 0) {
                	log.error(getErrorMessage());
                    return;
                }
            }else{
            	log.error( "setData is not json ; jsonStr" + jsonStr); 
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        makeDefaultRootNode();
    }

    public synchronized String getCurrentAmount() {
        return formatDouble(rootNode.path("return").path(current_position)
                .path("amount").asDouble());
    }

    public synchronized boolean hasNext() {
        if (it != null) {
            return it.hasNext();
        }
        return false;
    }

    public synchronized void switchNext() {
        if (it != null) {
            current_position = it.next();
        }
    }

    public synchronized void switchReset() {
        it = rootNode.path("return").fieldNames();
    }

    public synchronized String getCurrentPosition() {
        return current_position;
    }
}
