package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzi implements Parcelable.Creator<PasswordSpecification> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        int i2 = 0;
        ArrayList<Integer> arrayList = null;
        ArrayList<String> arrayList2 = null;
        String str = null;
        int i3 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    arrayList2 = zzb.zzC(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzb.zzB(parcel, readInt);
                    break;
                case 4:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 1000:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PasswordSpecification(i3, str, arrayList2, arrayList, i2, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PasswordSpecification[i];
    }
}
