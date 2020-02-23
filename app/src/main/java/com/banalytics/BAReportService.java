package com.banalytics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.btalk.helper.BBAppLogger;
import com.google.android.gms.drive.DriveFile;

public class BAReportService extends LoggerIntentService {
    private BATracker mBATracker;

    public BAReportService() {
        super("beeanalytics");
    }

    public void onCreate() {
        super.onCreate();
        this.mBATracker = BATracker.getInstance();
        this.mBATracker.init(this);
    }

    private int getCommandType(Intent intent) {
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("command", 0);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        int commandType = getCommandType(intent);
        switch (commandType) {
            case 3:
                BBAppLogger.i("send events command received", new Object[0]);
                AlarmManager mAlarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
                if (!this.mBATracker.sendEventsIfNeeded(getApplicationContext())) {
                    Intent serviceIntent = new Intent(this, BAReportService.class);
                    serviceIntent.putExtra("command", 3);
                    mAlarmManager.cancel(PendingIntent.getService(this, BATrackerConst.PENDING_INTENT_ID, serviceIntent, DriveFile.MODE_READ_ONLY));
                    BBAppLogger.i("send events completed", new Object[0]);
                    return;
                }
                Intent serviceIntent2 = new Intent(this, BAReportService.class);
                serviceIntent2.putExtra("command", 3);
                mAlarmManager.set(1, System.currentTimeMillis() + BATrackerConst.TRACKER_WAKE_UP_INTERVAL, PendingIntent.getService(this, BATrackerConst.PENDING_INTENT_ID, serviceIntent2, 134217728));
                return;
            default:
                BBAppLogger.i("report-service invalid command %d received", Integer.valueOf(commandType));
                return;
        }
    }
}
