package com.google.android.gms.auth.account;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzaqx;

public class WorkAccount {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("WorkAccount.API", zzajS, zzajR);
    public static final WorkAccountApi WorkAccountApi = new zzaqn();
    private static final Api.zzf<zzaqx> zzajR = new Api.zzf<>();
    private static final Api.zza<zzaqx, Api.ApiOptions.NoOptions> zzajS = new zzg();

    private WorkAccount() {
    }
}
