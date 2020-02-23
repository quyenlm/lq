package com.banalytics;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import com.btalk.helper.BBAppLogger;

public abstract class LoggerIntentService extends IntentService {
    public LoggerIntentService(String name) {
        super(name);
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService("notification");
            if (mNotificationManager.getNotificationChannel(BBAppLogger.NOTIFICATION_CHANNEL) == null) {
                NotificationChannel channel = new NotificationChannel(BBAppLogger.NOTIFICATION_CHANNEL, "BBAppLogger", 1);
                channel.setDescription("Internal Logging");
                channel.enableLights(false);
                channel.enableVibration(false);
                channel.setShowBadge(false);
                mNotificationManager.createNotificationChannel(channel);
            }
            startForeground(BBAppLogger.NOTIFICATION_ID, new Notification.Builder(this, BBAppLogger.NOTIFICATION_CHANNEL).setContentTitle("Logging").setContentText("We are doing the crazy stuff here").build());
        }
    }
}
