package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.List;

public final class zzbpv extends zza {
    public static final Parcelable.Creator<zzbpv> CREATOR = new zzbpw();
    private final List<String> zzaPl;

    zzbpv(List<String> list) {
        this.zzaPl = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzb(parcel, 2, this.zzaPl, false);
        zzd.zzI(parcel, zze);
    }
}
