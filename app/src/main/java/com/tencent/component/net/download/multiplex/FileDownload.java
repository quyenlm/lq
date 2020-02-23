package com.tencent.component.net.download.multiplex;

import android.content.Context;
import com.tencent.component.ComponentContext;
import com.tencent.component.net.download.multiplex.download.DownloadManager;
import com.tencent.component.net.download.multiplex.download.DownloadTask;
import com.tencent.component.net.download.multiplex.task.TaskObserver;
import com.tencent.component.utils.FileUtil;

public class FileDownload {
    private static final String TAG = FileDownload.class.getName();
    public static Context context;
    static DownloadManager downloadManager = null;

    static {
        FileUtil.init(ComponentContext.getContext());
    }

    private static DownloadManager getDownloadManager() {
        if (downloadManager == null) {
            downloadManager = new DownloadManager();
            downloadManager.init();
        }
        return downloadManager;
    }

    public static void init(Context context2) {
        if (context == null) {
            context = context2.getApplicationContext();
        }
    }

    public static boolean startDownloadFile(String url, String folderPath, String fileName, TaskObserver observer) {
        DownloadTask dTask = getDownloadManager().downloadFile(url, fileName, folderPath, true);
        DownloaderLog.i(TAG, "======== [FileDonwloader] startDownloadApp DownloadTask:" + dTask + "=========");
        if (dTask == null) {
            return false;
        }
        dTask.addObserver(observer);
        return true;
    }

    public static boolean addDownloadTask(String url, String folderPath, String fileName, TaskObserver observer) {
        DownloadTask dTask = getDownloadManager().getTask(url);
        DownloaderLog.i(TAG, "======== [FileDonwloader] addDownloadTask DownloadTask:" + dTask + "=========");
        if (dTask != null) {
            return false;
        }
        DownloadTask dTask2 = new DownloadTask(url, fileName, folderPath);
        dTask2.addObserver(observer);
        getDownloadManager().addTask(dTask2, false);
        return true;
    }

    public static void startAllDownloadTask() {
        DownloaderLog.i(TAG, "======== [FileDonwloader] startAllDownloadTask =========");
        getDownloadManager().startAllDownloadTask();
    }

    public static void pauseAllDownloadTask() {
        DownloaderLog.i(TAG, "======== [FileDonwloader] pauseAllDownloadTask =========");
        getDownloadManager().pauseAllDownloadTask();
    }

    public static DownloadTask getDownloadTask(String url) {
        return getDownloadManager().getTask(url);
    }

    public static boolean downloadTaskToNext(DownloadTask dTask) {
        DownloaderLog.i(TAG, "======== [FileDonwloader] downloadTaskToNext =========");
        if (dTask != null) {
            switch (dTask.getStatus()) {
                case 0:
                case 1:
                case 2:
                    getDownloadManager().cancelTask(dTask.getTaskId());
                    return true;
                case 5:
                case 6:
                    getDownloadManager().resumeTask(dTask);
                    return true;
            }
        }
        return false;
    }

    public static void addTaskObsever(String url, TaskObserver observer) {
        DownloaderLog.i(TAG, "======== [FileDonwloader] addTaskObsever =========");
        DownloadTask dTask = getDownloadTask(url);
        if (dTask != null) {
            dTask.addObserver(observer);
        }
    }

    public static boolean deleteDownloadTask(String url) {
        DownloaderLog.i(TAG, "======== [FileDonwloader] deleteDownloadTask =========");
        DownloadTask task = getDownloadTask(url);
        if (task != null) {
            return getDownloadManager().deleteTask(task.getTaskId(), true);
        }
        return false;
    }
}
