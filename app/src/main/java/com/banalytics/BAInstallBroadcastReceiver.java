package com.banalytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BAInstallBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            BATracker.startRecordInstallService(context, "", intent);
        }
    }
}
