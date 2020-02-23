package com.google.android.gms.internal;

import java.util.HashMap;

public final class zzcz extends zzbs<Integer, Object> {
    public Boolean zzqA;
    public Long zzqy;
    public Boolean zzqz;

    public zzcz() {
    }

    public zzcz(String str) {
        zzi(str);
    }

    /* access modifiers changed from: protected */
    public final void zzi(String str) {
        HashMap zzj = zzj(str);
        if (zzj != null) {
            this.zzqy = (Long) zzj.get(0);
            this.zzqz = (Boolean) zzj.get(1);
            this.zzqA = (Boolean) zzj.get(2);
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Object> zzv() {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(0, this.zzqy);
        hashMap.put(1, this.zzqz);
        hashMap.put(2, this.zzqA);
        return hashMap;
    }
}
