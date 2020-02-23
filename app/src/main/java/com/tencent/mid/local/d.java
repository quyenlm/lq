package com.tencent.mid.local;

import android.content.Context;
import android.preference.PreferenceManager;

class d extends g {
    public d(Context context) {
        super(context);
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
            string = PreferenceManager.getDefaultSharedPreferences(this.a).getString(e(), (String) null);
        }
        return string;
    }
}
