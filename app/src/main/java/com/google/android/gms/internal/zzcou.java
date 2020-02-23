package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcou implements Parcelable.Creator<zzcot> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        IBinder iBinder = null;
        byte[] bArr = null;
        String str = null;
        String str2 = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    iBinder4 = zzb.zzr(parcel, readInt);
                    break;
                case 2:
                    iBinder3 = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 7:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcot(iBinder4, iBinder3, iBinder2, str2, str, bArr, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcot[i];
    }
}
