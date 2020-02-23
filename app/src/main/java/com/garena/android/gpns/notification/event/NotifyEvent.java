package com.garena.android.gpns.notification.event;

public class NotifyEvent {
    public Object notifyData;
    public int notifyId;

    public NotifyEvent(int notifyId2, Object notifyData2) {
        this.notifyId = notifyId2;
        this.notifyData = notifyData2;
    }

    public NotifyEvent(Object notifyData2) {
        this.notifyData = notifyData2;
    }
}
