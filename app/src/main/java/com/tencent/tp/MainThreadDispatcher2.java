package com.tencent.tp;

import android.os.Handler;
import android.os.Looper;

public class MainThreadDispatcher2 {

    static class a implements Runnable {
        private String a;

        public a(String str) {
            this.a = str;
        }

        public void run() {
            MainThreadDispatcher2.doOnCmd(this.a);
        }
    }

    public static void SendCmd(String str) {
        if (str.startsWith("update_adb_enabled_over_usb:")) {
            doOnCmd(str);
        } else {
            new Handler(Looper.getMainLooper()).post(onCmdRunnable(str));
        }
    }

    /* access modifiers changed from: private */
    public static void doOnCmd(String str) {
        TssJavaMethod.sendCmd(str);
    }

    private static Runnable onCmdRunnable(String str) {
        return new a(str);
    }
}
