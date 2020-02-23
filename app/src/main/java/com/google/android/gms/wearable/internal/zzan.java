package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;
import java.io.InputStream;

final class zzan extends zzn<Channel.GetInputStreamResult> {
    private /* synthetic */ zzak zzbSk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzan(zzak zzak, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzbSk = zzak;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String zza = this.zzbSk.zzakv;
        zzbd zzbd = new zzbd();
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfi(this, zzbd), (zzdg) zzbd, zza);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzas(status, (InputStream) null);
    }
}
