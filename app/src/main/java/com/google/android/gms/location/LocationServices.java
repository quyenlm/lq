package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbay;
import com.google.android.gms.internal.zzccb;
import com.google.android.gms.internal.zzccq;
import com.google.android.gms.internal.zzcdj;
import com.google.android.gms.internal.zzcdu;

public class LocationServices {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("LocationServices.API", zzajS, zzajR);
    public static final FusedLocationProviderApi FusedLocationApi = new zzccb();
    public static final GeofencingApi GeofencingApi = new zzccq();
    public static final SettingsApi SettingsApi = new zzcdu();
    private static final Api.zzf<zzcdj> zzajR = new Api.zzf<>();
    private static final Api.zza<zzcdj, Api.ApiOptions.NoOptions> zzajS = new zzs();

    public static abstract class zza<R extends Result> extends zzbay<R, zzcdj> {
        public zza(GoogleApiClient googleApiClient) {
            super((Api<?>) LocationServices.API, googleApiClient);
        }

        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }
    }

    private LocationServices() {
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Activity activity) {
        return new FusedLocationProviderClient(activity);
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Context context) {
        return new FusedLocationProviderClient(context);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Activity activity) {
        return new GeofencingClient(activity);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Context context) {
        return new GeofencingClient(context);
    }

    public static SettingsClient getSettingsClient(@NonNull Activity activity) {
        return new SettingsClient(activity);
    }

    public static SettingsClient getSettingsClient(@NonNull Context context) {
        return new SettingsClient(context);
    }

    public static zzcdj zzg(GoogleApiClient googleApiClient) {
        boolean z = true;
        zzbo.zzb(googleApiClient != null, (Object) "GoogleApiClient parameter is required.");
        zzcdj zzcdj = (zzcdj) googleApiClient.zza(zzajR);
        if (zzcdj == null) {
            z = false;
        }
        zzbo.zza(z, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return zzcdj;
    }
}
