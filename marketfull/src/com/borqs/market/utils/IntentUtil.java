package com.borqs.market.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.borqs.market.ProductDetailActivity;

/**
 * Created by IntelliJ IDEA. User: b608 Date: 11-8-29 Time: 下午4:47 To change
 * this template use File | Settings | File Templates.
 */
public class IntentUtil {
    private final static String TAG = "IntentUtil";

    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";
    public static final String EXTRA_KEY_VERSION = "EXTRA_KEY_VERSION";
    public static final String EXTRA_KEY_NAME = "EXTRA_KEY_NAME";

    public static void startProductDetailActivity(Activity context,
            String product_id, int version_code,String name) {
        Intent intent = new Intent();
        intent.setClass(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_KEY_ID, product_id);
        intent.putExtra(EXTRA_KEY_VERSION, version_code);
        intent.putExtra(EXTRA_KEY_NAME, name);
        context.startActivity(intent);
    }

    public static void sendBroadCastforInstall(Context context, String fileUri,
            String productId) {
        Intent downIntent = new Intent(MarketUtils.ACTION_MARKET_THEME_INSTALL);
        downIntent.putExtra(MarketUtils.EXTRA_FILE_URI, fileUri);
        downIntent.putExtra(MarketUtils.EXTRA_PRODUCT_ID, productId);
        context.sendBroadcast(downIntent, null);
    }
    
    public static void sendBroadCastforApplyed(Context context, String fileUri,
            String productId) {
        Intent downIntent = new Intent(MarketUtils.ACTION_MARKET_THEME_APPLY);
        downIntent.putExtra(MarketUtils.EXTRA_FILE_URI, fileUri);
        downIntent.putExtra(MarketUtils.EXTRA_PRODUCT_ID, productId);
        context.sendBroadcast(downIntent, null);
    }

    public static void sendBroadCastDownloaded(Context context, String fileUri,
            String productId, int version_code, String version_name) {
        Intent downIntent = new Intent(
                MarketUtils.ACTION_MARKET_DOWNLOAD_COMPLETE);
        downIntent.putExtra(MarketUtils.EXTRA_FILE_URI, fileUri);
        downIntent.putExtra(MarketUtils.EXTRA_PRODUCT_ID, productId);
        downIntent.putExtra(MarketUtils.EXTRA_VERSION_CODE, version_code);
        downIntent.putExtra(MarketUtils.EXTRA_VERSION_NAME, version_name);
        context.sendBroadcast(downIntent, null);
    }

    public static void sendBroadCastDelete(Context context, String productId) {
        Intent downIntent = new Intent(MarketUtils.ACTION_MARKET_THEME_DELETE);
        downIntent.putExtra(MarketUtils.EXTRA_PRODUCT_ID, productId);
        context.sendBroadcast(downIntent, null);
    }
    
    public static void startWireLessSetting(Context context) {
        Intent intent=null;
        if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.HONEYCOMB){
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }else{
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings","com.android.settings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }
    
    public static void startAddAccountSetting(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_ADD_ACCOUNT);
        context.startActivity(intent);
    }

}
