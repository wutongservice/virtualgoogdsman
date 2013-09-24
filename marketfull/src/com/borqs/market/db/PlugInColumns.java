package com.borqs.market.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class PlugInColumns implements BaseColumns {

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + DownLoadProvider.AUTHORITY + "/" + DownLoadProvider.TABLE_PLUGIN);

    public static final String NAME = "name";
    public static final String PRODUCT_ID = "product_id";
    public static final String VERSION_NAME = "version_name";
    public static final String VERSION_CODE = "version_code";
    public static final String TYPE = "type";
    public static final String IS_APPLY = "is_apply";

    public static final String[] PROJECTION = { NAME, PRODUCT_ID,VERSION_NAME, VERSION_CODE,
        TYPE,IS_APPLY};

}
