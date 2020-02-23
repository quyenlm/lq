package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzdc extends zza {
    public static final Parcelable.Creator<zzdc> CREATOR = new zzdd();
    public final int statusCode;
    public final ParcelFileDescriptor zzbww;

    public zzdc(int i, ParcelFileDescriptor parcelFileDescriptor) {
        this.statusCode = i;
        this.zzbww = parcelFileDescriptor;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.statusCode);
        zzd.zza(parcel, 3, (Parcelable) this.zzbww, i | 1, false);
        zzd.zzI(parcel, zze);
    }
}
