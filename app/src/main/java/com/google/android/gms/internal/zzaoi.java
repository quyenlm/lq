package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.debug.FileTracerConfig;
import java.util.UUID;

public final class zzaoi {
    private final String mName;
    private final long zzaiB;
    private /* synthetic */ zzaog zzaiC;

    private zzaoi(zzaog zzaog, String str, long j) {
        this.zzaiC = zzaog;
        zzbo.zzcF(str);
        zzbo.zzaf(j > 0);
        this.mName = str;
        this.zzaiB = j;
    }

    private final void zzma() {
        long currentTimeMillis = this.zzaiC.zzkq().currentTimeMillis();
        SharedPreferences.Editor edit = this.zzaiC.zzaix.edit();
        edit.remove(zzme());
        edit.remove(zzmf());
        edit.putLong(zzmd(), currentTimeMillis);
        edit.commit();
    }

    private final long zzmc() {
        return this.zzaiC.zzaix.getLong(zzmd(), 0);
    }

    private final String zzmd() {
        return String.valueOf(this.mName).concat(":start");
    }

    private final String zzme() {
        return String.valueOf(this.mName).concat(":count");
    }

    private final String zzmf() {
        return String.valueOf(this.mName).concat(":value");
    }

    public final void zzbA(String str) {
        if (zzmc() == 0) {
            zzma();
        }
        if (str == null) {
            str = "";
        }
        synchronized (this) {
            long j = this.zzaiC.zzaix.getLong(zzme(), 0);
            if (j <= 0) {
                SharedPreferences.Editor edit = this.zzaiC.zzaix.edit();
                edit.putString(zzmf(), str);
                edit.putLong(zzme(), 1);
                edit.apply();
                return;
            }
            boolean z = (UUID.randomUUID().getLeastSignificantBits() & FileTracerConfig.FOREVER) < FileTracerConfig.FOREVER / (j + 1);
            SharedPreferences.Editor edit2 = this.zzaiC.zzaix.edit();
            if (z) {
                edit2.putString(zzmf(), str);
            }
            edit2.putLong(zzme(), j + 1);
            edit2.apply();
        }
    }

    public final Pair<String, Long> zzmb() {
        long zzmc = zzmc();
        long abs = zzmc == 0 ? 0 : Math.abs(zzmc - this.zzaiC.zzkq().currentTimeMillis());
        if (abs < this.zzaiB) {
            return null;
        }
        if (abs > (this.zzaiB << 1)) {
            zzma();
            return null;
        }
        String string = this.zzaiC.zzaix.getString(zzmf(), (String) null);
        long j = this.zzaiC.zzaix.getLong(zzme(), 0);
        zzma();
        if (string == null || j <= 0) {
            return null;
        }
        return new Pair<>(string, Long.valueOf(j));
    }
}
