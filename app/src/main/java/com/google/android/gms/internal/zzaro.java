package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;

public final class zzaro extends zzz<zzart> {
    @Nullable
    private final Auth.AuthCredentialsOptions zzalC;

    public zzaro(Context context, Looper looper, zzq zzq, Auth.AuthCredentialsOptions authCredentialsOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 68, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzalC = authCredentialsOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        return queryLocalInterface instanceof zzart ? (zzart) queryLocalInterface : new zzaru(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.auth.api.credentials.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        return this.zzalC == null ? new Bundle() : this.zzalC.zzmo();
    }

    /* access modifiers changed from: package-private */
    public final Auth.AuthCredentialsOptions zzmu() {
        return this.zzalC;
    }
}
