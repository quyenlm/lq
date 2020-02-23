package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzapg implements Parcelable.Creator<zzapf> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String[] strArr = null;
        ArrayList<zzapl> arrayList = null;
        Status status = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    status = (Status) zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, zzapl.CREATOR);
                    break;
                case 3:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzapf(status, arrayList, strArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzapf[i];
    }
}
