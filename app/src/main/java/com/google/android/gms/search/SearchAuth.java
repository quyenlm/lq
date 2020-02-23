package com.google.android.gms.search;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzcsy;
import com.google.android.gms.internal.zzcsz;

public class SearchAuth {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("SearchAuth.API", zzbCz, zzajR);
    public static final SearchAuthApi SearchAuthApi = new zzcsz();
    private static Api.zzf<zzcsy> zzajR = new Api.zzf<>();
    private static final Api.zza<zzcsy, Api.ApiOptions.NoOptions> zzbCz = new zzb();

    public static class StatusCodes {
        public static final int AUTH_DISABLED = 10000;
        public static final int AUTH_THROTTLED = 10001;
        public static final int DEVELOPER_ERROR = 10;
        public static final int INTERNAL_ERROR = 8;
        public static final int SUCCESS = 0;
    }

    private SearchAuth() {
    }
}
