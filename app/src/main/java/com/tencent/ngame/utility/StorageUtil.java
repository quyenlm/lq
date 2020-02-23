package com.tencent.ngame.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class StorageUtil {
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static long getAvailableSDFreeSize() {
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) sf.getAvailableBlocks()) * ((long) sf.getBlockSize());
    }

    public static long getAvailableInternalMemorySize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static long getTotalSDSize() {
        long totalBlocks;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = (long) sf.getBlockSize();
        if (Build.VERSION.SDK_INT < 18) {
            totalBlocks = (long) sf.getBlockCount();
        } else {
            totalBlocks = sf.getBlockCountLong();
        }
        return totalBlocks * blockSize;
    }

    public static long getTotalInternalMemorySize() {
        long totalBlocksLong;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT < 18) {
            totalBlocksLong = (long) statFs.getBlockCount();
        } else {
            totalBlocksLong = statFs.getBlockCountLong();
        }
        return totalBlocksLong * ((long) statFs.getBlockSize());
    }

    public static boolean isSupportOpenGL3(Context context) {
        ActivityManager activityManager;
        ConfigurationInfo configurationInfo;
        if (context == null || (activityManager = (ActivityManager) context.getSystemService("activity")) == null || (configurationInfo = activityManager.getDeviceConfigurationInfo()) == null || configurationInfo.reqGlEsVersion < 196608) {
            return false;
        }
        return true;
    }
}
