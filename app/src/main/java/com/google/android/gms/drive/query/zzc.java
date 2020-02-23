package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.query.internal.zzf;
import java.util.ArrayList;

public final class zzc implements Parcelable.Creator<SortOrder> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzf> arrayList = null;
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList = zzb.zzc(parcel, readInt, zzf.CREATOR);
                    break;
                case 2:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new SortOrder(arrayList, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SortOrder[i];
    }
}
