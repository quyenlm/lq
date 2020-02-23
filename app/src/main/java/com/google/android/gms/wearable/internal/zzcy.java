package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.List;

public final class zzcy extends zza {
    public static final Parcelable.Creator<zzcy> CREATOR = new zzcz();
    public final int statusCode;
    public final List<zzeg> zzbSO;

    public zzcy(int i, List<zzeg> list) {
        this.statusCode = i;
        this.zzbSO = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.statusCode);
        zzd.zzc(parcel, 3, this.zzbSO, false);
        zzd.zzI(parcel, zze);
    }
}
