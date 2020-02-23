package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Arrays;

public final class Strategy extends zza {
    public static final Parcelable.Creator<Strategy> CREATOR = new zzf();
    public static final Strategy P2P_CLUSTER = new Strategy(1, 3);
    public static final Strategy P2P_STAR = new Strategy(1, 2);
    private final int zzbwC;
    private final int zzbwD;

    Strategy(int i, int i2) {
        this.zzbwC = i;
        this.zzbwD = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Strategy)) {
            return false;
        }
        Strategy strategy = (Strategy) obj;
        return this.zzbwC == strategy.zzbwC && this.zzbwD == strategy.zzbwD;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzbwC), Integer.valueOf(this.zzbwD)});
    }

    public final String toString() {
        Object[] objArr = new Object[3];
        objArr[0] = P2P_CLUSTER.equals(this) ? "P2P_CLUSTER" : P2P_STAR.equals(this) ? "P2P_STAR" : "UNKNOWN";
        objArr[1] = Integer.valueOf(this.zzbwC);
        objArr[2] = Integer.valueOf(this.zzbwD);
        return String.format("Strategy(%s){connectionType=%d, topology=%d}", objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 3, this.zzbwC);
        zzd.zzc(parcel, 4, this.zzbwD);
        zzd.zzI(parcel, zze);
    }
}
