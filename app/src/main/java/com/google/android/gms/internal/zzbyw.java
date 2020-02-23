package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.request.zzaq;

final class zzbyw extends zzbvn {
    private /* synthetic */ PendingIntent zzaVL;
    private /* synthetic */ zzbyx zzaVZ;
    private /* synthetic */ zzt zzaWa;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyw(zzbys zzbys, GoogleApiClient googleApiClient, zzbyx zzbyx, zzt zzt, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzaVZ = zzbyx;
        this.zzaWa = zzt;
        this.zzaVL = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final Status zzA(Status status) {
        return status;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwt) ((zzbvk) zzb).zzrf()).zza(new zzaq(this.zzaWa, this.zzaVL, new zzbyy(this, this.zzaVZ, (zzbyt) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
