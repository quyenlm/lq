package com.tencent.component.net.download.multiplex.download;

import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.download.DownloadSections;
import com.tencent.component.net.download.multiplex.download.extension.FileUtils;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.component.net.download.multiplex.http.MttInputStream;
import com.tencent.component.net.download.multiplex.http.MttRequest;
import com.tencent.component.net.download.multiplex.http.MttResponse;
import com.tencent.component.net.download.multiplex.http.RequesterFactory;
import com.tencent.component.net.download.multiplex.task.Task;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Downloader extends Task {
    public static final int FIRE_THREHOLD = 1000;
    private static final int MAX_RETRY_TIMES = 5;
    private static final String TAG = "Downloader";
    private long mCurrentPos;
    private int mDownloaderId = -1;
    protected long mEndPos;
    private int mErrorCode = 0;
    private boolean mFinish = false;
    private boolean mHasTryNoRange = false;
    private int mHttpResponseCode = -1;
    private boolean mIsFirstSection = true;
    private long mLastFireTime;
    private int mReadTimeout = 30000;
    private int mRetryTimes = 0;
    protected long mStartPos;
    private DownloadTask mTask;
    private Thread mThread;

    public static Downloader createDownloader(DownloadTask task, DownloadSections.DownloadSection pos, int downloaderId) {
        return new Downloader(task, pos, downloaderId);
    }

    protected Downloader(DownloadTask task, DownloadSections.DownloadSection pos, int downloaderId) {
        setNeedNotfiyCanceled(true);
        this.mTask = task;
        this.mDownloaderId = downloaderId;
        this.mStartPos = pos.startPos;
        this.mEndPos = pos.getEndPos();
        this.mCurrentPos = pos.currentPos;
        this.mHttpResponseCode = -1;
        this.mMttRequest = new MttRequest();
        this.mMttRequest.setRequestType(MttRequest.REQUEST_FILE_DOWNLOAD);
        this.mMttRequest.setUrl(task.getTaskUrl());
        addObserver(this.mTask);
    }

    public void setHasTryNoRange(boolean hasTry) {
        this.mHasTryNoRange = hasTry;
    }

    public void setIsFirstSection(boolean first) {
        this.mIsFirstSection = first;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public int getDownloaderId() {
        return this.mDownloaderId;
    }

    public int getResponseCode() {
        return this.mHttpResponseCode;
    }

    public long getStartPos() {
        return this.mStartPos;
    }

    public long getEndPos() {
        return this.mEndPos;
    }

    public long getCurrentPos() {
        return this.mCurrentPos;
    }

    public void setStartPos(long startPos) {
        this.mStartPos = startPos;
    }

    public void setEndPos(long endPos) {
        this.mEndPos = endPos;
    }

    public void setCurrentPos(long currentPos) {
        this.mCurrentPos = currentPos;
    }

    public long getRemainingLen() {
        return this.mEndPos - this.mCurrentPos;
    }

    public void setFinished(boolean finished) {
        this.mFinish = finished;
    }

    public int getRetryTimes() {
        return this.mRetryTimes;
    }

    public String getTaskUrl() {
        return this.mMttRequest.getUrl();
    }

    public void start() {
        this.mThread = new Thread() {
            public void run() {
                Downloader.this.run();
            }
        };
        this.mThread.setName("downloader:" + this.mDownloaderId);
        this.mThread.start();
    }

    public void refreshThreadPriority() {
        if (this.mTask != null && this.mThread != null) {
            if (this.mTask.isForground()) {
                this.mThread.setPriority(5);
            } else {
                this.mThread.setPriority(1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void init() {
        if (this.mTask.isForground()) {
            Thread.currentThread().setPriority(5);
        } else {
            Thread.currentThread().setPriority(1);
        }
        this.mFinish = false;
        this.mErrorCode = 0;
        this.mCanceled = false;
    }

    /* access modifiers changed from: protected */
    public long getDownloadedSize() {
        return -1;
    }

    public synchronized void run() {
        long downloadedSize = getDownloadedSize();
        if (downloadedSize >= 0) {
            this.mTask.update(this.mDownloaderId, this.mCurrentPos, downloadedSize);
            this.mFinish = true;
            this.mStatus = 3;
            fireObserverEvent();
        } else {
            runDownload();
        }
    }

    /* access modifiers changed from: protected */
    public void runDownload() {
        String str;
        String str2;
        int statusCode;
        int len;
        DownloaderLog.w(TAG, "[Downloader] Start to connnect [id:" + this.mDownloaderId + "] start pos:" + this.mStartPos + " end pos:" + this.mEndPos + " current pos:" + this.mCurrentPos);
        this.mHttpResponseCode = -1;
        if (this.mEndPos <= 0 || this.mCurrentPos <= this.mEndPos) {
            init();
            this.mStatus = 1;
            fireObserverEvent();
            String referer = this.mTask.getReferer();
            if (!TextUtils.isEmpty(referer)) {
                this.mMttRequest.setReferer(referer);
            }
            if (!this.mTask.getIsSupportResume()) {
                this.mCurrentPos = 0;
            }
            this.mRetryTimes = 0;
            while (true) {
                this.mRequester = RequesterFactory.getRequester();
                this.mRequester.setIsWWWRequest(this.mTask.getIsWWW());
                this.mRequester.setReadTimeout(this.mReadTimeout);
                try {
                    DownloaderLog.d(TAG, "[Downloader] Start downloading from  [" + this.mCurrentPos + "] to  [" + this.mEndPos + "]");
                    if (isRangeNotSupported()) {
                        this.mMttRequest.removeHeader("Range");
                        this.mCurrentPos = 0;
                        DownloaderLog.d(TAG, "[Downloader] start no range request");
                    } else if (this.mEndPos == -1) {
                        this.mMttRequest.addHeader("Range", "bytes=" + this.mCurrentPos + "-");
                    } else {
                        this.mMttRequest.addHeader("Range", "bytes=" + this.mCurrentPos + "-" + this.mEndPos);
                    }
                    MttResponse mttResponse = this.mRequester.execute(this.mMttRequest);
                    setMttResponse(mttResponse);
                    statusCode = mttResponse.getStatusCode().intValue();
                    this.mHttpResponseCode = statusCode;
                    if (this.mCanceled) {
                        taskCanceled();
                        DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                        closeQuietly();
                        return;
                    } else if (statusCode == 200 || statusCode == 206) {
                        DownloaderLog.d(TAG, "[Downloader] Connect Success !");
                        DownloaderLog.d(TAG, "content-type: " + mttResponse.getContentType().getType() + Constants.URL_PATH_DELIMITER + mttResponse.getContentType().getTypeValue());
                        DownloaderLog.d(TAG, "content-disposition: " + mttResponse.getContentDisposition());
                        makeSureSectionLength(mttResponse);
                        if (isFirstDetectDownloader()) {
                            DownloaderLog.d(TAG, "[Downloader] first detect complete.");
                            if (!this.mTask.onFirstRequestFinished(this, mttResponse)) {
                                DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                closeQuietly();
                                return;
                            }
                        } else {
                            String originalETag = this.mTask.getETag();
                            String newETag = mttResponse.getETag();
                            DownloaderLog.d(TAG, "[Downloader] original etag:" + originalETag);
                            DownloaderLog.d(TAG, "[Downloader] new etag:" + newETag);
                            DownloaderLog.d(TAG, "[Downloader] downloader id:" + this.mDownloaderId);
                            if (this.mIsFirstSection && this.mDownloaderId == 0 && !TextUtils.isEmpty(originalETag) && !TextUtils.isEmpty(newETag) && !originalETag.equals(newETag)) {
                                notifyFileChanged();
                                DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                closeQuietly();
                                return;
                            }
                        }
                        try {
                            MttInputStream inputStream = mttResponse.getInputStream();
                            if (inputStream != null) {
                                byte[] buffer = new byte[8192];
                                boolean isEnd = false;
                                int readLen = 8192;
                                while (true) {
                                    if (this.mCanceled || (len = inputStream.read(buffer, 0, readLen)) <= 0 || this.mCanceled) {
                                        break;
                                    }
                                    try {
                                        this.mTask.writeDataFromNet(this.mDownloaderId, this.mCurrentPos, buffer, (long) len);
                                        if (this.mEndPos <= 0) {
                                            this.mCurrentPos += (long) len;
                                        } else if (this.mCurrentPos > this.mEndPos) {
                                            break;
                                        } else {
                                            if (this.mCurrentPos + ((long) len) > this.mEndPos + 1) {
                                                isEnd = true;
                                                len = (int) (((long) len) - (((this.mCurrentPos + ((long) len)) - this.mEndPos) - 1));
                                            }
                                            this.mCurrentPos += (long) len;
                                            long trail = this.mEndPos - this.mCurrentPos;
                                            if (trail > 0 && trail < PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                                                readLen = (int) trail;
                                            }
                                        }
                                        this.mTask.update(this.mDownloaderId, this.mCurrentPos, (long) len);
                                        long now = System.currentTimeMillis();
                                        if (!this.mCanceled && now - this.mLastFireTime > 1000) {
                                            this.mLastFireTime = now;
                                            this.mStatus = 2;
                                            fireObserverEvent();
                                            continue;
                                        }
                                        if (isEnd) {
                                            break;
                                        }
                                    } catch (IOException e) {
                                        this.mErrorCode = 5;
                                        if (FileUtils.getSdcardFreeSpace() < 2024) {
                                            this.mErrorCode = 2;
                                        }
                                        this.mStatus = 5;
                                        DownloaderLog.e(TAG, "[Downloader] Error status code:" + statusCode, e);
                                        fireObserverEvent();
                                        DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                        closeQuietly();
                                        return;
                                    }
                                }
                                inputStream.close();
                            }
                            if (!this.mCanceled) {
                                if (this.mTask.isFileExist()) {
                                    this.mFinish = true;
                                    DownloaderLog.i(TAG, "[Downloader] download complete, [id:" + this.mDownloaderId + "]");
                                    this.mStatus = 3;
                                    fireObserverEvent();
                                    break;
                                }
                                DownloaderLog.d(TAG, "[Downloader] File not exist after downloading.");
                                this.mCurrentPos = this.mStartPos;
                                this.mErrorCode = 4;
                                this.mStatus = 5;
                                fireObserverEvent();
                                DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                closeQuietly();
                                return;
                            }
                            taskCanceled();
                            DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                            closeQuietly();
                            return;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            if (((e2 instanceof SocketTimeoutException) || (e2 instanceof SocketException)) && Apn.isNetworkConnected() && this.mRetryTimes < 5) {
                                DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                closeQuietly();
                            } else {
                                this.mErrorCode = 3;
                                if (FileUtils.getSdcardFreeSpace() < 2024) {
                                    this.mErrorCode = 2;
                                }
                                if ((e2 instanceof IOException) && this.mErrorCode == 3 && Apn.isNetworkConnected() && this.mTask.getProgress() < 10) {
                                    this.mErrorCode = 7;
                                }
                                this.mStatus = 5;
                                DownloaderLog.e(TAG, "[Downloader] Error status code:" + statusCode, e2);
                                fireObserverEvent();
                                DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                                closeQuietly();
                                return;
                            }
                        }
                    } else if (statusCode < 300 || statusCode > 307) {
                        if (statusCode != 416) {
                            break;
                        } else if (this.mEndPos == this.mCurrentPos && this.mEndPos > 0) {
                            this.mStatus = 3;
                        }
                    } else {
                        String location = mttResponse.getLocation();
                        DownloaderLog.d(TAG, "[Downloader] Download Task request old url:" + this.mMttRequest.getUrl());
                        this.mMttRequest.setUrl(location);
                        DownloaderLog.d(TAG, "[Downloader] Download Task 302,location:" + location);
                        DownloaderLog.d(TAG, "[Downloader] Download Task request new url:" + this.mMttRequest.getUrl());
                        DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                        closeQuietly();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    if (this.mCanceled) {
                        taskCanceled();
                        DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                        closeQuietly();
                        return;
                    }
                    DownloaderLog.e(TAG, "[Downloader] Download task " + this.mRetryTimes + " failed - ", e3);
                    if (this.mRetryTimes < 5) {
                        try {
                            Thread.sleep(com.tencent.imsdk.expansion.downloader.Constants.ACTIVE_THREAD_WATCHDOG);
                        } catch (InterruptedException ie) {
                            DownloaderLog.e(TAG, "[Downloader] Interrupted while sleeping to retry - ", ie);
                        }
                        if (this.mCanceled) {
                            taskCanceled();
                            return;
                        }
                        this.mRetryTimes++;
                        if (Apn.isNetworkConnected()) {
                            if (this.mTask == null || !this.mTask.isRangeNotSupported() || this.mRetryTimes != 1 || !(e3 instanceof SocketTimeoutException)) {
                                this.mReadTimeout = 30000;
                            } else {
                                DownloaderLog.d(TAG, "[Downloader] reset read timeout to 100000");
                                this.mReadTimeout = InnerErrorCode.SDK_ERROR_BASIC_XG;
                            }
                        }
                    } else {
                        DownloaderLog.w(TAG, "[Downloader] request file length error, change the request without range!");
                        if (this.mEndPos == -1 && Apn.isNetworkConnected() && this.mTask != null && !this.mTask.isRangeNotSupported() && !this.mHasTryNoRange) {
                            this.mErrorCode = 10;
                            this.mHasTryNoRange = true;
                        } else if (!(e3 instanceof IOException) || !Apn.isNetworkConnected()) {
                            this.mErrorCode = 3;
                        } else {
                            this.mErrorCode = 7;
                        }
                        this.mStatus = 5;
                        fireObserverEvent();
                        DownloaderLog.w(TAG, "[Downloader] close connect [id:" + this.mDownloaderId + "]");
                        closeQuietly();
                        return;
                    }
                } finally {
                    String str3 = TAG;
                    str = "[Downloader] close connect [id:";
                    StringBuilder append = new StringBuilder().append(str).append(this.mDownloaderId);
                    str2 = "]";
                    DownloaderLog.w(str3, append.append(str2).toString());
                    closeQuietly();
                }
            }
            if ((statusCode == 416 || statusCode == 406) && this.mEndPos == -1) {
                this.mErrorCode = 10;
                this.mStatus = 5;
            } else {
                DownloaderLog.d(TAG, "[Downloader] download fail status code:" + statusCode);
                this.mErrorCode = 6;
                this.mStatus = 5;
            }
            fireObserverEvent();
            return;
        }
        this.mFinish = true;
        this.mStatus = 3;
        fireObserverEvent();
    }

    /* access modifiers changed from: protected */
    public void makeSureSectionLength(MttResponse mttResponse) {
    }

    /* access modifiers changed from: protected */
    public boolean isFirstDetectDownloader() {
        return this.mEndPos == -1;
    }

    /* access modifiers changed from: protected */
    public boolean isRangeNotSupported() {
        return this.mTask.isRangeNotSupported();
    }

    public boolean getIsCancelled() {
        return this.mCanceled;
    }

    private void notifyFileChanged() {
        this.mErrorCode = 8;
        this.mStatus = 5;
        DownloaderLog.d(TAG, "etag has changed!downloading from beginning again...");
        fireObserverEvent();
    }

    private void taskCanceled() {
        this.mStatus = 6;
        fireObserverEvent();
    }

    public boolean isFinish() {
        return this.mFinish;
    }

    public boolean isPending() {
        return false;
    }

    public void cancel() {
        DownloaderLog.d(TAG, "[Downloader] Cancel task.");
        if (!this.mCanceled) {
            DownloaderLog.d(TAG, "[Downloader] Cancel task implemented.");
            this.mCanceled = true;
            if (!isPending()) {
                if (this.mStatus == 1 || this.mStatus == 2) {
                    this.mStatus = 7;
                    fireObserverEvent();
                    return;
                }
                taskCanceled();
            }
        }
    }
}
