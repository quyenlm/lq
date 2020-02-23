package com.garena.android.gpns.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.notification.event.NotifyEvent;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.CONSTANT;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getBundleExtra(CONSTANT.ALARM.DATA_BUNDLE);
        if (data != null) {
            if (!GNotificationService.IS_RUNNING) {
                Intent restartIntent = new Intent(context, GNotificationService.class);
                restartIntent.putExtra("ALARM_RESTART_SERVICE_DIED", true);
                context.startService(restartIntent);
                return;
            }
            switch (data.getInt(CONSTANT.ALARM.KEY_ALARM_TYPE)) {
                case 0:
                    GNotificationService.getBus().fire(CONSTANT.BUS.SHORT_PING, (NotifyEvent) null);
                    return;
                case 1:
                    AppLogger.i("TYPE_LONG_PING");
                    AppLogger.f("TYPE_LONG_PING");
                    GNotificationService.getBus().fire(CONSTANT.BUS.LONG_PING, (NotifyEvent) null);
                    return;
                case 2:
                    AppLogger.i("TYPE_WAKE_CONNECT");
                    GNotificationService.getBus().fire(CONSTANT.BUS.WAKE_CONNECT, (NotifyEvent) null);
                    return;
                default:
                    return;
            }
        }
    }
}
