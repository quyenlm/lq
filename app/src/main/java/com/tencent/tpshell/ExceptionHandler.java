package com.tencent.tpshell;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.facebook.internal.NativeProtocol;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExceptionHandler {
    private static final long MAX_REPORT_LIMIT_TIMES = 4320000;
    private static final int MAX_WAIT_TIMES = 3000;
    private static final String PREFERENCE_NAME = "tp_crash_info";

    private native void nativeInit(String str);

    public ExceptionHandler(String str) {
        nativeInit(str);
    }

    public void writeBugtraceInfo(Context context) {
        File file = new File(context.getDir("Bugtrace", 0), "bugtrace_info.txt");
        if (file.exists()) {
            file.delete();
        }
        String packageName = context.getPackageName();
        String str = "UNKNOWN";
        String str2 = "UNKNOWN";
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                str = packageInfo.versionName;
            }
            if (applicationInfo != null) {
                str2 = packageManager.getApplicationLabel(applicationInfo).toString();
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        String format = String.format("%s=%s\n", new Object[]{NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, str2});
        String format2 = String.format("%s=%s\n", new Object[]{"app_version", str});
        String format3 = String.format("%s=%s\n", new Object[]{"package_name", packageName});
        String format4 = String.format("%s=%s(%s)\n", new Object[]{"os_name", "Android", DeviceInfo.getSystemVersion()});
        String format5 = String.format("%s=%s\n", new Object[]{"model", DeviceInfo.getDeviceModel()});
        String format6 = String.format("%s=%s\n", new Object[]{"imei", DeviceInfo.getDeviceId(context)});
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(format.getBytes());
            fileOutputStream.write(format2.getBytes());
            fileOutputStream.write(format3.getBytes());
            fileOutputStream.write(format4.getBytes());
            fileOutputStream.write(format5.getBytes());
            fileOutputStream.write(format6.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException | IOException e2) {
        }
    }
}
