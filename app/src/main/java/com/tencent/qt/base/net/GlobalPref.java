package com.tencent.qt.base.net;

import android.util.Log;

public class GlobalPref {
    private static final String LIB_NAME_NETWORKHELPER = "networkhelper";
    private static final GlobalPref instant = new GlobalPref();
    private String TAG = "GlobalPref";
    private volatile boolean isLoadLibary = false;

    private GlobalPref() {
    }

    public static GlobalPref getInstant() {
        return instant;
    }

    public void loadLibary() {
        if (!this.isLoadLibary) {
            try {
                Log.i(this.TAG, "load library : networkhelper");
                this.isLoadLibary = true;
                System.loadLibrary("networkhelper");
            } catch (UnsatisfiedLinkError e) {
                Log.e(this.TAG, "load library fail", e);
                this.isLoadLibary = false;
            }
        }
    }

    public boolean isLoadLibary() {
        return this.isLoadLibary;
    }
}
