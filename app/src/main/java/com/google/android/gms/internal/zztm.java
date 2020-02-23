package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzp;
import java.util.Arrays;

@zzzn
final class zztm {
    private final Object[] mParams;

    zztm(zzir zzir, String str, int i) {
        this.mParams = zzp.zza((String) zzbs.zzbL().zzd(zzmo.zzEa), zzir, str, i, (zziv) null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zztm)) {
            return false;
        }
        return Arrays.equals(this.mParams, ((zztm) obj).mParams);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.mParams);
    }

    public final String toString() {
        String valueOf = String.valueOf(Arrays.toString(this.mParams));
        return new StringBuilder(String.valueOf(valueOf).length() + 24).append("[InterstitialAdPoolKey ").append(valueOf).append("]").toString();
    }
}
