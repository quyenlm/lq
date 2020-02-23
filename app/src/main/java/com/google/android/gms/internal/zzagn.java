package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzagn extends zzagr {
    private /* synthetic */ boolean zzZj;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagn(Context context, boolean z) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZj = z;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putBoolean("content_url_opted_out", this.zzZj);
        edit.apply();
    }
}
