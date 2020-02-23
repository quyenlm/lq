package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbpb extends zza {
    public static final Parcelable.Creator<zzbpb> CREATOR = new zzbpc();
    final zzbog zzaOW;

    public zzbpb(zzbog zzbog) {
        this.zzaOW = zzbog;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzaOW, i, false);
        zzd.zzI(parcel, zze);
    }
}
