package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;

public final class zzd extends zzz<zzt> {
    private final GoogleSignInOptions zzamk;

    public zzd(Context context, Looper looper, zzq zzq, GoogleSignInOptions googleSignInOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 91, zzq, connectionCallbacks, onConnectionFailedListener);
        googleSignInOptions = googleSignInOptions == null ? new GoogleSignInOptions.Builder().build() : googleSignInOptions;
        if (!zzq.zzro().isEmpty()) {
            GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(googleSignInOptions);
            for (Scope requestScopes : zzq.zzro()) {
                builder.requestScopes(requestScopes, new Scope[0]);
            }
            googleSignInOptions = builder.build();
        }
        this.zzamk = googleSignInOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzt ? (zzt) queryLocalInterface : new zzu(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.auth.api.signin.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.auth.api.signin.internal.ISignInService";
    }

    public final boolean zzmG() {
        return true;
    }

    public final Intent zzmH() {
        return zze.zza(getContext(), this.zzamk);
    }

    public final GoogleSignInOptions zzmI() {
        return this.zzamk;
    }
}
