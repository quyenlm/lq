package com.tencent.imsdk.tool.etc;

import java.net.URL;

class DownloadItem {
    public long mFileLength;
    public URL mFileUrl;
    public String mHashValue;
    public String mLocalFilePath;
    public float mPercent = 0.0f;

    public DownloadItem(URL url, String filePath, String hashValue) {
        this.mFileUrl = url;
        this.mLocalFilePath = filePath;
        this.mHashValue = hashValue;
    }

    public DownloadItem() {
    }

    public String toString() {
        return "DownloadItem{mPercent=" + this.mPercent + ", mFileUrl=" + this.mFileUrl + ", mHashValue='" + this.mHashValue + '\'' + ", mFileLength=" + this.mFileLength + ", mLocalFilePath='" + this.mLocalFilePath + '\'' + '}';
    }
}
