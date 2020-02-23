package com.google.android.gms.instantapps;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzcbk;
import com.google.android.gms.internal.zzcbn;
import com.google.android.gms.internal.zzcbo;
import com.google.android.gms.internal.zzcbr;

public final class InstantApps {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("InstantApps.API", zzajS, zzajR);
    public static final InstantAppsApi InstantAppsApi = new zzcbn();
    private static final Api.zzf<zzcbo> zzajR = new Api.zzf<>();
    private static final Api.zza<zzcbo, Api.ApiOptions.NoOptions> zzajS = new zza();

    private InstantApps() {
    }

    public static ActivityCompat getActivityCompat(Activity activity) {
        return new zzcbk(activity);
    }

    @Deprecated
    public static PackageManagerWrapper getPackageManager(Context context, boolean z) {
        return zzcbr.zzi(context, z);
    }

    public static PackageManagerCompat getPackageManagerCompat(Context context) {
        return zzcbr.zzi(context, true);
    }
}
