package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzi extends zza {
    public static final Parcelable.Creator<zzi> CREATOR = new zzj();
    private final String mValue;
    private byte zzbRN;
    private final byte zzbRO;

    public zzi(byte b, byte b2, String str) {
        this.zzbRN = b;
        this.zzbRO = b2;
        this.mValue = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (this.zzbRN != zzi.zzbRN) {
            return false;
        }
        if (this.zzbRO != zzi.zzbRO) {
            return false;
        }
        return this.mValue.equals(zzi.mValue);
    }

    public final int hashCode() {
        return ((((this.zzbRN + 31) * 31) + this.zzbRO) * 31) + this.mValue.hashCode();
    }

    public final String toString() {
        byte b = this.zzbRN;
        byte b2 = this.zzbRO;
        String str = this.mValue;
        return new StringBuilder(String.valueOf(str).length() + 73).append("AmsEntityUpdateParcelable{, mEntityId=").append(b).append(", mAttributeId=").append(b2).append(", mValue='").append(str).append("'}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbRN);
        zzd.zza(parcel, 3, this.zzbRO);
        zzd.zza(parcel, 4, this.mValue, false);
        zzd.zzI(parcel, zze);
    }
}
