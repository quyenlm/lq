package com.tencent.imsdk.expansion.downloader.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Messenger;
import com.tencent.imsdk.expansion.downloader.DownloadProgressInfo;
import com.tencent.imsdk.expansion.downloader.DownloaderClientMarshaller;
import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.IDownloaderClient;
import com.tencent.imsdk.expansion.downloader.R;

public class DownloadNotification implements IDownloaderClient {
    static final String LOGTAG = "DownloadNotification";
    static final int NOTIFICATION_ID = LOGTAG.hashCode();
    private IDownloaderClient mClientProxy;
    private PendingIntent mContentIntent;
    private final Context mContext;
    private Notification.Builder mCurNotificationBuilder;
    private Notification mCurrentNotification;
    private String mCurrentText;
    private String mCurrentTitle;
    final ICustomNotification mCustomNotification;
    private CharSequence mLabel;
    private final NotificationManager mNotificationManager;
    private DownloadProgressInfo mProgressInfo;
    private int mState = -1;

    public interface ICustomNotification {
        void setCurrentBytes(long j);

        void setIcon(int i);

        void setPendingIntent(PendingIntent pendingIntent);

        void setTicker(CharSequence charSequence);

        void setTimeRemaining(long j);

        void setTitle(CharSequence charSequence);

        void setTotalBytes(long j);

        Notification updateNotification(Context context);
    }

    public PendingIntent getClientIntent() {
        return this.mContentIntent;
    }

    public void setClientIntent(PendingIntent mClientIntent) {
        this.mContentIntent = mClientIntent;
    }

    public void resendState() {
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadStateChanged(this.mState);
        }
    }

    public void onDownloadStateChanged(int newState) {
        int iconResource;
        int stringDownloadID;
        boolean ongoingEvent;
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadStateChanged(newState);
        }
        if (newState != this.mState) {
            this.mState = newState;
            if (newState != 1 && this.mContentIntent != null) {
                switch (newState) {
                    case 0:
                        iconResource = 17301642;
                        stringDownloadID = R.string.state_unknown;
                        ongoingEvent = false;
                        break;
                    case 2:
                    case 3:
                        iconResource = 17301634;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                    case 4:
                        iconResource = 17301633;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                    case 5:
                    case 7:
                        iconResource = 17301634;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = false;
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        iconResource = 17301642;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = false;
                        break;
                    default:
                        iconResource = 17301642;
                        stringDownloadID = Helpers.getDownloaderStringResourceIDFromState(newState);
                        ongoingEvent = true;
                        break;
                }
                this.mCurrentText = this.mContext.getString(stringDownloadID);
                this.mCurrentTitle = this.mLabel.toString();
                this.mCurNotificationBuilder.setTicker(this.mLabel + ": " + this.mCurrentText).setSmallIcon(iconResource).setContentTitle(this.mCurrentTitle).setContentText(this.mCurrentText).setContentIntent(this.mContentIntent);
                this.mCurrentNotification = this.mCurNotificationBuilder.getNotification();
                if (ongoingEvent) {
                    this.mCurrentNotification.flags |= 2;
                } else {
                    this.mCurrentNotification.flags &= -3;
                    this.mCurrentNotification.flags |= 16;
                }
                this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentNotification);
            }
        }
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        this.mProgressInfo = progress;
        if (this.mClientProxy != null) {
            this.mClientProxy.onDownloadProgress(progress);
        }
        if (progress.mOverallTotal <= 0) {
            this.mCurNotificationBuilder.setTicker(this.mCurrentTitle).setSmallIcon(17301633).setContentTitle(this.mLabel).setContentText(this.mCurrentText).setContentIntent(this.mContentIntent);
            this.mCurrentNotification = this.mCurNotificationBuilder.getNotification();
        } else {
            this.mCustomNotification.setCurrentBytes(progress.mOverallProgress);
            this.mCustomNotification.setTotalBytes(progress.mOverallTotal);
            this.mCustomNotification.setIcon(17301633);
            this.mCustomNotification.setPendingIntent(this.mContentIntent);
            this.mCustomNotification.setTicker(this.mLabel + ": " + this.mCurrentText);
            this.mCustomNotification.setTitle(this.mLabel);
            this.mCustomNotification.setTimeRemaining(progress.mTimeRemaining);
            this.mCurrentNotification = this.mCustomNotification.updateNotification(this.mContext);
        }
        this.mNotificationManager.notify(NOTIFICATION_ID, this.mCurrentNotification);
    }

    public void setMessenger(Messenger msg) {
        this.mClientProxy = DownloaderClientMarshaller.CreateProxy(msg);
        if (this.mProgressInfo != null) {
            this.mClientProxy.onDownloadProgress(this.mProgressInfo);
        }
        if (this.mState != -1) {
            this.mClientProxy.onDownloadStateChanged(this.mState);
        }
    }

    DownloadNotification(Context ctx, CharSequence applicationLabel) {
        this.mContext = ctx;
        this.mLabel = applicationLabel;
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        this.mCustomNotification = CustomNotificationFactory.createCustomNotification();
        this.mCurNotificationBuilder = new Notification.Builder(ctx);
    }

    public void onServiceConnected(Messenger m) {
    }
}
