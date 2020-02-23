package com.tencent.imsdk.expansion.downloader.impl;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;
import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.R;
import com.tencent.imsdk.expansion.downloader.impl.DownloadNotification;

public class V3CustomNotification implements DownloadNotification.ICustomNotification {
    long mCurrentBytes = -1;
    int mIcon;
    Notification mNotification = new Notification();
    PendingIntent mPendingIntent;
    CharSequence mTicker;
    long mTimeRemaining;
    CharSequence mTitle;
    long mTotalBytes = -1;

    public void setIcon(int icon) {
        this.mIcon = icon;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setTotalBytes(long totalBytes) {
        this.mTotalBytes = totalBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.mCurrentBytes = currentBytes;
    }

    public Notification updateNotification(Context c) {
        Notification n = this.mNotification;
        n.icon = this.mIcon;
        n.flags |= 2;
        if (Build.VERSION.SDK_INT > 10) {
            n.flags |= 8;
        }
        RemoteViews expandedView = new RemoteViews(c.getPackageName(), R.layout.status_bar_ongoing_event_progress_bar);
        expandedView.setTextViewText(R.id.title, this.mTitle);
        expandedView.setViewVisibility(R.id.description, 0);
        expandedView.setTextViewText(R.id.description, Helpers.getDownloadProgressString(this.mCurrentBytes, this.mTotalBytes));
        expandedView.setViewVisibility(R.id.progress_bar_frame, 0);
        expandedView.setProgressBar(R.id.progress_bar, (int) (this.mTotalBytes >> 8), (int) (this.mCurrentBytes >> 8), this.mTotalBytes <= 0);
        expandedView.setViewVisibility(R.id.time_remaining, 0);
        expandedView.setTextViewText(R.id.time_remaining, c.getString(R.string.time_remaining_notification, new Object[]{Helpers.getTimeRemaining(this.mTimeRemaining)}));
        expandedView.setTextViewText(R.id.progress_text, Helpers.getDownloadProgressPercent(this.mCurrentBytes, this.mTotalBytes));
        expandedView.setImageViewResource(R.id.appIcon, this.mIcon);
        n.contentView = expandedView;
        n.contentIntent = this.mPendingIntent;
        return n;
    }

    public void setPendingIntent(PendingIntent contentIntent) {
        this.mPendingIntent = contentIntent;
    }

    public void setTicker(CharSequence ticker) {
        this.mTicker = ticker;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.mTimeRemaining = timeRemaining;
    }
}
