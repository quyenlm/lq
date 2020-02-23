package com.google.android.gms.auth.api.phone;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.internal.zzash;
import com.google.android.gms.internal.zzbas;
import com.google.android.gms.internal.zzbem;
import com.google.android.gms.tasks.Task;

public abstract class SmsRetrieverClient extends GoogleApi<Api.ApiOptions.NoOptions> implements SmsRetrieverApi {
    private static final Api<Api.ApiOptions.NoOptions> API = new Api<>("SmsRetriever.API", zzajS, zzajR);
    private static final Api.zzf<zzash> zzajR = new Api.zzf<>();
    private static final Api.zza<zzash, Api.ApiOptions.NoOptions> zzajS = new zza();

    public SmsRetrieverClient(@NonNull Activity activity) {
        super(activity, API, null, (zzbem) new zzbas());
    }

    public SmsRetrieverClient(@NonNull Context context) {
        super(context, API, null, (zzbem) new zzbas());
    }

    public abstract Task<Void> startSmsRetriever();
}
