package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzaer implements Parcelable.Creator<zzaeq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        boolean z2 = false;
        ArrayList<String> arrayList = null;
        boolean z3 = false;
        boolean z4 = false;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    z4 = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 7:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaeq(str2, str, z4, z3, arrayList, z2, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaeq[i];
    }
}
