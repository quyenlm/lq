package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzj;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzalm extends zzj<zzalm> {
    private Map<Integer, String> zzaeR = new HashMap(4);

    public final String toString() {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : this.zzaeR.entrySet()) {
            String valueOf = String.valueOf(next.getKey());
            hashMap.put(new StringBuilder(String.valueOf(valueOf).length() + 9).append("dimension").append(valueOf).toString(), next.getValue());
        }
        return zzh(hashMap);
    }

    public final /* synthetic */ void zzb(zzj zzj) {
        ((zzalm) zzj).zzaeR.putAll(this.zzaeR);
    }

    public final Map<Integer, String> zzjP() {
        return Collections.unmodifiableMap(this.zzaeR);
    }
}
