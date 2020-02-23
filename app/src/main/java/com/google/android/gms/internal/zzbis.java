package com.google.android.gms.internal;

import com.google.android.gms.awareness.state.BeaconState;
import com.google.android.gms.common.internal.zzbo;

public final class zzbis {
    private final acj zzaKS;

    private zzbis(acj acj) {
        this.zzaKS = (acj) zzbo.zzu(acj);
    }

    private static acj zza(int i, BeaconState.TypeFilter[] typeFilterArr, long j) {
        acj acj = new acj();
        acj.zzcqq = i;
        acj.zzcqz = new ack[typeFilterArr.length];
        acj.zzcqr = 3000;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= typeFilterArr.length) {
                return acj;
            }
            acj.zzcqz[i3] = typeFilterArr[i3].zzmV();
            i2 = i3 + 1;
        }
    }

    public static zzbis zza(BeaconState.TypeFilter[] typeFilterArr) {
        return new zzbis(zza(1, typeFilterArr, 3000));
    }

    public static zzbis zzb(BeaconState.TypeFilter[] typeFilterArr) {
        return new zzbis(zza(2, typeFilterArr, 3000));
    }

    public static zzbis zzc(BeaconState.TypeFilter[] typeFilterArr) {
        return new zzbis(zza(3, typeFilterArr, 3000));
    }

    public final acj zzsF() {
        return this.zzaKS;
    }
}
