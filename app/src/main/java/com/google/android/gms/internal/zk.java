package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;

public final class zk extends GoogleApi<Api.ApiOptions.NoOptions> {
    private static Api<Api.ApiOptions.NoOptions> API = new Api<>("DynamicLinks.API", zzajS, zzajR);
    private static final Api.zzf<zm> zzajR = new Api.zzf<>();
    private static final Api.zza<zm, Api.ApiOptions.NoOptions> zzajS = new zl();

    public zk(@NonNull Context context) {
        super(context, API, null, GoogleApi.zza.zzaAO);
    }
}
