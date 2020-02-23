package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagg extends zzagr {
    private /* synthetic */ long zzZg;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagg(Context context, long j) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZg = j;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putLong("app_last_background_time_ms", this.zzZg);
        edit.apply();
    }
}
