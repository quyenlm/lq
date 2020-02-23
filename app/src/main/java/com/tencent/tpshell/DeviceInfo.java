package com.tencent.tpshell;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class DeviceInfo {
    private static final String PREF_SHARED_NAME = "MTPSHELL";
    private static String mCPUArch;
    private static boolean mIsArt;
    private static String mVMLibName;
    private static String mVMName;

    public static String getDeviceId(Context context) {
        return getUUID(context);
    }

    private static String getUUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_SHARED_NAME, 0);
        if (sharedPreferences.contains("MTP_UUID")) {
            return sharedPreferences.getString("MTP_UUID", "000000");
        }
        String uuid = UUID.randomUUID().toString();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("MTP_UUID", uuid);
        edit.apply();
        return uuid;
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static boolean isArt() {
        return mIsArt;
    }

    public static String vmName() {
        return mVMName;
    }

    public static String vmLibName() {
        return mVMLibName;
    }

    public static String getCpuArch() {
        return mCPUArch;
    }

    private static void initCPUArchInfo() {
        String str;
        String str2 = Build.CPU_ABI;
        if (str2.startsWith("armeabi")) {
            str = "arm";
        } else if (str2.compareTo("arm64-v8a") == 0) {
            str = "arm64";
        } else if (str2.compareTo("x86") == 0) {
            str = "x86";
        } else if (str2.compareTo("x86_64") == 0) {
            str = "x86_64";
        } else {
            str = null;
        }
        mCPUArch = str;
    }

    private static boolean isYunOS() {
        return new File("/system/lib/libvmkid_lemur.so").exists();
    }

    private static boolean judgeYunOSVMIsDalvik() {
        boolean z = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/proc/self/maps")));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    int lastIndexOf = readLine.lastIndexOf(47);
                    if (!(lastIndexOf == -1 || readLine.indexOf("libvmkid_lemur.so", lastIndexOf) == -1)) {
                        z = true;
                    }
                } else {
                    bufferedReader.close();
                    return z;
                }
            }
        } catch (FileNotFoundException | IOException e) {
            return true;
        }
    }

    private static void init() {
        boolean z = true;
        if (isYunOS()) {
            if (judgeYunOSVMIsDalvik()) {
                z = false;
            }
            mIsArt = z;
            mVMName = mIsArt ? "art" : "vmkid";
            mVMLibName = mIsArt ? "libart.so" : "libvmkid_lemur.so";
        } else {
            if (System.getProperty("java.vm.version").compareTo("2.0.0") < 0) {
                z = false;
            }
            mIsArt = z;
            mVMName = mIsArt ? "art" : "dalvik";
            mVMLibName = mIsArt ? "libart.so" : "libdvm.so";
        }
        initCPUArchInfo();
    }

    static {
        init();
    }
}
