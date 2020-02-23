package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzbtv extends zza {
    public static final Parcelable.Creator<zzbtv> CREATOR = new zzbtw();
    private int zzaRI;

    public zzbtv(int i) {
        this.zzaRI = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaRI);
        zzd.zzI(parcel, zze);
    }
}
