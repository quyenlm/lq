package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;

public final class zzaqh extends zzz<zzaql> {
    private final String zzake;

    public zzaqh(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzq zzq) {
        super(context, looper, 77, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzake = zzq.zzrq();
    }

    public final void zza(zzaqj zzaqj) {
        try {
            ((zzaql) zzrf()).zza(zzaqj);
        } catch (RemoteException e) {
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appinvite.internal.IAppInviteService");
        return queryLocalInterface instanceof zzaql ? (zzaql) queryLocalInterface : new zzaqm(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.appinvite.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.appinvite.internal.IAppInviteService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        Bundle bundle = new Bundle();
        bundle.putString("authPackage", this.zzake);
        return bundle;
    }
}
