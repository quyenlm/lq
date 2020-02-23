package com.tencent.imsdk.expansion.downloader.impl;

import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.IMLogger;

public class DownloadInfo {
    public int mControl;
    public long mCurrentBytes;
    public String mETag;
    public final String mFileName;
    public int mFuzz = Helpers.sRandom.nextInt(1001);
    public final int mIndex;
    boolean mInitialized;
    public long mLastMod;
    public int mNumFailed;
    public int mRedirectCount;
    public int mRetryAfter;
    public int mStatus;
    public long mTotalBytes;
    public String mUri;

    public DownloadInfo(int index, String fileName, String pkg) {
        this.mFileName = fileName;
        this.mIndex = index;
    }

    public void resetDownload() {
        this.mCurrentBytes = 0;
        this.mETag = "";
        this.mLastMod = 0;
        this.mStatus = 0;
        this.mControl = 0;
        this.mNumFailed = 0;
        this.mRetryAfter = 0;
        this.mRedirectCount = 0;
    }

    public long restartTime(long now) {
        if (this.mNumFailed == 0) {
            return now;
        }
        if (this.mRetryAfter > 0) {
            return this.mLastMod + ((long) this.mRetryAfter);
        }
        return this.mLastMod + ((long) ((this.mFuzz + 1000) * 30 * (1 << (this.mNumFailed - 1))));
    }

    public void logVerboseInfo() {
        IMLogger.v("Service adding new entry");
        IMLogger.v("FILENAME: " + this.mFileName);
        IMLogger.v("URI     : " + this.mUri);
        IMLogger.v("FILENAME: " + this.mFileName);
        IMLogger.v("CONTROL : " + this.mControl);
        IMLogger.v("STATUS  : " + this.mStatus);
        IMLogger.v("FAILED_C: " + this.mNumFailed);
        IMLogger.v("RETRY_AF: " + this.mRetryAfter);
        IMLogger.v("REDIRECT: " + this.mRedirectCount);
        IMLogger.v("LAST_MOD: " + this.mLastMod);
        IMLogger.v("TOTAL   : " + this.mTotalBytes);
        IMLogger.v("CURRENT : " + this.mCurrentBytes);
        IMLogger.v("ETAG    : " + this.mETag);
    }
}
