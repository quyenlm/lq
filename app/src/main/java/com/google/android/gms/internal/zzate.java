package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzate extends zza {
    public static final Parcelable.Creator<zzate> CREATOR = new zzatf();
    private final int zzanY;
    private final double zzanZ;

    public zzate(int i, double d) {
        this.zzanY = i;
        this.zzanZ = d;
    }

    public final String toString() {
        String valueOf = String.valueOf(Integer.toString(this.zzanY));
        return new StringBuilder(String.valueOf(valueOf).length() + 69).append("PowerConnectionState = ").append(valueOf).append(" Battery Percentage = ").append(this.zzanZ).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzanY);
        zzd.zza(parcel, 3, this.zzanZ);
        zzd.zzI(parcel, zze);
    }
}
