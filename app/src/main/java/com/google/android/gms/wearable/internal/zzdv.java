package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.MessageApi;

final class zzdv extends zzn<Status> {
    private zzbdw<MessageApi.MessageListener> zzaEU;
    private MessageApi.MessageListener zzbSV;
    private IntentFilter[] zzbSW;

    private zzdv(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, zzbdw<MessageApi.MessageListener> zzbdw, IntentFilter[] intentFilterArr) {
        super(googleApiClient);
        this.zzbSV = (MessageApi.MessageListener) zzbo.zzu(messageListener);
        this.zzaEU = (zzbdw) zzbo.zzu(zzbdw);
        this.zzbSW = (IntentFilter[]) zzbo.zzu(intentFilterArr);
    }

    /* synthetic */ zzdv(GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener, zzbdw zzbdw, IntentFilter[] intentFilterArr, zzdt zzdt) {
        this(googleApiClient, messageListener, zzbdw, intentFilterArr);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<Status>) this, this.zzbSV, this.zzaEU, this.zzbSW);
        this.zzbSV = null;
        this.zzaEU = null;
        this.zzbSW = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzbSV = null;
        this.zzaEU = null;
        this.zzbSW = null;
        return status;
    }
}
