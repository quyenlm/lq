package com.tencent.component.plugin;

import android.content.Context;
import android.content.Intent;
import com.tencent.component.annotation.PluginApi;

public class PluginReceiverHandler {
    @PluginApi(since = 300)
    public void onReceiveAlarm(Context context, Intent intent) {
    }

    @PluginApi(since = 300)
    public void onReceiveNotification(Context context, Intent intent) {
    }
}
