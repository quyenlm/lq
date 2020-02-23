package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzazv implements Parcelable.Creator<zzazu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = true;
        zzcqn[] zzcqnArr = null;
        byte[][] bArr = null;
        int[] iArr = null;
        String[] strArr = null;
        int[] iArr2 = null;
        byte[] bArr2 = null;
        zzbak zzbak = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    zzbak = (zzbak) zzb.zza(parcel, readInt, zzbak.CREATOR);
                    break;
                case 3:
                    bArr2 = zzb.zzt(parcel, readInt);
                    break;
                case 4:
                    iArr2 = zzb.zzw(parcel, readInt);
                    break;
                case 5:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 6:
                    iArr = zzb.zzw(parcel, readInt);
                    break;
                case 7:
                    bArr = zzb.zzu(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    zzcqnArr = (zzcqn[]) zzb.zzb(parcel, readInt, zzcqn.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzazu(zzbak, bArr2, iArr2, strArr, iArr, bArr, z, zzcqnArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzazu[i];
    }
}
