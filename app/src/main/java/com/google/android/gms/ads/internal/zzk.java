package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzoa;
import com.google.android.gms.internal.zzua;
import com.google.android.gms.internal.zzud;
import com.google.android.gms.internal.zzut;

final class zzk implements Runnable {
    private /* synthetic */ zzafg zzsW;
    private /* synthetic */ zzi zztb;

    zzk(zzi zzi, zzafg zzafg) {
        this.zztb = zzi;
        this.zzsW = zzafg;
    }

    public final void run() {
        this.zztb.zzb(new zzaff(this.zzsW, (zzaka) null, (zzua) null, (zzut) null, (String) null, (zzud) null, (zzoa) null, (String) null));
    }
}
