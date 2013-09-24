package com.borqs.market.db;

import com.borqs.market.json.Product;
import com.borqs.market.utils.MarketUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class DownLoadHelper {
    private final static String TAG = "DownLoadHelper";
    private static DownLoadHelper _instance;
    private Context mContext;

    private DownLoadHelper(Context context) {
        mContext = context;
    }

    public DownLoadHelper(Context context, boolean forSync) {
        mContext = context;
    }

    public static DownLoadHelper getInstance(Context context) {
        if (_instance == null) {
            _instance = new DownLoadHelper(context);
        }

        return _instance;
    }

    public static void closeCursor(Cursor cursor) {
        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }

    public void insert(Product p, String path, long down_id) {
        ContentValues values = new ContentValues();
        values.put(DownloadInfoColumns.NAME, p.name);
        values.put(DownloadInfoColumns.LOCAL_PATH, path);
        values.put(DownloadInfoColumns.PRODUCT_ID, p.product_id);
        values.put(DownloadInfoColumns.DOWNLOAD_ID, down_id);
        values.put(DownloadInfoColumns.TYPE, p.type);
        values.put(DownloadInfoColumns.SIZE, p.size);
        values.put(DownloadInfoColumns.VERSION_NAME, p.version_name);
        values.put(DownloadInfoColumns.VERSION_CODE, p.version_code);
        values.put(DownloadInfoColumns.DOWNLOAD_STATUS,
                DownloadInfoColumns.DOWNLOAD_STATUS_DOWNLOADING);
        if(isHasDownloadFile(p.product_id)) {
            String where = DownloadInfoColumns.PRODUCT_ID + "='" + p.product_id
                    + "'";
            mContext.getContentResolver().update(DownloadInfoColumns.CONTENT_URI,
                    values, where, null);
        }else {
            mContext.getContentResolver().insert(DownloadInfoColumns.CONTENT_URI,
                    values);
        }
    }

    public void delete(String name) {
        mContext.getContentResolver().delete(DownloadInfoColumns.CONTENT_URI,
                DownloadInfoColumns.NAME + " = '" + name + "'", null);
    }

    public void update(ContentValues values, String where) {
        mContext.getContentResolver().update(DownloadInfoColumns.CONTENT_URI,
                values, where, null);
    }

    public void updateDownloadStatus(long downloadID, int status, String uri,
            String md5, long size) {
        ContentValues values = new ContentValues();
        String where = DownloadInfoColumns.DOWNLOAD_ID + "='" + downloadID
                + "'";
        values.put(DownloadInfoColumns.DOWNLOAD_STATUS, status);
        values.put(DownloadInfoColumns.LOCAL_PATH, uri);
        values.put(DownloadInfoColumns.MD5_STRING, md5);
        values.put(DownloadInfoColumns.SIZE, size);
        mContext.getContentResolver().update(DownloadInfoColumns.CONTENT_URI,
                values, where, null);
    }

    public Cursor queryDownloadingByDownloadId(long downloadID) {
        String where = DownloadInfoColumns.DOWNLOAD_ID + "='" + downloadID
                + "' and " + DownloadInfoColumns.DOWNLOAD_STATUS + "='"
                        + DownloadInfoColumns.DOWNLOAD_STATUS_DOWNLOADING + "'";
        return queryFile(where);
    }

    public boolean isHasDownloadFile(String productID) {
        String where = DownloadInfoColumns.PRODUCT_ID + "='" + productID + "'";
        Cursor c = queryFile(where);
        if (c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    public void deleteFile(String productID, int version_code) {
        String where = DownloadInfoColumns.PRODUCT_ID + "='" + productID
                + "' and " + DownloadInfoColumns.VERSION_CODE + "='" + version_code
                + "' and " + DownloadInfoColumns.DOWNLOAD_STATUS + "='"
                + DownloadInfoColumns.DOWNLOAD_STATUS_COMPLETED + "'";
        mContext.getContentResolver().delete(DownloadInfoColumns.CONTENT_URI,
                where, null);
    }
    
    public void cancleDownloadFile(String productID) {
        String where = DownloadInfoColumns.PRODUCT_ID + "='" + productID +  "'";
        mContext.getContentResolver().delete(DownloadInfoColumns.CONTENT_URI,
                where, null);
    }

    public Cursor queryDownloadFile(String productID) {
        String where = DownloadInfoColumns.PRODUCT_ID + "='" + productID
                + "'";
        String orderby = DownloadInfoColumns.VERSION_CODE + " desc," + DownloadInfoColumns.VERSION_NAME + " desc";
        Cursor c = mContext.getContentResolver().query(
                DownloadInfoColumns.CONTENT_URI,
                DownloadInfoColumns.PROJECTION, where, null,orderby);
        return c;
    }

    // public int queryDownloadStatus(String name) {
    // int result = 0;
    // String where = DownloadInfoColumns.NAME + "='" + name + "'";
    // Cursor cursor = queryFile(where);
    // if (cursor.moveToFirst()) {
    // result =
    // cursor.getInt(cursor.getColumnIndexOrThrow(DownloadInfoColumns.DOWNLOAD_STATUS));
    // }
    // return result;
    // }
    //
    public Cursor queryFile(String where) {
        Cursor cursor = mContext.getContentResolver().query(
                DownloadInfoColumns.CONTENT_URI,
                DownloadInfoColumns.PROJECTION, where, null, null);
        return cursor;
    }


    public Cursor queryTheme(String productID) {
        String where = PlugInColumns.PRODUCT_ID + " ='" + productID
                + "'";
        String orderby = PlugInColumns.VERSION_CODE + " desc ";
        Cursor cursor = mContext.getContentResolver().query(PlugInColumns.CONTENT_URI,PlugInColumns.PROJECTION, where, null, orderby);
        return cursor;
    }

}
