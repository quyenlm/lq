package com.google.firebase.database.connection.idl;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public final class zzb implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        ArrayList<String> arrayList = null;
        ArrayList<String> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList2 = com.google.android.gms.common.internal.safeparcel.zzb.zzC(parcel, readInt);
                    break;
                case 3:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zzb.zzC(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new zza(arrayList2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
