package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcnw extends zza {
    public static final Parcelable.Creator<zzcnw> CREATOR = new zzcnx();
    private final String zzbwG;

    public zzcnw(String str) {
        this.zzbwG = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzcnw) {
            return zzbe.equal(this.zzbwG, ((zzcnw) obj).zzbwG);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbwG});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbwG, false);
        zzd.zzI(parcel, zze);
    }

    public final String zzzF() {
        return this.zzbwG;
    }
}
