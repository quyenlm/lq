package com.tencent.wetest;

import android.util.Log;

class i implements Runnable {
    i() {
    }

    public void run() {
        Log.i("wetest", "Try to crash Main UI");
        int i = 1 / 0;
    }
}
