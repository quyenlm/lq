package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzafu extends zzagr {
    private /* synthetic */ boolean zzYY;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafu(Context context, boolean z) {
        super((zzafu) null);
        this.zztF = context;
        this.zzYY = z;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putBoolean("use_https", this.zzYY);
        edit.apply();
    }
}
