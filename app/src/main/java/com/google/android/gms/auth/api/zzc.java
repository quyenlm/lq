package com.google.android.gms.auth.api;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import java.util.Collections;
import java.util.List;

final class zzc extends Api.zza<zzd, GoogleSignInOptions> {
    zzc() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, @Nullable Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzd(context, looper, zzq, (GoogleSignInOptions) obj, connectionCallbacks, onConnectionFailedListener);
    }

    public final /* synthetic */ List zzn(@Nullable Object obj) {
        GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
        return googleSignInOptions == null ? Collections.emptyList() : googleSignInOptions.zzmA();
    }
}
