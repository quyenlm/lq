package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.internal.zzbas;
import com.google.android.gms.internal.zzbem;
import com.google.android.gms.tasks.Task;

public class SettingsClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    public SettingsClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, (zzbem) new zzbas());
    }

    public SettingsClient(@NonNull Context context) {
        super(context, LocationServices.API, null, (zzbem) new zzbas());
    }

    public Task<LocationSettingsResponse> checkLocationSettings(LocationSettingsRequest locationSettingsRequest) {
        return zzbh.zza(LocationServices.SettingsApi.checkLocationSettings(zzpi(), locationSettingsRequest), new LocationSettingsResponse());
    }
}
