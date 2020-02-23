package com.garena.android.gpns.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.utility.AlarmUtil;
import com.garena.android.gpns.utility.AppLogger;
import com.garena.android.gpns.utility.DeviceUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (DeviceUtil.isConnectedToNetwork(context)) {
            AppLogger.i("NETWORK_CHANGED : true");
            AppLogger.i("SCHEDULE_WAKE_CONNECT");
            AlarmUtil.scheduleWakeConnect(context, 30000);
            GNotificationService.getBroadcastManager().unregisterNetworkChangeReceiver();
        }
    }
}
