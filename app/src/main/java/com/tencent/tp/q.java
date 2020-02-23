package com.tencent.tp;

import android.util.Log;

public class q {
    public static int a(int i, int i2, int i3, int i4) {
        return ((i4 & 255) << 24) | ((i & 255) << 16) | ((i2 & 255) << 8) | (i3 & 255);
    }

    public static void a(String str) {
        Log.d("tss", str);
    }
}
