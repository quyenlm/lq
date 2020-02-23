package com.garena.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.garena.android.gpns.strategy.ServiceLoaderIntentService;
import com.garena.android.gpns.utility.AppLogger;

public class GPNSRebootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        AppLogger.d("======== Reboot Received =========" + context.getPackageName());
        if (isServiceToReboot(context)) {
            context.startService(new Intent(context, ServiceLoaderIntentService.class));
        }
    }

    /* access modifiers changed from: protected */
    public boolean isServiceToReboot(Context serviceContext) {
        return Build.VERSION.SDK_INT < 24;
    }
}
