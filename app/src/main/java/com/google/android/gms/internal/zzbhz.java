package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzbhz implements Parcelable.Creator<zzbhy> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        long j = 0;
        DataHolder dataHolder = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        ArrayList<String> arrayList = null;
        int i = 0;
        ArrayList<zzbhi> arrayList2 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 9:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 10:
                    arrayList2 = zzb.zzc(parcel, readInt, zzbhi.CREATOR);
                    break;
                case 11:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbhy(str, j, dataHolder, str2, str3, str4, arrayList, i, arrayList2, i2, i3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhy[i];
    }
}
