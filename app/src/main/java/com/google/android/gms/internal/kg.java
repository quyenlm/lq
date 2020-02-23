package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbo;

public final class kg implements Api.ApiOptions.HasOptions {
    private final String zzbVj;

    private kg(@NonNull String str) {
        this.zzbVj = zzbo.zzh(str, "A valid API key must be provided");
    }

    /* synthetic */ kg(String str, kf kfVar) {
        this(str);
    }

    public final String getApiKey() {
        return this.zzbVj;
    }
}
