package com.google.android.gms.ads.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzaet;
import com.google.android.gms.internal.zzzn;
import java.util.Map;

@zzzn
public final class zzw {
    private boolean zztq;
    private zzaet zztr;

    public zzw(zzaet zzaet) {
        this.zztr = zzaet;
    }

    public final void recordClick() {
        this.zztq = true;
    }

    public final boolean zzaR() {
        return !(this.zztr == null ? false : this.zztr.zzgY().zzXw) || this.zztq;
    }

    public final void zzt(@Nullable String str) {
        if (this.zztr != null) {
            this.zztr.zza(str, (Map<String, String>) null, 3);
        }
    }
}
