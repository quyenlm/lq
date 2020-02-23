package com.tencent.tp;

import android.os.Handler;
import android.os.Looper;

public class MainThreadDispatcher {
    public static final String TAG = "TssSDK";

    public static class NativeRunnable {
        public long callbackFuncPtr = 0;
        public String clazz_name = null;
        public long dataPtr = 0;
        public byte[] method_cmd_data = null;
        public String method_name = null;
        public String method_signature = null;
        public Object obj_receiver = null;
    }

    static class a implements Runnable {
        NativeRunnable a;

        public a(NativeRunnable nativeRunnable) {
            this.a = nativeRunnable;
        }

        public void run() {
            if (this.a.callbackFuncPtr != 0) {
                MainThreadDispatcher.handleNativeFuncCallback(this.a.callbackFuncPtr, this.a.dataPtr);
            }
            if (this.a.clazz_name != null && this.a.method_name != null && this.a.method_signature != null) {
                MainThreadDispatcher.handleNativeCallBack(this.a);
            }
        }
    }

    /* access modifiers changed from: private */
    public static native void handleNativeCallBack(NativeRunnable nativeRunnable);

    /* access modifiers changed from: private */
    public static native void handleNativeFuncCallback(long j, long j2);

    private static Runnable parseNativeRunnable(NativeRunnable nativeRunnable) {
        return new a(nativeRunnable);
    }

    public static void post(NativeRunnable nativeRunnable) {
        new Handler(Looper.getMainLooper()).post(parseNativeRunnable(nativeRunnable));
    }

    public static void postAtTime(NativeRunnable nativeRunnable, long j) {
        new Handler(Looper.getMainLooper()).postAtTime(parseNativeRunnable(nativeRunnable), j);
    }

    public static void postDelayed(NativeRunnable nativeRunnable, long j) {
        new Handler(Looper.getMainLooper()).postDelayed(parseNativeRunnable(nativeRunnable), j);
    }
}
