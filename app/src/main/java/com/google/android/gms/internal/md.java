package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.dynamic.zzn;
import com.google.firebase.crash.FirebaseCrash;

public final class md extends mb {
    private final Throwable zzaaS;
    private final mp zzbXW;

    public md(@NonNull Context context, @NonNull FirebaseCrash.zza zza, @NonNull Throwable th, @Nullable mp mpVar) {
        super(context, zza);
        this.zzaaS = th;
        this.zzbXW = mpVar;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String getErrorMessage() {
        return "Failed to report caught exception";
    }

    public final /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    /* access modifiers changed from: protected */
    public final void zzd(@NonNull mj mjVar) throws RemoteException {
        if (this.zzbXW != null) {
            this.zzbXW.zza(false, System.currentTimeMillis());
        }
        mjVar.zzL(zzn.zzw(this.zzaaS));
    }
}
