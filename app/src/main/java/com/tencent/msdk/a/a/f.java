package com.tencent.msdk.a.a;

import android.content.Context;
import android.provider.Settings;

public class f extends g {
    public f(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        synchronized (this) {
            i.a("write mid to Settings.System");
            try {
                Settings.System.putString(this.a.getContentResolver(), f(), str);
            } catch (Throwable th) {
                i.a(th);
            }
        }
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
            string = Settings.System.getString(this.a.getContentResolver(), f());
        }
        return string;
    }
}
