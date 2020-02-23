package com.garena.android.gpns.strategy;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.os.PowerManager;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.strategy.ServiceLoader;
import com.garena.android.gpns.utility.AppLogger;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.Iterator;

public class ServiceLoaderIntentService extends Service implements ServiceLoader.ServiceStatusListener {
    public static final String SERVICE_VERSION = "com.garena.sdk.push.version";
    private PowerManager.WakeLock wakeLock;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.wakeLock = ((PowerManager) getApplicationContext().getSystemService("power")).newWakeLock(1, "ALARM_RECEIVER_WAKE_LOCK");
        this.wakeLock.acquire(Constants.WATCHDOG_WAKE_TIMER);
        this.wakeLock.setReferenceCounted(false);
        boolean isBestChoice = true;
        Iterator<ApplicationInfo> it = getPackageManager().getInstalledApplications(128).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ApplicationInfo applicationInfo = it.next();
            if (applicationInfo.metaData != null) {
                Integer version = Integer.valueOf(applicationInfo.metaData.getInt(SERVICE_VERSION, -1));
                if (version.intValue() > 4 || (version.intValue() == 4 && CompetitiveBootStrategy.isSuperior(applicationInfo.packageName, getPackageName()))) {
                    AppLogger.d("we have a better choice now:" + applicationInfo.packageName);
                    isBestChoice = false;
                } else {
                    AppLogger.d("service_version:" + version + " " + applicationInfo.packageName);
                }
            }
        }
        if (isBestChoice) {
            AppLogger.d("i am the better choice now:" + getPackageName());
            new ServiceLoader(getApplicationContext(), this).loadService();
        } else {
            AppLogger.d("i am quitting:" + getPackageName());
            if (this.wakeLock.isHeld()) {
                this.wakeLock.release();
            }
            stopSelf();
        }
        return 2;
    }

    public void onServiceStarted() {
        AppLogger.d("start my own service via service loader " + getPackageName());
        getApplicationContext().startService(new Intent(getApplicationContext(), GNotificationService.class));
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        stopSelf();
    }

    public void onOtherServiceRunning(ComponentName componentName) {
        AppLogger.d("don't start my own service " + getPackageName());
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        stopSelf();
    }
}
