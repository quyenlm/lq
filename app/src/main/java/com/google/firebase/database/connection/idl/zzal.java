package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzal implements Parcelable.Creator<zzak> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<String> arrayList = null;
        ArrayList<String> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList2 = zzb.zzC(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzak(arrayList2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzak[i];
    }
}
