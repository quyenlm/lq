package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

final class h extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ ValueCallback c;

    h(Context context, String str, ValueCallback valueCallback) {
        this.a = context;
        this.b = str;
        this.c = valueCallback;
    }

    public void run() {
        br a2 = br.a();
        a2.a(this.a);
        boolean z = false;
        if (a2.b()) {
            z = a2.c().a(this.a, this.b);
        }
        new Handler(Looper.getMainLooper()).post(new i(this, z));
    }
}
