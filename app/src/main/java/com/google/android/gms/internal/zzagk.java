package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagk extends zzagr {
    private /* synthetic */ long zzZi;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagk(Context context, long j) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZi = j;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putLong("first_ad_req_time_ms", this.zzZi);
        edit.apply();
    }
}
