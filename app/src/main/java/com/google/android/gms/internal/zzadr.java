package com.google.android.gms.internal;

public final class zzadr {
    /* access modifiers changed from: private */
    public int mErrorCode;
    /* access modifiers changed from: private */
    public String zzMs;
    /* access modifiers changed from: private */
    public String zzWJ;
    /* access modifiers changed from: private */
    public long zzWK;

    public final zzadr zzax(String str) {
        this.zzMs = str;
        return this;
    }

    public final zzadr zzay(String str) {
        this.zzWJ = str;
        return this;
    }

    public final zzadr zzg(long j) {
        this.zzWK = j;
        return this;
    }

    public final zzadp zzgU() {
        return new zzadp(this);
    }

    public final zzadr zzw(int i) {
        this.mErrorCode = i;
        return this;
    }
}
