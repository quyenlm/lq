package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;

public final class zzcur implements zzcux {
    private static final Object zzbDk = new Object();
    private static zzcur zzbHY;
    private static final Set<String> zzbIb = new HashSet(Arrays.asList(new String[]{"GET", HttpHead.METHOD_NAME, "POST", HttpPut.METHOD_NAME}));
    private zzcvt zzbHZ;
    private zzcuy zzbIa;

    private zzcur(Context context) {
        this(zzcuz.zzbw(context), new zzcwb());
    }

    private zzcur(zzcuy zzcuy, zzcvt zzcvt) {
        this.zzbIa = zzcuy;
        this.zzbHZ = zzcvt;
    }

    public static zzcux zzbv(Context context) {
        zzcur zzcur;
        synchronized (zzbDk) {
            if (zzbHY == null) {
                zzbHY = new zzcur(context);
            }
            zzcur = zzbHY;
        }
        return zzcur;
    }

    public final void dispatch() {
        zzcwd.zzCA().dispatch();
    }

    public final boolean zzW(String str, String str2) {
        return zza(str, (String) null, str2, (Map<String, String>) null, (String) null);
    }

    public final boolean zza(String str, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4) {
        if (str2 != null && !zzbIb.contains(str2)) {
            zzcvk.zzaT(String.format("Unsupport http method %s. Drop the hit.", new Object[]{str2}));
            return false;
        } else if (zzcvs.zzCw().isPreview() || this.zzbHZ.zzlL()) {
            this.zzbIa.zzb(str, str2, str3, map, str4);
            return true;
        } else {
            zzcvk.zzaT("Too many hits sent too quickly (rate throttled).");
            return false;
        }
    }

    public final boolean zzfh(String str) {
        return zza(str, (String) null, (String) null, (Map<String, String>) null, (String) null);
    }
}
