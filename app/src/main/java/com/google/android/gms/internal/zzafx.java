package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

final class zzafx extends zzagr {
    private /* synthetic */ zzags zzZb;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzafx(Context context, zzags zzags) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZb = zzags;
    }

    public final void zzbd() {
        SharedPreferences sharedPreferences = this.zztF.getSharedPreferences("admob", 0);
        Bundle bundle = new Bundle();
        bundle.putBoolean("auto_collect_location", sharedPreferences.getBoolean("auto_collect_location", false));
        if (this.zzZb != null) {
            this.zzZb.zzf(bundle);
        }
    }
}
