package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbpj extends zza {
    public static final Parcelable.Creator<zzbpj> CREATOR = new zzbpk();
    private ParcelFileDescriptor zzaPh;

    public zzbpj(ParcelFileDescriptor parcelFileDescriptor) {
        this.zzaPh = parcelFileDescriptor;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzaPh, i | 1, false);
        zzd.zzI(parcel, zze);
    }
}
