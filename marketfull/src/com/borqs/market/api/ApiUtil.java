package com.borqs.market.api;

import java.util.Locale;

import android.content.Context;

import com.borqs.market.account.AccountSession;
import com.borqs.market.net.HttpManager;
import com.borqs.market.net.RequestListener;
import com.borqs.market.net.WutongParameters;
import com.borqs.market.utils.BLog;
import com.borqs.market.utils.MarketUtils;
import com.borqs.market.utils.QiupuConfig;
import com.borqs.market.utils.QiupuHelper;

public class ApiUtil extends WutongAPI {

    private static final String API_PRODUCTS_DETAIL = "api/v2/purchase/products/get";
    private static final String API_PRODUCTS_PURCHASE = "api/v2/purchase/purchase";
    private static final String API_ACCOUNT_ACTIVE = "api/v2/account/active";
    private static final String API_PRODUCTS_LIST = "api/v2/purchase/products/list";

    public void getProductList(Context context, final int page,
            final int count, final String category, final int versionCode,
            final String packageName, final RequestListener listener) {

        WutongParameters bundle = new WutongParameters();
        bundle.add("app_version", versionCode);
        if(!QiupuConfig.SEVER_DEBUG) {
            bundle.add("app_id", packageName);
            bundle.add("category_id", category);
        }else {
            bundle.add("app_id", "com.borqs.se");
        }
        if (MarketUtils.IS_ONLY_FREE) {
            bundle.add("paid", 1);
        }else {
            bundle.add("paid", 0);
        }
        bundle.add("page", String.valueOf(page));
        bundle.add("count", String.valueOf(count));
        bundle.add("ticket", AccountSession.account_session);
        bundle.add("locale", Locale.getDefault().toString());
        bundle.add("device_id", QiupuHelper.getDeviceID(context));
        request(getApiUrl(API_PRODUCTS_LIST), bundle,
                HttpManager.HTTPMETHOD_GET, listener, false);
    }

    public void getProductDetail(Context context, final String product_id,
            final int version, final RequestListener listener) {

        WutongParameters bundle = new WutongParameters();
        bundle.add("id", product_id);
        bundle.add("version", version);
        bundle.add("ticket", AccountSession.account_session);
        bundle.add("locale", Locale.getDefault().toString());
        bundle.add("device_id", QiupuHelper.getDeviceID(context));
        request(getApiUrl(API_PRODUCTS_DETAIL), bundle,
                HttpManager.HTTPMETHOD_GET, listener, false);
    }

    public void productPurchase(Context context, final String product_id,
            final int version,WutongParameters bundle, final RequestListener listener) {
        if(bundle == null) {
            BLog.e("productPurchase()  why is bundle null?");
            return;
        }
        bundle.add("id", product_id);
        bundle.add("version", version);
        bundle.add("ticket", AccountSession.account_session);
        bundle.add("device_id", QiupuHelper.getDeviceID(context));
        bundle.add("locale", Locale.getDefault().toString());
        request(getApiUrl(API_PRODUCTS_PURCHASE), bundle,
                HttpManager.HTTPMETHOD_GET, listener, false);
    }
    
    public void accountActiveByPhone(Context context,final String phoneNumber, 
            final RequestListener listener) {
        WutongParameters bundle = new WutongParameters();
        bundle.add("phone", phoneNumber);
        request(getApiUrl(API_ACCOUNT_ACTIVE), bundle,
                HttpManager.HTTPMETHOD_GET, listener, false);
    }
    public void accountActiveByGoogle(WutongParameters params, final RequestListener listener) {
        request(API_SERVER + API_ACCOUNT_ACTIVE, params,
                HttpManager.HTTPMETHOD_GET, listener, false);
    }

    public String getApiUrl(String url) {
        StringBuffer sb = new StringBuffer(API_SERVER);
        sb.append(url);
        return sb.toString();
    }

    private static ApiUtil instance = null;

    public synchronized static ApiUtil getInstance() {
        if (instance == null) {
            instance = new ApiUtil();
        }
        return instance;
    }

}
