package com.tencent.component.plugin.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public final class PluginService extends Service {
    private static final String ARGS_PLATFORM_ID = "platformId";
    private static final String PLUGIN_SERVICE_ACTION = "com.tencent.component.plugin.server.PluginService.";
    private PluginServiceLogic mPluginServiceLogic;

    public void onCreate() {
        super.onCreate();
        this.mPluginServiceLogic = new PluginServiceLogic(getApplicationContext());
    }

    public IBinder onBind(Intent intent) {
        return this.mPluginServiceLogic.onBind(intent);
    }

    public static void bindPluginService(Context context, ServiceConnection connection, String platformId) {
        Intent intent = new Intent(context, PluginService.class);
        intent.putExtra(ARGS_PLATFORM_ID, platformId);
        context.bindService(intent, connection, 1);
    }
}
