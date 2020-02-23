package com.tencent.component.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.tencent.component.annotation.PluginApi;

public class LeafService extends Service {
    private TreeService mHostService;

    @PluginApi(since = 310)
    public void init(Plugin plugin, TreeService hostService) {
        attachBaseContext(plugin.getContext());
        this.mHostService = hostService;
        onCreate();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 400)
    public void setServiceForeground() {
        this.mHostService.setTreeServiceForeground();
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 400)
    public void setServiceBackground() {
        this.mHostService.setTreeServiceBackground();
    }
}
