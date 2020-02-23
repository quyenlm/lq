package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbcm implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private /* synthetic */ zzbcd zzaDp;

    private zzbcm(zzbcd zzbcd) {
        this.zzaDp = zzbcd;
    }

    /* synthetic */ zzbcm(zzbcd zzbcd, zzbce zzbce) {
        this(zzbcd);
    }

    public final void onConnected(Bundle bundle) {
        this.zzaDp.zzaDh.zza(new zzbck(this.zzaDp));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaDp.zzaCv.lock();
        try {
            if (this.zzaDp.zzd(connectionResult)) {
                this.zzaDp.zzpZ();
                this.zzaDp.zzpX();
            } else {
                this.zzaDp.zze(connectionResult);
            }
        } finally {
            this.zzaDp.zzaCv.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
