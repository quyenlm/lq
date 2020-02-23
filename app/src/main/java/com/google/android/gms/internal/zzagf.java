package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagf extends zzagr {
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagf(Context context) {
        super((zzafu) null);
        this.zztF = context;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.remove("native_advanced_settings");
        edit.apply();
    }
}
