package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzasz extends zza {
    public static final Parcelable.Creator<zzasz> CREATOR = new zzata();
    private final int zzanV;
    private final int zzanW;

    public zzasz(int i, int i2) {
        this.zzanV = i;
        this.zzanW = i2;
    }

    public final String toString() {
        String valueOf = String.valueOf(Integer.toString(this.zzanV));
        String valueOf2 = String.valueOf(Integer.toString(this.zzanW));
        return new StringBuilder(String.valueOf(valueOf).length() + 41 + String.valueOf(valueOf2).length()).append("ConnectionState = ").append(valueOf).append(" NetworkMeteredState = ").append(valueOf2).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzanV);
        zzd.zzc(parcel, 3, this.zzanW);
        zzd.zzI(parcel, zze);
    }
}
