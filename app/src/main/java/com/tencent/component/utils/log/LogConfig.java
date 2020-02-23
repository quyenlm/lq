package com.tencent.component.utils.log;

import android.content.SharedPreferences;
import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.component.ComponentContext;
import com.tencent.component.cache.sp.PreferenceUtil;
import com.tencent.qqgamemi.util.TimeUtils;

public class LogConfig {
    public static final int DataThreshold = 8192;
    private static int DefFileBlockCount = 24;
    private static long DefFileKeepPeriod = 604800000;
    private static int DefFileTraceLevel = 63;
    public static final boolean Enabled = true;
    public static final String FileBlockCount = "debug.file.blockcount";
    public static final int FileBlockSize = 262144;
    public static final String FileKeepPeriod = "debug.file.keepperiod";
    public static final String FileTraceLevel = "debug.file.tracelevel";
    public static final boolean FileTracerEnabled = true;
    public static final boolean InfiniteTraceFile = false;
    public static final boolean LogcatTracerEnabled = true;
    public static final long MinSpaceRequired = 8388608;
    public static final boolean NeedAttached = false;
    public static final boolean ShowErrorCode = false;
    public static final int TimeThreshold = 10000;
    private static volatile LogConfig sInstance;
    private SharedPreferences mSharedPreferences = PreferenceUtil.getGlobalPreference(ComponentContext.getContext(), "app_log_config");

    private LogConfig() {
    }

    public static LogConfig getInstance() {
        if (sInstance == null) {
            synchronized (LogConfig.class) {
                if (sInstance == null) {
                    sInstance = new LogConfig();
                }
            }
        }
        return sInstance;
    }

    public void startListen(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void setMaxFolderSize(long maxSize) {
        int blockCount = (int) (maxSize / PlaybackStateCompat.ACTION_SET_REPEAT_MODE);
        if (blockCount < 1) {
            blockCount = DefFileBlockCount;
        }
        this.mSharedPreferences.edit().putInt(FileBlockCount, blockCount).commit();
    }

    public int getMaxFolderSize() {
        return this.mSharedPreferences.getInt(FileBlockCount, DefFileBlockCount);
    }

    public void setMaxKeepPeriod(long maxPeriod) {
        if (maxPeriod < TimeUtils.MILLIS_IN_DAY) {
            maxPeriod = DefFileKeepPeriod;
        }
        this.mSharedPreferences.edit().putLong(FileKeepPeriod, maxPeriod).commit();
    }

    public long getMaxKeepPeriod() {
        return this.mSharedPreferences.getLong(FileKeepPeriod, DefFileKeepPeriod);
    }

    public void setFileTraceLevel(int level) {
        int traceLevel = level;
        if (level > 63 || level < 0) {
            traceLevel = DefFileTraceLevel;
        }
        this.mSharedPreferences.edit().putInt(FileTraceLevel, traceLevel).commit();
    }

    public int getFileTraceLevel() {
        return this.mSharedPreferences.getInt(FileTraceLevel, DefFileTraceLevel);
    }
}
