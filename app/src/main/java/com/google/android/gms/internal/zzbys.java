package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.SensorsApi;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.zzak;
import com.google.android.gms.fitness.request.zzam;
import com.google.android.gms.fitness.result.DataSourcesResult;

public final class zzbys implements SensorsApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzt zzt, PendingIntent pendingIntent, zzbyx zzbyx) {
        return googleApiClient.zze(new zzbyw(this, googleApiClient, zzbyx, zzt, pendingIntent));
    }

    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, SensorRequest sensorRequest, zzt zzt, PendingIntent pendingIntent) {
        return googleApiClient.zzd(new zzbyu(this, googleApiClient, sensorRequest, zzt, pendingIntent));
    }

    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, PendingIntent pendingIntent) {
        return zza(googleApiClient, sensorRequest, (zzt) null, pendingIntent);
    }

    public final PendingResult<Status> add(GoogleApiClient googleApiClient, SensorRequest sensorRequest, OnDataPointListener onDataPointListener) {
        return zza(googleApiClient, sensorRequest, (zzt) zzam.zztV().zza(onDataPointListener), (PendingIntent) null);
    }

    public final PendingResult<DataSourcesResult> findDataSources(GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        return googleApiClient.zzd(new zzbyt(this, googleApiClient, dataSourcesRequest));
    }

    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return zza(googleApiClient, (zzt) null, pendingIntent, (zzbyx) null);
    }

    public final PendingResult<Status> remove(GoogleApiClient googleApiClient, OnDataPointListener onDataPointListener) {
        zzak zzb = zzam.zztV().zzb(onDataPointListener);
        return zzb == null ? PendingResults.zza(Status.zzaBm, googleApiClient) : zza(googleApiClient, (zzt) zzb, (PendingIntent) null, (zzbyx) new zzbyv(this, onDataPointListener));
    }
}
