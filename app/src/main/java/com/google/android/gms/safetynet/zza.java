package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zza extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private final String zzbBF;

    public zza(String str) {
        this.zzbBF = str;
    }

    public final String getJwsResult() {
        return this.zzbBF;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbBF, false);
        zzd.zzI(parcel, zze);
    }
}
