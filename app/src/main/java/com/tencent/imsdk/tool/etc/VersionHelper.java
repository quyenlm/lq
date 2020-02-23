package com.tencent.imsdk.tool.etc;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionHelper {
    private Context ctx;
    private String pkgName = "";

    public VersionHelper(Context ctx2, String pkgName2) {
        this.ctx = ctx2;
        this.pkgName = pkgName2;
    }

    public int compareVersion(String comparedVersion) {
        String appVersion = getAppVersionName(this.ctx, this.pkgName);
        IMLogger.d("appVersion :" + appVersion);
        return compareVersion(appVersion, comparedVersion);
    }

    public static String getAppVersionName(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int getAppVersionCode(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null || version1.length() <= 0 || version2.length() <= 0) {
            return 0;
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int i = 0;
        while (i < versionArray1.length && i < versionArray2.length) {
            try {
                int ver1 = Integer.parseInt(versionArray1[i]);
                int ver2 = Integer.parseInt(versionArray2[i]);
                if (ver1 < ver2) {
                    return -1;
                }
                if (ver1 > ver2) {
                    return 1;
                }
                i++;
            } catch (NumberFormatException e) {
                IMLogger.d("NumberFormatException ");
                return version1.compareTo(version2);
            }
        }
        if (versionArray1.length > i) {
            return 1;
        }
        if (versionArray2.length <= i) {
            return 0;
        }
        return -1;
    }
}
