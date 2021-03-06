package org.finance.mybtc.api.btc_e.official;

import java.io.InputStream;
import java.util.ArrayList;

abstract class PrivateSingleResponse extends PrivateBaseClass {
    private ArrayList<String> currencyNames;

    PrivateSingleResponse() {
        currencyNames = new ArrayList<String>();
    }

    protected synchronized void setData(InputStream json) {
        try {
            currencyNames.clear();
            rootNode = null;
            it = null;
            localTimestamp = 0;
            success = false;
            String jsonStr = convertStreamToString(json);
            if(jsonStr.startsWith("{") || jsonStr.startsWith("[")){
            	log.debug("json is  " + jsonStr);
            	rootNode = mapper.readTree(jsonStr);
                if (rootNode.path("success").toString().equals("1")) {
                    it = rootNode.path("return").path("funds").fieldNames();
                    if (it != null) {
                        while (it.hasNext()) {
                            currencyNames.add(convertPairName(it.next()));
                        }
                        localTimestamp = System.currentTimeMillis();
                        success = true;
                        return;
                    }
                } else if (getErrorMessage().length() != 0) {
                	log.error(getErrorMessage());
                    return;
                }
            }else{
            	log.error( "setData is not json ; jsonStr == " + jsonStr); 
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        makeDefaultRootNode();
    }
    
    public synchronized ArrayList<String> getCurrencyList() {
        return currencyNames;
    }

    /**
     * Return user's balance.
     *
     * @param currency - btc, ltc, etc.
     * @return balance for selected currency.
     */
    public synchronized String getBalance(String currency) {
        return formatDouble(rootNode.path("return").path("funds")
                .path(currency.toLowerCase()).asDouble());
    }
}
