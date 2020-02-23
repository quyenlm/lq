package com.tencent.component.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.component.UtilitiesInitial;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.utils.ParcelUtil;
import com.tencent.component.utils.log.LogUtil;

public class PluginProxyReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.tencent.component.plugin.receiver";
    public static final String ALARM_ACTION = "com.tencent.component.plugin.alarm";
    private static final int MAX_RETRY_TIME = 3;
    public static final String NOTIFICATION_ACTION = "com.tencent.component.plugin.notification";
    public static final String PARAMS_ALARM_ID = "_plugin_reciever_alarm_id";
    public static final String PARAMS_PLATFORM_CONFIG = "_plugin_platform_config_byte";
    public static final String PARAMS_PLUGIN_ID = "_plugin_reciever_plugin_id";
    public static final String PARAMS_RETRY_COUNT = "_plugin_reciever_retry_count";
    private static final String TAG = "PluginProxyReceiver";

    public void onReceive(Context context, Intent intent) {
        try {
            intent.setExtrasClassLoader(PluginProxyReceiver.class.getClassLoader());
            if (NOTIFICATION_ACTION.equalsIgnoreCase(intent.getAction())) {
                onReceiveNotification(context, intent);
            } else if (ALARM_ACTION.equalsIgnoreCase(intent.getAction())) {
                onReceiveAlarm(context, intent);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public void onReceiveAlarm(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            extras.setClassLoader(PluginProxyReceiver.class.getClassLoader());
            String pluginId = extras.getString(PARAMS_PLUGIN_ID);
            String alarmId = extras.getString(PARAMS_ALARM_ID);
            PluginPlatformConfig pluginPlatformConfig = (PluginPlatformConfig) ParcelUtil.readParcelable(extras.getByteArray(PARAMS_PLATFORM_CONFIG), getClass().getClassLoader());
            if (TextUtils.isEmpty(pluginId) || TextUtils.isEmpty(alarmId) || pluginPlatformConfig == null) {
                LogUtil.w(TAG, "ingnore alarm action[pluginId:" + pluginId + "|alarmId:" + alarmId + "]");
                return;
            }
            PluginManager pluginManager = PluginManager.getInstance(context, pluginPlatformConfig);
            if (pluginManager.isPlatformInitialFinish()) {
                notifyPluginAlarm(pluginManager, pluginId, intent);
                return;
            }
            int retryCount = extras.getInt(PARAMS_RETRY_COUNT, 0);
            if (retryCount < 3) {
                try {
                    UtilitiesInitial.init(context);
                    LogUtil.i(TAG, "plugin not loaded,try to start qmi first.");
                    if (startPlatform(context)) {
                        PluginAlarmManager.getInstance(pluginManager).delayAlarm(context, pluginPlatformConfig, pluginId, alarmId, intent);
                    } else {
                        LogUtil.w(TAG, "ingnore alarm action by start qmi failed.[pluginId:" + pluginId + "|platformId:" + pluginManager.pluginPlatformConfig.platformId + "|alarmId:" + alarmId + "]");
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            } else {
                LogUtil.w(TAG, "ingnore alarm action by reach max retry count:" + retryCount + ".[pluginId:" + pluginId + "|platformId:" + pluginManager.pluginPlatformConfig.platformId + "|alarmId:" + alarmId + "]");
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean startPlatform(Context context) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onReceiveNotification(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            extras.setClassLoader(getClass().getClassLoader());
            String pluginId = extras.getString(PARAMS_PLUGIN_ID);
            PluginPlatformConfig pluginPlatformConfig = (PluginPlatformConfig) ParcelUtil.readParcelable(extras.getByteArray(PARAMS_PLATFORM_CONFIG), getClass().getClassLoader());
            if (TextUtils.isEmpty(pluginId) || pluginPlatformConfig == null) {
                LogUtil.w(TAG, "ignore notification action[pluginId:" + pluginId + "]");
                return;
            }
            PluginManager pluginManager = PluginManager.getInstance(context, pluginPlatformConfig);
            if (pluginManager.isPlatformInitialFinish()) {
                notifyPluginNotification(pluginManager, pluginId, intent);
            } else {
                LogUtil.w(TAG, "ignore notification by plugin not loaded.");
            }
        }
    }

    private void notifyPluginAlarm(final PluginManager pluginManager, String pluginId, final Intent intent) {
        if (pluginManager != null) {
            pluginManager.getPluginInfo(pluginId, new PluginManager.GetPluginInfoCallback() {
                public void onGetPluginInfo(PluginInfo pluginInfo) {
                    Plugin plugin;
                    PluginReceiverHandler receiver;
                    if (pluginInfo != null && (plugin = pluginManager.getPlugin(pluginInfo)) != null && (receiver = plugin.getPluginReceiverHandler()) != null) {
                        receiver.onReceiveAlarm(plugin.getContext(), intent);
                    }
                }
            });
        }
    }

    private void notifyPluginNotification(final PluginManager pluginManager, String pluginId, final Intent intent) {
        if (pluginManager != null) {
            pluginManager.getPluginInfo(pluginId, new PluginManager.GetPluginInfoCallback() {
                public void onGetPluginInfo(PluginInfo pluginInfo) {
                    Plugin plugin;
                    PluginReceiverHandler receiver;
                    if (pluginInfo != null && (plugin = pluginManager.getPlugin(pluginInfo)) != null && (receiver = plugin.getPluginReceiverHandler()) != null) {
                        receiver.onReceiveNotification(plugin.getContext(), intent);
                    }
                }
            });
        }
    }
}
