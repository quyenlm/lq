package com.google.android.gms.appinvite;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzapy;
import com.google.android.gms.internal.zzaqh;

@Deprecated
public final class AppInvite {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("AppInvite.API", zzajS, zzajR);
    public static final AppInviteApi AppInviteApi = new zzapy();
    private static Api.zzf<zzaqh> zzajR = new Api.zzf<>();
    private static final Api.zza<zzaqh, Api.ApiOptions.NoOptions> zzajS = new zza();

    private AppInvite() {
    }
}
