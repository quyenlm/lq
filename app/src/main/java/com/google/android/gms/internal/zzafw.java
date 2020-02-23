package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzafw extends zzagr {
    private /* synthetic */ boolean zzZa;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafw(Context context, boolean z) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZa = z;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putBoolean("auto_collect_location", this.zzZa);
        edit.apply();
    }
}
