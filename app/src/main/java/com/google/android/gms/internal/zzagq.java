package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

final class zzagq extends zzagr {
    private /* synthetic */ zzags zzZb;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagq(Context context, zzags zzags) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZb = zzags;
    }

    public final void zzbd() {
        SharedPreferences sharedPreferences = this.zztF.getSharedPreferences("admob", 0);
        Bundle bundle = new Bundle();
        bundle.putString("content_url_hashes", sharedPreferences.getString("content_url_hashes", ""));
        if (this.zzZb != null) {
            this.zzZb.zzf(bundle);
        }
    }
}
