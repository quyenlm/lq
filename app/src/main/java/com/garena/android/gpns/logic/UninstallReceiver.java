package com.garena.android.gpns.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.garena.android.gpns.strategy.ServiceLoaderIntentService;
import com.garena.android.gpns.utility.AppLogger;

public class UninstallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        AppLogger.d("======== Uninstall Received =========");
        context.startService(new Intent(context, ServiceLoaderIntentService.class));
    }
}
