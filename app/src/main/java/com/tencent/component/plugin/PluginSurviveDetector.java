package com.tencent.component.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.log.LogUtil;

@PluginApi(since = 16)
public abstract class PluginSurviveDetector {
    private static final String TAG = "PluginSurviveDetector";

    @PluginApi(since = 16)
    public abstract boolean isSurvivable();

    static PluginSurviveDetector instantiate(Context context, PluginInfo pluginInfo) {
        if (pluginInfo == null) {
            return null;
        }
        String surviveDetector = pluginInfo.surviveDetector;
        if (TextUtils.isEmpty(surviveDetector)) {
            return null;
        }
        try {
            Class<?> clazz = PluginClassLoader.obtainClassLoader(context, pluginInfo).loadClass(surviveDetector);
            LogUtil.d(TAG, "new survive detector for " + pluginInfo.pluginId + " " + pluginInfo.installPath);
            if (clazz != null) {
                return (PluginSurviveDetector) clazz.newInstance();
            }
            return null;
        } catch (ClassNotFoundException e) {
            throw new InstantiationException("Unable to instantiate survive detector " + surviveDetector + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (InstantiationException e2) {
            throw new InstantiationException("Unable to instantiate survive detector " + surviveDetector + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (IllegalAccessException e3) {
            throw new InstantiationException("Unable to instantiate survive detector " + surviveDetector + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        }
    }

    public static final class InstantiationException extends RuntimeException {
        private static final long serialVersionUID = 7119757964464278300L;

        public InstantiationException(String msg, Exception cause) {
            super(msg, cause);
        }
    }
}
