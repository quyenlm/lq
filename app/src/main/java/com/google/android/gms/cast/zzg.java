package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzbaz;

final class zzg extends Cast.zza {
    private /* synthetic */ String zzaoR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzg(Cast.CastApi.zza zza, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zzaoR = str;
    }

    public final void zza(zzaxx zzaxx) throws RemoteException {
        try {
            String str = this.zzaoR;
            LaunchOptions launchOptions = new LaunchOptions();
            launchOptions.setRelaunchIfRunning(false);
            zzaxx.zza(str, launchOptions, (zzbaz<Cast.ApplicationConnectionResult>) this);
        } catch (IllegalStateException e) {
            zzad(2001);
        }
    }
}
