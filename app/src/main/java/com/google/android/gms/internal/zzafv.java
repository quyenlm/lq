package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;

final class zzafv extends zzagr {
    private /* synthetic */ String zzYZ;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafv(Context context, String str) {
        super((zzafu) null);
        this.zztF = context;
        this.zzYZ = str;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putString("content_vertical_hashes", this.zzYZ);
        edit.apply();
    }
}
