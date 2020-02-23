package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import java.util.Arrays;

public final class zzcpj extends zza {
    public static final Parcelable.Creator<zzcpj> CREATOR = new zzcpk();
    private static final String zzbyL = null;
    public static final zzcpj zzbyM = new zzcpj("", (String) null);
    private int zzaku;
    private final String zzbyN;
    @Nullable
    private final String zzbyO;

    zzcpj(int i, @Nullable String str, @Nullable String str2) {
        this.zzaku = ((Integer) zzbo.zzu(Integer.valueOf(i))).intValue();
        this.zzbyN = str == null ? "" : str;
        this.zzbyO = str2;
    }

    private zzcpj(String str, String str2) {
        this(1, str, (String) null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcpj)) {
            return false;
        }
        zzcpj zzcpj = (zzcpj) obj;
        return zzbe.equal(this.zzbyN, zzcpj.zzbyN) && zzbe.equal(this.zzbyO, zzcpj.zzbyO);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbyN, this.zzbyO});
    }

    public final String toString() {
        String str = this.zzbyN;
        String str2 = this.zzbyO;
        return new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(str2).length()).append("NearbyDevice{handle=").append(str).append(", bluetoothAddress=").append(str2).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 3, this.zzbyN, false);
        zzd.zza(parcel, 6, this.zzbyO, false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
