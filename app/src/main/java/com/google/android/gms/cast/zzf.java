package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayi;
import com.google.android.gms.internal.zzbaz;

final class zzf extends zzayi {
    private /* synthetic */ String val$message;
    private /* synthetic */ String zzaoQ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzf(Cast.CastApi.zza zza, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient);
        this.zzaoQ = str;
        this.val$message = str2;
    }

    public final void zza(zzaxx zzaxx) throws RemoteException {
        try {
            zzaxx.zza(this.zzaoQ, this.val$message, (zzbaz<Status>) this);
        } catch (IllegalArgumentException | IllegalStateException e) {
            zzad(2001);
        }
    }
}
