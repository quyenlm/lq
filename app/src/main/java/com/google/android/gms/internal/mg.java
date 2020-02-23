package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.util.zzg;
import com.google.firebase.crash.FirebaseCrash;

public final class mg extends mb {
    private final boolean zzbYk;

    public mg(@NonNull Context context, @NonNull FirebaseCrash.zza zza, boolean z) {
        super(context, zza);
        this.zzbYk = z;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String getErrorMessage() {
        return new StringBuilder(36).append("Failed to set crash enabled to ").append(this.zzbYk).toString();
    }

    public final void run() {
        try {
            mj zzFg = this.zzbYh.zzFg();
            if (zzFg == null) {
                Log.e("FirebaseCrash", "Crash api not available");
            } else {
                zzd(zzFg);
            }
        } catch (RemoteException | RuntimeException e) {
            zzg.zza(this.mContext, e);
            Log.e("FirebaseCrash", getErrorMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public final void zzd(@NonNull mj mjVar) throws RemoteException {
        mjVar.zzaz(this.zzbYk);
    }
}
