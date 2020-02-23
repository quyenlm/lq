package com.tencent.component.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.log.LogUtil;

@PluginApi(since = 6)
public abstract class BootCompleteReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @PluginApi(since = 6)
    public abstract void onBootComplete(Context context);

    static BootCompleteReceiver instantiate(Context context, PluginInfo pluginInfo) {
        if (pluginInfo == null) {
            return null;
        }
        String bootCompleteReceiver = pluginInfo.bootCompleteReceiver;
        if (TextUtils.isEmpty(bootCompleteReceiver)) {
            return null;
        }
        try {
            Class<?> clazz = PluginClassLoader.obtainClassLoader(context, pluginInfo).loadClass(bootCompleteReceiver);
            LogUtil.d(TAG, "new boot receiver for " + pluginInfo.pluginId + " " + pluginInfo.installPath);
            if (clazz != null) {
                return (BootCompleteReceiver) clazz.newInstance();
            }
            return null;
        } catch (ClassNotFoundException e) {
            throw new InstantiationException("Unable to instantiate boot receiver " + bootCompleteReceiver + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (InstantiationException e2) {
            throw new InstantiationException("Unable to instantiate boot receiver " + bootCompleteReceiver + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (IllegalAccessException e3) {
            throw new InstantiationException("Unable to instantiate boot receiver " + bootCompleteReceiver + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        }
    }

    public static final class InstantiationException extends RuntimeException {
        private static final long serialVersionUID = 7119757964464278300L;

        public InstantiationException(String msg, Exception cause) {
            super(msg, cause);
        }
    }
}
