package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.zzs;
import java.util.List;

public final class zzbok extends zza {
    public static final Parcelable.Creator<zzbok> CREATOR = new zzbol();
    private int zzJA;
    private List<zzs> zzaOP;

    public zzbok(List<zzs> list, int i) {
        this.zzaOP = list;
        this.zzJA = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaOP, false);
        zzd.zzc(parcel, 3, this.zzJA);
        zzd.zzI(parcel, zze);
    }
}
