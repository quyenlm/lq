package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.zzg;
import java.util.ArrayList;

public final class zzbpe implements Parcelable.Creator<zzbpd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ArrayList<zzg> arrayList = null;
        long j = 0;
        long j2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzb.zzc(parcel, readInt, zzg.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbpd(j2, j, i, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbpd[i];
    }
}
