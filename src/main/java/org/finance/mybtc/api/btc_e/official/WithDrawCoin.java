package org.finance.mybtc.api.btc_e.official;

public class WithDrawCoin extends PrivateMultipleResponse {
    public WithDrawCoin(PrivateNetwork nt) {
        network = nt;
    }

    /**
     * @param str
     */
    public synchronized void setCoinName(String str) {
        paramsMap.put("coinName", str);
    }

    /**
     * @param str
     */
    public synchronized void setAmount(String str) {
        paramsMap.put("amount", str);
    }

    public synchronized void setaddress(String str) {
        paramsMap.put("address", str);
    }

    public synchronized boolean runMethod() {
        setData(network.sendRequest("WithdrawCoin", getParams(),
                connectTimeoutMillis, readTimeoutMillis));
        return success;
    }

    public synchronized String getAmountSent() {
        return formatDouble(rootNode.path("return").path("amountSent").asDouble());
    }

    public synchronized int getTid() {
        return rootNode.path("return").path("tId").asInt();
    }
}
