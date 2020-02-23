package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcop implements Parcelable.Creator<zzcoo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        long j = 0;
        String str = null;
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        byte[] bArr = null;
        long j2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 4:
                    parcelFileDescriptor2 = (ParcelFileDescriptor) zzb.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 7:
                    parcelFileDescriptor = (ParcelFileDescriptor) zzb.zza(parcel, readInt, ParcelFileDescriptor.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcoo(j2, i, bArr, parcelFileDescriptor2, str, j, parcelFileDescriptor);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcoo[i];
    }
}
