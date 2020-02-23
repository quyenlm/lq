package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagc extends zzagr {
    private /* synthetic */ String zzZf;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagc(Context context, String str) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZf = str;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putString("native_advanced_settings", this.zzZf);
        edit.apply();
    }
}
