package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcqf extends zzcqi {
    private /* synthetic */ Uri zzbzR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcqf(zzcqe zzcqe, GoogleApiClient googleApiClient, Uri uri) {
        super(googleApiClient);
        this.zzbzR = uri;
    }

    /* access modifiers changed from: protected */
    public final void zza(Context context, zzcqc zzcqc) throws RemoteException {
        zzcqc.zza(new zzcqj(this), this.zzbzR, (Bundle) null, false);
    }
}
