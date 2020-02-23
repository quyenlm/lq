package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzc implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ArrayList<zzb> arrayList = null;
        String str = null;
        ArrayList<zzb> arrayList2 = null;
        String str2 = null;
        ArrayList<zzb> arrayList3 = null;
        String str3 = null;
        ArrayList<Integer> arrayList4 = null;
        String str4 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    arrayList4 = zzb.zzB(parcel, readInt);
                    break;
                case 4:
                    arrayList3 = zzb.zzc(parcel, readInt, zzb.CREATOR);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    arrayList2 = zzb.zzc(parcel, readInt, zzb.CREATOR);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    arrayList = zzb.zzc(parcel, readInt, zzb.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zza(str4, arrayList4, i, str3, arrayList3, str2, arrayList2, str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
