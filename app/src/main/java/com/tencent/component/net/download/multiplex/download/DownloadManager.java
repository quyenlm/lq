package com.tencent.component.net.download.multiplex.download;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.component.net.download.multiplex.download.extension.DownFileInfo;
import com.tencent.component.net.download.multiplex.download.extension.FileUtils;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.component.net.download.multiplex.task.Task;
import com.tencent.component.net.download.multiplex.task.TaskObserver;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadManager implements TaskObserver {
    public static final String DOWNLOAD_SERVER = "http://disk.html5.qq.com/u?action=fetch";
    public static final int EVENT_CLICK_CANCELLED = 1;
    public static final int EVENT_CLICK_OK = 2;
    private static final int PREFIX_LENGTH = 8;
    private static final String TAG = "DownloadManager";
    private static final long TIMER_PERIOD = 1000;
    /* access modifiers changed from: private */
    public DownloadDBHelper mDBHelper = new DownloadDBHelper();
    private List<OnDownloadedTaskListener> mDownloadedTaskListener = new LinkedList();
    private boolean mInitCompleted = false;
    private Object mInitLockObj = new Object();
    private boolean mIsForground = false;
    private boolean mNeedNotification = true;
    private List<DownloadTask> mNotCompletedTaskList = new LinkedList();
    /* access modifiers changed from: private */
    public List<DownloadTask> mOngoingTaskList = new LinkedList();
    private boolean mShow2GConfirmDialog = true;
    /* access modifiers changed from: private */
    public DownloadTaskManager mTaskManager = new DownloadTaskManager();
    private List<TaskObserver> mTaskObserver = new LinkedList();
    private Timer mTimer;

    public interface FileDeletedListener {
        void onDeletedFail(File file);

        void onDeletedSuccess(File file);
    }

    public interface OnDownloadFeedbackListener {
        void notifyFeedbackEvent(int i);
    }

    public interface OnDownloadedTaskListener {
        void notifyTaskDeleted(DownloadInfo downloadInfo);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init() {
        /*
            r6 = this;
            java.lang.String r3 = "DownloadManager"
            java.lang.String r4 = "begin init"
            com.tencent.component.net.download.multiplex.DownloaderLog.d(r3, r4)
            java.lang.Object r4 = r6.mInitLockObj
            monitor-enter(r4)
            boolean r3 = r6.mInitCompleted     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0017
            java.lang.String r3 = "DownloadManager"
            java.lang.String r5 = "already init and return"
            com.tencent.component.net.download.multiplex.DownloaderLog.d(r3, r5)     // Catch:{ all -> 0x0055 }
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
        L_0x0016:
            return
        L_0x0017:
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r3 = r6.mDBHelper     // Catch:{ all -> 0x0055 }
            r3.init()     // Catch:{ all -> 0x0055 }
            r0 = 0
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r3 = r6.mDBHelper     // Catch:{ Exception -> 0x003d }
            android.database.Cursor r0 = r3.getDownloadingList()     // Catch:{ Exception -> 0x003d }
            if (r0 == 0) goto L_0x0058
        L_0x0025:
            boolean r3 = r0.moveToNext()     // Catch:{ Exception -> 0x003d }
            if (r3 == 0) goto L_0x0058
            com.tencent.component.net.download.multiplex.download.DownloadTask r2 = r6.cursor2Task(r0)     // Catch:{ Exception -> 0x003d }
            byte r3 = r2.mStatus     // Catch:{ Exception -> 0x003d }
            r5 = 7
            if (r3 != r5) goto L_0x0037
            r3 = 6
            r2.mStatus = r3     // Catch:{ Exception -> 0x003d }
        L_0x0037:
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r6.mNotCompletedTaskList     // Catch:{ Exception -> 0x003d }
            r3.add(r2)     // Catch:{ Exception -> 0x003d }
            goto L_0x0025
        L_0x003d:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x0046
            r0.close()     // Catch:{ all -> 0x0055 }
        L_0x0046:
            r3 = 1
            r6.mInitCompleted = r3     // Catch:{ all -> 0x0055 }
            r6.checkResumedTask()     // Catch:{ all -> 0x0055 }
            java.lang.String r3 = "DownloadManager"
            java.lang.String r5 = "end init"
            com.tencent.component.net.download.multiplex.DownloaderLog.d(r3, r5)     // Catch:{ all -> 0x0055 }
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            goto L_0x0016
        L_0x0055:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            throw r3
        L_0x0058:
            if (r0 == 0) goto L_0x0046
            r0.close()     // Catch:{ all -> 0x0055 }
            goto L_0x0046
        L_0x005e:
            r3 = move-exception
            if (r0 == 0) goto L_0x0064
            r0.close()     // Catch:{ all -> 0x0055 }
        L_0x0064:
            throw r3     // Catch:{ all -> 0x0055 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.init():void");
    }

    public boolean hasInitCompleted() {
        return this.mInitCompleted;
    }

    public void startDownloadTaskWithUI(DownloadInfo downloadInfo, OnDownloadFeedbackListener listener) {
        startDownloadToLocalTask(downloadInfo, listener);
    }

    public DownloadTask startDownlodTask(final DownloadInfo downloadInfo) {
        DownloadTask dt = null;
        if (downloadInfo != null) {
            if (!this.mShow2GConfirmDialog || !Apn.is3GOr2GMode() || !(downloadInfo.fromWhere == 1 || downloadInfo.fromWhere == 3 || downloadInfo.fromWhere == 2)) {
                dt = startRealDownlodTask(downloadInfo);
                if (downloadInfo.observer != null) {
                    downloadInfo.observer.onTaskCreated(dt);
                }
            } else if (downloadInfo.fromWhere == 3) {
                dt = startRealDownlodTask(downloadInfo);
                if (downloadInfo.observer != null) {
                    downloadInfo.observer.onTaskCreated(dt);
                }
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        DownloadManager.this.showConfirmaDialog(downloadInfo);
                    }
                });
            }
        }
        return dt;
    }

    /* access modifiers changed from: private */
    public void showConfirmaDialog(DownloadInfo downloadInfo) {
    }

    private DownloadTask startRealDownlodTask(DownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            return null;
        }
        if (downloadInfo.videoType != 99) {
            startDownloadVideoForLaterWatching(downloadInfo, downloadInfo.listener);
            return null;
        } else if (downloadInfo.isPluginTask) {
            return startPluginDownload(downloadInfo);
        } else {
            if (downloadInfo.hasSelectWnd) {
                startDownloadTaskWithUI(downloadInfo, downloadInfo.listener);
                return null;
            }
            if (downloadInfo.hasBtn) {
            }
            DownloadTask task = getTaskFromInfo(downloadInfo);
            DownloadTask completedTask = getDownloadCompletedTaskFromDatabase(downloadInfo.url);
            if (completedTask != null && (!completedTask.isFileExist() || downloadInfo.deleteTaskIfCompleted)) {
                deleteTask(completedTask.getTaskId(), true);
            }
            String skinName = downloadInfo.skinName;
            if (!TextUtils.isEmpty(skinName)) {
                task.setAnnotation(skinName);
            }
            DownloadTask realTask = addTaskWithCheck(task);
            if (realTask == null) {
                return null;
            }
            if (realTask == task || realTask == null) {
                return realTask;
            }
            int status = realTask.mStatus;
            if (status == 1 || status == 2 || status == 6 || status == 5 || status == 4) {
                resumeTask(realTask);
                return realTask;
            } else if (status != 7) {
                return realTask;
            } else {
                realTask.fixDownloadStatus();
                return null;
            }
        }
    }

    public void addDownloadedTaskListener(OnDownloadedTaskListener listener) {
        synchronized (this.mDownloadedTaskListener) {
            if (!this.mDownloadedTaskListener.contains(listener)) {
                this.mDownloadedTaskListener.add(listener);
            }
        }
    }

    public void removeDownloadedTaskListener(OnDownloadedTaskListener listener) {
        synchronized (this.mDownloadedTaskListener) {
            this.mDownloadedTaskListener.remove(listener);
        }
    }

    public void addTaskObserver(TaskObserver observer) {
        synchronized (this.mTaskObserver) {
            if (!this.mTaskObserver.contains(observer)) {
                this.mTaskObserver.add(observer);
            }
        }
    }

    public void removeTaskObserver(TaskObserver observer) {
        synchronized (this.mTaskObserver) {
            this.mTaskObserver.remove(observer);
        }
    }

    private DownloadTask cursor2Task(Cursor c) {
        if (c == null) {
            return null;
        }
        int idIndex = c.getColumnIndexOrThrow("id");
        int statusIndex = c.getColumnIndexOrThrow("status");
        int urlIndex = c.getColumnIndexOrThrow("url");
        int fileNameIndex = c.getColumnIndexOrThrow("filename");
        int fileFolderIndex = c.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH);
        int downloadedSizeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.DOWNLOADEDSIZE);
        int totalSizeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.TOTALSIZE);
        int isSupportResumeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.ISSUPPORTRESUME);
        int refererIndex = c.getColumnIndexOrThrow(DownloadDBHelper.REFERER);
        int flagIndex = c.getColumnIndexOrThrow(DownloadDBHelper.FLAG);
        int costTimeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.COSTTIME);
        int createTimeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.CREATEDATE);
        int etagIndex = c.getColumnIndexOrThrow("etag");
        int threadNumIndex = c.getColumnIndexOrThrow(DownloadDBHelper.THREADNUM);
        int annotationIndex = c.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATION);
        int annotationExtIndex = c.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATIONEXT);
        int pkgNameIndex = c.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_1);
        int saveFlowSizeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_2);
        DownloadTask task = new DownloadTask(c.getInt(idIndex), (byte) c.getInt(statusIndex), c.getString(urlIndex), c.getString(fileNameIndex), c.getString(fileFolderIndex), c.getLong(downloadedSizeIndex), c.getLong(totalSizeIndex), c.getInt(isSupportResumeIndex) == 1, c.getString(refererIndex), c.getInt(flagIndex), true, c.getLong(costTimeIndex), c.getString(pkgNameIndex));
        task.setCreateTime(c.getLong(createTimeIndex));
        task.setETag(c.getString(etagIndex));
        task.setMaxThreadNum(c.getInt(threadNumIndex));
        task.setAnnotation(c.getString(annotationIndex));
        task.setAnnotationExt(c.getString(annotationExtIndex));
        task.setSaveFlowSize(c.getLong(saveFlowSizeIndex));
        return task;
    }

    private void startTimerAsNeed() {
        synchronized (this) {
            if (this.mTimer == null) {
                this.mTimer = new Timer(TAG, true);
                this.mTimer.schedule(new TimerTask() {
                    public void run() {
                        DownloadManager.this.mTaskManager.execute();
                        synchronized (DownloadManager.this.mOngoingTaskList) {
                            for (DownloadTask task : DownloadManager.this.mOngoingTaskList) {
                                task.accumulateSpeedData();
                                task.saveConfigData();
                                DownloadManager.this.mDBHelper.updateTask(task);
                                DownloadTask dt = task;
                                boolean isNotification = true;
                                if (DownloadManager.this.mTaskManager.getOngoingTask(dt.getTaskId()) == null) {
                                    isNotification = false;
                                }
                                if (!dt.isHidden() && isNotification && dt.mStatus != 1 && dt.mStatus == 2) {
                                }
                            }
                        }
                        DownloadManager.this.cancelTimerAsNeed();
                    }
                }, 1000, 1000);
            }
        }
    }

    public void updateTask(DownloadTask task) {
        if (this.mDBHelper != null) {
            synchronized (task) {
                this.mDBHelper.updateTask(task);
            }
        }
    }

    /* access modifiers changed from: private */
    public void cancelTimerAsNeed() {
        if (!this.mTaskManager.hasTaskOngoing()) {
            DownloaderLog.d(TAG, "[DownloadManager] Cancel download manager timer.");
            synchronized (this) {
                if (this.mTimer != null) {
                    this.mTimer.cancel();
                    this.mTimer = null;
                }
            }
        }
    }

    public void setTasksPrority(boolean isForground) {
        this.mIsForground = isForground;
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask task : this.mNotCompletedTaskList) {
                task.setForground(isForground);
            }
        }
    }

    public void updatePreDownloadTask(String url) {
        if (!TextUtils.isEmpty(url)) {
            synchronized (this.mNotCompletedTaskList) {
                Iterator i$ = this.mNotCompletedTaskList.iterator();
                while (true) {
                    if (!i$.hasNext()) {
                        break;
                    }
                    DownloadTask task = i$.next();
                    if (url.equalsIgnoreCase(task.getTaskUrl())) {
                        task.setPreDownload(false);
                        updatePreDownloadNotification(task);
                        break;
                    }
                }
            }
            DownloadTask dt = getTaskFromDatabase(url);
            if (dt != null && dt.isPreDownload()) {
                dt.setPreDownload(false);
                updateTask(dt);
                updatePreDownloadNotification(dt);
            }
        }
    }

    private void updatePreDownloadNotification(DownloadTask task) {
        int status = task.getStatus();
        if (status != 3) {
            if (status != 5 || task.isHidden() || !this.mNeedNotification || getFailedTaskNum() == 0) {
            }
        } else if (!task.isHidden()) {
            boolean needNotifyDownloadSuccess = true;
            if (!this.mNeedNotification) {
                needNotifyDownloadSuccess = false;
            }
            this.mNeedNotification = true;
            if (!needNotifyDownloadSuccess || task.getNeedNotification()) {
            }
        }
    }

    public void deleteTaskBatch(ArrayList<Integer> taskIds, boolean isDeleteFile, FileDeletedListener listener) {
        deleteTaskBatch(taskIds, isDeleteFile, listener, (ArrayList<File>) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: com.tencent.component.net.download.multiplex.download.DownloadInfo} */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01d5, code lost:
        r30.mDBHelper.deleteTask(r12.next().intValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01e9, code lost:
        r6 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01ec, code lost:
        r4.setTransactionSuccessful();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01ef, code lost:
        if (r4 == null) goto L_0x014b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        r4.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01f7, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01fc, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0201, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0208, code lost:
        r25 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0209, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x020c, code lost:
        r25 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ad, code lost:
        removeTaskFromTaskManager(r20);
        r26 = r30.mOngoingTaskList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ba, code lost:
        monitor-enter(r26);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r21 = removeTaskFromList(r31, r30.mOngoingTaskList);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00cb, code lost:
        monitor-exit(r26);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cc, code lost:
        removeTaskFromTaskManager(r21);
        r4 = r30.mDBHelper.getDatabase();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00dd, code lost:
        if (r4 != null) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00df, code lost:
        com.tencent.component.net.download.multiplex.DownloaderLog.d(TAG, "[DownloadManager] Fail to get SQLiteDatabase!!");
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r4.beginTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f2, code lost:
        if (r32 == false) goto L_0x01cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f4, code lost:
        r19 = false;
        r10 = new java.util.ArrayList<>();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00fb, code lost:
        if (r34 == null) goto L_0x0104;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r10.addAll(r34);
        r19 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0104, code lost:
        r12 = r31.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x010c, code lost:
        if (r12.hasNext() == false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010e, code lost:
        r14 = r12.next();
        r11 = new java.util.ArrayList<>();
        r5 = getTaskFromDatabase(r14.intValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0125, code lost:
        if (r5 == null) goto L_0x0108;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x012b, code lost:
        if (r5.isM3U8() == false) goto L_0x01af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x012d, code lost:
        r2 = r33;
        new java.lang.Thread(new com.tencent.component.net.download.multiplex.download.DownloadManager.AnonymousClass3(r30)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0141, code lost:
        r6 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0142, code lost:
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r6.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0146, code lost:
        if (r4 != null) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r4.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0151, code lost:
        monitor-enter(r30.mDownloadedTaskListener);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r12 = r31.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0155, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x015c, code lost:
        if (r12.hasNext() == false) goto L_0x0205;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x015e, code lost:
        r14 = r12.next();
        r15 = r17.get(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x016f, code lost:
        if (r15 == null) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0171, code lost:
        r16 = new com.tencent.component.net.download.multiplex.download.DownloadInfo();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        r16.taskId = r14.intValue();
        r16.alreadyCompleted = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0188, code lost:
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r15.statusCache = 8;
        r13 = r30.mDownloadedTaskListener.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x019e, code lost:
        if (r13.hasNext() != false) goto L_0x01a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01a0, code lost:
        r13.next().notifyTaskDeleted(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01ac, code lost:
        r25 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01ae, code lost:
        throw r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01af, code lost:
        if (r19 != false) goto L_0x0108;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r10.addAll(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b6, code lost:
        r25 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01b7, code lost:
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01b8, code lost:
        if (r4 != null) goto L_0x01ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        r4.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01bd, code lost:
        throw r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        new com.tencent.component.net.download.multiplex.download.DownloadManager.FileDeleteExecutor(r30, r10, r33).excute();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01ca, code lost:
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:?, code lost:
        r12 = r31.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01d3, code lost:
        if (r12.hasNext() == false) goto L_0x01ec;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x015e A[Catch:{ all -> 0x01ac }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01ba A[SYNTHETIC, Splitter:B:90:0x01ba] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void deleteTaskBatch(java.util.ArrayList<java.lang.Integer> r31, boolean r32, com.tencent.component.net.download.multiplex.download.DownloadManager.FileDeletedListener r33, java.util.ArrayList<java.io.File> r34) {
        /*
            r30 = this;
            java.lang.String r25 = "DownloadManager"
            java.lang.StringBuilder r26 = new java.lang.StringBuilder
            r26.<init>()
            java.lang.String r27 = "[DownloadManager] deleteTaskBatch, isDeleteFile="
            java.lang.StringBuilder r26 = r26.append(r27)
            r0 = r26
            r1 = r32
            java.lang.StringBuilder r26 = r0.append(r1)
            java.lang.String r26 = r26.toString()
            com.tencent.component.net.download.multiplex.DownloaderLog.d(r25, r26)
            r9 = 0
            java.util.HashMap r17 = new java.util.HashMap
            r17.<init>()
            r15 = 0
            r22 = 0
            r20 = 0
            r21 = 0
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r0 = r0.mNotCompletedTaskList
            r26 = r0
            monitor-enter(r26)
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r0 = r0.mNotCompletedTaskList     // Catch:{ all -> 0x00e9 }
            r25 = r0
            java.util.Iterator r18 = r25.iterator()     // Catch:{ all -> 0x00e9 }
            r16 = r15
        L_0x003c:
            boolean r25 = r18.hasNext()     // Catch:{ all -> 0x020e }
            if (r25 == 0) goto L_0x009c
            java.lang.Object r25 = r18.next()     // Catch:{ all -> 0x020e }
            r0 = r25
            com.tencent.component.net.download.multiplex.download.DownloadTask r0 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r0     // Catch:{ all -> 0x020e }
            r22 = r0
            if (r22 == 0) goto L_0x003c
            int r25 = r22.getTaskId()     // Catch:{ all -> 0x020e }
            java.lang.Integer r25 = java.lang.Integer.valueOf(r25)     // Catch:{ all -> 0x020e }
            r0 = r31
            r1 = r25
            boolean r25 = r0.contains(r1)     // Catch:{ all -> 0x020e }
            if (r25 == 0) goto L_0x003c
            java.lang.Integer r23 = new java.lang.Integer     // Catch:{ all -> 0x020e }
            int r25 = r22.getTaskId()     // Catch:{ all -> 0x020e }
            r0 = r23
            r1 = r25
            r0.<init>(r1)     // Catch:{ all -> 0x020e }
            com.tencent.component.net.download.multiplex.download.DownloadInfo r15 = new com.tencent.component.net.download.multiplex.download.DownloadInfo     // Catch:{ all -> 0x020e }
            r15.<init>()     // Catch:{ all -> 0x020e }
            java.lang.String r25 = r22.getTaskUrl()     // Catch:{ all -> 0x00e9 }
            r0 = r25
            r15.url = r0     // Catch:{ all -> 0x00e9 }
            java.lang.String r25 = r22.getAnnotation()     // Catch:{ all -> 0x00e9 }
            r0 = r25
            r15.annotation = r0     // Catch:{ all -> 0x00e9 }
            int r25 = r22.getFlag()     // Catch:{ all -> 0x00e9 }
            r0 = r25
            r15.flag = r0     // Catch:{ all -> 0x00e9 }
            long r28 = r22.getCreateTime()     // Catch:{ all -> 0x00e9 }
            r0 = r28
            r15.createTime = r0     // Catch:{ all -> 0x00e9 }
            r0 = r17
            r1 = r23
            r0.put(r1, r15)     // Catch:{ all -> 0x00e9 }
            r16 = r15
            goto L_0x003c
        L_0x009c:
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r0 = r0.mNotCompletedTaskList     // Catch:{ all -> 0x020e }
            r25 = r0
            r0 = r30
            r1 = r31
            r2 = r25
            java.util.ArrayList r20 = r0.removeTaskFromList(r1, r2)     // Catch:{ all -> 0x020e }
            monitor-exit(r26)     // Catch:{ all -> 0x020e }
            r0 = r30
            r1 = r20
            r0.removeTaskFromTaskManager(r1)
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r0 = r0.mOngoingTaskList
            r26 = r0
            monitor-enter(r26)
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r0 = r0.mOngoingTaskList     // Catch:{ all -> 0x00ec }
            r25 = r0
            r0 = r30
            r1 = r31
            r2 = r25
            java.util.ArrayList r21 = r0.removeTaskFromList(r1, r2)     // Catch:{ all -> 0x00ec }
            monitor-exit(r26)     // Catch:{ all -> 0x00ec }
            r0 = r30
            r1 = r21
            r0.removeTaskFromTaskManager(r1)
            r0 = r30
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r0 = r0.mDBHelper
            r25 = r0
            android.database.sqlite.SQLiteDatabase r4 = r25.getDatabase()
            if (r4 != 0) goto L_0x00ef
            java.lang.String r25 = "DownloadManager"
            java.lang.String r26 = "[DownloadManager] Fail to get SQLiteDatabase!!"
            com.tencent.component.net.download.multiplex.DownloaderLog.d(r25, r26)
            r15 = r16
        L_0x00e8:
            return
        L_0x00e9:
            r25 = move-exception
        L_0x00ea:
            monitor-exit(r26)     // Catch:{ all -> 0x00e9 }
            throw r25
        L_0x00ec:
            r25 = move-exception
            monitor-exit(r26)     // Catch:{ all -> 0x00ec }
            throw r25
        L_0x00ef:
            r4.beginTransaction()     // Catch:{ Exception -> 0x01e9 }
            if (r32 == 0) goto L_0x01cb
            r19 = 0
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ Exception -> 0x01e9 }
            r10.<init>()     // Catch:{ Exception -> 0x01e9 }
            if (r34 == 0) goto L_0x0104
            r0 = r34
            r10.addAll(r0)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r19 = 1
        L_0x0104:
            java.util.Iterator r12 = r31.iterator()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
        L_0x0108:
            boolean r25 = r12.hasNext()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            if (r25 == 0) goto L_0x01be
            java.lang.Object r14 = r12.next()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            java.lang.Integer r14 = (java.lang.Integer) r14     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r11.<init>()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            int r25 = r14.intValue()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r0 = r30
            r1 = r25
            com.tencent.component.net.download.multiplex.download.DownloadTask r5 = r0.getTaskFromDatabase((int) r1)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            if (r5 == 0) goto L_0x0108
            boolean r25 = r5.isM3U8()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            if (r25 == 0) goto L_0x01af
            java.lang.Thread r25 = new java.lang.Thread     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            com.tencent.component.net.download.multiplex.download.DownloadManager$3 r26 = new com.tencent.component.net.download.multiplex.download.DownloadManager$3     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r0 = r26
            r1 = r30
            r2 = r33
            r0.<init>(r14, r5, r2)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r25.<init>(r26)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r25.start()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            goto L_0x0108
        L_0x0141:
            r6 = move-exception
            r9 = r10
        L_0x0143:
            r6.printStackTrace()     // Catch:{ all -> 0x020c }
            if (r4 == 0) goto L_0x014b
            r4.endTransaction()     // Catch:{ IllegalStateException -> 0x01fb }
        L_0x014b:
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadManager$OnDownloadedTaskListener> r0 = r0.mDownloadedTaskListener
            r26 = r0
            monitor-enter(r26)
            java.util.Iterator r12 = r31.iterator()     // Catch:{ all -> 0x0208 }
            r15 = r16
        L_0x0158:
            boolean r25 = r12.hasNext()     // Catch:{ all -> 0x01ac }
            if (r25 == 0) goto L_0x0205
            java.lang.Object r14 = r12.next()     // Catch:{ all -> 0x01ac }
            java.lang.Integer r14 = (java.lang.Integer) r14     // Catch:{ all -> 0x01ac }
            r0 = r17
            java.lang.Object r25 = r0.get(r14)     // Catch:{ all -> 0x01ac }
            r0 = r25
            com.tencent.component.net.download.multiplex.download.DownloadInfo r0 = (com.tencent.component.net.download.multiplex.download.DownloadInfo) r0     // Catch:{ all -> 0x01ac }
            r15 = r0
            if (r15 != 0) goto L_0x018a
            com.tencent.component.net.download.multiplex.download.DownloadInfo r16 = new com.tencent.component.net.download.multiplex.download.DownloadInfo     // Catch:{ all -> 0x01ac }
            r16.<init>()     // Catch:{ all -> 0x01ac }
            int r25 = r14.intValue()     // Catch:{ all -> 0x0208 }
            r0 = r25
            r1 = r16
            r1.taskId = r0     // Catch:{ all -> 0x0208 }
            r25 = 1
            r0 = r25
            r1 = r16
            r1.alreadyCompleted = r0     // Catch:{ all -> 0x0208 }
            r15 = r16
        L_0x018a:
            r25 = 8
            r0 = r25
            r15.statusCache = r0     // Catch:{ all -> 0x01ac }
            r0 = r30
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadManager$OnDownloadedTaskListener> r0 = r0.mDownloadedTaskListener     // Catch:{ all -> 0x01ac }
            r25 = r0
            java.util.Iterator r13 = r25.iterator()     // Catch:{ all -> 0x01ac }
        L_0x019a:
            boolean r25 = r13.hasNext()     // Catch:{ all -> 0x01ac }
            if (r25 == 0) goto L_0x0158
            java.lang.Object r24 = r13.next()     // Catch:{ all -> 0x01ac }
            com.tencent.component.net.download.multiplex.download.DownloadManager$OnDownloadedTaskListener r24 = (com.tencent.component.net.download.multiplex.download.DownloadManager.OnDownloadedTaskListener) r24     // Catch:{ all -> 0x01ac }
            r0 = r24
            r0.notifyTaskDeleted(r15)     // Catch:{ all -> 0x01ac }
            goto L_0x019a
        L_0x01ac:
            r25 = move-exception
        L_0x01ad:
            monitor-exit(r26)     // Catch:{ all -> 0x01ac }
            throw r25
        L_0x01af:
            if (r19 != 0) goto L_0x0108
            r10.addAll(r11)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            goto L_0x0108
        L_0x01b6:
            r25 = move-exception
            r9 = r10
        L_0x01b8:
            if (r4 == 0) goto L_0x01bd
            r4.endTransaction()     // Catch:{ IllegalStateException -> 0x0200 }
        L_0x01bd:
            throw r25
        L_0x01be:
            com.tencent.component.net.download.multiplex.download.DownloadManager$FileDeleteExecutor r8 = new com.tencent.component.net.download.multiplex.download.DownloadManager$FileDeleteExecutor     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r0 = r30
            r1 = r33
            r8.<init>(r0, r10, r1)     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r8.excute()     // Catch:{ Exception -> 0x0141, all -> 0x01b6 }
            r9 = r10
        L_0x01cb:
            java.util.Iterator r12 = r31.iterator()     // Catch:{ Exception -> 0x01e9 }
        L_0x01cf:
            boolean r25 = r12.hasNext()     // Catch:{ Exception -> 0x01e9 }
            if (r25 == 0) goto L_0x01ec
            java.lang.Object r14 = r12.next()     // Catch:{ Exception -> 0x01e9 }
            java.lang.Integer r14 = (java.lang.Integer) r14     // Catch:{ Exception -> 0x01e9 }
            r0 = r30
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r0 = r0.mDBHelper     // Catch:{ Exception -> 0x01e9 }
            r25 = r0
            int r26 = r14.intValue()     // Catch:{ Exception -> 0x01e9 }
            r25.deleteTask(r26)     // Catch:{ Exception -> 0x01e9 }
            goto L_0x01cf
        L_0x01e9:
            r6 = move-exception
            goto L_0x0143
        L_0x01ec:
            r4.setTransactionSuccessful()     // Catch:{ Exception -> 0x01e9 }
            if (r4 == 0) goto L_0x014b
            r4.endTransaction()     // Catch:{ IllegalStateException -> 0x01f6 }
            goto L_0x014b
        L_0x01f6:
            r7 = move-exception
            r15 = r16
            goto L_0x00e8
        L_0x01fb:
            r7 = move-exception
            r15 = r16
            goto L_0x00e8
        L_0x0200:
            r7 = move-exception
            r15 = r16
            goto L_0x00e8
        L_0x0205:
            monitor-exit(r26)     // Catch:{ all -> 0x01ac }
            goto L_0x00e8
        L_0x0208:
            r25 = move-exception
            r15 = r16
            goto L_0x01ad
        L_0x020c:
            r25 = move-exception
            goto L_0x01b8
        L_0x020e:
            r25 = move-exception
            r15 = r16
            goto L_0x00ea
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.deleteTaskBatch(java.util.ArrayList, boolean, com.tencent.component.net.download.multiplex.download.DownloadManager$FileDeletedListener, java.util.ArrayList):void");
    }

    /* access modifiers changed from: private */
    public boolean deleteM3U8Files(int taskId, String fileFolder, String fileName) {
        File folder = new File(fileFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }
        List<File> files = getM3U8DownloadFiles(taskId, fileFolder, fileName);
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
            DownloaderLog.d(TAG, "[DownloadManager] deleteM3U8Files,taskId=" + taskId + ",deleted " + files.size() + " files");
        }
        folder.delete();
        return true;
    }

    /* access modifiers changed from: private */
    public ArrayList<File> getM3U8DownloadFiles(int taskId, String folder, String fileName) {
        File folderFile = new File(folder);
        if (!folder.startsWith(FileUtils.getMediaDirPath()) || folder.length() <= FileUtils.getMediaDirPath().length() + 1 || !folderFile.exists() || !folderFile.isDirectory()) {
            return null;
        }
        ArrayList<File> allFiles = new ArrayList<>();
        File[] subFiles = folderFile.listFiles();
        if (subFiles == null) {
            return null;
        }
        for (File subFile : subFiles) {
            if (!subFile.isDirectory() && subFile.getName().indexOf(fileName) >= 0) {
                allFiles.add(subFile);
                DownloaderLog.d(TAG, "[DownloadManager] getM3U8DownloadFiles,taskId=" + taskId + ",add file=" + subFile.getAbsolutePath());
            }
        }
        return allFiles;
    }

    public ArrayList<DownloadTask> addTaskBatch(ArrayList<DownloadTask> taskList) {
        SQLiteDatabase db = this.mDBHelper.getDatabase();
        if (db == null) {
            DownloaderLog.d(TAG, "[DownloadManager] Fail To Get SQLiteDatabase!!");
            ArrayList<DownloadTask> arrayList = taskList;
            return null;
        }
        ArrayList<DownloadTask> existTaskList = new ArrayList<>();
        boolean isSuccess = true;
        try {
            db.beginTransaction();
            Iterator iterator = taskList.iterator();
            while (iterator.hasNext()) {
                DownloadTask task = iterator.next();
                if (task == null || task.getTaskId() != -1) {
                    DownloaderLog.d(TAG, "[DownloadManager] Add wrong task - " + task);
                    iterator.remove();
                } else {
                    DownloadTask existTask = isTaskAlreadyAdded(task);
                    if (existTask != null) {
                        existTaskList.add(existTask);
                        iterator.remove();
                    } else {
                        task.setFileName(FileUtils.renameFileIfExist(task.getFileFolderPath(), task.getFileName()));
                        this.mDBHelper.addTask(task);
                    }
                }
            }
            db.setTransactionSuccessful();
            if (!(db == null || db == null)) {
                try {
                    db.endTransaction();
                } catch (IllegalStateException e) {
                    ArrayList<DownloadTask> arrayList2 = taskList;
                    return null;
                }
            }
        } catch (Exception e2) {
            isSuccess = false;
            e2.printStackTrace();
            if (!(db == null || db == null)) {
                try {
                    db.endTransaction();
                } catch (IllegalStateException e3) {
                    ArrayList<DownloadTask> arrayList3 = taskList;
                    return null;
                }
            }
        } catch (Throwable th) {
            if (!(db == null || db == null)) {
                try {
                    db.endTransaction();
                } catch (IllegalStateException e4) {
                    ArrayList<DownloadTask> arrayList4 = taskList;
                    return null;
                }
            }
            throw th;
        }
        if (isSuccess) {
            Iterator i$ = taskList.iterator();
            while (i$.hasNext()) {
                DownloadTask task2 = i$.next();
                task2.addObserver(this);
                this.mTaskManager.addTask(task2);
                task2.setForground(this.mIsForground);
            }
            synchronized (this.mNotCompletedTaskList) {
                Iterator i$2 = taskList.iterator();
                while (i$2.hasNext()) {
                    DownloadTask task3 = i$2.next();
                    int taskId = task3.getTaskId();
                    int length = this.mNotCompletedTaskList.size();
                    int i = 0;
                    while (i < length && this.mNotCompletedTaskList.get(i).getTaskId() <= taskId) {
                        i++;
                    }
                    this.mNotCompletedTaskList.add(i, task3);
                    startTimerAsNeed();
                }
            }
            Iterator i$3 = existTaskList.iterator();
            while (i$3.hasNext()) {
                taskList.add(i$3.next());
            }
        } else {
            taskList = null;
        }
        ArrayList<DownloadTask> arrayList5 = taskList;
        return taskList;
    }

    public void resumeTask(DownloadTask task) {
        if (task != null) {
            if (task.mStatus == 6 || task.mStatus == 5 || task.mStatus == 4) {
                if (task.mStatus == 5) {
                }
                task.mStatus = 0;
                task.addObserver(this);
                this.mDBHelper.updateTask(task);
                this.mTaskManager.addTask(task);
                startTimerAsNeed();
            }
        }
    }

    public DownloadTask cancelTask(int taskId) {
        DownloadTask task = this.mTaskManager.removeTask(taskId);
        if (task != null) {
            synchronized (this.mOngoingTaskList) {
                this.mOngoingTaskList.remove(task);
            }
            task.removeObserver(this);
            this.mDBHelper.updateTask(task);
        }
        return task;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.component.net.download.multiplex.download.DownloadTask restartTask(int r29) {
        /*
            r28 = this;
            r28.cancelTask(r29)
            r25 = 0
            r17 = 0
            r0 = r28
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r4 = r0.mDBHelper     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r29
            android.database.Cursor r17 = r4.getDownloadTask((int) r0)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            boolean r4 = r17.moveToFirst()     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            if (r4 == 0) goto L_0x01b7
            java.lang.String r4 = "filename"
            r0 = r17
            int r4 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            java.lang.String r6 = r0.getString(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "filefolderpath"
            r0 = r17
            int r4 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            java.lang.String r7 = r0.getString(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "url"
            r0 = r17
            int r4 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            java.lang.String r27 = r0.getString(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r28
            r0.deleteFile(r7, r6)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "etag"
            r0 = r17
            int r19 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "threadnum"
            r0 = r17
            int r26 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "annotation"
            r0 = r17
            int r16 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "annotationext"
            r0 = r17
            int r2 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "extend_1"
            r0 = r17
            int r23 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "extend_2"
            r0 = r17
            int r24 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r4 = "flag"
            r0 = r17
            int r20 = r0.getColumnIndexOrThrow(r4)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            com.tencent.component.net.download.multiplex.download.DownloadTask r3 = new com.tencent.component.net.download.multiplex.download.DownloadTask     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r4 = -1
            java.lang.String r5 = "url"
            r0 = r17
            int r5 = r0.getColumnIndexOrThrow(r5)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            java.lang.String r5 = r0.getString(r5)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r8 = 0
            java.lang.String r10 = "totalsize"
            r0 = r17
            int r10 = r0.getColumnIndexOrThrow(r10)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            long r10 = r0.getLong(r10)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r12 = "supportresume"
            r0 = r17
            int r12 = r0.getColumnIndexOrThrow(r12)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            int r12 = r0.getInt(r12)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r13 = 1
            if (r12 != r13) goto L_0x018e
            r12 = 1
        L_0x00b1:
            java.lang.String r13 = "referer"
            r0 = r17
            int r13 = r0.getColumnIndexOrThrow(r13)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            java.lang.String r13 = r0.getString(r13)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            java.lang.String r14 = "flag"
            r0 = r17
            int r14 = r0.getColumnIndexOrThrow(r14)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            int r14 = r0.getInt(r14)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r15 = 0
            r3.<init>(r4, r5, r6, r7, r8, r10, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0191, all -> 0x019e }
            r0 = r17
            r1 = r19
            java.lang.String r4 = r0.getString(r1)     // Catch:{ Exception -> 0x01b5 }
            r3.setETag(r4)     // Catch:{ Exception -> 0x01b5 }
            r0 = r17
            r1 = r26
            int r4 = r0.getInt(r1)     // Catch:{ Exception -> 0x01b5 }
            r3.setMaxThreadNum(r4)     // Catch:{ Exception -> 0x01b5 }
            r0 = r17
            r1 = r16
            java.lang.String r4 = r0.getString(r1)     // Catch:{ Exception -> 0x01b5 }
            r3.setAnnotation(r4)     // Catch:{ Exception -> 0x01b5 }
            r0 = r17
            java.lang.String r4 = r0.getString(r2)     // Catch:{ Exception -> 0x01b5 }
            r3.setAnnotationExt(r4)     // Catch:{ Exception -> 0x01b5 }
            r0 = r17
            r1 = r23
            java.lang.String r4 = r0.getString(r1)     // Catch:{ Exception -> 0x01b5 }
            r3.setPackageName(r4)     // Catch:{ Exception -> 0x01b5 }
            r0 = r17
            r1 = r24
            long r4 = r0.getLong(r1)     // Catch:{ Exception -> 0x01b5 }
            r3.setSaveFlowSize(r4)     // Catch:{ Exception -> 0x01b5 }
        L_0x0111:
            if (r17 == 0) goto L_0x0116
            r17.close()
        L_0x0116:
            if (r3 == 0) goto L_0x018d
            r0 = r28
            com.tencent.component.net.download.multiplex.download.DownloadDBHelper r4 = r0.mDBHelper
            r0 = r29
            r4.deleteTask(r0)
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r5 = r0.mNotCompletedTaskList
            monitor-enter(r5)
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mNotCompletedTaskList     // Catch:{ all -> 0x01aa }
            int r22 = r4.size()     // Catch:{ all -> 0x01aa }
            r21 = 0
        L_0x0130:
            r0 = r21
            r1 = r22
            if (r0 >= r1) goto L_0x0153
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mNotCompletedTaskList     // Catch:{ all -> 0x01aa }
            r0 = r21
            java.lang.Object r4 = r4.get(r0)     // Catch:{ all -> 0x01aa }
            com.tencent.component.net.download.multiplex.download.DownloadTask r4 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r4     // Catch:{ all -> 0x01aa }
            int r4 = r4.getTaskId()     // Catch:{ all -> 0x01aa }
            r0 = r29
            if (r4 != r0) goto L_0x01a7
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mNotCompletedTaskList     // Catch:{ all -> 0x01aa }
            r0 = r21
            r4.remove(r0)     // Catch:{ all -> 0x01aa }
        L_0x0153:
            monitor-exit(r5)     // Catch:{ all -> 0x01aa }
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r5 = r0.mOngoingTaskList
            monitor-enter(r5)
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mOngoingTaskList     // Catch:{ all -> 0x01b0 }
            int r22 = r4.size()     // Catch:{ all -> 0x01b0 }
            r21 = 0
        L_0x0163:
            r0 = r21
            r1 = r22
            if (r0 >= r1) goto L_0x0186
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mOngoingTaskList     // Catch:{ all -> 0x01b0 }
            r0 = r21
            java.lang.Object r4 = r4.get(r0)     // Catch:{ all -> 0x01b0 }
            com.tencent.component.net.download.multiplex.download.DownloadTask r4 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r4     // Catch:{ all -> 0x01b0 }
            int r4 = r4.getTaskId()     // Catch:{ all -> 0x01b0 }
            r0 = r29
            if (r4 != r0) goto L_0x01ad
            r0 = r28
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r0.mOngoingTaskList     // Catch:{ all -> 0x01b0 }
            r0 = r21
            r4.remove(r0)     // Catch:{ all -> 0x01b0 }
        L_0x0186:
            monitor-exit(r5)     // Catch:{ all -> 0x01b0 }
            r4 = 1
            r0 = r28
            r0.addTask(r3, r4)
        L_0x018d:
            return r3
        L_0x018e:
            r12 = 0
            goto L_0x00b1
        L_0x0191:
            r18 = move-exception
            r3 = r25
        L_0x0194:
            r18.printStackTrace()     // Catch:{ all -> 0x01b3 }
            if (r17 == 0) goto L_0x0116
            r17.close()
            goto L_0x0116
        L_0x019e:
            r4 = move-exception
            r3 = r25
        L_0x01a1:
            if (r17 == 0) goto L_0x01a6
            r17.close()
        L_0x01a6:
            throw r4
        L_0x01a7:
            int r21 = r21 + 1
            goto L_0x0130
        L_0x01aa:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x01aa }
            throw r4
        L_0x01ad:
            int r21 = r21 + 1
            goto L_0x0163
        L_0x01b0:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x01b0 }
            throw r4
        L_0x01b3:
            r4 = move-exception
            goto L_0x01a1
        L_0x01b5:
            r18 = move-exception
            goto L_0x0194
        L_0x01b7:
            r3 = r25
            goto L_0x0111
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.restartTask(int):com.tencent.component.net.download.multiplex.download.DownloadTask");
    }

    public DownloadTask getSkinTask(String skinName) {
        if (TextUtils.isEmpty(skinName)) {
            return null;
        }
        synchronized (this.mOngoingTaskList) {
            int length = this.mOngoingTaskList.size();
            for (int i = 0; i < length; i++) {
                DownloadTask task = this.mOngoingTaskList.get(i);
                if (skinName.equalsIgnoreCase(task.getAnnotation())) {
                    return task;
                }
            }
            return null;
        }
    }

    public boolean hasSkinTaskFailed(String skinName) {
        if (TextUtils.isEmpty(skinName)) {
            return false;
        }
        synchronized (this.mNotCompletedTaskList) {
            int length = this.mNotCompletedTaskList.size();
            for (int i = 0; i < length; i++) {
                DownloadTask task = this.mNotCompletedTaskList.get(i);
                if (skinName.equalsIgnoreCase(task.getAnnotation()) && task.getStatus() == 5) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean deleteSkinFile(String skinName) {
        if (TextUtils.isEmpty(skinName)) {
            return false;
        }
        Cursor c = null;
        try {
            Cursor c2 = this.mDBHelper.getAllDownloadList();
            if (c2 != null) {
                int idIndex = c2.getColumnIndexOrThrow("id");
                int annotationIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATION);
                while (c2.moveToNext()) {
                    if (skinName.equalsIgnoreCase(c2.getString(annotationIndex))) {
                        deleteTask(c2.getInt(idIndex), true);
                    }
                }
            }
            if (c2 == null) {
                return true;
            }
            c2.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (c == null) {
                return false;
            }
            c.close();
            return false;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    public boolean deleteTask(int taskId, boolean isDeleteFile) {
        boolean fileDeleted;
        boolean fileDeleted2 = false;
        DownloaderLog.d(TAG, "[DownloadManager] deleteTask isDeleteFile:" + isDeleteFile);
        DownloadTask task = null;
        synchronized (this.mNotCompletedTaskList) {
            Iterator i$ = this.mNotCompletedTaskList.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                DownloadTask t = i$.next();
                if (t.getTaskId() == taskId) {
                    task = t;
                    break;
                }
            }
        }
        DownloadInfo info = new DownloadInfo();
        info.taskId = taskId;
        info.statusCache = 8;
        if (task != null) {
            info.alreadyCompleted = task.getStatus() == 3;
            task.setDeleted(true);
            task.closeSavedFile();
            info.flag = task.getFlag();
            info.annotation = task.getAnnotation();
            info.fileFolderPath = task.getFileFolderPath();
            info.fileName = task.getFileName();
            info.url = task.getTaskUrl();
            info.createTime = task.getCreateTime();
        }
        DownloadTask task2 = cancelTask(taskId);
        if (isDeleteFile) {
            if (task2 != null) {
                DownloaderLog.d(TAG, "[DownloadManager] task != null");
                final String path = task2.getFileFolderPath();
                final String name = task2.getFileName();
                FileUtils.deleteDownloadTypeIconFile(name, path);
                if (task2.isM3U8()) {
                    final DownloadTask m3u8Task = task2;
                    new Thread(new Runnable() {
                        public void run() {
                            boolean unused = DownloadManager.this.deleteM3U8Files(m3u8Task.getTaskId(), path, name);
                        }
                    }).start();
                    fileDeleted2 = true;
                } else {
                    fileDeleted2 = deleteFile(path, name);
                }
                DownloaderLog.d(TAG, "[DownloadManager] deleteTask path:" + path + "   " + "name:" + name + "   " + "fileDeleted:" + fileDeleted2);
            } else {
                DownloaderLog.d(TAG, "[DownloadManager] task == null");
                Cursor c = null;
                try {
                    c = this.mDBHelper.getDownloadTask(taskId);
                    if (c != null && c.moveToFirst()) {
                        int taskId1 = c.getInt(c.getColumnIndexOrThrow("id"));
                        int flag = c.getInt(c.getColumnIndexOrThrow(DownloadDBHelper.FLAG));
                        final String path2 = c.getString(c.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH));
                        final String name2 = c.getString(c.getColumnIndexOrThrow("filename"));
                        String string = c.getString(c.getColumnIndexOrThrow("url"));
                        if (DownloadTask.isM3U8Flag(flag)) {
                            final int i = taskId1;
                            new Thread(new Runnable() {
                                public void run() {
                                    boolean unused = DownloadManager.this.deleteM3U8Files(i, path2, name2);
                                }
                            }).start();
                            fileDeleted = true;
                        } else {
                            fileDeleted = deleteFile(path2, name2);
                        }
                        DownloaderLog.d(TAG, "[DownloadManager] deleteTask path:" + path2 + "   " + "name:" + name2 + "   " + "fileDeleted:" + fileDeleted2);
                    }
                    if (c != null) {
                        c.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (c != null) {
                        c.close();
                    }
                } catch (Throwable th) {
                    if (c != null) {
                        c.close();
                    }
                    throw th;
                }
            }
        }
        this.mDBHelper.deleteTask(taskId);
        synchronized (this.mNotCompletedTaskList) {
            int length = this.mNotCompletedTaskList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (this.mNotCompletedTaskList.get(i2).getTaskId() == taskId) {
                    this.mNotCompletedTaskList.remove(i2);
                    break;
                } else {
                    i2++;
                }
            }
        }
        synchronized (this.mOngoingTaskList) {
            int length2 = this.mOngoingTaskList.size();
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    break;
                } else if (this.mOngoingTaskList.get(i3).getTaskId() == taskId) {
                    this.mOngoingTaskList.remove(i3);
                    break;
                } else {
                    i3++;
                }
            }
        }
        if (info != null) {
            synchronized (this.mDownloadedTaskListener) {
                for (OnDownloadedTaskListener listener : this.mDownloadedTaskListener) {
                    listener.notifyTaskDeleted(info);
                }
            }
        }
        return fileDeleted2;
    }

    public boolean deleteFile(String path, String name) {
        try {
            File cfgFile = new File(path, "." + name + ".dltmp");
            if (cfgFile != null && cfgFile.exists()) {
                cfgFile.delete();
                File tmpFile = new File(path, name + DownloadTask.DL_FILE_SUFFIX);
                if (tmpFile.exists()) {
                    tmpFile.delete();
                }
            }
            File file = new File(path, name);
            if (file.exists()) {
                return file.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getDownloadFileByTask(DownloadTask task) {
        if (task == null) {
            return null;
        }
        String path = task.getFileFolderPath();
        String name = task.getFileName();
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(name)) {
            return null;
        }
        File file = new File(path, name);
        File file2 = file;
        return file;
    }

    public DownloadTask addTask(DownloadTask task, boolean isExecuted) {
        DownloaderLog.d(TAG, "[DownloadManager] addTask");
        if (task == null || task.getTaskId() != -1) {
            DownloaderLog.d(TAG, "[DownloadManager] Add wrong task - " + task);
            return null;
        }
        DownloadTask existTask = isTaskAlreadyAdded(task);
        if (existTask != null) {
            DownloaderLog.d(TAG, "[DownloadManager] addTask, already exist");
            return existTask;
        }
        task.addObserver(this);
        try {
            this.mDBHelper.addTask(task);
            this.mTaskManager.addTask(task);
            task.setForground(this.mIsForground);
            int taskId = task.getTaskId();
            DownloaderLog.d(TAG, "[DownloadManager] addTask, task.isM3U8=" + task.isM3U8() + ",taskId=" + task.getTaskId());
            synchronized (this.mNotCompletedTaskList) {
                int length = this.mNotCompletedTaskList.size();
                int i = 0;
                while (i < length && this.mNotCompletedTaskList.get(i).getTaskId() <= taskId) {
                    i++;
                }
                DownloaderLog.d(TAG, "[DownloadManager] addTask, add to mNotCompletedTaskList");
                this.mNotCompletedTaskList.add(i, task);
            }
            if (isExecuted) {
                startTimerAsNeed();
            }
            return task;
        } catch (SQLiteException e) {
            DownloaderLog.e(TAG, "[DownloadManager] Data base add task error", e);
            return null;
        }
    }

    private DownloadTask isTaskAlreadyAdded(DownloadTask task) {
        Cursor c = null;
        DownloadTask existTask = null;
        try {
            c = this.mDBHelper.getDownloadTask(task.getTaskUrl());
            if (c != null && c.moveToNext()) {
                DownloaderLog.d(TAG, "[DownloadManager] A task with same url already existed, so just skipped.");
                DownloadTask dt = getTask(task.getTaskUrl());
                existTask = dt != null ? dt : cursor2Task(c);
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
        return existTask;
    }

    private ArrayList<DownloadTask> removeTaskFromList(ArrayList<Integer> taskIds, List<DownloadTask> taskList) {
        ArrayList<DownloadTask> tasksToRemove = new ArrayList<>();
        Iterator iterator = taskList.iterator();
        while (iterator.hasNext()) {
            DownloadTask task = iterator.next();
            if (taskIds.contains(Integer.valueOf(task.getTaskId()))) {
                iterator.remove();
                tasksToRemove.add(task);
            }
        }
        return tasksToRemove;
    }

    private void removeTaskFromTaskManager(ArrayList<DownloadTask> tasks) {
        if (tasks != null && tasks.size() >= 1) {
            Iterator i$ = tasks.iterator();
            while (i$.hasNext()) {
                DownloadTask downloadTask = i$.next();
                this.mTaskManager.removeTask(downloadTask.getTaskId());
                downloadTask.removeObserver(this);
                downloadTask.setDeleted(true);
                downloadTask.closeSavedFile();
            }
        }
    }

    private boolean getDownloadFilesById(Integer id, List<File> toDelFiles) {
        boolean isM3U8 = false;
        Cursor c = null;
        try {
            c = this.mDBHelper.getDownloadTask(id.intValue());
            if (c != null && c.moveToFirst()) {
                int flag = c.getInt(c.getColumnIndexOrThrow(DownloadDBHelper.FLAG));
                String path = c.getString(c.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH));
                String name = c.getString(c.getColumnIndexOrThrow("filename"));
                if (DownloadTask.isM3U8Flag(flag)) {
                    List<File> m3u8DownloadFiles = getM3U8DownloadFiles(id.intValue(), path, name);
                    if (m3u8DownloadFiles != null) {
                        toDelFiles.addAll(m3u8DownloadFiles);
                    }
                    isM3U8 = true;
                } else {
                    toDelFiles.add(new File(path, name));
                }
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
        return isM3U8;
    }

    public void startAppDownloadBatchTask(ArrayList<DownFileInfo> downList, ArrayList<OnDownloadFeedbackListener> arrayList) {
        long totalFileSize = 0;
        for (int i = 0; i < downList.size(); i++) {
            totalFileSize += downList.get(i).mSize;
        }
        if (FileUtils.getSdcardFreeSpace() < totalFileSize) {
        }
    }

    public Task startTask(String url, TaskObserver observer) {
        DownloadTask task = new DownloadTask(url);
        task.addObserver(observer);
        task.addObserver(this);
        Thread taskThread = new Thread(task, "download_startTask");
        taskThread.setPriority(1);
        taskThread.start();
        startTimerAsNeed();
        return task;
    }

    public DownloadTask startTask(String url, String path, TaskObserver observer) {
        DownloadTask task = new DownloadTask(url, path);
        task.addObserver(observer);
        task.addObserver(this);
        Thread taskThread = new Thread(task, "download_startTask");
        taskThread.setPriority(1);
        taskThread.start();
        startTimerAsNeed();
        return task;
    }

    public DownloadTask startTask(String url, TaskObserver observer, String allPath, boolean isNeedNotification) {
        int pos;
        this.mNeedNotification = isNeedNotification;
        String path = null;
        String fileName = null;
        if (!(allPath == null || (pos = allPath.lastIndexOf(47)) == -1)) {
            path = allPath.substring(0, pos + 1);
            fileName = allPath.substring(pos + 1, allPath.length());
            DownloaderLog.d(TAG, "[DownloadManager] path : " + path);
            DownloaderLog.d(TAG, "[DownloadManager] fileName : " + fileName);
        }
        DownloadTask task = new DownloadTask(url, path);
        task.addObserver(observer);
        task.addObserver(this);
        task.setNeedNotification(false);
        task.setFileName(fileName);
        Thread taskThread = new Thread(task, "download_startTask2");
        taskThread.setPriority(1);
        taskThread.start();
        startTimerAsNeed();
        return task;
    }

    public DownloadTask startImageAttachmentTask(String url, String fileName, long fileSize, TaskObserver observer, String path, boolean isNeedNotification, boolean isWWW) {
        this.mNeedNotification = isNeedNotification;
        DownloadTask task = new DownloadTask(url, fileName, path, fileSize, (String) null, isWWW);
        task.addObserver(observer);
        task.addObserver(this);
        task.setFileName(fileName);
        task.setNeedNotification(false);
        new Thread(task, "download_startImageAttachmentTask").start();
        startTimerAsNeed();
        return task;
    }

    private void addUpdateTask(String updateUrl) {
        DownloadTask tempTask;
        if (!TextUtils.isEmpty(updateUrl) && updateUrl.startsWith("http://")) {
            DownloadTask task = new DownloadTask(updateUrl);
            task.setFlag(1);
            if (addTaskWithCheck(task, updateUrl.contains(".apk")) && (tempTask = getDownloadedFileInfo(updateUrl)) != null) {
                FileUtils.openLocalFile(FileDownload.context, tempTask.getFileFolderPath(), tempTask.getFileName());
            }
        }
    }

    public void startAllDownloadTask() {
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask task : this.mNotCompletedTaskList) {
                resumeTask(task);
            }
        }
    }

    public void pauseAllDownloadTask() {
        synchronized (this.mOngoingTaskList) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < this.mOngoingTaskList.size(); i++) {
                temp.add(Integer.valueOf(this.mOngoingTaskList.get(i).getTaskId()));
            }
            Iterator i$ = temp.iterator();
            while (i$.hasNext()) {
                cancelTask(i$.next().intValue());
            }
        }
    }

    private DownloadTask getTaskFromInfo(DownloadInfo info) {
        if (info == null) {
            return null;
        }
        DownloadTask task = new DownloadTask(-1, info.url, info.fileName, info.fileFolderPath, 0, info.fileSize, true, info.referer, info.flag, false);
        task.setAnnotation(info.annotation);
        task.setAnnotationExt(info.annotationExt);
        task.setSaveFlowSize(info.saveFlowSize);
        return task;
    }

    public void startDownloadToLocalTask(DownloadInfo downloadInfo, OnDownloadFeedbackListener listener) {
        startDownloadTaskWithUI(downloadInfo, false, listener);
    }

    public DownloadTask getDownloadedFileInfo(String url) {
        DownloadTask task = new DownloadTask(url);
        boolean isSuccess = false;
        Cursor c = null;
        try {
            Cursor c2 = this.mDBHelper.getDownloadTask(url);
            if (c2 != null && c2.moveToFirst() && c2.getInt(c2.getColumnIndexOrThrow("status")) == 3) {
                task.setFileName(c2.getString(c2.getColumnIndexOrThrow("filename")));
                task.setFileFolderPath(c2.getString(c2.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH)));
                isSuccess = true;
            }
            if (c2 != null) {
                c2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
        if (!isSuccess) {
            return null;
        }
        return task;
    }

    public void renameTask(String url, String newName) {
        DownloadTask dt = getDownloadCompletedTaskFromDatabase(url);
        if (dt == null) {
            dt = getTask(url);
        }
        if (dt != null) {
            dt.rename(FileUtils.renameFileIfExist(dt.getFileFolderPath(), newName));
            updateTask(dt);
        }
    }

    public DownloadTask addTaskWithCheck(DownloadTask dt) {
        dt.setFileName(FileUtils.renameFileIfExist(dt.getFileFolderPath(), dt.getFileName()));
        return addTask(dt, true);
    }

    public boolean addTaskWithCheck(DownloadTask dt, boolean isNeedReturn) {
        return addTaskWithCheck(dt, isNeedReturn, true);
    }

    private boolean addTaskWithCheck(DownloadTask dt, boolean isNeedReturn, boolean note) {
        boolean isDownloaded = false;
        DownloaderLog.d(TAG, "[DownloadManager] addTaskWithCheck getFileName:" + dt.getFileName());
        String newName = FileUtils.renameFileIfExist(dt.getFileFolderPath(), dt.getFileName());
        dt.setFileName(newName);
        DownloaderLog.d(TAG, "[DownloadManager] addTaskWithCheck newName:" + newName);
        Cursor c = null;
        try {
            c = this.mDBHelper.getDownloadTask(dt.getTaskUrl());
            DownloaderLog.d(TAG, "[DownloadManager] getDownloadTask(url) return " + c);
            if (c != null && c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndexOrThrow("status"));
                int taskId = c.getInt(c.getColumnIndexOrThrow("id"));
                DownloaderLog.d(TAG, "[DownloadManager] DB task.status=" + status + ",taskId=" + taskId);
                DownloadTask task = null;
                synchronized (this.mNotCompletedTaskList) {
                    Iterator i$ = this.mNotCompletedTaskList.iterator();
                    while (true) {
                        if (!i$.hasNext()) {
                            break;
                        }
                        DownloadTask t = i$.next();
                        if (t.getTaskId() == taskId) {
                            task = t;
                            break;
                        }
                    }
                }
                DownloaderLog.d(TAG, "[DownloadManager] find task from mNoteCompletedTaskList=" + task);
                if (task != null) {
                    status = task.getStatus();
                    DownloaderLog.d(TAG, "[DownloadManager] Mem task.status=" + status + ",task.isM3U8=" + task.isM3U8());
                }
                switch (status) {
                    case 3:
                        if (new File(c.getString(c.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH)), c.getString(c.getColumnIndexOrThrow("filename"))).exists() && isNeedReturn) {
                            isDownloaded = true;
                            break;
                        }
                    case 4:
                    case 5:
                    case 6:
                        if (task == null) {
                            int etagIndex = c.getColumnIndexOrThrow("etag");
                            int threadNumIndex = c.getColumnIndexOrThrow(DownloadDBHelper.THREADNUM);
                            int annotationIndex = c.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATION);
                            int annotationExtIndex = c.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATIONEXT);
                            int pkgNameIndex = c.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_1);
                            int saveFlowSizeIndex = c.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_2);
                            int columnIndexOrThrow = c.getColumnIndexOrThrow(DownloadDBHelper.FLAG);
                            DownloadTask task2 = new DownloadTask(-1, c.getString(c.getColumnIndexOrThrow("url")), c.getString(c.getColumnIndexOrThrow("filename")), c.getString(c.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH)), c.getLong(c.getColumnIndexOrThrow(DownloadDBHelper.DOWNLOADEDSIZE)), c.getLong(c.getColumnIndexOrThrow(DownloadDBHelper.TOTALSIZE)), c.getInt(c.getColumnIndexOrThrow(DownloadDBHelper.ISSUPPORTRESUME)) == 1, c.getString(c.getColumnIndexOrThrow(DownloadDBHelper.REFERER)), c.getInt(c.getColumnIndexOrThrow(DownloadDBHelper.FLAG)), true);
                            task2.setETag(c.getString(etagIndex));
                            task2.setMaxThreadNum(c.getInt(threadNumIndex));
                            task2.setAnnotation(c.getString(annotationIndex));
                            task2.setAnnotationExt(c.getString(annotationExtIndex));
                            task2.setPackageName(c.getString(pkgNameIndex));
                            task2.setSaveFlowSize(c.getLong(saveFlowSizeIndex));
                            break;
                        }
                        break;
                }
            } else {
                addTask(dt, true);
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
                throw th;
            }
        }
        return isDownloaded;
    }

    private void startDownloadVideoForLaterWatching(DownloadInfo downloadInfo, OnDownloadFeedbackListener listener) {
        DownloaderLog.d(TAG, "[DownloadManager] downloadInfo.isM3U8=" + downloadInfo.videoType);
        addTaskWithCheck(new DownloadTask(downloadInfo.url, downloadInfo.fileName, downloadInfo.fileFolderPath, downloadInfo.fileSize, downloadInfo.referer, true), true, false);
    }

    private void startDownloadTaskWithUI(DownloadInfo downloadInfo, boolean isUpdateFile, OnDownloadFeedbackListener listener) {
        if (FileUtils.getSdcardFreeSpace() >= downloadInfo.fileSize) {
            if (isUpdateFile) {
                addUpdateTask(downloadInfo.url);
                return;
            }
            DownloadTask dt = getTaskFromDatabase(downloadInfo.url);
            if (Apn.isWifiMode() && dt == null) {
                startPreDownloadTask(downloadInfo);
            }
        }
    }

    private void startPreDownloadTask(DownloadInfo downloadInfo) {
        downloadInfo.isPreDownload = true;
        if (downloadInfo.videoType == 0) {
            downloadInfo.fileFolderPath = FileUtils.getMediaDirPath();
            startDownlodTask(downloadInfo);
        } else if (downloadInfo.videoType == 1) {
            downloadInfo.fileFolderPath = FileUtils.getMediaDirPath() + Constants.URL_PATH_DELIMITER + downloadInfo.fileName;
            startDownlodTask(downloadInfo);
        } else {
            DownloadTask task = new DownloadTask(downloadInfo.url, downloadInfo.fileName, (String) null, downloadInfo.fileSize, downloadInfo.referer, downloadInfo.isWWW);
            if (!TextUtils.isEmpty(downloadInfo.skinName)) {
                task.setAnnotation(downloadInfo.skinName);
            }
            task.setPreDownload(true);
            addTaskWithCheck(task, false, false);
        }
    }

    public void onTaskCreated(Task task) {
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskCreated(task);
            }
        }
        DownloadTask dt = (DownloadTask) task;
        boolean isNotification = true;
        if (this.mTaskManager.getOngoingTask(dt.getTaskId()) == null) {
            isNotification = false;
        }
        if (dt.isHidden() || isNotification || (dt.getFlag() & 2) == 0) {
        }
    }

    public void onTaskStarted(Task task) {
        DownloadTask dt = (DownloadTask) task;
        this.mDBHelper.updateTask(dt);
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskStarted(task);
            }
        }
        synchronized (this.mOngoingTaskList) {
            this.mOngoingTaskList.add(dt);
        }
    }

    public void onTaskProgress(Task task) {
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskProgress(task);
            }
        }
    }

    public void onTaskCompleted(Task task) {
        DownloaderLog.d(TAG, "[DownloadManager] onTaskCompleted()");
        DownloadTask dt = (DownloadTask) task;
        this.mTaskManager.taskCompleted(dt);
        this.mDBHelper.updateTask(dt);
        synchronized (this.mNotCompletedTaskList) {
            this.mNotCompletedTaskList.remove(dt);
        }
        synchronized (this.mOngoingTaskList) {
            this.mOngoingTaskList.remove(dt);
        }
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskCompleted(dt);
            }
        }
        if (!dt.isHidden()) {
            boolean needNotifyDownloadSuccess = true;
            if (!this.mNeedNotification) {
                needNotifyDownloadSuccess = false;
            }
            this.mNeedNotification = true;
            if (dt.isPreDownload() || !needNotifyDownloadSuccess || dt.getNeedNotification()) {
            }
        }
        FileDownload.context.getApplicationContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(IMSDKFileProvider.FILE_SCHEME + dt.getFileFolderPath() + Constants.URL_PATH_DELIMITER + dt.getFileName())));
        if ((dt.getFlag() & 1) == 1) {
            new File(dt.getFileFolderPath(), dt.getFileName());
        }
    }

    public void onTaskFailed(Task task) {
        DownloaderLog.d(TAG, "[DownloadManager] onTaskFailed() - " + task.mStatus);
        DownloadTask dt = (DownloadTask) task;
        this.mTaskManager.taskCompleted(dt);
        this.mDBHelper.updateTask(dt);
        synchronized (this.mOngoingTaskList) {
            this.mOngoingTaskList.remove(dt);
        }
        synchronized (this.mTaskObserver) {
            if (this.mTaskObserver.size() == 0) {
                String errorDesc = dt.getErrorDesc();
                if (errorDesc == null || !"".equals(errorDesc)) {
                }
            } else {
                for (TaskObserver observer : this.mTaskObserver) {
                    observer.onTaskFailed(dt);
                }
            }
        }
        if (dt.isHidden() || dt.isPreDownload() || !this.mNeedNotification || getFailedTaskNum() != 0) {
        }
        DownloaderLog.d(TAG, "DownloadManager onTaskFailed flow:" + dt.getFlow());
    }

    public List<DownloadTask> getNotCompletedTaskList(boolean isHide) {
        List<DownloadTask> list;
        synchronized (this.mNotCompletedTaskList) {
            list = new LinkedList<>();
            for (DownloadTask task : this.mNotCompletedTaskList) {
                if (task.isHidden() == isHide) {
                    list.add(task);
                }
            }
        }
        return list;
    }

    public List<DownloadTask> getOngoingTaskList(boolean isHide) {
        List<DownloadTask> list;
        synchronized (this.mOngoingTaskList) {
            list = new LinkedList<>();
            for (DownloadTask task : this.mOngoingTaskList) {
                if (task.isHidden() == isHide) {
                    list.add(task);
                }
            }
        }
        return list;
    }

    public int getFailedTaskNum() {
        int failedNum = 0;
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask task : this.mNotCompletedTaskList) {
                if (task != null && !task.isHidden() && task.getStatus() == 5) {
                    failedNum++;
                }
            }
        }
        return failedNum;
    }

    public synchronized boolean hasOnGoingList() {
        boolean z = true;
        synchronized (this) {
            if (this.mOngoingTaskList != null) {
                if (this.mOngoingTaskList.size() > 0 && this.mNotCompletedTaskList.size() > 0) {
                    Iterator i$ = this.mOngoingTaskList.iterator();
                    while (true) {
                        if (i$.hasNext()) {
                            if (!i$.next().isHidden()) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            z = false;
        }
        return z;
    }

    public Cursor getDownloadedList() throws Exception {
        return this.mDBHelper.getDownloadedList();
    }

    public ArrayList<DownloadTask> getDownloadedTaskList(boolean isHide) {
        ArrayList<DownloadTask> list = new ArrayList<>();
        Cursor c = null;
        try {
            Cursor c2 = getDownloadedList();
            if (c2 != null) {
                int idIndex = c2.getColumnIndexOrThrow("id");
                int statusIndex = c2.getColumnIndexOrThrow("status");
                int urlIndex = c2.getColumnIndexOrThrow("url");
                int fileNameIndex = c2.getColumnIndexOrThrow("filename");
                int fileFolderIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.FILEFOLDERPATH);
                int downloadedSizeIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.DOWNLOADEDSIZE);
                int totalSizeIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.TOTALSIZE);
                int isSupportResumeIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.ISSUPPORTRESUME);
                int refererIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.REFERER);
                int flagIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.FLAG);
                int costTimeIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.COSTTIME);
                int etagIndex = c2.getColumnIndexOrThrow("etag");
                int threadNumIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.THREADNUM);
                int annotationIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATION);
                int annotationExtIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.ANNOTATIONEXT);
                int pkgNameIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_1);
                int saveFlowSizeIndex = c2.getColumnIndexOrThrow(DownloadDBHelper.EXTEND_2);
                while (c2.moveToNext()) {
                    int flag = c2.getInt(flagIndex);
                    if (((flag & 32) == 32) == isHide) {
                        DownloadTask task = new DownloadTask(c2.getInt(idIndex), (byte) c2.getInt(statusIndex), c2.getString(urlIndex), c2.getString(fileNameIndex), c2.getString(fileFolderIndex), c2.getLong(downloadedSizeIndex), c2.getLong(totalSizeIndex), c2.getInt(isSupportResumeIndex) == 1, c2.getString(refererIndex), flag, true, c2.getLong(costTimeIndex), c2.getString(pkgNameIndex));
                        task.setETag(c2.getString(etagIndex));
                        task.setMaxThreadNum(c2.getInt(threadNumIndex));
                        task.setAnnotation(c2.getString(annotationIndex));
                        task.setAnnotationExt(c2.getString(annotationExtIndex));
                        task.setPackageName(c2.getString(pkgNameIndex));
                        task.setSaveFlowSize(c2.getLong(saveFlowSizeIndex));
                        if (task.mStatus == 3) {
                            list.add(task);
                        }
                    }
                }
            }
            if (c2 != null) {
                c2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
        return list;
    }

    public DownloadTask getOngoingTask(int taskId) {
        return this.mTaskManager.getOngoingTask(taskId);
    }

    public void changePoolThreshold(int threshold) {
        this.mTaskManager.setWorkerPoolSize(threshold);
    }

    public void shutdown() {
        try {
            DownloaderLog.d(TAG, "[DownloadManager] begin update when shutdown");
            synchronized (this.mOngoingTaskList) {
                for (DownloadTask task : this.mOngoingTaskList) {
                    task.updateCostTime();
                    this.mDBHelper.updateTask(task);
                }
            }
            DownloaderLog.d(TAG, "[DownloadManager] end update when shutdown");
        } catch (Exception e) {
            DownloaderLog.d(TAG, "[DownloadManager] Error while shutdowning DownloadManager - " + e);
        }
        DownloaderLog.d(TAG, "[DownloadManager] downloa manager cancel all notifications");
    }

    public void checkResumedTask() {
        DownloadTask updateTask = null;
        synchronized (this.mNotCompletedTaskList) {
            Iterator i$ = this.mNotCompletedTaskList.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                DownloadTask task = i$.next();
                if (task.isPreDownload()) {
                    deleteTask(task.getTaskId(), true);
                } else if (task.mStatus == 0 || task.mStatus == 1 || task.mStatus == 2) {
                    if ((task.getFlag() & 1) == 1) {
                        task.mStatus = 6;
                        updateTask = task;
                    } else if (!task.isHidden()) {
                        break;
                    }
                }
            }
        }
        if (updateTask != null) {
            resumeTask(updateTask);
        }
        updatePreviousTask();
    }

    public void resumePreviousTask() {
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask task : this.mNotCompletedTaskList) {
                if (task.mStatus == 0 || task.mStatus == 1 || task.mStatus == 2) {
                    task.mStatus = 6;
                    if (!task.isHidden()) {
                        task.setFlag(task.getFlag() | 2);
                        resumeTask(task);
                    } else {
                        this.mDBHelper.updateTask(task);
                    }
                }
            }
        }
    }

    public void updatePreviousTask() {
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask task : this.mNotCompletedTaskList) {
                if (task.mStatus == 0 || task.mStatus == 1 || task.mStatus == 2) {
                    task.mStatus = 6;
                    this.mDBHelper.updateTask(task);
                }
            }
        }
    }

    public void postDangerDownloadDialog(String url) {
    }

    public boolean isOnGoing(String url) {
        boolean isRunning = false;
        synchronized (this.mNotCompletedTaskList) {
            Iterator i$ = this.mNotCompletedTaskList.iterator();
            while (true) {
                if (i$.hasNext()) {
                    if (i$.next().getTaskUrl().equals(url)) {
                        isRunning = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return isRunning;
    }

    public DownloadTask getOnGoingTask(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadTask task = null;
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask t : this.mNotCompletedTaskList) {
                if (url.equals(t.getTaskUrl())) {
                    task = t;
                }
            }
        }
        return task;
    }

    public int getOnGoingTaskProgress(String url) {
        if (TextUtils.isEmpty(url)) {
            return 0;
        }
        int progress = 0;
        synchronized (this.mNotCompletedTaskList) {
            for (DownloadTask t : this.mNotCompletedTaskList) {
                if (url.equals(t.getTaskUrl())) {
                    progress = t.getProgress();
                }
            }
        }
        return progress;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r3 = r4.mOngoingTaskList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0 = r4.mOngoingTaskList.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        if (r0.hasNext() == false) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
        r1 = r0.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        if (r1 == null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (r1.getTaskUrl().equals(r5) == false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004e, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.component.net.download.multiplex.download.DownloadTask getTask(java.lang.String r5) {
        /*
            r4 = this;
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r4.mNotCompletedTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r2 = r4.mNotCompletedTaskList     // Catch:{ all -> 0x004a }
            java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x004a }
        L_0x0009:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x004a }
            if (r2 == 0) goto L_0x0023
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x004a }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x004a }
            if (r1 == 0) goto L_0x0009
            java.lang.String r2 = r1.getTaskUrl()     // Catch:{ all -> 0x004a }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x004a }
            if (r2 == 0) goto L_0x0009
            monitor-exit(r3)     // Catch:{ all -> 0x004a }
        L_0x0022:
            return r1
        L_0x0023:
            monitor-exit(r3)     // Catch:{ all -> 0x004a }
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r4.mOngoingTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r2 = r4.mOngoingTaskList     // Catch:{ all -> 0x0047 }
            java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x0047 }
        L_0x002d:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x0047 }
            if (r2 == 0) goto L_0x004d
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0047 }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x002d
            java.lang.String r2 = r1.getTaskUrl()     // Catch:{ all -> 0x0047 }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x0047 }
            if (r2 == 0) goto L_0x002d
            monitor-exit(r3)     // Catch:{ all -> 0x0047 }
            goto L_0x0022
        L_0x0047:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0047 }
            throw r2
        L_0x004a:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004a }
            throw r2
        L_0x004d:
            monitor-exit(r3)     // Catch:{ all -> 0x0047 }
            r1 = 0
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.getTask(java.lang.String):com.tencent.component.net.download.multiplex.download.DownloadTask");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r3 = r4.mOngoingTaskList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r0 = r4.mOngoingTaskList.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002b, code lost:
        if (r0.hasNext() == false) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002d, code lost:
        r1 = r0.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        if (r1.getTaskId() != r5) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0042, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.component.net.download.multiplex.download.DownloadTask getTaskByID(int r5) {
        /*
            r4 = this;
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r4.mNotCompletedTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r2 = r4.mNotCompletedTaskList     // Catch:{ all -> 0x003e }
            java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x003e }
        L_0x0009:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x003e }
            if (r2 == 0) goto L_0x001d
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x003e }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x003e }
            int r2 = r1.getTaskId()     // Catch:{ all -> 0x003e }
            if (r2 != r5) goto L_0x0009
            monitor-exit(r3)     // Catch:{ all -> 0x003e }
        L_0x001c:
            return r1
        L_0x001d:
            monitor-exit(r3)     // Catch:{ all -> 0x003e }
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r4.mOngoingTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r2 = r4.mOngoingTaskList     // Catch:{ all -> 0x003b }
            java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x003b }
        L_0x0027:
            boolean r2 = r0.hasNext()     // Catch:{ all -> 0x003b }
            if (r2 == 0) goto L_0x0041
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x003b }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x003b }
            int r2 = r1.getTaskId()     // Catch:{ all -> 0x003b }
            if (r2 != r5) goto L_0x0027
            monitor-exit(r3)     // Catch:{ all -> 0x003b }
            goto L_0x001c
        L_0x003b:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003b }
            throw r2
        L_0x003e:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003e }
            throw r2
        L_0x0041:
            monitor-exit(r3)     // Catch:{ all -> 0x003b }
            r1 = 0
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.getTaskByID(int):com.tencent.component.net.download.multiplex.download.DownloadTask");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002e, code lost:
        r3 = r5.mOngoingTaskList;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0030, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0 = r5.mOngoingTaskList.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        if (r0.hasNext() == false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
        r1 = r0.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
        if (r6.equals(r1.getFileName()) == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.component.net.download.multiplex.download.DownloadTask getTaskByFileName(java.lang.String r6) {
        /*
            r5 = this;
            r2 = 0
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 == 0) goto L_0x0009
            r1 = r2
        L_0x0008:
            return r1
        L_0x0009:
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r5.mNotCompletedTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r5.mNotCompletedTaskList     // Catch:{ all -> 0x002a }
            java.util.Iterator r0 = r4.iterator()     // Catch:{ all -> 0x002a }
        L_0x0012:
            boolean r4 = r0.hasNext()     // Catch:{ all -> 0x002a }
            if (r4 == 0) goto L_0x002d
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x002a }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x002a }
            java.lang.String r4 = r1.getFileName()     // Catch:{ all -> 0x002a }
            boolean r4 = r6.equals(r4)     // Catch:{ all -> 0x002a }
            if (r4 == 0) goto L_0x0012
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            goto L_0x0008
        L_0x002a:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            throw r2
        L_0x002d:
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r3 = r5.mOngoingTaskList
            monitor-enter(r3)
            java.util.List<com.tencent.component.net.download.multiplex.download.DownloadTask> r4 = r5.mOngoingTaskList     // Catch:{ all -> 0x004f }
            java.util.Iterator r0 = r4.iterator()     // Catch:{ all -> 0x004f }
        L_0x0037:
            boolean r4 = r0.hasNext()     // Catch:{ all -> 0x004f }
            if (r4 == 0) goto L_0x0052
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x004f }
            com.tencent.component.net.download.multiplex.download.DownloadTask r1 = (com.tencent.component.net.download.multiplex.download.DownloadTask) r1     // Catch:{ all -> 0x004f }
            java.lang.String r4 = r1.getFileName()     // Catch:{ all -> 0x004f }
            boolean r4 = r6.equals(r4)     // Catch:{ all -> 0x004f }
            if (r4 == 0) goto L_0x0037
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            goto L_0x0008
        L_0x004f:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            throw r2
        L_0x0052:
            monitor-exit(r3)     // Catch:{ all -> 0x004f }
            r1 = r2
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.net.download.multiplex.download.DownloadManager.getTaskByFileName(java.lang.String):com.tencent.component.net.download.multiplex.download.DownloadTask");
    }

    public DownloadTask getTaskFromDatabase(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadTask task = null;
        Cursor c = null;
        try {
            Cursor c2 = this.mDBHelper.getDownloadTask(url);
            if (c2 != null && c2.moveToNext()) {
                task = cursor2Task(c2);
            }
            if (c2 == null) {
                return task;
            }
            c2.close();
            return task;
        } catch (Exception e) {
            e.printStackTrace();
            if (c == null) {
                return null;
            }
            c.close();
            return null;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    public DownloadTask getTaskFromDatabase(int taskId) {
        DownloadTask task = null;
        Cursor c = null;
        try {
            Cursor c2 = this.mDBHelper.getDownloadTask(taskId);
            if (c2 != null && c2.moveToNext()) {
                task = cursor2Task(c2);
            }
            if (c2 != null) {
                c2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
        return task;
    }

    public void showNoSdcardDialog() {
    }

    public void showNoSpaceDialog() {
    }

    public DownloadTask downloadFile(String url, String fileName, String folderPath, boolean needNotifcation) {
        File file;
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadTask dt = getTask(url);
        if (dt == null) {
            dt = getDownloadCompletedTaskFromDatabase(url);
        }
        if (!(dt == null || (file = new File(FileUtils.getApkDir(), dt.getFileName())) == null || !file.exists())) {
            int status = dt.mStatus;
            if (status == 1 || status == 2 || status == 6 || status == 5 || status == 4) {
                resumeTask(dt);
                return dt;
            } else if (status == 3) {
                FileUtils.openLocalFile(FileDownload.context, file.getParent(), file.getName());
                return null;
            }
        }
        if (dt != null) {
            deleteTask(dt.getTaskId(), true);
        }
        DownloadTask task = new DownloadTask(url, fileName, folderPath);
        task.setNeedNotification(needNotifcation);
        DownloadTask tempTask = addTask(task, true);
        if (tempTask != null) {
            resumeTask(tempTask);
        }
        return tempTask;
    }

    public DownloadTask downloadApk(String url, String fileName, boolean needNotifcation) {
        return downloadFile(url, fileName, (String) null, needNotifcation);
    }

    public DownloadTask getDownloadCompletedTaskFromDatabase(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        DownloadTask task = null;
        Cursor c = null;
        try {
            Cursor c2 = this.mDBHelper.getDownloadCompletedTask(url);
            if (c2 != null && c2.moveToNext()) {
                task = cursor2Task(c2);
            }
            if (c2 == null) {
                return task;
            }
            c2.close();
            return task;
        } catch (Exception e) {
            e.printStackTrace();
            if (c == null) {
                return null;
            }
            c.close();
            return null;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    public boolean hasTaskCompleted(String skinName) {
        if (TextUtils.isEmpty(skinName)) {
            return false;
        }
        Cursor c = null;
        boolean completed = false;
        try {
            Cursor c2 = this.mDBHelper.getDownloadedSkinTask(skinName);
            if (c2 != null && c2.moveToNext()) {
                completed = true;
            }
            if (c2 == null) {
                return completed;
            }
            c2.close();
            return completed;
        } catch (Exception e) {
            e.printStackTrace();
            if (c == null) {
                return false;
            }
            c.close();
            return false;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    public boolean isPauseToResumed(Task task) {
        if (task == null || !(task instanceof DownloadTask) || !((DownloadTask) task).getIsResumedTask()) {
            return false;
        }
        return true;
    }

    public void onTaskExtEvent(Task task) {
        updateTask((DownloadTask) task);
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskExtEvent(task);
            }
        }
    }

    public boolean isCanClear() {
        List<DownloadTask> downloadedlist = getDownloadedTaskList(false);
        if (downloadedlist != null && downloadedlist.size() > 0) {
            return true;
        }
        if (this.mNotCompletedTaskList == null || this.mNotCompletedTaskList.size() <= 0) {
            return false;
        }
        return true;
    }

    private class FileDeleteExecutor implements FileDeletedListener {
        private static final int MAX_THREADS_COUNT = 5;
        private ArrayList<File> mFileList;
        private FileDeletedListener mListener;
        private int mMaxThreadCount;
        private boolean mTryDelParentFolder;

        public FileDeleteExecutor(DownloadManager downloadManager, ArrayList<File> fileList, FileDeletedListener listener) {
            this(fileList, listener, false);
        }

        public FileDeleteExecutor(ArrayList<File> fileList, FileDeletedListener listener, boolean tryDelParentFolder) {
            this.mFileList = null;
            this.mMaxThreadCount = 5;
            this.mTryDelParentFolder = false;
            this.mFileList = fileList;
            this.mListener = listener;
            this.mTryDelParentFolder = tryDelParentFolder;
            if (tryDelParentFolder) {
                this.mMaxThreadCount = 1;
            } else {
                this.mMaxThreadCount = 5;
            }
        }

        public void excute() {
            if (this.mFileList != null) {
                int threadCount = 0;
                synchronized (this.mFileList) {
                    Iterator iterator = this.mFileList.iterator();
                    while (iterator.hasNext()) {
                        iterator.remove();
                        new DeleteFileThread(iterator.next(), this).start();
                        threadCount++;
                        if (threadCount >= this.mMaxThreadCount) {
                            return;
                        }
                    }
                }
            }
        }

        public void onDeletedFail(File file) {
            if (this.mListener != null) {
                this.mListener.onDeletedFail(file);
            }
            createNewThreadIfNeed();
        }

        public void onDeletedSuccess(File file) {
            if (this.mListener != null) {
                this.mListener.onDeletedSuccess(file);
            }
            if (this.mTryDelParentFolder && this.mFileList != null && this.mFileList.size() <= 0) {
                File parentFile = file.getParentFile();
                if (parentFile.isDirectory()) {
                    File[] subFiles = parentFile.listFiles();
                    if (subFiles == null || subFiles.length <= 0) {
                        parentFile.delete();
                        DownloaderLog.d(DownloadManager.TAG, "[DownloadManager] 删除父目录:" + parentFile.getAbsolutePath());
                    } else {
                        DownloaderLog.d(DownloadManager.TAG, "[DownloadManager] 未删除父目录, 因为还有" + subFiles.length + "个子文件,subFiles[0]=" + subFiles[0].getAbsolutePath());
                    }
                }
            }
            createNewThreadIfNeed();
        }

        private void createNewThreadIfNeed() {
            File file = null;
            if (this.mFileList != null) {
                synchronized (this.mFileList) {
                    if (!this.mFileList.isEmpty()) {
                        file = this.mFileList.remove(0);
                    }
                }
            }
            if (file != null) {
                new DeleteFileThread(file, this).start();
            }
        }
    }

    private class DeleteFileThread extends Thread {
        File mFile = null;
        FileDeletedListener mListener = null;

        public DeleteFileThread(File file, FileDeletedListener listener) {
            setName("DeleteFileThread");
            this.mFile = file;
            this.mListener = listener;
        }

        public void run() {
            if (this.mFile != null) {
                String path = this.mFile.getParent();
                String name = this.mFile.getName();
                boolean rslt = DownloadManager.this.deleteFile(path, name);
                FileUtils.deleteDownloadTypeIconFile(name, path);
                if (this.mListener == null) {
                    return;
                }
                if (rslt) {
                    this.mListener.onDeletedSuccess(this.mFile);
                } else {
                    this.mListener.onDeletedFail(this.mFile);
                }
            }
        }
    }

    private void fireExtEvent(DownloadTask dt) {
        updateTask(dt);
        synchronized (this.mTaskObserver) {
            for (TaskObserver observer : this.mTaskObserver) {
                observer.onTaskExtEvent(dt);
            }
        }
    }

    private void updateInstalledTask(Intent intent) {
        DownloadTask existTask;
        String pkgName = getPkgName(intent);
        if (!TextUtils.isEmpty(pkgName)) {
            DownloaderLog.d(TAG, "[DownloadManager] install start" + pkgName);
            Cursor c = null;
            try {
                Cursor c2 = this.mDBHelper.getApkDownloadTask(pkgName);
                if (!(c2 == null || !c2.moveToNext() || (existTask = cursor2Task(c2)) == null)) {
                    DownloaderLog.d(TAG, "[DownloadManager] install" + pkgName);
                    existTask.setInstalled(true);
                    fireExtEvent(existTask);
                }
                if (c2 != null) {
                    c2.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
                throw th;
            }
        }
    }

    private String getInstallerExtra(Intent intent) {
        PackageManager myapp = FileDownload.context.getPackageManager();
        String pkgName = getPkgName(intent);
        DownloaderLog.d(TAG, "[DownloadManager] get package name from intent:" + pkgName);
        String installer = "";
        try {
            Object ret = PackageManager.class.getMethod("getInstallerPackageName", new Class[]{String.class}).invoke(myapp, new Object[]{pkgName});
            if (ret != null) {
                installer = (String) ret;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
        DownloaderLog.d(TAG, "[DownloadManager] get task id from intent:" + installer);
        if (!TextUtils.isEmpty(installer)) {
            return installer;
        }
        return null;
    }

    private String getPkgName(Intent intent) {
        try {
            return intent.getDataString().substring(8);
        } catch (Exception e) {
            return null;
        }
    }

    private DownloadTask startPluginDownload(DownloadInfo downloadInfo) {
        if (downloadInfo == null) {
            return null;
        }
        DownloadTask dt = getTask(downloadInfo.url);
        if (dt != null) {
            int status = dt.mStatus;
            if (status == 1 || status == 2 || status == 6 || status == 5 || status == 4) {
                resumeTask(dt);
                return dt;
            } else if (status == 7) {
                dt.fixDownloadStatus();
                return null;
            }
        }
        DownloadTask completedTask = getDownloadCompletedTaskFromDatabase(downloadInfo.url);
        if (completedTask != null) {
            deleteTask(completedTask.getTaskId(), true);
        }
        DownloadTask task = new DownloadTask(downloadInfo.url, downloadInfo.fileName, downloadInfo.fileFolderPath);
        task.setNeedNotification(downloadInfo.needNotification);
        if (!TextUtils.isEmpty(downloadInfo.annotation)) {
            task.setAnnotation(downloadInfo.annotation);
        }
        String skinName = downloadInfo.skinName;
        if (!TextUtils.isEmpty(skinName)) {
            task.setAnnotation(skinName);
        }
        task.setHidden(true);
        DownloadTask tempTask = addTask(task, true);
        if (tempTask != null) {
            resumeTask(tempTask);
        }
        return tempTask;
    }

    public void setShow2gConfirmDialog(boolean b) {
        this.mShow2GConfirmDialog = b;
    }

    public boolean getShow2gConfirmDialog() {
        return this.mShow2GConfirmDialog;
    }
}
