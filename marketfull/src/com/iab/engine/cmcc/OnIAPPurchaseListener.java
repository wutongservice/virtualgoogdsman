package com.iab.engine.cmcc;

import java.util.HashMap;

import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import mm.purchasesdk.PurchaseCode;
import android.content.Context;
import android.util.Log;

import com.borqs.market.utils.BLog;
import com.iab.engine.MarketBillingResult;
import com.iab.engine.MarketPurchaseListener;

public class OnIAPPurchaseListener implements OnPurchaseListener {
    private final String TAG = OnIAPPurchaseListener.class.getSimpleName();
    private MarketPurchaseListener mpListener;
    public OnIAPPurchaseListener(MarketPurchaseListener mpListener) {
        this.mpListener = mpListener;
    }

    @Override
    public void onAfterApply() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAfterDownload() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBeforeApply() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBeforeDownload() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBillingFinish(int returnCode, HashMap  returnObject) {
        boolean isSuccess = false;
        MarketBillingResult billingResult = null;
        if (returnCode == PurchaseCode.ORDER_OK || (returnCode == PurchaseCode.AUTH_OK)) {
            isSuccess = true;
            billingResult = new MarketBillingResult(MarketBillingResult.TYPE_IAP);
            if (returnObject != null) {
                billingResult.orderId = (String) returnObject.get(OnPurchaseListener.ORDERID);
                billingResult.payCode = (String) returnObject.get(OnPurchaseListener.PAYCODE);
                billingResult.tradeId = (String) returnObject.get(OnPurchaseListener.TRADEID);
            }
        } else {
            isSuccess = false;
            BLog.d(TAG,Purchase.getReason(returnCode));
        }
        mpListener.onBillingFinished(isSuccess, billingResult);
    }

    @Override
    public void onQueryFinish(int code, HashMap arg1) {
        //TODO
    }

    @Override
    public void onInitFinish(int arg0) {
        BLog.d(TAG,Purchase.getReason(arg0));
    }

}
