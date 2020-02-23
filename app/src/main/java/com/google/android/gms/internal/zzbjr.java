package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.TimeZone;

public final class zzbjr {
    private final add zzaLm;

    private zzbjr(add add) {
        this.zzaLm = (add) zzbo.zzu(add);
    }

    public static zzbjr zza(int i, TimeZone timeZone, long j, long j2) {
        boolean z = true;
        zzbo.zzaf(i != 1);
        zzbo.zzaf(j >= 0);
        zzbo.zzaf(j <= TimeUtils.MILLIS_IN_DAY);
        zzbo.zzaf(j2 >= 0);
        zzbo.zzaf(j2 <= TimeUtils.MILLIS_IN_DAY);
        if (j > j2) {
            z = false;
        }
        zzbo.zzaf(z);
        return new zzbjr(zzc(i, timeZone, j, j2));
    }

    public static zzbjr zzb(int i, TimeZone timeZone, long j, long j2) {
        int i2;
        boolean z = true;
        zzbo.zzaf(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7);
        zzbo.zzaf(j >= 0);
        zzbo.zzaf(j <= TimeUtils.MILLIS_IN_DAY);
        zzbo.zzaf(j2 >= 0);
        zzbo.zzaf(j2 <= TimeUtils.MILLIS_IN_DAY);
        if (j > j2) {
            z = false;
        }
        zzbo.zzaf(z);
        switch (i) {
            case 1:
                i2 = 5;
                break;
            case 2:
                i2 = 6;
                break;
            case 3:
                i2 = 7;
                break;
            case 4:
                i2 = 8;
                break;
            case 5:
                i2 = 9;
                break;
            case 6:
                i2 = 10;
                break;
            case 7:
                i2 = 11;
                break;
            default:
                return null;
        }
        return new zzbjr(zzc(i2, timeZone, j, j2));
    }

    private static add zzc(int i, TimeZone timeZone, long j, long j2) {
        add add = new add();
        add.zzcqq = i;
        if (i == 1) {
            add.zzcrI = false;
        } else if (timeZone == null || TextUtils.isEmpty(timeZone.getID())) {
            add.zzcrI = true;
        } else {
            add.zzcrF = timeZone.getID();
            add.zzcrI = false;
        }
        add.zzaTb = j;
        add.zzcrG = j2;
        return add;
    }

    public static zzbjr zze(long j, long j2) {
        boolean z = false;
        zzbo.zzaf(j >= 0);
        zzbo.zzaf(j2 >= 0);
        if (j <= j2) {
            z = true;
        }
        zzbo.zzaf(z);
        return new zzbjr(zzc(1, (TimeZone) null, j, j2));
    }

    public final add zzsI() {
        return this.zzaLm;
    }
}
