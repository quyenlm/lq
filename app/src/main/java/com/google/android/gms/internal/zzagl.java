package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

final class zzagl extends zzagr {
    private /* synthetic */ zzags zzZb;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagl(Context context, zzags zzags) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZb = zzags;
    }

    public final void zzbd() {
        SharedPreferences sharedPreferences = this.zztF.getSharedPreferences("admob", 0);
        Bundle bundle = new Bundle();
        bundle.putLong("first_ad_req_time_ms", sharedPreferences.getLong("first_ad_req_time_ms", 0));
        if (this.zzZb != null) {
            this.zzZb.zzf(bundle);
        }
    }
}
