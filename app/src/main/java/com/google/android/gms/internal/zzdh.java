package com.google.android.gms.internal;

import java.util.HashMap;

public final class zzdh extends zzbs<Integer, Long> {
    public Long zzcv;
    public Long zzcw;
    public Long zzrj;

    public zzdh() {
    }

    public zzdh(String str) {
        zzi(str);
    }

    /* access modifiers changed from: protected */
    public final void zzi(String str) {
        HashMap zzj = zzj(str);
        if (zzj != null) {
            this.zzrj = (Long) zzj.get(0);
            this.zzcv = (Long) zzj.get(1);
            this.zzcw = (Long) zzj.get(2);
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Long> zzv() {
        HashMap<Integer, Long> hashMap = new HashMap<>();
        hashMap.put(0, this.zzrj);
        hashMap.put(1, this.zzcv);
        hashMap.put(2, this.zzcw);
        return hashMap;
    }
}
