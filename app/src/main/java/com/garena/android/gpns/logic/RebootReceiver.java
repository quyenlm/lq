package com.garena.android.gpns.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.garena.android.gpns.strategy.ServiceLoaderIntentService;
import com.garena.android.gpns.utility.AppLogger;

public class RebootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        AppLogger.d("======== Reboot Received =========" + context.getPackageName());
        if (isServiceToReboot(context)) {
            context.startService(new Intent(context, ServiceLoaderIntentService.class));
        }
    }

    /* access modifiers changed from: protected */
    public boolean isServiceToReboot(Context serviceContext) {
        return true;
    }
}
