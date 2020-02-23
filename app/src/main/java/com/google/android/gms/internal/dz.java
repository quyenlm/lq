package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class dz extends dp<Map<String, dp<?>>> {
    private static final Map<String, zzcxo> zzbLo;
    private boolean zzbLC = false;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("hasOwnProperty", zzczp.zzbJW);
        zzbLo = Collections.unmodifiableMap(hashMap);
    }

    public dz(Map<String, dp<?>> map) {
        this.zzbLl = (Map) zzbo.zzu(map);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof dz) {
            return this.zzbLl.entrySet().equals(((dz) obj).zzbLl.entrySet());
        }
        return false;
    }

    public final boolean isImmutable() {
        return this.zzbLC;
    }

    public final String toString() {
        return this.zzbLl.toString();
    }

    public final Iterator<dp<?>> zzDk() {
        return zzDm();
    }

    public final /* synthetic */ Object zzDl() {
        return this.zzbLl;
    }

    public final Map<String, dp<?>> zzDt() {
        return this.zzbLl;
    }

    public final void zzDu() {
        this.zzbLC = true;
    }

    public final dp<?> zzfZ(String str) {
        dp<?> zzfZ = super.zzfZ(str);
        return zzfZ == null ? dv.zzbLu : zzfZ;
    }

    public final boolean zzga(String str) {
        return zzbLo.containsKey(str);
    }

    public final zzcxo zzgb(String str) {
        if (zzga(str)) {
            return zzbLo.get(str);
        }
        throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 51).append("Native Method ").append(str).append(" is not defined for type ListWrapper.").toString());
    }
}
