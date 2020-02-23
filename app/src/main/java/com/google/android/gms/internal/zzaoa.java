package com.google.android.gms.internal;

import com.google.android.gms.common.util.zze;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzaoa {
    private final String zzaeX;
    private final long zzaih;
    private final int zzaii;
    private double zzaij;
    private long zzaik;
    private final Object zzail;
    private final zze zzvw;

    private zzaoa(int i, long j, String str, zze zze) {
        this.zzail = new Object();
        this.zzaii = 60;
        this.zzaij = (double) this.zzaii;
        this.zzaih = 2000;
        this.zzaeX = str;
        this.zzvw = zze;
    }

    public zzaoa(String str, zze zze) {
        this(60, 2000, str, zze);
    }

    public final boolean zzlL() {
        boolean z;
        synchronized (this.zzail) {
            long currentTimeMillis = this.zzvw.currentTimeMillis();
            if (this.zzaij < ((double) this.zzaii)) {
                double d = ((double) (currentTimeMillis - this.zzaik)) / ((double) this.zzaih);
                if (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    this.zzaij = Math.min((double) this.zzaii, d + this.zzaij);
                }
            }
            this.zzaik = currentTimeMillis;
            if (this.zzaij >= 1.0d) {
                this.zzaij -= 1.0d;
                z = true;
            } else {
                String str = this.zzaeX;
                zzaob.zzaT(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
                z = false;
            }
        }
        return z;
    }
}
