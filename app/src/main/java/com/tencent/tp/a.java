package com.tencent.tp;

import android.content.Context;
import android.os.AsyncTask;

class a extends AsyncTask {
    protected Context a;

    public a(Context context) {
        this.a = context;
    }

    private String a(Context context) {
        try {
            return r.a(context);
        } catch (Throwable th) {
            return "";
        }
    }

    private void a() {
        i.a(this.a);
        TssSdkRuntime.doSyncInitalizeTask2(this.a);
        m.a("Language:" + r.m());
        m.a("Country:" + r.e());
        m.a("cpu_model:" + a(this.a));
        m.a("simulator_name:" + r.b(this.a));
        m.a("adb:" + TssSdkRuntime.getAdbEnabledOverUsb(this.a));
        m.a("info:on initialize done");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        try {
            a();
            return null;
        } catch (Exception e) {
            m.a("*#07#:" + e.toString());
            return null;
        }
    }
}
