package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcpi implements Parcelable.Creator<zzcph> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        byte[] bArr = null;
        byte[] bArr2 = null;
        int i = 0;
        byte[] bArr3 = null;
        byte[] bArr4 = null;
        ParcelUuid parcelUuid = null;
        ParcelUuid parcelUuid2 = null;
        ParcelUuid parcelUuid3 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    parcelUuid3 = (ParcelUuid) zzb.zza(parcel, readInt, ParcelUuid.CREATOR);
                    break;
                case 5:
                    parcelUuid2 = (ParcelUuid) zzb.zza(parcel, readInt, ParcelUuid.CREATOR);
                    break;
                case 6:
                    parcelUuid = (ParcelUuid) zzb.zza(parcel, readInt, ParcelUuid.CREATOR);
                    break;
                case 7:
                    bArr4 = zzb.zzt(parcel, readInt);
                    break;
                case 8:
                    bArr3 = zzb.zzt(parcel, readInt);
                    break;
                case 9:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 10:
                    bArr2 = zzb.zzt(parcel, readInt);
                    break;
                case 11:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcph(i2, parcelUuid3, parcelUuid2, parcelUuid, bArr4, bArr3, i, bArr2, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcph[i];
    }
}
