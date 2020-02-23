package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApp;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public final class mr implements pu {
    /* access modifiers changed from: private */
    public final ScheduledExecutorService zzbZs;
    private final FirebaseApp zzbZt;

    public mr(@NonNull FirebaseApp firebaseApp, @NonNull ScheduledExecutorService scheduledExecutorService) {
        this.zzbZt = firebaseApp;
        this.zzbZs = scheduledExecutorService;
    }

    public final void zza(pw pwVar) {
        this.zzbZt.zza((FirebaseApp.zza) new mu(this, pwVar));
    }

    public final void zza(boolean z, @NonNull pv pvVar) {
        this.zzbZt.getToken(z).addOnSuccessListener((Executor) this.zzbZs, new mt(this, pvVar)).addOnFailureListener((Executor) this.zzbZs, (OnFailureListener) new ms(this, pvVar));
    }
}
