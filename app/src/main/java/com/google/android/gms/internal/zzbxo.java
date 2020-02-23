package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.zzd;

final class zzbxo extends zzbus {
    private /* synthetic */ BleDevice zzaVB;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbxo(zzbxk zzbxk, GoogleApiClient googleApiClient, BleDevice bleDevice) {
        super(googleApiClient);
        this.zzaVB = bleDevice;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwh) ((zzbup) zzb).zzrf()).zza(new zzd(this.zzaVB.getAddress(), this.zzaVB, new zzbzi(this)));
    }
}
