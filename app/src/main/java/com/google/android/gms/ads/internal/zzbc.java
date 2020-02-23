package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzud;
import com.google.android.gms.internal.zzut;

final class zzbc implements Runnable {
    private /* synthetic */ zzafg zzsW;
    private /* synthetic */ zzbb zzuQ;

    zzbc(zzbb zzbb, zzafg zzafg) {
        this.zzuQ = zzbb;
        this.zzsW = zzafg;
    }

    public final void run() {
        this.zzuQ.zzb(new zzaff(this.zzsW, (zzaka) null, (zzua) null, (zzut) null, (String) null, (zzud) null, (zzoa) null, (String) null));
    }
}
