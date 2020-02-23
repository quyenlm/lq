package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

final class zzagb extends zzagr {
    private /* synthetic */ zzags zzZb;
    private /* synthetic */ Context zztF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzagb(Context context, zzags zzags) {
        super((zzafu) null);
        this.zztF = context;
        this.zzZb = zzags;
    }

    public final void zzbd() {
        SharedPreferences sharedPreferences = this.zztF.getSharedPreferences("admob", 0);
        Bundle bundle = new Bundle();
        bundle.putString("app_settings_json", sharedPreferences.getString("app_settings_json", ""));
        bundle.putLong("app_settings_last_update_ms", sharedPreferences.getLong("app_settings_last_update_ms", 0));
        if (this.zzZb != null) {
            this.zzZb.zzf(bundle);
        }
    }
}
