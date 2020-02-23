package com.tencent.tpshell;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TPShellApplication extends Application {
    private static final String TAG = "TPShellApplication";
    public static String libname = "";
    private static ExceptionHandler sException = null;
    private static String s_apkPath = null;
    private static String s_dataDir = null;
    private static String s_libDir = null;
    private static String s_packageName = null;
    private String process_name;

    private static native int chmod(String str, int i);

    private static native String getCrashReportHost();

    private static native int getEncBlockSize();

    private static native int getEncType();

    private static native int getGameId(String str, String str2);

    private static native String getShellVersion();

    private static native String getapkPath();

    private static native String getdataDir();

    private static native String getlibDir();

    private static native String getpackageName();

    private native void infoUpdate();

    private native int initialize(String str, String str2, String str3, String str4, String str5, String str6);

    private static native int initinfo();

    private static native int isAssertEnabled();

    private static native int isBlockEncEnabled();

    private static native int isX86();

    public static native int log(String str);

    private static native void release();

    static {
        System.loadLibrary("tprt");
    }

    public TPShellApplication() {
        this.process_name = "";
        this.process_name = getProcessName();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        Initial();
    }

    /* access modifiers changed from: protected */
    public void Initial() {
        ApplicationInfo applicationInfo = getApplicationInfo();
        try {
            initialize(applicationInfo.packageName, applicationInfo.dataDir, applicationInfo.nativeLibraryDir, applicationInfo.sourceDir, this);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isMainProcess() {
        return this.process_name.compareTo(getApplicationInfo().packageName) == 0;
    }

    private static String getProcessName() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/self/cmdline")));
            String str = bufferedReader.readLine().toString();
            bufferedReader.close();
            return str.substring(0, str.indexOf(0));
        } catch (Throwable th) {
            return "";
        }
    }

    private static File ensurePrivateDirExists(File file) {
        if (!file.exists()) {
            file.mkdir();
            chmod(file.getAbsolutePath(), 505);
        }
        return file;
    }

    private void initialize(String str, String str2, String str3, String str4, Context context) throws Exception {
        File filesDir;
        String processName = getProcessName();
        if (context == null) {
            filesDir = ensurePrivateDirExists(new File(str2, "files"));
        } else {
            filesDir = context.getFilesDir();
        }
        if (initialize(str, filesDir.getAbsolutePath(), str3, str4, processName, str2) != 0) {
            throw new Exception("initialize failed.");
        }
        release();
        log("done");
    }
}
