package com.borqs.market.listener;

public interface DownloadListener {
    public void downloadSuccess(String fileUri);

    public void downloadFailed();
}
