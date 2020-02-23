package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.a;
import java.io.File;
import java.lang.ref.WeakReference;

public class TXCTraeJNI {
    private static Context mContext;
    private static WeakReference<e> mTraeRecordListener;

    public static native void nativeAppendLibraryPath(String str);

    public static native void nativeCacheClassForNative();

    public static native void nativeSetAudioMode(int i);

    public static native void nativeSetTraeConfig(String str);

    public static native boolean nativeTraeIsPlaying();

    public static native boolean nativeTraeIsRecording();

    public static native void nativeTraeSetRecordMute(boolean z);

    public static native void nativeTraeSetRecordReverb(int i);

    public static native void nativeTraeSetVolume(float f);

    public static native void nativeTraeStartPlay(Context context);

    public static native void nativeTraeStartRecord(Context context, int i, int i2, int i3);

    public static native void nativeTraeStopPlay();

    public static native void nativeTraeStopRecord(boolean z);

    static {
        a.d();
        nativeCacheClassForNative();
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void setTraeRecordListener(WeakReference<e> weakReference) {
        mTraeRecordListener = weakReference;
    }

    public static void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        if (mTraeRecordListener != null && mTraeRecordListener.get() != null) {
            ((e) mTraeRecordListener.get()).b(bArr, j, i, i2, i3);
        }
    }

    public static void onRecordError(int i, String str) {
        if (mTraeRecordListener != null && mTraeRecordListener.get() != null) {
            ((e) mTraeRecordListener.get()).a(i, str);
        }
    }

    public static void SetAudioMode(int i) {
        com.tencent.liteav.audio.a.a(mContext, i);
    }

    public static void InitTraeEngineLibrary(Context context) {
        if (context == null) {
            TXCLog.e("TXCAudioJNI", "nativeInitTraeEngine failed, context is null!");
            return;
        }
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String str = applicationInfo.nativeLibraryDir;
            String str2 = applicationInfo.dataDir + "/lib";
            nativeAppendLibraryPath("add_libpath:" + str);
            nativeAppendLibraryPath("add_libpath:" + str2);
            nativeAppendLibraryPath("add_libpath:" + ("/data/data/" + applicationInfo.packageName + "/lib"));
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static boolean nativeCheckTraeEngine(Context context) {
        if (context == null) {
            TXCLog.e("TXCAudioJNI", "nativeCheckTraeEngine failed, context is null!");
            return false;
        }
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String str = applicationInfo.nativeLibraryDir;
        String str2 = applicationInfo.dataDir + "/lib";
        String str3 = "/data/data/" + applicationInfo.packageName + "/lib";
        String e = a.e();
        if (e == null) {
            e = "";
        }
        if (new File(str + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str2 + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str3 + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(e + "/libtraeimp-rtmp-armeabi-v7a.so").exists()) {
            return true;
        }
        if (new File(str + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        if (new File(str2 + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        if (new File(str3 + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        if (new File(e + "/libtraeimp-rtmp-armeabi.so").exists()) {
            return true;
        }
        return false;
    }
}
