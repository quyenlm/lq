package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzc implements Parcelable.Creator<AutocompleteFilter> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        ArrayList<Integer> arrayList = null;
        boolean z = false;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 2:
                    arrayList = zzb.zzB(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new AutocompleteFilter(i, z, arrayList, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AutocompleteFilter[i];
    }
}
