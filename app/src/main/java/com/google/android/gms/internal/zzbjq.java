package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.tencent.qqgamemi.util.TimeUtils;

public final class zzbjq {
    private final adb zzaLl;

    private zzbjq(adb adb) {
        this.zzaLl = (adb) zzbo.zzu(adb);
    }

    private static adb zza(int i, long j, long j2) {
        boolean z = true;
        zzbo.zzaf(j2 > j);
        zzbo.zzaf(Math.abs(j) <= TimeUtils.MILLIS_IN_DAY);
        if (Math.abs(j2) > TimeUtils.MILLIS_IN_DAY) {
            z = false;
        }
        zzbo.zzaf(z);
        adb adb = new adb();
        adb.zzcqq = i;
        adb.zzcrD = j;
        adb.zzcrE = j2;
        return adb;
    }

    public static zzbjq zzc(long j, long j2) {
        return new zzbjq(zza(1, j, j2));
    }

    public static zzbjq zzd(long j, long j2) {
        return new zzbjq(zza(2, j, j2));
    }

    public final adb zzsH() {
        return this.zzaLl;
    }
}
