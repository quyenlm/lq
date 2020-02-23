package com.google.android.gms.internal;

import java.util.HashMap;

public final class zzda extends zzbs<Integer, Long> {
    public Long zzqB;
    public Long zzqC;

    public zzda() {
    }

    public zzda(String str) {
        zzi(str);
    }

    /* access modifiers changed from: protected */
    public final void zzi(String str) {
        HashMap zzj = zzj(str);
        if (zzj != null) {
            this.zzqB = (Long) zzj.get(0);
            this.zzqC = (Long) zzj.get(1);
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Long> zzv() {
        HashMap<Integer, Long> hashMap = new HashMap<>();
        hashMap.put(0, this.zzqB);
        hashMap.put(1, this.zzqC);
        return hashMap;
    }
}
