package com.google.android.gms.internal;

import android.os.Build;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;

public abstract class zzbam {
    private int zzamr;

    public zzbam(int i) {
        this.zzamr = i;
    }

    /* access modifiers changed from: private */
    public static Status zza(RemoteException remoteException) {
        StringBuilder sb = new StringBuilder();
        if (Build.VERSION.SDK_INT >= 15 && (remoteException instanceof TransactionTooLargeException)) {
            sb.append("TransactionTooLargeException: ");
        }
        sb.append(remoteException.getLocalizedMessage());
        return new Status(8, sb.toString());
    }

    public abstract void zza(@NonNull zzbbt zzbbt, boolean z);

    public abstract void zza(zzbdd<?> zzbdd) throws DeadObjectException;

    public abstract void zzp(@NonNull Status status);
}
