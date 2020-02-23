package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.SearchAuth;

final class zzctb extends zzbay<Status, zzcsy> {
    private final String zzbCA;
    /* access modifiers changed from: private */
    public final boolean zzbCB = Log.isLoggable("SearchAuth", 3);
    private final String zzbCx;

    protected zzctb(GoogleApiClient googleApiClient, String str) {
        super((Api<?>) SearchAuth.API, googleApiClient);
        this.zzbCx = str;
        this.zzbCA = googleApiClient.getContext().getPackageName();
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Status) obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzcsy zzcsy = (zzcsy) zzb;
        if (this.zzbCB) {
            Log.d("SearchAuth", "ClearTokenImpl started");
        }
        ((zzcsw) zzcsy.zzrf()).zzb(new zzctc(this), this.zzbCA, this.zzbCx);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        if (this.zzbCB) {
            String valueOf = String.valueOf(status.getStatusMessage());
            Log.d("SearchAuth", valueOf.length() != 0 ? "ClearTokenImpl received failure: ".concat(valueOf) : new String("ClearTokenImpl received failure: "));
        }
        return status;
    }
}
