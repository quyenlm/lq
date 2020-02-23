package com.tencent.imsdk.expansion.downloader.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Messenger;
import com.tencent.imsdk.expansion.downloader.DownloadProgressInfo;
import com.tencent.imsdk.expansion.downloader.DownloaderClientMarshaller;
import com.tencent.imsdk.expansion.downloader.DownloaderServiceMarshaller;
import com.tencent.imsdk.expansion.downloader.IDownloaderClient;
import com.tencent.imsdk.expansion.downloader.IDownloaderService;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.IStub;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;

public class DownloaderUIBase implements IDownloaderClient {
    private IDownloaderClient iDownloaderClient = null;
    private IStub mDownloaderClientStub;
    private IDownloaderService mRemoteService;

    private PendingIntent createPendingIntent(Context context) {
        Intent launchIntent = ((Activity) context).getIntent();
        Intent i2LaunchActivity4Notification = new Intent(context, context.getClass());
        i2LaunchActivity4Notification.setFlags(335544320);
        i2LaunchActivity4Notification.setAction(launchIntent.getAction());
        if (launchIntent.getCategories() != null) {
            for (String category : launchIntent.getCategories()) {
                i2LaunchActivity4Notification.addCategory(category);
            }
        }
        return PendingIntent.getActivity(context, 0, i2LaunchActivity4Notification, 134217728);
    }

    public int onCreate(Context context, PendingIntent pendingIntent) {
        IMLogger.init(context);
        if (pendingIntent == null) {
            pendingIntent = createPendingIntent(context);
        }
        try {
            this.mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, DownloaderService.class);
            return DownloaderClientMarshaller.startDownloadServiceIfRequired(context, pendingIntent, (Class<?>) DownloaderService.class);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void onStart(Context context) {
        if (this.mDownloaderClientStub != null) {
            this.mDownloaderClientStub.connect(context);
        }
    }

    public void onStop(Context context) {
        if (this.mDownloaderClientStub != null) {
            this.mDownloaderClientStub.disconnect(context);
        }
    }

    public void onServiceConnected(Messenger m) {
        if (this.iDownloaderClient != null) {
            this.iDownloaderClient.onServiceConnected(m);
        }
        this.mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
        this.mRemoteService.onClientUpdated(this.mDownloaderClientStub.getMessenger());
    }

    public void onDownloadStateChanged(int newState) {
        if (this.iDownloaderClient != null) {
            this.iDownloaderClient.onDownloadStateChanged(newState);
        }
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        if (this.iDownloaderClient != null) {
            this.iDownloaderClient.onDownloadProgress(progress);
        }
    }

    public IDownloaderService getmRemoteService() {
        return this.mRemoteService;
    }

    public void setCustomDownloaderClient(IDownloaderClient download) {
        this.iDownloaderClient = download;
    }
}
