package com.borqs.market.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.text.TextUtils;

import com.borqs.market.MarketHomeActivity;
import com.borqs.market.ProductListActivity;
import com.borqs.market.db.PlugInColumns;
import com.borqs.market.db.PlugInInfo;
import com.borqs.market.json.Product.ProductType;

public class MarketUtils {
    @SuppressWarnings("unused")
    private final static String TAG = "MarketUtils";

    public static void setLogVisibility(boolean show) {
        BLog.setSHOW_LOG(show);
    }
    
    public static void showPerformanceLog(boolean show) {
        BLog.setShowPerformanceLog(show);
    }

    public static final String EXTRA_APP_VERSION = "EXTRA_APP_VERSION";
    public static final String EXTRA_PACKAGE_NAME = "EXTRA_PACKAGE_NAME";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    public static final String CATEGORY_THEME = ProductType.THEME;
    public static final String CATEGORY_OBJECT = ProductType.OBJECT;
    public static final String CATEGORY_SCENE = ProductType.SCENE;
    
    public static final String DOWNLOAD_AUTHORITY = "com.borqs.freehdhome.download";//请更改为androidManifest中定义的DownloadProvider的authority
    
    public static boolean IS_ONLY_FREE = false; //是否只取免费的

    /**
     * 
     * @param context
     * @param version_code
     *            application version code
     * @param package_name
     *            application package name
     * @param category
     *            MarketUtils.CATEGORY_THEME MarketUtils.CATEGORY_OBJECT
     *            MarketUtils.CATEGORY_SCENE
     */
    public static void startMarketIntent(Context context, String package_name, String category,boolean isOnlyFree) {
        IS_ONLY_FREE = isOnlyFree;
        if (TextUtils.isEmpty(package_name)) {
            throw new IllegalArgumentException("package name is null");
        }
        int version_code = 0;
        try {
            version_code = context.getPackageManager()
                .getPackageInfo(package_name,PackageManager.GET_UNINSTALLED_PACKAGES).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, MarketHomeActivity.class);
        intent.putExtra(EXTRA_APP_VERSION, version_code);
        intent.putExtra(EXTRA_PACKAGE_NAME, package_name);
        intent.putExtra(EXTRA_CATEGORY, category);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    /**
     * 
     * @param context
     * @param version_code
     *            application version code
     * @param package_name
     *            application package name
     * @param category
     *            MarketUtils.CATEGORY_THEME MarketUtils.CATEGORY_OBJECT
     *            MarketUtils.CATEGORY_SCENE
     */
    public static void startProductListIntent(Context context, String package_name, String category,boolean isOnlyFree) {
        IS_ONLY_FREE = isOnlyFree;
        if (TextUtils.isEmpty(package_name)) {
            throw new IllegalArgumentException("package name is null");
        }
        int version_code = 0;
        try {
            version_code = context.getPackageManager()
                .getPackageInfo(package_name,PackageManager.GET_UNINSTALLED_PACKAGES).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(EXTRA_APP_VERSION, version_code);
        intent.putExtra(EXTRA_PACKAGE_NAME, package_name);
        intent.putExtra(EXTRA_CATEGORY, category);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    public static final String EXTRA_FILE_URI = "EXTRA_FILE_URI";
    public static final String EXTRA_FILE_NAME = "EXTRA_FILE_NAME";
    public static final String EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID";
    public static final String EXTRA_VERSION_CODE = "EXTRA_VERSION_CODE";
    public static final String EXTRA_VERSION_NAME = "EXTRA_VERSION_NAME";
    public static final String ACTION_MARKET_DOWNLOAD_COMPLETE = MarketConfiguration.getPackageName() + ".market.intent.action.DOWNLOAD_COMPLETE";
    public static final String ACTION_MARKET_THEME_INSTALL = MarketConfiguration.getPackageName() + ".market.intent.action.INSTALL";
    public static final String ACTION_MARKET_THEME_APPLY = MarketConfiguration.getPackageName() + ".market.intent.action.APPLY";
    public static final String ACTION_MARKET_THEME_DELETE = MarketConfiguration.getPackageName() + ".market.intent.action.DELETE";

    public static void insertPlugIn(Context context, PlugInInfo plugin) {
        ContentValues pluginValues = new ContentValues();
        pluginValues.put(PlugInColumns.NAME, plugin.name);
        pluginValues.put(PlugInColumns.PRODUCT_ID, plugin.product_id);
        pluginValues.put(PlugInColumns.VERSION_NAME, plugin.version_name);
        pluginValues.put(PlugInColumns.VERSION_CODE, plugin.version_code);
        pluginValues.put(PlugInColumns.TYPE, plugin.type);
        pluginValues.put(PlugInColumns.IS_APPLY,plugin.is_apply?1:0);
        context.getContentResolver().insert(PlugInColumns.CONTENT_URI, pluginValues);
    }
    
    public static void updatePlugIn(Context context, String id, boolean isApply) {
        ContentValues values = new ContentValues();
        String where = PlugInColumns.PRODUCT_ID + " = '" + id + "'";
        values.put(PlugInColumns.IS_APPLY,isApply?1:0);
        context.getContentResolver().update(PlugInColumns.CONTENT_URI, values, where, null);
    }
    
    public static void updatePlugInVersion(Context context, String id, int versionCode, String versionName) {
        ContentValues values = new ContentValues();
        String where = PlugInColumns.PRODUCT_ID + " = '" + id + "'";
        values.put(PlugInColumns.VERSION_CODE,versionCode);
        values.put(PlugInColumns.VERSION_NAME,versionName);
        context.getContentResolver().update(PlugInColumns.CONTENT_URI, values, where, null);
    }
    
    public static void deletePlugIn(Context context, String id) {
        String where = PlugInColumns.PRODUCT_ID + " = '" + id + "'";
        context.getContentResolver().delete(PlugInColumns.CONTENT_URI, where, null);
    }
    
    public static Cursor queryPlugIn(Context context, String id) {
        String where = PlugInColumns.PRODUCT_ID + " = '" + id + "'";
        return context.getContentResolver().query(PlugInColumns.CONTENT_URI, PlugInColumns.PROJECTION, where, null, null);
    }

}
