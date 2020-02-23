package com.garena.android.gpns.notification;

import com.garena.android.gpns.notification.event.NotifyEvent;

public interface NotifyItem {
    void onNotify(NotifyEvent notifyEvent);
}
