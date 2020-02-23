package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.firebase.crash.FirebaseCrash;

public final class mc extends mb {
    private final long zzayS;
    private final String zzbYi;
    private final Bundle zzbYj;

    public mc(@NonNull Context context, @NonNull FirebaseCrash.zza zza, @NonNull String str, long j, @NonNull Bundle bundle) {
        super(context, zza);
        this.zzbYi = str;
        this.zzayS = j;
        this.zzbYj = bundle;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String getErrorMessage() {
        return "Failed to log analytics event";
    }

    public final /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    /* access modifiers changed from: protected */
    public final void zzd(@NonNull mj mjVar) throws RemoteException {
        mjVar.zza(this.zzbYi, this.zzayS, this.zzbYj);
    }
}
