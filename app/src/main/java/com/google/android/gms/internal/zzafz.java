package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.beetalk.sdk.update.GPGameProviderContract;

final class zzafz extends zzagr {
    private /* synthetic */ zzags zzZb;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafz(Context context, zzags zzags) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZb = zzags;
    }

    public final void zzbd() {
        SharedPreferences sharedPreferences = this.zztF.getSharedPreferences("admob", 0);
        Bundle bundle = new Bundle();
        bundle.putInt(GPGameProviderContract.Column.VERSION_CODE, sharedPreferences.getInt(GPGameProviderContract.Column.VERSION_CODE, 0));
        if (this.zzZb != null) {
            this.zzZb.zzf(bundle);
        }
    }
}
