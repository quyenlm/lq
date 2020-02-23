package com.google.android.gms.panorama;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzcqe;
import com.google.android.gms.internal.zzcql;

public final class Panorama {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Panorama.API", zzajS, zzajR);
    public static final PanoramaApi PanoramaApi = new zzcqe();
    public static final Api.zzf<zzcql> zzajR = new Api.zzf<>();
    private static Api.zza<zzcql, Api.ApiOptions.NoOptions> zzajS = new zzb();

    private Panorama() {
    }
}
