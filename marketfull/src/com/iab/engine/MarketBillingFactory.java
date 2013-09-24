package com.iab.engine;

import android.app.Activity;
import android.content.Context;

import com.iab.engine.cmcc.IAPPurchase;
import com.iab.framework.MarketIab;

public class MarketBillingFactory {

    public static MarketBilling createMarketBilling(Activity act,int billType,MarketPurchaseListener mpListener) {
        if(billType == MarketBillingResult.TYPE_IAP) {
            return new IAPPurchase(act,mpListener);
        }else {
            return new MarketIab(act, mpListener);
        }
    }

}
