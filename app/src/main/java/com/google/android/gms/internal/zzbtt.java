package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbtt extends zza {
    public static final Parcelable.Creator<zzbtt> CREATOR = new zzbtu();
    private int mIndex;
    private int zzaSh;

    public zzbtt(int i, int i2) {
        this.mIndex = i;
        this.zzaSh = i2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.mIndex);
        zzd.zzc(parcel, 3, this.zzaSh);
        zzd.zzI(parcel, zze);
    }
}
