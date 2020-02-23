package com.tencent.component.cache.sp;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.component.utils.SecurityUtil;
import java.io.File;

public class PreferenceUtil {
    private static final String CACHE_NAME = "cache";
    private static final String DEFAULT_NAME = "preference";
    private static final String GLOBAL = "global";

    public static SharedPreferences getDefaultPreference(Context context, long uin) {
        return getPreference(context, uin, (String) null);
    }

    public static SharedPreferences getDefaultPreference(Context context, long uin, float version) {
        return getPreference(context, uin, (String) null);
    }

    public static SharedPreferences getPreference(Context context, long uin, String name) {
        if (name == null || name.length() == 0) {
            name = DEFAULT_NAME;
        }
        return context.getSharedPreferences(context.getPackageName() + "_" + (uin == 0 ? GLOBAL : SecurityUtil.encrypt(String.valueOf(uin))) + "_" + name.replaceAll(File.separator, "%2F"), 0);
    }

    public static SharedPreferences getPreference(Context context, long uin, String name, float version) {
        if (name == null || name.length() == 0) {
            name = DEFAULT_NAME;
        }
        return context.getSharedPreferences(context.getPackageName() + "_" + (uin == 0 ? GLOBAL : SecurityUtil.encrypt(String.valueOf(uin))) + "_" + name + "_" + version, 0);
    }

    public static SharedPreferences getCachePreference(Context context, long uin) {
        return getPreference(context, uin, CACHE_NAME);
    }

    public static SharedPreferences getCachePreference(Context context, long uin, float version) {
        return getPreference(context, uin, CACHE_NAME, version);
    }

    public static SharedPreferences getDefaultGlobalPreference(Context context) {
        return getGlobalPreference(context, (String) null);
    }

    public static SharedPreferences getDefaultGlobalPreference(Context context, float version) {
        return getGlobalPreference(context, (String) null, version);
    }

    public static SharedPreferences getGlobalPreference(Context context, String name) {
        return getPreference(context, 0, name);
    }

    public static SharedPreferences getGlobalPreference(Context context, String name, float version) {
        return getPreference(context, 0, name, version);
    }

    public static SharedPreferences getGlobalCachePreference(Context context) {
        return getGlobalPreference(context, CACHE_NAME);
    }

    public static SharedPreferences getGlobalCachePreference(Context context, float version) {
        return getGlobalPreference(context, CACHE_NAME, version);
    }
}
