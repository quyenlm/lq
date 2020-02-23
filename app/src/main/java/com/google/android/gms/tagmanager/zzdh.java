package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.imsdk.expansion.downloader.Constants;

final class zzdh implements zzek {
    private final String zzaeX;
    private final long zzaih = 900000;
    private final int zzaii = 5;
    private double zzaij = ((double) Math.min(1, 5));
    private long zzaik;
    private final Object zzail = new Object();
    private final long zzbFo = Constants.ACTIVE_THREAD_WATCHDOG;
    private final zze zzvw;

    public zzdh(int i, int i2, long j, long j2, String str, zze zze) {
        this.zzaeX = str;
        this.zzvw = zze;
    }

    public final boolean zzlL() {
        boolean z = false;
        synchronized (this.zzail) {
            long currentTimeMillis = this.zzvw.currentTimeMillis();
            if (currentTimeMillis - this.zzaik < this.zzbFo) {
                String str = this.zzaeX;
                zzdj.zzaT(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
            } else {
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
                    String str2 = this.zzaeX;
                    zzdj.zzaT(new StringBuilder(String.valueOf(str2).length() + 34).append("Excessive ").append(str2).append(" detected; call ignored.").toString());
                }
            }
        }
        return z;
    }
}
