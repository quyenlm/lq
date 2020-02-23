package com.tencent.msdk.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class e extends g {
    public e(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        synchronized (this) {
            i.a("write mid to sharedPreferences");
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this.a).edit();
            edit.putString(f(), str);
            edit.commit();
        }
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        return true;
    }

    /* access modifiers changed from: protected */
    public String b() {
        String string;
        synchronized (this) {
            i.a("read mid from sharedPreferences");
            string = PreferenceManager.getDefaultSharedPreferences(this.a).getString(f(), (String) null);
        }
        return string;
    }
}
