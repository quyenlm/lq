package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public final class zzctl implements Api.ApiOptions.Optional {
    public static final zzctl zzbCM = new zzctl(false, false, (String) null, false, (String) null, false, (Long) null, (Long) null);
    private final boolean zzalh = false;
    private final String zzali = null;
    private final boolean zzama = false;
    private final String zzamb = null;
    private final boolean zzbCN = false;
    private final boolean zzbCO = false;
    private final Long zzbCP = null;
    private final Long zzbCQ = null;

    static {
        new zzctm();
    }

    private zzctl(boolean z, boolean z2, String str, boolean z3, String str2, boolean z4, Long l, Long l2) {
    }

    public final String getServerClientId() {
        return this.zzali;
    }

    public final boolean isIdTokenRequested() {
        return this.zzalh;
    }

    public final boolean zzAr() {
        return this.zzbCN;
    }

    public final boolean zzAs() {
        return this.zzama;
    }

    @Nullable
    public final String zzAt() {
        return this.zzamb;
    }

    public final boolean zzAu() {
        return this.zzbCO;
    }

    @Nullable
    public final Long zzAv() {
        return this.zzbCP;
    }

    @Nullable
    public final Long zzAw() {
        return this.zzbCQ;
    }
}
