package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbig extends zza {
    public static final Parcelable.Creator<zzbig> CREATOR = new zzbih();
    private final Bundle zzaKJ;

    public zzbig(Bundle bundle) {
        this.zzaKJ = bundle;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzaKJ, false);
        zzd.zzI(parcel, zze);
    }

    public final Bundle zzsy() {
        return this.zzaKJ;
    }
}
