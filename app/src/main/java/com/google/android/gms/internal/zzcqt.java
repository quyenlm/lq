package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcqt implements Parcelable.Creator<zzcqn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        byte[][] bArr = null;
        int[] iArr = null;
        byte[][] bArr2 = null;
        byte[][] bArr3 = null;
        byte[][] bArr4 = null;
        byte[][] bArr5 = null;
        byte[] bArr6 = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    bArr6 = zzb.zzt(parcel, readInt);
                    break;
                case 4:
                    bArr5 = zzb.zzu(parcel, readInt);
                    break;
                case 5:
                    bArr4 = zzb.zzu(parcel, readInt);
                    break;
                case 6:
                    bArr3 = zzb.zzu(parcel, readInt);
                    break;
                case 7:
                    bArr2 = zzb.zzu(parcel, readInt);
                    break;
                case 8:
                    iArr = zzb.zzw(parcel, readInt);
                    break;
                case 9:
                    bArr = zzb.zzu(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcqn(str, bArr6, bArr5, bArr4, bArr3, bArr2, iArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcqn[i];
    }
}
