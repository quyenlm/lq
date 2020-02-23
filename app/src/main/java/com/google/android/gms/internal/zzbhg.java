package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

public final class zzbhg {
    /* access modifiers changed from: private */
    public long zzaKm = 43200;
    /* access modifiers changed from: private */
    public Map<String, String> zzaKn;
    /* access modifiers changed from: private */
    public int zzaKo;
    /* access modifiers changed from: private */
    public int zzaKq = -1;
    /* access modifiers changed from: private */
    public int zzaKr = -1;

    public final zzbhg zzA(long j) {
        this.zzaKm = j;
        return this;
    }

    public final zzbhg zzA(String str, String str2) {
        if (this.zzaKn == null) {
            this.zzaKn = new HashMap();
        }
        this.zzaKn.put(str, str2);
        return this;
    }

    public final zzbhg zzaE(int i) {
        this.zzaKo = 10300;
        return this;
    }

    public final zzbhg zzaF(int i) {
        this.zzaKq = i;
        return this;
    }

    public final zzbhg zzaG(int i) {
        this.zzaKr = i;
        return this;
    }

    public final zzbhf zzsr() {
        return new zzbhf(this);
    }
}
