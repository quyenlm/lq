package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzab implements Parcelable.Creator<zzaa> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzeg> arrayList = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, zzeg.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaa(str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaa[i];
    }
}
