package com.iab.framework;

import android.app.Activity;
import android.util.Log;

import com.borqs.market.json.Product;
import com.iab.engine.MarketBilling;
import com.iab.engine.MarketBillingResult;
import com.iab.engine.MarketPurchaseListener;
import com.iab.engine.google.util.IabHelper;
import com.iab.engine.google.util.IabResult;
import com.iab.engine.google.util.Purchase;

public class MarketIab implements MarketBilling {
    private static final String TAG = MarketIab.class.getSimpleName();
    private Activity act;
    private MarketPurchaseListener mpListener;
    public MarketIab(Activity activity,MarketPurchaseListener mpListener) {
        act = activity;
        this.mpListener = mpListener;
    }
    @Override
    public void purchase(Product product) {
        final String sku = product.getSku(IabHelperFactory.GOOGLE);
        final String payload = product.getPayload();
        IabHelperFactory.launchPurchaseFlow(act, sku,
                new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
                
                if (result.isFailure()) {
                    IabHelperFactory.complain(act, "Error purchasing: " + result);
                    return;
                }
                if (!IabHelperFactory.verifyDeveloperPayload(act, purchase)) {
                    IabHelperFactory.complain(act, "Error purchasing. Authenticity verification failed.");
                    return;
                }
                MarketBillingResult billingResult = null;
                if(result.isSuccess()) {
                    billingResult = new MarketBillingResult(MarketBillingResult.TYPE_IAB);
                    if (purchase != null) {
                        billingResult.orderId = purchase.getOrderId();
                        billingResult.payCode = purchase.getSku();
                    }
                }
                mpListener.onBillingFinished(result.isSuccess(), billingResult);
                Log.d(TAG, "Purchase successful.");
            }
        }
        , payload);
        
    }


}
