package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagi extends zzagr {
    private /* synthetic */ int zzZh;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagi(Context context, int i) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZh = i;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putInt("request_in_session_count", this.zzZh);
        edit.apply();
    }
}
