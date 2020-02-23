package com.tencent.component.net.download.multiplex.download;

import com.tencent.component.net.download.multiplex.download.DownloadManager;
import com.tencent.component.net.download.multiplex.download.extension.FileUtils;

public class DownloadInfo {
    public static final int DOWNLOAD_FROM_DELTA_UPDATE = 3;
    public static final int DOWNLOAD_FROM_QQMARKET = 1;
    public static final int DOWNLOAD_FROM_VIDEO = 2;
    public boolean alreadyCompleted;
    public String annotation;
    public String annotationExt;
    public long createTime;
    public boolean deleteTaskIfCompleted;
    public String fileFolderPath;
    public String fileName;
    public long fileSize;
    public int flag;
    public byte fromWhere;
    public boolean hasBtn;
    public boolean hasSelectWnd;
    public boolean isPluginTask;
    public boolean isPreDownload;
    public boolean isWWW;
    public DownloadManager.OnDownloadFeedbackListener listener;
    public int mWindowId;
    public boolean needNotification;
    public DownloadTaskConfirmObserver observer;
    public String referer;
    public long saveFlowSize;
    public String skinName;
    public byte statusCache;
    public int taskId;
    public String url;
    public int videoType;

    public interface DownloadTaskConfirmObserver {
        void onTaskCancelled(DownloadInfo downloadInfo);

        void onTaskCreated(DownloadTask downloadTask);
    }

    public DownloadInfo(String url2, String fileName2, long fileSize2, String referer2, String fileFolderPath2) {
        this.alreadyCompleted = false;
        this.annotation = "";
        this.annotationExt = "";
        this.url = url2;
        this.fileName = fileName2;
        this.fileSize = fileSize2;
        this.referer = referer2;
        if (fileFolderPath2 == null) {
            this.fileFolderPath = FileUtils.getDownloadDir().getAbsolutePath();
        } else {
            this.fileFolderPath = fileFolderPath2;
        }
        this.videoType = 99;
        this.fromWhere = 0;
    }

    public DownloadInfo() {
        this.alreadyCompleted = false;
        this.annotation = "";
        this.annotationExt = "";
        this.url = null;
        this.fileName = null;
        this.fileSize = 0;
        this.referer = null;
        this.fileFolderPath = null;
        this.isWWW = false;
        this.hasBtn = true;
        this.hasSelectWnd = true;
        this.isPluginTask = false;
        this.needNotification = true;
        this.deleteTaskIfCompleted = false;
        this.saveFlowSize = 0;
        this.videoType = 99;
        this.fromWhere = 0;
        this.isPreDownload = false;
    }
}
