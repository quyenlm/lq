package com.google.android.gms.internal;

import com.tencent.component.debug.TraceFormat;
import java.util.HashMap;

public final class zzbu extends zzbs<Integer, Object> {
    public String zzaT;
    public String zzaV;
    public String zzaW;
    public String zzaX;
    public long zzlO;

    public zzbu() {
        this.zzaT = TraceFormat.STR_ERROR;
        this.zzlO = -1;
        this.zzaV = TraceFormat.STR_ERROR;
        this.zzaW = TraceFormat.STR_ERROR;
        this.zzaX = TraceFormat.STR_ERROR;
    }

    public zzbu(String str) {
        this();
        zzi(str);
    }

    /* access modifiers changed from: protected */
    public final void zzi(String str) {
        HashMap zzj = zzj(str);
        if (zzj != null) {
            this.zzaT = zzj.get(0) == null ? TraceFormat.STR_ERROR : (String) zzj.get(0);
            this.zzlO = zzj.get(1) == null ? -1 : ((Long) zzj.get(1)).longValue();
            this.zzaV = zzj.get(2) == null ? TraceFormat.STR_ERROR : (String) zzj.get(2);
            this.zzaW = zzj.get(3) == null ? TraceFormat.STR_ERROR : (String) zzj.get(3);
            this.zzaX = zzj.get(4) == null ? TraceFormat.STR_ERROR : (String) zzj.get(4);
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Object> zzv() {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(0, this.zzaT);
        hashMap.put(4, this.zzaX);
        hashMap.put(3, this.zzaW);
        hashMap.put(2, this.zzaV);
        hashMap.put(1, Long.valueOf(this.zzlO));
        return hashMap;
    }
}
