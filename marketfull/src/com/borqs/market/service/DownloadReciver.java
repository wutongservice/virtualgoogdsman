package com.borqs.market.service;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Set;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.borqs.market.db.DownLoadHelper;
import com.borqs.market.db.DownloadInfoColumns;
import com.borqs.market.listener.DownloadListener;
import com.borqs.market.utils.BLog;
import com.borqs.market.utils.IntentUtil;
import com.borqs.market.utils.QiupuHelper;

public class DownloadReciver extends BroadcastReceiver {
    private static final String TAG = "DownloadReciver";
    DownloadManager dm = null;

    private static final HashMap<String, DownloadListener> listeners = new HashMap<String, DownloadListener>();

    public static void registerDownloadListener(String key,
            DownloadListener listener) {
        if (listeners.get(key) == null) {
            synchronized (listeners) {
                listeners.put(key, listener);
            }
        }
    }

    public static void unregisterDownloadListener(String key) {
        synchronized (listeners) {
            listeners.remove(key);
        }
    }

    private DownloadManager getDM(Context context) {
        if (dm == null)
            dm = (DownloadManager) context
                    .getSystemService(Context.DOWNLOAD_SERVICE);
        return dm;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BLog.d(TAG, "downloadComplete: onReceive CALLED");
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        Cursor downCusor = DownLoadHelper.getInstance(context)
                .queryDownloadingByDownloadId(id);
        String product_id = null;
        String version_name = null;
        int version_code = 0;
        if (downCusor.getCount() > 0 && downCusor.moveToFirst()) {
            product_id = downCusor.getString(downCusor
                    .getColumnIndexOrThrow(DownloadInfoColumns.PRODUCT_ID));
            version_name = downCusor.getString(downCusor
                    .getColumnIndexOrThrow(DownloadInfoColumns.VERSION_NAME));
            version_code = downCusor.getInt(downCusor
                    .getColumnIndexOrThrow(DownloadInfoColumns.VERSION_CODE));
        } else {
            downCusor.close();
            return;
        }
        if (downCusor != null) {
            downCusor.close();
        }
        Query query = new Query();
        query.setFilterById(id);

        Cursor c = getDM(context).query(query);
        if (c.moveToFirst()) {

            int statusIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = c.getInt(statusIndex);
            // BLog.d(TAG, "COLUMN_LOCAL_URI = " +
            // c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
            // BLog.d(TAG, "COLUMN_DESCRIPTION = " +
            // c.getString(c.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION)));
            // BLog.d(TAG, "COLUMN_MEDIAPROVIDER_URI = " +
            // c.getString(c.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI)));
            String local_uri = c.getString(c
                    .getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
            c.close();
            switch (status) {
            case DownloadManager.STATUS_SUCCESSFUL:
                File file = new File(URI.create(local_uri));
                String md5 = QiupuHelper.toMD5String(file);

                BLog.d(TAG,
                        "updateDownloadStatus: beginTime="
                                + System.currentTimeMillis());
                DownLoadHelper.getInstance(context).updateDownloadStatus(id,
                        DownloadInfoColumns.DOWNLOAD_STATUS_COMPLETED,
                        local_uri, md5, file.length());
                BLog.d(TAG,
                        "updateDownloadStatus: endTime="
                                + System.currentTimeMillis());

                updateActivityUI(true, local_uri);

                IntentUtil.sendBroadCastDownloaded(context, local_uri,
                        product_id, version_code, version_name);
                break;
            case DownloadManager.STATUS_FAILED:
                updateActivityUI(false, null);
                break;
            default:
                BLog.d(TAG, "_ID " + id + " completed with status " + status);
            }
        }
        if(c != null) {
            c.close();
        }
    }

    private void updateActivityUI(boolean success, String fileUri) {
        // BLog.d(TAG, "updateActivityUI msgcode:" + msgcode +
        // " listener count:" + listeners.size());
        synchronized (listeners) {
            Set<String> set = listeners.keySet();
            for (String key : set) {
                DownloadListener listener = listeners.get(key);
                if (listener != null) {
                    if (success) {
                        listener.downloadSuccess(fileUri);
                    } else {
                        listener.downloadFailed();
                    }
                }
            }
        }
    }
}
