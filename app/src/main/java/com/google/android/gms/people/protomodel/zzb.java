package com.google.android.gms.people.protomodel;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public final class zzb implements Parcelable.Creator<zzc> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        Long l = null;
        Long l2 = null;
        String str = null;
        ArrayList<zzg> arrayList = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zzg.CREATOR);
                    break;
                case 4:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    l2 = com.google.android.gms.common.internal.safeparcel.zzb.zzj(parcel, readInt);
                    break;
                case 6:
                    l = com.google.android.gms.common.internal.safeparcel.zzb.zzj(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new zzc(str2, arrayList, str, l2, l);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzc[i];
    }
}
