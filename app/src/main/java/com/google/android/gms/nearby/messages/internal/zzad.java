package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;

public final class zzad extends zza {
    public static final Parcelable.Creator<zzad> CREATOR = new zzae();
    private String type;
    private int zzaku;
    private String zzbxU;

    zzad(int i, String str, String str2) {
        this.zzaku = i;
        this.zzbxU = str;
        this.type = str2;
    }

    public zzad(String str, String str2) {
        this(1, str, str2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzad) || hashCode() != obj.hashCode()) {
            return false;
        }
        zzad zzad = (zzad) obj;
        return zzbe.equal(this.zzbxU, zzad.zzbxU) && zzbe.equal(this.type, zzad.type);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxU, this.type});
    }

    public final String toString() {
        String str = this.zzbxU;
        String str2 = this.type;
        return new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(str2).length()).append("namespace=").append(str).append(", type=").append(str2).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbxU, false);
        zzd.zza(parcel, 2, this.type, false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
