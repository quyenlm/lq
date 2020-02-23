package com.tencent.mid.local;

import android.content.Context;
import android.provider.Settings;

public class f extends g {
    public f(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        return i.a(this.a, "android.permission.WRITE_SETTINGS");
    }

    /* access modifiers changed from: protected */
    public String b() {
        String string;
        synchronized (this) {
            i.a("read mid from Settings.System");
            string = Settings.System.getString(this.a.getContentResolver(), e());
        }
        return string;
    }
}
