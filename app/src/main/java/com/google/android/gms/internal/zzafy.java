package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.beetalk.sdk.update.GPGameProviderContract;

final class zzafy extends zzagr {
    private /* synthetic */ int zzZc;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafy(Context context, int i) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZc = i;
    }

    public final void zzbd() {
        SharedPreferences.Editor edit = this.zztF.getSharedPreferences("admob", 0).edit();
        edit.putInt(GPGameProviderContract.Column.VERSION_CODE, this.zzZc);
        edit.apply();
    }
}
