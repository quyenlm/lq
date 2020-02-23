package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;
import com.google.android.gms.search.SearchAuth;
import com.google.android.gms.search.SearchAuthApi;

final class zzctd extends zzbay<SearchAuthApi.GoogleNowAuthResult, zzcsy> {
    private final String zzbCA;
    /* access modifiers changed from: private */
    public final boolean zzbCB = Log.isLoggable("SearchAuth", 3);
    private final String zzbCD;

    protected zzctd(GoogleApiClient googleApiClient, String str) {
        super((Api<?>) SearchAuth.API, googleApiClient);
        this.zzbCD = str;
        this.zzbCA = googleApiClient.getContext().getPackageName();
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((SearchAuthApi.GoogleNowAuthResult) obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzcsy zzcsy = (zzcsy) zzb;
        if (this.zzbCB) {
            Log.d("SearchAuth", "GetGoogleNowAuthImpl started");
        }
        ((zzcsw) zzcsy.zzrf()).zza(new zzcte(this), this.zzbCA, this.zzbCD);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        if (this.zzbCB) {
            String valueOf = String.valueOf(status.getStatusMessage());
            Log.d("SearchAuth", valueOf.length() != 0 ? "GetGoogleNowAuthImpl received failure: ".concat(valueOf) : new String("GetGoogleNowAuthImpl received failure: "));
        }
        return new zzctf(status, (GoogleNowAuthState) null);
    }
}
