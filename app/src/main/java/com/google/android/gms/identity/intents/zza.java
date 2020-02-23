package com.google.android.gms.identity.intents;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.internal.zzcbe;

final class zza extends Api.zza<zzcbe, Address.AddressOptions> {
    zza() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Address.AddressOptions addressOptions = (Address.AddressOptions) obj;
        zzbo.zzb(context instanceof Activity, (Object) "An Activity must be used for Address APIs");
        if (addressOptions == null) {
            addressOptions = new Address.AddressOptions();
        }
        return new zzcbe((Activity) context, looper, zzq, addressOptions.theme, connectionCallbacks, onConnectionFailedListener);
    }
}
