package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzei extends zza {
    public static final Parcelable.Creator<zzei> CREATOR = new zzej();
    public final int statusCode;
    public final zzak zzbSj;

    public zzei(int i, zzak zzak) {
        this.statusCode = i;
        this.zzbSj = zzak;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.statusCode);
        zzd.zza(parcel, 3, (Parcelable) this.zzbSj, i, false);
        zzd.zzI(parcel, zze);
    }
}
