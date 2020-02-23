package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzcpq;
import com.google.android.gms.internal.zzcpt;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.PublishOptions;

final class zzan extends zzav {
    private /* synthetic */ Message zzbzk;
    private /* synthetic */ zzbdw zzbzl;
    private /* synthetic */ PublishOptions zzbzm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzan(zzak zzak, GoogleApiClient googleApiClient, Message message, zzbdw zzbdw, PublishOptions publishOptions) {
        super(googleApiClient);
        this.zzbzk = message;
        this.zzbzl = zzbdw;
        this.zzbzm = publishOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzah zzah = (zzah) zzb;
        zzbdw<zzbaz<Status>> zzzX = zzzX();
        zzaf zza = zzaf.zza(this.zzbzk);
        zzbdw zzbdw = this.zzbzl;
        ((zzs) zzah.zzrf()).zza(new zzax(zza, this.zzbzm.getStrategy(), new zzcpq(zzzX), zzbdw == null ? null : new zzcpt(zzbdw)));
    }
}
