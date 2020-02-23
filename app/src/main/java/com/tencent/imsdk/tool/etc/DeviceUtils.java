package com.tencent.imsdk.tool.etc;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class DeviceUtils {
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        if (context == null || packageName == null) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            IMLogger.w("catch NameNotFoundException : " + e.getMessage());
            packageInfo = null;
        }
        if (packageInfo != null) {
            return true;
        }
        return false;
    }
}
