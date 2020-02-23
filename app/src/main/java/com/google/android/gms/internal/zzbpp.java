package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbpp extends zza {
    public static final Parcelable.Creator<zzbpp> CREATOR = new zzbpu();
    private boolean zzuH;

    public zzbpp(boolean z) {
        this.zzuH = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzuH);
        zzd.zzI(parcel, zze);
    }
}
