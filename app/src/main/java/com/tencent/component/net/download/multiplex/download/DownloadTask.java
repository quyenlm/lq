package com.tencent.component.net.download.multiplex.download;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.component.net.download.multiplex.download.DownloadSections;
import com.tencent.component.net.download.multiplex.download.extension.FileUtils;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.component.net.download.multiplex.http.MttRequest;
import com.tencent.component.net.download.multiplex.http.MttResponse;
import com.tencent.component.net.download.multiplex.task.Task;
import com.tencent.component.net.download.multiplex.task.TaskObserver;
import com.tencent.tp.a.h;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadTask extends Task implements TaskObserver {
    private static final Pattern CONTENT_RANGE_PATTERN = Pattern.compile("[^\\d]*(\\d+)\\-(\\d+)\\/(\\d+|\\*)");
    public static final String DL_FILE_SUFFIX = ".qbdltmp";
    public static final int ERRORCODE_CHECK_DIFF_FILE_ERROR = 12;
    public static final int ERRORCODE_ERROR_ETAG_CHANGED = 8;
    public static final int ERRORCODE_ERROR_RENAME_FAILED = 9;
    public static final int ERRORCODE_FILE_DELETED = 4;
    public static final int ERRORCODE_NETWORK_ERROR = 3;
    public static final int ERRORCODE_NONE = 0;
    public static final int ERRORCODE_NO_SDCARD = 1;
    public static final int ERRORCODE_NO_SPACE = 2;
    public static final int ERRORCODE_PARSE_M3U8_ERROR = 11;
    public static final int ERRORCODE_RANGE_NOT_SUPPORTED = 10;
    public static final int ERRORCODE_RESPONSE_ERROR = 6;
    public static final int ERRORCODE_UNEXPECTED_ERROR = 7;
    public static final int ERRORCODE_UNKNOWN = 101;
    public static final int ERRORCODE_WRITE_EXCEPTION = 5;
    public static final long FILE_SIZE_UNSPECIFIED = -1;
    public static final int FLAG_DELTA_UPDATE_FILED_TASK = 1024;
    public static final int FLAG_HAS_INSTALLED = 64;
    public static final int FLAG_HIDE_FILE = 32;
    public static final int FLAG_IS_M3U8 = 512;
    public static final int FLAG_NORMAL_FILE = 0;
    public static final int FLAG_PRE_DOWNLOAD_TASK = 4096;
    public static final int FLAG_QQBROWSER_DELTA_UPDATE = 2048;
    public static final int FLAG_QQMARKET_DELTA_UPDATE = 256;
    public static final int FLAG_QQMARKET_TASK_FILE = 16;
    public static final int FLAG_RANGE_NOT_SUPPORT = 128;
    public static final int FLAG_RESUME_TASK_FILE = 2;
    public static final int FLAG_SILENT_INSTALL_FILE = 8;
    public static final int FLAG_UPDATE_FILE = 1;
    public static final int FLAG_WWW_TASK_FILE = 4;
    private static long MIN_SIZE_THRESHOLD = PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
    private static final String TAG = "DownloadTask";
    public static final int TASK_ID_UNSPECIFIED = -1;
    private String mAnnotation;
    private String mAnnotationExt;
    private int mApnType;
    private long mCostTime;
    private long mCreateTime;
    protected String mDownloadUrl;
    protected long mDownloadedSize;
    protected List<Downloader> mDownloaders;
    private String mETag;
    protected int mErrorCode;
    protected String mFileFolderPath;
    protected String mFileName;
    protected long mFileSize;
    /* access modifiers changed from: private */
    public int mFlag;
    private boolean mHasInstalled;
    private Bitmap mIconBitmap;
    private String mIconUrl;
    private boolean mIsAppUpdateTask;
    private boolean mIsDeleted;
    private boolean mIsFixedPath;
    private boolean mIsForground;
    private boolean mIsHiddenTask;
    private boolean mIsLimited;
    private boolean mIsMergeFile;
    private boolean mIsPreDownload;
    private boolean mIsResumedTask;
    protected boolean mIsSupportResume;
    private boolean mIsWWW;
    protected int mMaxDownloaderNum;
    private boolean mNeedNotification;
    private String mPkgName;
    private boolean mRangeNotSupported;
    private final Object mReadWriteProgressLock;
    private String mReferer;
    private final Object mRenameLock;
    private long mSaveFlowSize;
    protected RandomAccessFile mSavedFile;
    protected DownloadSections mSectionData;
    private Queue<DownloadSpeedData> mSpeedData;
    protected long mStartDownloadedSize;
    protected long mStartTime;
    protected int mTaskId;
    private final Object mWriteDataLock;
    private int mZeroSpeedCount;

    public DownloadTask(String url) {
        this(url, (String) null, -1, (String) null);
    }

    public DownloadTask(String url, String fileName, long fileSize, String referer) {
        this(-1, url, fileName, (String) null, 0, fileSize, true, referer, 0, false);
    }

    public DownloadTask(String url, String fileName, String filePath) {
        this(-1, url, fileName, filePath, 0, 0, true, (String) null, 0, false);
    }

    public DownloadTask(String url, String fileName, String path, long fileSize, String referer, boolean isWWW) {
        this(-1, url, fileName, path, 0, fileSize, true, referer, 0, false);
        this.mIsWWW = isWWW;
        if (this.mIsWWW) {
            this.mFlag |= 4;
        }
    }

    public DownloadTask(int taskId, String url, String fileName, String fileFolderPath, long currentPos, long totalSize, boolean isSupportResume, String referer, int flag, boolean isReloadedTask) {
        this(taskId, (byte) 0, url, fileName, fileFolderPath, currentPos, totalSize, isSupportResume, referer, flag, isReloadedTask, 0, (String) null);
    }

    public DownloadTask(String url, String path) {
        this(-1, (byte) 0, url, (String) null, path, 0, -1, true, (String) null, 0, false, 0, (String) null);
    }

    public DownloadTask(int taskId, byte taskStatus, String url, String fileName, String fileFolderPath, long currentPos, long totalSize, boolean isSupportResume, String referer, int flag, boolean isReloadedTask, long costTime, String pkgName) {
        this.mRenameLock = new Object();
        this.mFileName = "";
        this.mIsFixedPath = false;
        this.mErrorCode = 0;
        this.mIsWWW = false;
        this.mIsAppUpdateTask = false;
        this.mMaxDownloaderNum = 0;
        this.mSectionData = new DownloadSections();
        this.mIsResumedTask = false;
        this.mIsDeleted = false;
        this.mIsForground = false;
        this.mNeedNotification = true;
        this.mIsLimited = false;
        this.mIsHiddenTask = false;
        this.mAnnotation = "";
        this.mAnnotationExt = "";
        this.mHasInstalled = true;
        this.mRangeNotSupported = false;
        this.mSaveFlowSize = 0;
        this.mApnType = Apn.getApnType();
        this.mReadWriteProgressLock = new Object();
        this.mWriteDataLock = new Object();
        this.mZeroSpeedCount = 0;
        this.mIsMergeFile = false;
        this.mIsPreDownload = false;
        this.mTaskType = 3;
        this.mDownloaders = new ArrayList();
        this.mHasInstalled = (flag & 64) == 64;
        this.mRangeNotSupported = (flag & 128) == 128;
        this.mTaskId = taskId;
        this.mStatus = taskStatus;
        this.mPkgName = pkgName;
        this.mIsHiddenTask = (flag & 32) > 0;
        this.mIsPreDownload = (flag & 4096) > 0;
        this.mMttRequest = new MttRequest();
        this.mMttRequest.setRequestType(MttRequest.REQUEST_FILE_DOWNLOAD);
        this.mDownloadUrl = url;
        this.mMttRequest.setUrl(this.mDownloadUrl);
        if (TextUtils.isEmpty(fileFolderPath)) {
            this.mFileFolderPath = FileUtils.getDownloadFilePath(fileName);
        } else {
            this.mFileFolderPath = fileFolderPath;
            this.mIsFixedPath = true;
        }
        this.mFileName = fileName;
        if (isM3U8() && fileName != null && !fileName.endsWith(".m3u8")) {
            this.mFileName = fileName + ".m3u8";
        }
        this.mFileSize = totalSize;
        this.mDownloadedSize = currentPos;
        DownloaderLog.d(TAG, "[DownloadTask]DownloadTask construct, mFileSize=" + this.mFileSize + ",mDownloadedSize=" + this.mDownloadedSize);
        this.mCostTime = costTime;
        this.mIsSupportResume = isSupportResume;
        if (isReloadedTask) {
            addTaskAttr(2);
        }
        this.mSpeedData = new LinkedList();
        this.mReferer = referer;
        setFlag(flag);
        this.mIsWWW = (this.mFlag & 4) != 0;
        this.mIconBitmap = FileUtils.getDownloadTypeIcon(this.mFileName, this.mFileFolderPath);
        if ((getFlag() & 2048) == 2048) {
            getInstallStateAndLogoIcon();
        }
        if (this.mStatus == 3 || (getFlag() & 16) == 16) {
        }
    }

    public String getPackageName() {
        return this.mPkgName;
    }

    public void setPackageName(String pkgName) {
        this.mPkgName = pkgName;
    }

    public long getSaveFlowSize() {
        return this.mSaveFlowSize;
    }

    public void setSaveFlowSize(long size) {
        this.mSaveFlowSize = size;
    }

    public boolean isHidden() {
        return this.mIsHiddenTask;
    }

    public boolean isPreDownload() {
        return this.mIsPreDownload;
    }

    public boolean hasInstalled() {
        return this.mHasInstalled;
    }

    public void setInstalled(boolean isInstalled) {
        this.mHasInstalled = isInstalled;
        if (isInstalled) {
            this.mFlag |= 64;
        } else {
            this.mFlag &= -65;
        }
    }

    public int getHttpResponseCode() {
        if (this.mDownloaders != null && this.mDownloaders.size() > 1) {
            for (Downloader d : this.mDownloaders) {
                if (d.getStatus() == 5) {
                    return d.getResponseCode();
                }
            }
        }
        return -1;
    }

    public boolean isMergingFile() {
        return this.mIsMergeFile;
    }

    public boolean hasDeltaUpdateFailed() {
        return (this.mFlag & 1024) > 0;
    }

    public void setDeltaUpdateFailed(boolean isFailed) {
        if (isFailed) {
            this.mFlag |= 1024;
        } else {
            this.mFlag &= -1025;
        }
    }

    public void setRangeNotSupported(boolean isNotSupported) {
        this.mRangeNotSupported = isNotSupported;
        if (isNotSupported) {
            this.mFlag |= 128;
        } else {
            this.mFlag &= -129;
        }
    }

    public void setHidden(boolean isHide) {
        this.mIsHiddenTask = isHide;
        if (isHide) {
            this.mFlag |= 32;
        } else {
            this.mFlag &= -33;
        }
    }

    public void setPreDownload(boolean isPre) {
        this.mIsPreDownload = isPre;
        if (isPre) {
            this.mFlag |= 4096;
        } else {
            this.mFlag &= -4097;
        }
    }

    public int getThreadSize() {
        return this.mDownloaders.size();
    }

    public boolean getIsWWW() {
        return this.mIsWWW;
    }

    public void setNeedNotification(boolean need) {
        this.mNeedNotification = need;
    }

    public boolean getNeedNotification() {
        return this.mNeedNotification;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public int getTotalRetryTimes() {
        int len = this.mDownloaders.size();
        int times = 0;
        for (int i = 0; i < len; i++) {
            times += this.mDownloaders.get(i).getRetryTimes();
        }
        return times;
    }

    public boolean isDeleted() {
        return this.mIsDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.mIsDeleted = isDeleted;
    }

    public void notifyIconArrival(boolean force) {
    }

    /* access modifiers changed from: private */
    public void checkInstallState(PackageInfo info, Context context) {
        if (info != null) {
            this.mPkgName = info.packageName;
            int oldVersion = info.versionCode;
            boolean isExist = true;
            PackageInfo pi = null;
            try {
                pi = context.getPackageManager().getPackageInfo(info.packageName, 0);
            } catch (IllegalArgumentException e) {
                isExist = false;
            } catch (PackageManager.NameNotFoundException e2) {
                isExist = false;
            }
            if (isExist && oldVersion > 0 && oldVersion != pi.versionCode) {
                isExist = false;
            }
            if (isExist) {
            }
            setInstalled(isExist);
        }
    }

    public void getInstallStateAndLogoIcon() {
        new Thread() {
            public void run() {
                Context context = FileDownload.context;
                PackageInfo info = context.getPackageManager().getPackageArchiveInfo(DownloadTask.this.mFileFolderPath + Constants.URL_PATH_DELIMITER + DownloadTask.this.mFileName, 1);
                DownloadTask.this.checkInstallState(info, context);
                if (!((DownloadTask.this.mFlag & 16) == 16 || info == null)) {
                    try {
                        Drawable icon = context.getPackageManager().getApplicationIcon(info.packageName);
                        if (icon instanceof BitmapDrawable) {
                            Bitmap logo = ((BitmapDrawable) icon).getBitmap();
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                DownloadTask.this.fireExtEvent();
            }
        }.start();
    }

    public Bitmap getIconBitmap() {
        return this.mIconBitmap;
    }

    public String getFileName() {
        return this.mFileName;
    }

    public String getShowFileName() {
        return this.mFileName;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public boolean isApkFile() {
        if (TextUtils.isEmpty(this.mFileName) || !this.mFileName.toLowerCase().endsWith(".apk")) {
            return false;
        }
        return true;
    }

    public String getFileFolderPath() {
        return this.mFileFolderPath;
    }

    public void setFileFolderPath(String path) {
        this.mFileFolderPath = path;
    }

    public int getProgress() {
        if (this.mFileSize != 0) {
            return (int) ((this.mDownloadedSize * 100) / this.mFileSize);
        }
        return 0;
    }

    public String getTaskUrl() {
        return this.mDownloadUrl;
    }

    public long getDownloadedSize() {
        return this.mDownloadedSize;
    }

    public long getTotalSize() {
        return this.mFileSize;
    }

    public void rename(String newName) {
        synchronized (this.mWriteDataLock) {
            File savedFile = new File(this.mFileFolderPath, this.mFileName + DL_FILE_SUFFIX);
            File newSavedFile = new File(this.mFileFolderPath, newName + DL_FILE_SUFFIX);
            savedFile.renameTo(newSavedFile);
            if (savedFile != null && !newSavedFile.exists()) {
                try {
                    newSavedFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                this.mSavedFile = new RandomAccessFile(newSavedFile, "rw");
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        synchronized (this.mReadWriteProgressLock) {
            new File(this.mFileFolderPath, "." + this.mFileName + ".dltmp").renameTo(new File(this.mFileFolderPath, "." + newName + ".dltmp"));
            if (this.mSectionData != null) {
                this.mSectionData.setDownloadFilePath(this.mFileFolderPath, newName);
            }
        }
        this.mFileName = newName;
        return;
    }

    public void writeDataFromNet(int sectionId, long offset, byte[] data, long len) throws IOException {
        synchronized (this.mWriteDataLock) {
            if (this.mSavedFile != null) {
                this.mSavedFile.seek(offset);
                this.mSavedFile.write(data, 0, (int) len);
            }
        }
    }

    public void setDownloadedBytes(long len) {
        this.mDownloadedSize = len;
    }

    public void notifyTaskStarted() {
        this.mStatus = 1;
        fireObserverEvent();
    }

    public void notifyTaskCanceled() {
        this.mStatus = 6;
        fireObserverEvent();
    }

    public void notifyTaskFailed(int errorCode) {
        this.mErrorCode = errorCode;
        this.mStatus = 5;
        fireObserverEvent();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(int r9, long r10, long r12) {
        /*
            r8 = this;
            java.lang.Object r2 = r8.mReadWriteProgressLock
            monitor-enter(r2)
            com.tencent.component.net.download.multiplex.download.DownloadSections r1 = r8.mSectionData     // Catch:{ all -> 0x002a }
            com.tencent.component.net.download.multiplex.download.DownloadSections$DownloadSection r0 = r1.getSection(r9)     // Catch:{ all -> 0x002a }
            if (r0 != 0) goto L_0x000d
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
        L_0x000c:
            return
        L_0x000d:
            r0.currentPos = r10     // Catch:{ all -> 0x002a }
            long r4 = r8.mDownloadedSize     // Catch:{ all -> 0x002a }
            long r4 = r4 + r12
            r8.mDownloadedSize = r4     // Catch:{ all -> 0x002a }
            long r4 = r8.mFileSize     // Catch:{ all -> 0x002a }
            r6 = 0
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x0028
            long r4 = r8.mDownloadedSize     // Catch:{ all -> 0x002a }
            long r6 = r8.mFileSize     // Catch:{ all -> 0x002a }
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x0028
            long r4 = r8.mFileSize     // Catch:{ all -> 0x002a }
            r8.mDownloadedSize = r4     // Catch:{ all -> 0x002a }
        L_0x0028:
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            goto L_0x000c
        L_0x002a:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadTask.update(int, long, long):void");
    }

    public float getSpeed() {
        DownloadSpeedData data;
        float speed = 0.0f;
        synchronized (this.mSpeedData) {
            data = this.mSpeedData.peek();
        }
        long currentTime = System.currentTimeMillis();
        long currentSize = this.mDownloadedSize;
        if (data != null) {
            if (data.mReceiveTime == currentTime) {
                speed = 0.0f;
            } else {
                speed = (((float) (currentSize - data.mDataSize)) * 1000.0f) / ((float) (currentTime - data.mReceiveTime));
            }
        }
        if (speed == 0.0f) {
            this.mZeroSpeedCount++;
        } else {
            this.mZeroSpeedCount = 0;
        }
        float averageSpeed = getAverageSpeed();
        if (this.mZeroSpeedCount >= 3 || speed >= averageSpeed) {
            return speed;
        }
        return averageSpeed;
    }

    public void accumulateSpeedData() {
        synchronized (this.mSpeedData) {
            if (this.mSpeedData.size() > 3) {
                this.mSpeedData.poll();
            }
            this.mSpeedData.offer(new DownloadSpeedData(this.mDownloadedSize, System.currentTimeMillis()));
        }
    }

    public void setETag(String etag) {
        this.mETag = etag;
    }

    public String getETag() {
        return this.mETag;
    }

    public int getMaxThreadNum() {
        return this.mMaxDownloaderNum;
    }

    public void setMaxThreadNum(int num) {
        this.mMaxDownloaderNum = num;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setTaskId(int taskId) {
        this.mTaskId = taskId;
    }

    public boolean getIsSupportResume() {
        return this.mIsSupportResume;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public void setCreateTime(long time) {
        this.mCreateTime = time;
    }

    public long getCostTime() {
        return this.mCostTime;
    }

    public void updateCostTime() {
        this.mCostTime += System.currentTimeMillis() - this.mStartTime;
    }

    public String getReferer() {
        return this.mReferer;
    }

    public void setReferer(String referer) {
        this.mReferer = referer;
    }

    public int getFlag() {
        return this.mFlag;
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    public boolean isAppUpdateTask() {
        return this.mIsAppUpdateTask;
    }

    public void setIsAppUpdateTask(boolean isAppUpdateTask) {
        this.mIsAppUpdateTask = isAppUpdateTask;
    }

    public boolean getIsResumedTask() {
        return this.mIsResumedTask;
    }

    public void setIsResumedTask(boolean isResumedTask) {
        this.mIsResumedTask = isResumedTask;
    }

    public boolean isFileExist() {
        if (TextUtils.isEmpty(this.mFileName) || TextUtils.isEmpty(this.mFileFolderPath)) {
            return false;
        }
        boolean isExist = new File(this.mFileFolderPath, this.mFileName).exists();
        if (!isExist) {
            return new File(this.mFileFolderPath, this.mFileName + DL_FILE_SUFFIX).exists();
        }
        return isExist;
    }

    public String getErrorDesc() {
        switch (this.mErrorCode) {
            case 1:
                return buildErrorMsg("没有sd卡", this.mErrorCode);
            case 2:
                return buildErrorMsg("空间不足", this.mErrorCode);
            case 3:
                return buildErrorMsg("网络错误", this.mErrorCode);
            case 4:
                return buildErrorMsg("文件被删除", this.mErrorCode);
            case 5:
                return buildErrorMsg("无法写入", this.mErrorCode);
            case 6:
                return buildErrorMsg("没有响应", this.mErrorCode);
            case 101:
                return buildErrorMsg("未知错误", this.mErrorCode);
            default:
                return buildErrorMsg("", this.mErrorCode);
        }
    }

    private String buildErrorMsg(String errorMsg, int errorCode) {
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = "未知错误";
        }
        sb.append(errorMsg).append(h.a).append(errorCode).append(h.b);
        return sb.toString();
    }

    public void fireTaskCreatedEvent() {
        this.mCanceled = false;
        synchronized (this.mSpeedData) {
            this.mSpeedData.clear();
        }
        this.mErrorCode = 0;
        this.mStatus = 0;
        fireObserverEvent();
    }

    public void setCancelled(boolean cancelled) {
        this.mCanceled = cancelled;
    }

    /* access modifiers changed from: protected */
    public void notifyTaskFailed() {
        resetConfigData();
        this.mErrorCode = 4;
        this.mStatus = 5;
        fireObserverEvent();
    }

    public void cancel() {
        DownloaderLog.d(TAG, "[DownloadTask] Cancel task. task id : " + this.mTaskId);
        this.mIsResumedTask = true;
        updateCostTime();
        if (this.mDownloadedSize > 0 && !isFileExist()) {
            DownloaderLog.d(TAG, "[DownloadTask] File not exist after downloading.");
            if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
                for (int i = 0; i < this.mMaxDownloaderNum; i++) {
                    try {
                        Downloader d = this.mDownloaders.get(i);
                        if (d != null) {
                            d.cancel();
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
            notifyTaskFailed();
        } else if (!this.mCanceled) {
            DownloaderLog.d(TAG, "[DownloadTask] Cancel task implemented.");
            this.mCanceled = true;
            if (this.mStatus == 1 || this.mStatus == 2) {
                this.mStatus = 7;
                boolean allFinished = true;
                if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
                    for (int i2 = 0; i2 < this.mMaxDownloaderNum; i2++) {
                        Downloader d2 = this.mDownloaders.get(i2);
                        if (d2 != null && !d2.isFinish()) {
                            d2.cancel();
                            if (!d2.isFinish()) {
                                allFinished = false;
                            }
                        }
                    }
                }
                if (allFinished) {
                    this.mStatus = 6;
                }
                fireObserverEvent();
                return;
            }
            DownloaderLog.d(TAG, "[DownloadTask]Cancel taskCanceled");
            taskCanceled();
        }
    }

    /* access modifiers changed from: protected */
    public void taskCanceled() {
        this.mStatus = 6;
        fireObserverEvent();
    }

    public void setForground(boolean isForground) {
        this.mIsForground = isForground;
        refreshThreadPriority();
    }

    public boolean isRangeNotSupported() {
        return this.mRangeNotSupported;
    }

    private void refreshThreadPriority() {
        if (this.mDownloaders != null) {
            int len = this.mDownloaders.size();
            for (int i = 0; i < len; i++) {
                Downloader downloader = this.mDownloaders.get(i);
                if (downloader != null) {
                    downloader.refreshThreadPriority();
                }
            }
        }
    }

    public boolean isForground() {
        return this.mIsForground;
    }

    private void resume() {
        DownloaderLog.d(TAG, "[DownloadTask]resume unfinished task");
        adjustMaxDownloaderNum(false);
        try {
            if (newSavedFile()) {
                if (this.mCanceled) {
                    taskCanceled();
                } else {
                    generateSectionsAndStart();
                }
            }
        } catch (IOException e) {
            this.mErrorCode = 5;
            if (FileUtils.getSdcardFreeSpace() < 2024) {
                this.mErrorCode = 2;
            }
            this.mStatus = 5;
            fireObserverEvent();
        }
    }

    public synchronized void run() {
        DownloaderLog.d(TAG, "============= [DownloadTask] Start [id:" + this.mTaskId + "] to download =============");
        if (this.mIsForground) {
            Thread.currentThread().setPriority(5);
        } else {
            Thread.currentThread().setPriority(1);
        }
        this.mIsMergeFile = false;
        this.mStatus = 1;
        fireObserverEvent();
        this.mStartDownloadedSize = this.mDownloadedSize;
        this.mStartTime = System.currentTimeMillis();
        this.mApnType = Apn.getApnTypeS();
        DownloaderLog.d(TAG, "[DownloadTask] [Task id:" + this.mTaskId + "]: 开始运行");
        if (canResume()) {
            DownloaderLog.d(TAG, "[DownloadTask] [Task id:" + this.mTaskId + "]: 开始恢复下载");
            resume();
        } else {
            DownloaderLog.d(TAG, "[DownloadTask] [Task id:" + this.mTaskId + "]: 开始侦查下载");
            startDetectionDownloader();
        }
    }

    /* access modifiers changed from: protected */
    public boolean canResume() {
        return this.mFileSize > 0 && this.mDownloadedSize > 0 && !TextUtils.isEmpty(this.mFileName) && FileUtils.checkFileName(this.mFileName);
    }

    /* access modifiers changed from: protected */
    public void startDetectionDownloader() {
        Downloader d = Downloader.createDownloader(this, this.mSectionData.createSection(0), 0);
        this.mDownloaders.add(0, d);
        if (this.mCanceled) {
            DownloaderLog.d(TAG, "[DownloadTask] [Task id:" + this.mTaskId + "] mCanceled==true, cancel it first");
            taskCanceled();
            return;
        }
        d.setFinished(false);
        DownloaderLog.d(TAG, "[DownloadTask] [Task id:" + this.mTaskId + "]: 开始启动 Downloader : " + d.getDownloaderId());
        d.start();
    }

    /* access modifiers changed from: protected */
    public boolean renameAfterFinish() {
        File savedFile = new File(this.mFileFolderPath, this.mFileName + DL_FILE_SUFFIX);
        if (savedFile == null || !savedFile.exists()) {
            return true;
        }
        return savedFile.renameTo(new File(this.mFileFolderPath, this.mFileName));
    }

    public void finishDownloadTask() {
        DownloaderLog.d(TAG, "[DownloadTask]finishDownloadTask");
        updateCostTime();
        if (this.mFileSize <= 0 || this.mDownloadedSize >= this.mFileSize) {
            closeSavedFile();
            if (!renameAfterFinish()) {
                DownloaderLog.d(TAG, "[DownloadTask]finishDownloadTask ERROR_RENAME_FAILED");
                this.mErrorCode = 9;
                this.mStatus = 5;
                fireObserverEvent();
            } else if (!isFileExist()) {
                DownloaderLog.d(TAG, "[DownloadTask]File not exist after downloading.");
                notifyTaskFailed();
            } else {
                deleteCfgFile();
                if (this.mFileSize <= 0) {
                    this.mFileSize = this.mDownloadedSize;
                }
                DownloaderLog.d(TAG, "[DownloadTask] file size:" + this.mFileSize + ",downloadedSize:" + this.mDownloadedSize);
                DownloaderLog.d(TAG, "[DownloadTask] cost time:" + (this.mCostTime / 1000) + "s");
                DownloaderLog.d(TAG, "[DownloadTask] retry times:" + getTotalRetryTimes());
                DownloaderLog.d(TAG, "[DownloadTask] speed:" + (((((float) this.mFileSize) / 1024.0f) * 1000.0f) / ((float) this.mCostTime)) + "KB/S");
                if ((getFlag() & 256) != 256 && (getFlag() & 2048) != 2048) {
                    if (isApkFile()) {
                        getInstallStateAndLogoIcon();
                    }
                    this.mStatus = 3;
                    fireObserverEvent();
                }
            }
        } else {
            DownloaderLog.d(TAG, "[DownloadTask]finishDownloadTask ERRORCODE_UNKNOWN, mDownloadedSize=" + this.mDownloadedSize + ", mFileSize=" + this.mFileSize);
            this.mErrorCode = 101;
            this.mStatus = 5;
            synchronized (this.mReadWriteProgressLock) {
                this.mDownloadedSize = 0;
                this.mSectionData.clear(true);
            }
            fireObserverEvent();
        }
    }

    private void doIncrUpdate() {
    }

    private void mergeDiffFile() {
    }

    public void resetDeltaUpdateFiledTask() {
    }

    public int getNewDeltaUPdateFileSize() {
        return 0;
    }

    private void fireDeltaTaskMergeFialedEvent() {
        if ((this.mFlag & 256) != 0) {
            this.mFlag &= -257;
            this.mFlag |= 1024;
            this.mStatus = 5;
            this.mErrorCode = 12;
            fireObserverEvent();
        }
    }

    public void saveConfigData() {
        if (this.mIsSupportResume) {
            synchronized (this.mReadWriteProgressLock) {
                this.mSectionData.saveConfigData(this.mDownloadedSize, this.mMaxDownloaderNum);
            }
        }
    }

    private void restoreConfigData() throws IOException {
        synchronized (this.mReadWriteProgressLock) {
            this.mSectionData.restoreConfigData(this.mFileFolderPath, this.mFileName, this.mMaxDownloaderNum);
            if (this.mSectionData.getDownloadedSizeFromCfg() >= 0) {
                this.mDownloadedSize = this.mSectionData.getDownloadedSizeFromCfg();
            }
        }
    }

    public void deleteCfgFile(File file) {
        this.mSectionData.deleteCfgFile(file);
    }

    public void deleteCfgFile() {
        this.mSectionData.deleteCfgFile();
    }

    /* access modifiers changed from: protected */
    public void restoreDownloaders() {
        DownloaderLog.d(TAG, "[DownloadTask]restoring downloaders from data");
        boolean finish = true;
        this.mDownloaders.clear();
        for (int i = 0; i < this.mMaxDownloaderNum; i++) {
            DownloadSections.DownloadSection pos = this.mSectionData.getSection(i);
            this.mDownloaders.add(i, Downloader.createDownloader(this, pos, i));
            Downloader d = this.mDownloaders.get(i);
            if (pos.isFinish() || pos.isPending()) {
                d.setFinished(true);
            } else if (this.mCanceled) {
                taskCanceled();
                return;
            } else {
                DownloaderLog.d(TAG, "[DownloadTask]new thread with startPos:" + pos);
                d.setFinished(false);
                d.start();
                finish = false;
            }
        }
        if (finish) {
            finishDownloadTask();
        }
    }

    public boolean onFirstRequestFinished(Downloader downloader, MttResponse mttResponse) {
        boolean isSuccess;
        boolean z;
        DownloaderLog.d(TAG, "[DownloadTask][" + this.mTaskId + "]: 侦查到HTTP Header信息");
        this.mIsSupportResume = false;
        String contentRange = mttResponse.getContentRange();
        if (contentRange != null) {
            Matcher m = CONTENT_RANGE_PATTERN.matcher(contentRange);
            if (m.find()) {
                long longStartRange = Long.parseLong(m.group(1));
                DownloaderLog.d(TAG, "[DownloadTask]**** Start range - " + longStartRange);
                DownloaderLog.d(TAG, "[DownloadTask]**** Start currentPos - 1");
                if (longStartRange == 0) {
                    z = true;
                } else {
                    z = false;
                }
                this.mIsSupportResume = z;
            }
        }
        makeSureSupportResume(this.mRangeNotSupported);
        this.mETag = mttResponse.getETag();
        makeSureFileSize(mttResponse.getContentLength());
        adjustMaxDownloaderNum(true);
        if ((this.mRangeNotSupported || !FileUtils.checkFileName(this.mFileName)) && !this.mIsFixedPath) {
            this.mFileFolderPath = FileUtils.getDownloadFilePath(this.mFileName);
        }
        DownloaderLog.d(TAG, "[DownloadTask][" + this.mTaskId + "]: 侦查后文件名:" + this.mFileName + ",目录=" + this.mFileFolderPath);
        synchronized (this.mRenameLock) {
            if (this.mNeedNotification) {
                this.mFileName = FileUtils.renameFileIfExist(this.mFileFolderPath, this.mFileName);
            }
            if (this.mIconBitmap != null) {
                FileUtils.saveDownloadFileTypeIcon(this.mFileName, this.mFileFolderPath, this.mIconBitmap);
            }
            isSuccess = newSavedFile();
        }
        if (!isSuccess) {
            return false;
        }
        DownloaderLog.d(TAG, "[DownloadTask]content-type: " + mttResponse.getContentType().getType());
        DownloaderLog.d(TAG, "[DownloadTask]content-value:" + mttResponse.getContentType().getTypeValue());
        DownloaderLog.d(TAG, "[DownloadTask]content-disposition: " + mttResponse.getContentDisposition());
        if (this.mCanceled) {
            taskCanceled();
            return false;
        } else if (this.mNeedNotification || !hasValidConfigData()) {
            this.mSectionData.setDownloadFilePath(this.mFileFolderPath, this.mFileName);
            this.mSectionData.clear(true);
            newSections(true);
            return true;
        } else {
            restoreDownloaders();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void makeSureFileSize(long contentLength) {
        this.mFileSize = contentLength;
    }

    /* access modifiers changed from: protected */
    public void makeSureSupportResume(boolean rangeNotSupported) {
        if (rangeNotSupported) {
            this.mIsSupportResume = false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean newSavedFile() {
        try {
            new File(this.mFileFolderPath).mkdirs();
            File savedFile = new File(this.mFileFolderPath, this.mFileName + DL_FILE_SUFFIX);
            if (this.mDownloadedSize > 0 && !savedFile.exists()) {
                savedFile = new File(this.mFileFolderPath, this.mFileName);
            }
            if (this.mDownloadedSize <= 0 || isFileExist()) {
                if (savedFile != null && !savedFile.exists()) {
                    savedFile.createNewFile();
                }
                this.mSavedFile = new RandomAccessFile(savedFile, "rw");
                return true;
            }
            DownloaderLog.d(TAG, "[DownloadTask]File not exist after downloading.");
            notifyTaskFailed();
            File file = new File(this.mFileFolderPath, "." + this.mFileName + ".dltmp");
            if (file == null || !file.exists()) {
                return false;
            }
            file.delete();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            this.mErrorCode = 5;
            if (FileUtils.getSdcardFreeSpace() < 2024) {
                this.mErrorCode = 2;
            }
            this.mStatus = 5;
            fireObserverEvent();
            return false;
        }
    }

    private boolean hasValidConfigData() {
        boolean isValid = this.mSectionData.hasValidConfigData(this.mFileFolderPath, this.mFileName, this.mFileSize, this.mMaxDownloaderNum);
        if (isValid) {
            this.mDownloadedSize = this.mSectionData.getDownloadedSizeFromCfg();
        }
        return isValid;
    }

    /* access modifiers changed from: protected */
    public void newSections(boolean firstTime) {
        Downloader d;
        long block = this.mFileSize / ((long) this.mMaxDownloaderNum);
        if (!firstTime) {
            this.mSectionData.clear(false);
        }
        this.mDownloadedSize = 0;
        for (int i = 0; i < this.mMaxDownloaderNum; i++) {
            long startPos = ((long) i) * block;
            long endPos = (((long) (i + 1)) * block) - 1;
            long currentPos = startPos;
            if (i == this.mMaxDownloaderNum - 1) {
                endPos = this.mFileSize - 1;
            }
            if (i != 0 || !firstTime) {
                DownloadSections.DownloadSection pos = this.mSectionData.createSection(i);
                pos.startPos = startPos;
                pos.setEndPos(endPos);
                pos.currentPos = currentPos;
                DownloaderLog.d(TAG, "[DownloadTask]new thread with startPos:" + startPos + " endPos:" + endPos);
                if (i >= 0 && i < this.mDownloaders.size()) {
                    this.mDownloaders.remove(i);
                }
                this.mDownloaders.add(i, Downloader.createDownloader(this, pos, i));
                if (this.mCanceled) {
                    taskCanceled();
                    return;
                }
                Downloader d2 = this.mDownloaders.get(i);
                d2.setFinished(false);
                d2.start();
            } else {
                if (this.mSectionData.size() <= 0) {
                    DownloadSections.DownloadSection pos2 = this.mSectionData.createSection(0);
                }
                DownloadSections.DownloadSection pos3 = this.mSectionData.getSection(0);
                pos3.startPos = startPos;
                pos3.setEndPos(endPos);
                pos3.currentPos = currentPos;
                if (this.mDownloaders.size() > 0) {
                    d = this.mDownloaders.get(0);
                } else {
                    d = Downloader.createDownloader(this, pos3, 0);
                    this.mDownloaders.add(0, d);
                }
                this.mDownloaders.get(0);
                d.setStartPos(startPos);
                d.setEndPos(endPos);
                d.setCurrentPos(currentPos);
            }
        }
    }

    private void generateSectionsAndStart() throws IOException {
        this.mDownloaders.clear();
        if (!this.mIsSupportResume) {
            newSections(false);
            return;
        }
        if (isValidSectionData()) {
            restoreDownloaders();
        } else {
            restoreConfigData();
            if (isValidSectionData()) {
                restoreDownloaders();
            } else {
                newSections(false);
            }
        }
        this.mStartDownloadedSize = this.mDownloadedSize;
        this.mStartTime = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public boolean isValidSectionData() {
        boolean valid = this.mSectionData.isValidSectionData(this.mFileSize, this.mMaxDownloaderNum);
        if (!valid) {
            DownloaderLog.d(TAG, "[DownloadTask]invalid section data");
        }
        return valid;
    }

    private float getAverageSpeed() {
        return (((float) (this.mDownloadedSize - this.mStartDownloadedSize)) * 1000.0f) / ((float) (System.currentTimeMillis() - this.mStartTime));
    }

    private long getMinSectionSizeThreshold() {
        long threshold = (long) getAverageSpeed();
        if (threshold == 0) {
            return MIN_SIZE_THRESHOLD;
        }
        return threshold;
    }

    private void divideDownloder(Downloader notFinishDownloader, Downloader finishedDownloader) {
        if (notFinishDownloader != null && finishedDownloader != null) {
            DownloaderLog.i(TAG, "[DownloadTask], lauch downloader[" + finishedDownloader.getDownloaderId() + "] to help download[" + notFinishDownloader.getDownloaderId() + "] handle not finish part!");
            if (notFinishDownloader.getStatus() != 5 || finishedDownloader.getStatus() == 5) {
                DownloaderLog.d(TAG, "[DownloadTask]Downloader " + finishedDownloader.getDownloaderId() + " had ended;");
                DownloaderLog.d(TAG, "[DownloadTask]get new task from downloader " + notFinishDownloader.getDownloaderId());
                long current = notFinishDownloader.getCurrentPos();
                long end = notFinishDownloader.getEndPos();
                long middle = (current + end) / 2;
                notFinishDownloader.setEndPos(middle);
                synchronized (this.mReadWriteProgressLock) {
                    DownloadSections.DownloadSection pos = this.mSectionData.getSection(notFinishDownloader.getDownloaderId());
                    if (pos != null) {
                        pos.setEndPos(middle);
                        finishedDownloader.setStartPos(1 + middle);
                        finishedDownloader.setCurrentPos(1 + middle);
                        finishedDownloader.setEndPos(end);
                        DownloadSections.DownloadSection pos2 = this.mSectionData.getSection(finishedDownloader.getDownloaderId());
                        pos2.startPos = 1 + middle;
                        pos2.currentPos = 1 + middle;
                        pos2.setEndPos(end);
                        finishedDownloader.setFinished(false);
                        finishedDownloader.setIsFirstSection(false);
                        finishedDownloader.start();
                        return;
                    }
                    return;
                }
            }
            notFinishDownloader.setFinished(false);
            notFinishDownloader.start();
        }
    }

    public void fixDownloadStatus() {
        boolean needFix = true;
        Iterator i$ = this.mDownloaders.iterator();
        while (true) {
            if (i$.hasNext()) {
                Downloader downloader = i$.next();
                if (downloader != null && downloader.getStatus() == 7) {
                    needFix = false;
                    break;
                }
            } else {
                break;
            }
        }
        if (needFix && this.mStatus == 7) {
            this.mStatus = 6;
        }
    }

    public boolean isInDataDir() {
        String dataDir = Environment.getDataDirectory().getAbsolutePath();
        if (TextUtils.isEmpty(this.mFileFolderPath) || !this.mFileFolderPath.startsWith(dataDir)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void doScheduleDownloaders(Downloader childTask) {
        Downloader dl;
        scheduelTasks(childTask);
        boolean needNotifyCancel = true;
        boolean finish = true;
        int size = this.mDownloaders.size();
        if (size > this.mMaxDownloaderNum) {
            for (int i = size - 1; i >= this.mMaxDownloaderNum; i--) {
                if (i >= 0 && i < this.mDownloaders.size() && (dl = this.mDownloaders.remove(i)) != null) {
                    dl.cancel();
                }
            }
        }
        if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
            for (int i2 = 0; i2 < this.mMaxDownloaderNum; i2++) {
                Downloader d = this.mDownloaders.get(i2);
                if (d == null) {
                    finish = false;
                } else if (!d.isFinish()) {
                    finish = false;
                    if (d.getStatus() == 7) {
                        needNotifyCancel = false;
                    }
                }
            }
        } else {
            DownloaderLog.d(TAG, "[DownloadTask]mDownloaders.size() != mMaxDownloaderNum = " + this.mMaxDownloaderNum);
            DownloaderLog.d(TAG, "[DownloadTask]mDownloaders.size() = " + this.mDownloaders.size());
            finish = false;
        }
        if (!finish) {
            boolean failed = true;
            Downloader failedDownloader = null;
            if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
                for (int i3 = 0; i3 < this.mMaxDownloaderNum; i3++) {
                    Downloader d2 = this.mDownloaders.get(i3);
                    if (d2 != null && !d2.isPending()) {
                        byte status = d2.getStatus();
                        if (!(status == 5 || status == 3)) {
                            failed = false;
                        }
                        if (status == 5) {
                            failedDownloader = d2;
                        }
                    }
                }
            }
            if (failed && failedDownloader != null) {
                handleTaskFailed(failedDownloader);
                return;
            }
        }
        DownloaderLog.d(TAG, "[DownloadTask] task file:" + getFileName() + " finished downloader id:" + childTask.getDownloaderId());
        if (needNotifyCancel && this.mCanceled) {
            this.mCanceled = false;
            taskCanceled();
        } else if (finish) {
            finishDownloadTask();
        }
    }

    /* access modifiers changed from: protected */
    public void scheduelTasks(Downloader childTask) {
        if (childTask != null && childTask.isFinish()) {
            int downloaderId = childTask.getDownloaderId();
            int size = this.mDownloaders.size();
            int j = 0;
            while (j < this.mMaxDownloaderNum) {
                if (j == downloaderId || j >= size || downloaderId >= size || this.mDownloaders.get(j).getRemainingLen() <= getMinSectionSizeThreshold()) {
                    j++;
                } else {
                    divideDownloder(this.mDownloaders.get(j), this.mDownloaders.get(downloaderId));
                    return;
                }
            }
        }
    }

    private void resetConfigData() {
        this.mDownloadedSize = 0;
        this.mDownloaders.clear();
        this.mSectionData.clear(true);
    }

    public synchronized void onTaskCreated(Task task) {
    }

    public synchronized void onTaskStarted(Task task) {
    }

    public synchronized void onTaskProgress(Task task) {
        if (this.mStatus != 5) {
            if (this.mStatus == 7 || this.mObservers == null || this.mObservers.size() == 0) {
                ((Downloader) task).cancel();
            } else if (!this.mCanceled) {
                this.mStatus = 2;
                fireObserverEvent();
            }
        }
    }

    private void stateFlow(Task task) {
    }

    public synchronized void onTaskCompleted(Task task) {
        DownloaderLog.d(TAG, "[DownloadTask] completed flow:" + task.getFlow());
        stateFlow(task);
        doScheduleDownloaders((Downloader) task);
    }

    public synchronized void onTaskFailed(Task task) {
        DownloaderLog.d(TAG, "[DownloadTask] onTaskFailed id : " + this.mTaskId);
        DownloaderLog.d("PV", "DownloadTask onTaskFailed flow:" + task.getFlow());
        stateFlow(task);
        Downloader downloader = (Downloader) task;
        if (downloader.getStatus() != 5 || this.mStatus == 5) {
            doScheduleDownloaders(downloader);
        } else {
            this.mStartDownloadedSize = this.mDownloadedSize;
            this.mStartTime = System.currentTimeMillis();
            boolean realFailed = true;
            int failedNum = 0;
            if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
                for (int i = 0; i < this.mMaxDownloaderNum; i++) {
                    Downloader d = this.mDownloaders.get(i);
                    if (!(d == null || d == downloader || d.isFinish() || d.isPending() || d.getStatus() == 5 || d.getStatus() == 3)) {
                        DownloaderLog.d(TAG, "[DownloadTask]downloader:" + d + " d status:" + d.getStatus());
                        realFailed = false;
                    }
                    if (d != null && d.getStatus() == 5) {
                        failedNum++;
                    }
                }
            }
            DownloaderLog.d(TAG, "[DownloadTask]downloader:" + downloader + " downloader real failed:" + realFailed);
            DownloaderLog.d(TAG, "[DownloadTask]downloader:" + downloader + " downloader status failed error:" + downloader.getStatus());
            DownloaderLog.d(TAG, "[DownloadTask]downloader:" + downloader + " downloader failed error:" + downloader.getErrorCode());
            if (realFailed) {
                DownloaderLog.d(TAG, "[DownloadTask]real failed with error code:" + downloader.getErrorCode());
                if (failedNum >= 2 && downloader.getErrorCode() == 7) {
                    DownloaderLog.d(TAG, "[DownloadTask]failedNum=" + failedNum + ",errcode=" + 7);
                    if (this.mApnType == Apn.getApnTypeS()) {
                        handleResumeWithOneDownloader(downloader);
                    } else {
                        handleTaskFailed(downloader);
                    }
                } else if (downloader.getErrorCode() == 10) {
                    handleResumeWithoutRange();
                } else if (downloader.getErrorCode() == 8) {
                    handleTaskRestart(this.mDownloadUrl, downloader);
                } else {
                    handleTaskFailed(downloader);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void handleTaskRestart(String url, Downloader downloader) {
        DownloaderLog.d(TAG, "[DownloadTask]handleTaskRestart");
        this.mDownloadUrl = url;
        this.mMttRequest.setUrl(url);
        if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
            for (int i = 0; i < this.mMaxDownloaderNum; i++) {
                Downloader d = this.mDownloaders.get(i);
                if (!(d == null || d.isFinish() || d == downloader)) {
                    d.cancel();
                }
            }
        }
        this.mStartDownloadedSize = 0;
        this.mDownloadedSize = 0;
        this.mETag = null;
        this.mStartTime = System.currentTimeMillis();
        this.mStatus = 2;
        this.mSectionData.clear(true);
        startDetectionDownloader();
    }

    private void handleResumeWithOneDownloader(Downloader downloader) {
        DownloaderLog.d(TAG, "[DownloadTask]handleResumeWithOneDownloader");
        if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
            for (int i = 0; i < this.mMaxDownloaderNum; i++) {
                Downloader d = this.mDownloaders.get(i);
                if (!(d == null || d.isFinish() || d == downloader)) {
                    d.cancel();
                }
            }
        }
        updateCostTime();
        this.mIsLimited = true;
        this.mStatus = 2;
        resume();
    }

    private void handleResumeWithoutRange() {
        DownloaderLog.d(TAG, "[DownloadTask]handleResumeWithoutRange");
        updateCostTime();
        this.mIsLimited = true;
        this.mMaxDownloaderNum = 1;
        setRangeNotSupported(true);
        this.mStatus = 2;
        startDetectionDownloader();
    }

    private void handleTaskFailed(Downloader downloader) {
        DownloaderLog.d(TAG, "[DownloadTask]handleTaskFailed");
        this.mStatus = 5;
        updateCostTime();
        this.mErrorCode = downloader.getErrorCode();
        if (this.mErrorCode == 4) {
            resetConfigData();
        }
        if (this.mDownloaders.size() == this.mMaxDownloaderNum) {
            for (int i = 0; i < this.mMaxDownloaderNum; i++) {
                Downloader d = this.mDownloaders.get(i);
                if (!(d == null || d.isFinish() || d == downloader)) {
                    d.cancel();
                }
            }
        }
        closeSavedFile();
        fireObserverEvent();
    }

    /* access modifiers changed from: protected */
    public void adjustMaxDownloaderNum(boolean firstTime) {
        if (this.mMaxDownloaderNum == 0) {
            if (!this.mIsSupportResume || this.mIsLimited) {
                this.mMaxDownloaderNum = 1;
            } else if (this.mFileSize < MIN_SIZE_THRESHOLD) {
                this.mMaxDownloaderNum = 1;
            } else if (this.mFileSize < MIN_SIZE_THRESHOLD * 2) {
                this.mMaxDownloaderNum = 2;
            } else {
                this.mMaxDownloaderNum = 3;
            }
            if (Apn.is2GMode()) {
                this.mMaxDownloaderNum = 1;
            }
        }
        if (this.mIsLimited) {
            this.mMaxDownloaderNum = 1;
        }
        if (!this.mIsSupportResume) {
            this.mMaxDownloaderNum = 1;
        }
    }

    public void closeSavedFile() {
        if (this.mSavedFile != null) {
            try {
                if (this.mFileSize > 0 && this.mSavedFile.length() > this.mFileSize) {
                    this.mSavedFile.setLength(this.mFileSize);
                }
                this.mSavedFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mSavedFile = null;
        }
    }

    public void onTaskExtEvent(Task task) {
    }

    public String getAnnotationExt() {
        return this.mAnnotationExt;
    }

    public void setAnnotationExt(String annotation) {
        this.mAnnotationExt = annotation;
    }

    public String getAnnotation() {
        return this.mAnnotation;
    }

    public void setAnnotation(String annotation) {
        this.mAnnotation = annotation;
    }

    public boolean isM3U8() {
        return false;
    }

    public static boolean isM3U8Flag(int flag) {
        return (flag & 512) != 0;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}
