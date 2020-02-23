package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzbbm implements zzbdq {
    private /* synthetic */ zzbbk zzaCx;

    private zzbbm(zzbbk zzbbk) {
        this.zzaCx = zzbbk;
    }

    /* synthetic */ zzbbm(zzbbk zzbbk, zzbbl zzbbl) {
        this(zzbbk);
    }

    public final void zzc(@NonNull ConnectionResult connectionResult) {
        this.zzaCx.zzaCv.lock();
        try {
            ConnectionResult unused = this.zzaCx.zzaCs = connectionResult;
            this.zzaCx.zzpF();
        } finally {
            this.zzaCx.zzaCv.unlock();
        }
    }

    public final void zze(int i, boolean z) {
        this.zzaCx.zzaCv.lock();
        try {
            if (this.zzaCx.zzaCu || this.zzaCx.zzaCt == null || !this.zzaCx.zzaCt.isSuccess()) {
                boolean unused = this.zzaCx.zzaCu = false;
                this.zzaCx.zzd(i, z);
                return;
            }
            boolean unused2 = this.zzaCx.zzaCu = true;
            this.zzaCx.zzaCn.onConnectionSuspended(i);
            this.zzaCx.zzaCv.unlock();
        } finally {
            this.zzaCx.zzaCv.unlock();
        }
    }

    public final void zzm(@Nullable Bundle bundle) {
        this.zzaCx.zzaCv.lock();
        try {
            this.zzaCx.zzl(bundle);
            ConnectionResult unused = this.zzaCx.zzaCs = ConnectionResult.zzazX;
            this.zzaCx.zzpF();
        } finally {
            this.zzaCx.zzaCv.unlock();
        }
    }
}
