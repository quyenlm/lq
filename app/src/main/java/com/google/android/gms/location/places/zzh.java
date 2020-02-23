package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public final class zzh implements Parcelable.Creator<PlaceFilter> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        ArrayList<zzo> arrayList = null;
        ArrayList<String> arrayList2 = null;
        ArrayList<Integer> arrayList3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList3 = zzb.zzB(parcel, readInt);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    arrayList = zzb.zzc(parcel, readInt, zzo.CREATOR);
                    break;
                case 6:
                    arrayList2 = zzb.zzC(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PlaceFilter((List<Integer>) arrayList3, z, (List<String>) arrayList2, (List<zzo>) arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PlaceFilter[i];
    }
}
