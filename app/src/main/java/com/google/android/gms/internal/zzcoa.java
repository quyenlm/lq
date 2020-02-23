package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzcoa extends zza {
    public static final Parcelable.Creator<zzcoa> CREATOR = new zzcob();
    private final String zzbxq;

    public zzcoa(String str) {
        this.zzbxq = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzcoa) {
            return zzbe.equal(this.zzbxq, ((zzcoa) obj).zzbxq);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxq});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbxq, false);
        zzd.zzI(parcel, zze);
    }

    public final String zzzJ() {
        return this.zzbxq;
    }
}
