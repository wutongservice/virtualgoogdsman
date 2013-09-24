package com.borqs.market.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DownloadInfoColumns implements BaseColumns {

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + DownLoadProvider.AUTHORITY + "/" + DownLoadProvider.TABLE_DOWNLOAD);

    public static final int DOWNLOAD_STATUS_DOWNLOADING = 0;
    public static final int DOWNLOAD_STATUS_COMPLETED = 1;

    public static final String NAME = "name";
    public static final String DOWNLOAD_ID = "download_id";
    public static final String PRODUCT_ID = "product_id";
    public static final String LOCAL_PATH = "local_path";
    public static final String TYPE = "type";
    public static final String SIZE = "size";
    public static final String VERSION_NAME = "version_name";
    public static final String VERSION_CODE = "version_code";
    public static final String DOWNLOAD_STATUS = "download_status";
    public static final String MD5_STRING = "md5_string";

    public static final String[] PROJECTION = { NAME, PRODUCT_ID, DOWNLOAD_ID,
            LOCAL_PATH, TYPE, SIZE, VERSION_NAME, VERSION_CODE, MD5_STRING,
            DOWNLOAD_STATUS };

}
