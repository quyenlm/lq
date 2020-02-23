package com.google.android.gms.tagmanager;

import android.text.TextUtils;

final class zzbx {
    private final long zzaid;
    private final long zzbEO;
    private final long zzbEP;
    private String zzbEQ;

    zzbx(long j, long j2, long j3) {
        this.zzbEO = j;
        this.zzaid = j2;
        this.zzbEP = j3;
    }

    /* access modifiers changed from: package-private */
    public final long zzBm() {
        return this.zzbEO;
    }

    /* access modifiers changed from: package-private */
    public final long zzBn() {
        return this.zzbEP;
    }

    /* access modifiers changed from: package-private */
    public final String zzBo() {
        return this.zzbEQ;
    }

    /* access modifiers changed from: package-private */
    public final void zzfl(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.zzbEQ = str;
        }
    }
}
