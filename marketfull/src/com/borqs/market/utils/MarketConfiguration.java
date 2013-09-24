package com.borqs.market.utils;

import java.io.File;

import android.content.Context;

public final class MarketConfiguration {
    
    private static Context context;
    private static File cacheDirctory;
    private static File downloadDirctory;
    private static String packageName;
    private static File externalDir;
    
    public static void init(Context mContext) {
        context = mContext.getApplicationContext();
        packageName = context.getPackageName();
//        MarketConfiguration.cacheDirctory = StorageUtils.getIndividualCacheDirectory(context,null);
//        MarketConfiguration.downloadDirctory = StorageUtils.getIndividualDownloadDirectory(context,null);
    }
    public static void setExternalFilesDir(File externalFilesDir) {
        if(context == null) {
            throw new IllegalAccessError("context is null,plase call init()");
        }else {
            if(externalFilesDir != null) {
//                MarketConfiguration.cacheDirctory = StorageUtils.getIndividualCacheDirectory(context,externalFilesDir.getPath());
//                MarketConfiguration.downloadDirctory = StorageUtils.getIndividualDownloadDirectory(context,externalFilesDir.getPath());
                externalDir = externalFilesDir;
            }
        }
    }
    public static File getCacheDirctory() {
        if(cacheDirctory == null) {
            if(externalDir != null) {
                cacheDirctory = StorageUtils.getIndividualCacheDirectory(context,externalDir.getPath());
            }else {
                cacheDirctory = StorageUtils.getIndividualCacheDirectory(context,null);
            }
        }else {
            if(!cacheDirctory.exists()) {
                cacheDirctory.mkdirs();
            }
        }
        return cacheDirctory;
    }
    
    public static File getDownloadDirctory() {
        if(downloadDirctory == null) {
            if(externalDir != null) {
                downloadDirctory = StorageUtils.getIndividualDownloadDirectory(context,externalDir.getPath());
            }else {
                downloadDirctory = StorageUtils.getIndividualDownloadDirectory(context,null);
            }
        }else {
            if(!downloadDirctory.exists()) {
                downloadDirctory.mkdirs();
            }
        }
        return downloadDirctory;
    }
    public static String getPackageName() {
        return packageName;
    }
    
    
}
