package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;

public final class zzm implements zzj {
    private /* synthetic */ zzd zzaHe;

    public zzm(zzd zzd) {
        this.zzaHe = zzd;
    }

    public final void zzf(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.isSuccess()) {
            this.zzaHe.zza((zzal) null, this.zzaHe.zzrh());
        } else if (this.zzaHe.zzaGW != null) {
            this.zzaHe.zzaGW.onConnectionFailed(connectionResult);
        }
    }
}
