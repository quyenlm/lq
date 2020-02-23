package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzapk implements Parcelable.Creator<zzapi> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 1;
        String str = null;
        int[] iArr = null;
        zzapd[] zzapdArr = null;
        String str2 = null;
        boolean z = false;
        boolean z2 = false;
        String str3 = null;
        String str4 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    zzapdArr = (zzapd[]) zzb.zzb(parcel, readInt, zzapd.CREATOR);
                    break;
                case 8:
                    iArr = zzb.zzw(parcel, readInt);
                    break;
                case 11:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzapi(str4, str3, z2, i, z, str2, zzapdArr, iArr, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzapi[i];
    }
}
