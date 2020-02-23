package com.tencent.component.plugin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.ParcelUtil;
import java.util.concurrent.ConcurrentHashMap;

public class PluginAlarmManager {
    private static final long DELAY_ALARM_INTERVAL = 60000;
    private static ConcurrentHashMap<String, PluginAlarmManager> sMaps = new ConcurrentHashMap<>();
    private Class pluginProxyReceiver;

    private PluginAlarmManager(Class pluginProxyReceiver2) {
        this.pluginProxyReceiver = pluginProxyReceiver2;
    }

    @PluginApi(since = 300)
    public static PluginAlarmManager getInstance(Plugin plugin) {
        PluginPlatformConfig pluginPlatformConfig;
        if (plugin == null || (pluginPlatformConfig = plugin.getPluginManager().pluginPlatformConfig) == null) {
            return null;
        }
        return getInstance(plugin.getContext(), pluginPlatformConfig.pluginProxyReceiver);
    }

    private static PluginAlarmManager getInstance(Context context, Class pluginProxyReceiver2) {
        PluginAlarmManager manager;
        if (pluginProxyReceiver2 == null) {
            pluginProxyReceiver2 = PluginProxyReceiver.class;
        }
        String key = pluginProxyReceiver2.getName();
        PluginAlarmManager manager2 = sMaps.get(key);
        if (manager2 == null) {
            synchronized (PluginAlarmManager.class) {
                if (manager2 == null) {
                    try {
                        manager = new PluginAlarmManager(pluginProxyReceiver2);
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                    try {
                        sMaps.put(key, manager);
                        manager2 = manager;
                    } catch (Throwable th2) {
                        th = th2;
                        PluginAlarmManager pluginAlarmManager = manager;
                        throw th;
                    }
                }
            }
        }
        return manager2;
    }

    public static PluginAlarmManager getInstance(PluginManager pluginManager) {
        PluginPlatformConfig pluginPlatformConfig;
        if (pluginManager == null || (pluginPlatformConfig = pluginManager.pluginPlatformConfig) == null) {
            return null;
        }
        return getInstance(pluginManager.getPlatformContext(), pluginPlatformConfig.pluginProxyReceiver);
    }

    @PluginApi(since = 300)
    public void setRepeatingAlarm(Context context, Bundle args, String alarmId, long triggerAtMillis, long intervalMillis, boolean wakeup) {
        Plugin plugin;
        if (context != null && (context instanceof PluginContextWrapper) && (plugin = ((PluginContextWrapper) context).getPlugin()) != null) {
            PluginInfo pluginInfo = plugin.getPluginInfo();
            Context baseContext = context.getApplicationContext();
            ((AlarmManager) baseContext.getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(wakeup ? 2 : 3, triggerAtMillis, intervalMillis, buildAlarmPendingIntent(plugin.getPluginManager().pluginPlatformConfig, pluginInfo.pluginId, alarmId, context, args));
        }
    }

    /* access modifiers changed from: package-private */
    public void delayAlarm(Context context, PluginPlatformConfig pluginPlatformConfig, String pluginId, String alarmId, Intent intent) {
        Bundle args = intent.getExtras();
        if (args == null) {
            args = new Bundle();
        }
        args.putInt(PluginProxyReceiver.PARAMS_RETRY_COUNT, args.getInt(PluginProxyReceiver.PARAMS_RETRY_COUNT, 0) + 1);
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(3, SystemClock.elapsedRealtime() + 60000, buildAlarmPendingIntent(pluginPlatformConfig, pluginId, alarmId, context, args));
    }

    @PluginApi(since = 300)
    public void setAlarm(Context context, Bundle args, String alarmId, long triggerAtMillis, boolean wakeup) {
        Plugin plugin;
        if (context != null && !TextUtils.isEmpty(alarmId) && (context instanceof PluginContextWrapper) && (plugin = ((PluginContextWrapper) context).getPlugin()) != null) {
            PluginInfo pluginInfo = plugin.getPluginInfo();
            Context baseContext = context.getApplicationContext();
            ((AlarmManager) baseContext.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(wakeup ? 0 : 1, triggerAtMillis, buildAlarmPendingIntent(plugin.getPluginManager().pluginPlatformConfig, pluginInfo.pluginId, alarmId, context, args));
        }
    }

    @PluginApi(since = 300)
    public void cancelAlarm(Context context, String alarmId) {
        Plugin plugin;
        if (context != null && !TextUtils.isEmpty(alarmId) && (context instanceof PluginContextWrapper) && (plugin = ((PluginContextWrapper) context).getPlugin()) != null) {
            PluginInfo pluginInfo = plugin.getPluginInfo();
            Context baseContext = context.getApplicationContext();
            ((AlarmManager) baseContext.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(buildAlarmPendingIntent(plugin.getPluginManager().pluginPlatformConfig, pluginInfo.pluginId, alarmId, context, (Bundle) null));
        }
    }

    private PendingIntent buildAlarmPendingIntent(PluginPlatformConfig pluginPlatformConfig, String pluginId, String alarmId, Context context, Bundle args) {
        if (args == null) {
            args = new Bundle();
        }
        args.putString(PluginProxyReceiver.PARAMS_PLUGIN_ID, pluginId);
        args.putString(PluginProxyReceiver.PARAMS_ALARM_ID, alarmId);
        if (pluginPlatformConfig != null) {
            args.putByteArray(PluginProxyReceiver.PARAMS_PLATFORM_CONFIG, ParcelUtil.writeParcelable(pluginPlatformConfig));
        }
        Context baseContext = context.getApplicationContext();
        if (this.pluginProxyReceiver == null) {
            this.pluginProxyReceiver = PluginProxyReceiver.class;
        }
        Intent intent = new Intent(baseContext, this.pluginProxyReceiver);
        intent.setData(Uri.parse("content://gamejoy/plugin/alarm/" + alarmId));
        intent.setAction(PluginProxyReceiver.ALARM_ACTION);
        intent.putExtras(args);
        intent.setExtrasClassLoader(PluginAlarmManager.class.getClassLoader());
        return PendingIntent.getBroadcast(baseContext, (pluginId + "_" + alarmId).hashCode(), intent, DriveFile.MODE_READ_ONLY);
    }
}
