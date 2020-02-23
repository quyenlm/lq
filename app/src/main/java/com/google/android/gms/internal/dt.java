package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class dt extends dp<Double> {
    private static final Map<String, zzcxo> zzbLo;
    private Double zzbLp;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("hasOwnProperty", zzczp.zzbJW);
        hashMap.put("toString", new r());
        zzbLo = Collections.unmodifiableMap(hashMap);
    }

    public dt(Double d) {
        zzbo.zzu(d);
        this.zzbLp = d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof dt) {
            return this.zzbLp.equals(((dt) obj).zzbLp);
        }
        return false;
    }

    public final String toString() {
        return this.zzbLp.toString();
    }

    public final /* synthetic */ Object zzDl() {
        return this.zzbLp;
    }

    public final Double zzDo() {
        return this.zzbLp;
    }

    public final boolean zzga(String str) {
        return zzbLo.containsKey(str);
    }

    public final zzcxo zzgb(String str) {
        if (zzga(str)) {
            return zzbLo.get(str);
        }
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 53).append("Native Method ").append(str).append(" is not defined for type DoubleWrapper.").toString());
    }
}
