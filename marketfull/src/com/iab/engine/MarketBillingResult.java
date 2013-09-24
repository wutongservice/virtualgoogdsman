package com.iab.engine;

public class MarketBillingResult {
    
    public static final int TYPE_IAB = 0;
    public static final int TYPE_IAP = 1;
    public String payCode;
    public String orderId;
    public String tradeId;
    public int billingType;
    
    public MarketBillingResult(int billingType) {
        super();
        this.billingType = billingType;
    }
    
}
