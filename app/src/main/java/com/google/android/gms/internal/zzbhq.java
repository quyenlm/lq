package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import java.util.Map;
import java.util.TreeMap;

final class zzbhq extends zzbhn {
    private /* synthetic */ zzbhp zzaKw;

    zzbhq(zzbhp zzbhp) {
        this.zzaKw = zzbhp;
    }

    public final void zza(Status status, zzbia zzbia) {
        if (zzbia.getStatusCode() == 6502 || zzbia.getStatusCode() == 6507) {
            this.zzaKw.setResult(new zzbhr(zzbhl.zzaH(zzbia.getStatusCode()), zzbhl.zza(zzbia), zzbia.getThrottleEndTimeMillis(), zzbhl.zzb(zzbia)));
        } else {
            this.zzaKw.setResult(new zzbhr(zzbhl.zzaH(zzbia.getStatusCode()), (Map<String, TreeMap<String, byte[]>>) zzbhl.zza(zzbia), zzbhl.zzb(zzbia)));
        }
    }
}
