package com.tencent.imsdk.expansion.downloader.impl;

import android.os.Build;
import com.tencent.imsdk.expansion.downloader.impl.DownloadNotification;

public class CustomNotificationFactory {
    public static DownloadNotification.ICustomNotification createCustomNotification() {
        if (Build.VERSION.SDK_INT > 13) {
            return new V14CustomNotification();
        }
        return new V3CustomNotification();
    }
}
