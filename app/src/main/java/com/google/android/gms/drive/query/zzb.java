package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.internal.zzr;
import java.util.ArrayList;
import java.util.List;

public final class zzb implements Parcelable.Creator<Query> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        boolean z = false;
        ArrayList<DriveSpace> arrayList = null;
        boolean z2 = false;
        ArrayList<String> arrayList2 = null;
        SortOrder sortOrder = null;
        String str = null;
        zzr zzr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzr = (zzr) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, zzr.CREATOR);
                    break;
                case 3:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    sortOrder = (SortOrder) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, SortOrder.CREATOR);
                    break;
                case 5:
                    arrayList2 = com.google.android.gms.common.internal.safeparcel.zzb.zzC(parcel, readInt);
                    break;
                case 6:
                    z2 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, DriveSpace.CREATOR);
                    break;
                case 8:
                    z = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new Query(zzr, str, sortOrder, (List<String>) arrayList2, z2, (List<DriveSpace>) arrayList, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Query[i];
    }
}
