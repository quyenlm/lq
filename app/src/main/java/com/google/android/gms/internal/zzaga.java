package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzaga extends zzagr {
    private /* synthetic */ String zzZd;
    private /* synthetic */ long zzZe;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaga(Context context, String str, long j) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZd = str;
        this.zzZe = j;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putString("app_settings_json", this.zzZd);
        edit.putLong("app_settings_last_update_ms", this.zzZe);
        edit.apply();
    }
}
