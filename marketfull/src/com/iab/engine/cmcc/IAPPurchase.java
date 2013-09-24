package com.iab.engine.cmcc;

import mm.purchasesdk.Purchase;
import android.content.Context;

import com.borqs.market.json.Product;
import com.iab.engine.MarketBilling;
import com.iab.engine.MarketPurchaseListener;

public class IAPPurchase implements MarketBilling{
    
    private Context context;
    private OnIAPPurchaseListener iapListener;
    private MarketPurchaseListener mpListener;
    private Purchase purchase;
    //计费信息 
    private static final String APPID = "300002827279";
    private static final String APPKEY = "204A8CE055D68211";
    
    public IAPPurchase(Context ctx,MarketPurchaseListener mpListener) {
        context = ctx;
        this.mpListener = mpListener;
        init();
    }
    
    private void init() {
        iapListener = new OnIAPPurchaseListener(mpListener);
        purchase = Purchase.getInstance();
        
        try {
            purchase.setAppInfo(APPID, APPKEY);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        purchase.init(context, iapListener);
    }
    
    @Override
    public void purchase(Product product) {
        try {
            purchase.order(context, product.mm_paycode,product.mm_paycode_count,iapListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
