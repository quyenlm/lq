package com.tencent.component.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.component.annotation.PluginApi;

public class DefendServiceManager {
    private static volatile DefendServiceManager instance = null;

    @PluginApi(since = 300)
    public static DefendServiceManager getInstance() {
        if (instance == null) {
            synchronized (DefendServiceManager.class) {
                if (instance == null) {
                    instance = new DefendServiceManager();
                }
            }
        }
        return instance;
    }

    @PluginApi(since = 300)
    public void delayStartGame(Context context, Bundle args, long delayMillis, String pkgName) {
        Plugin plugin;
        if (context != null && (context instanceof PluginContextWrapper) && (plugin = ((PluginContextWrapper) context).getPlugin()) != null) {
            context.startService(buildStartGameIntent(plugin.getPluginInfo().pluginId, context, args, delayMillis, pkgName));
        }
    }

    private Intent buildStartGameIntent(String pluginId, Context context, Bundle args, long delayMillis, String pkgName) {
        if (args == null) {
            args = new Bundle();
        }
        args.putString(DefendService.DEFEND_PLUGIN_ID, pluginId);
        args.putLong(DefendService.DEFEND_PLUGIN_DELAY_TIME, delayMillis);
        args.putString(DefendService.DEFEND_STARTGAME_PKGNAME, pkgName);
        Intent intent = new Intent();
        intent.setAction(DefendService.DELAY_STARTGAME_ACTION);
        intent.putExtras(args);
        return intent;
    }
}
