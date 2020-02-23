package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;

public final class zzbla extends zza {
    public static final Parcelable.Creator<zzbla> CREATOR = new zzblb();
    private int zzaLU;

    public zzbla(int i) {
        zzbo.zzb(i == 536870912 || i == 805306368, (Object) "Cannot create a new read-only contents!");
        this.zzaLU = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaLU);
        zzd.zzI(parcel, zze);
    }
}
