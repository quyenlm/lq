package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public final class zzb implements Parcelable.Creator<CommonWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        int i = 0;
        ArrayList<zzq> arrayList = new ArrayList<>();
        zzm zzm = null;
        ArrayList<LatLng> arrayList2 = new ArrayList<>();
        String str9 = null;
        String str10 = null;
        ArrayList<zze> arrayList3 = new ArrayList<>();
        boolean z = false;
        ArrayList<zzo> arrayList4 = new ArrayList<>();
        ArrayList<zzk> arrayList5 = new ArrayList<>();
        ArrayList<zzo> arrayList6 = new ArrayList<>();
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str5 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str6 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    str7 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str8 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zzq.CREATOR);
                    break;
                case 12:
                    zzm = (zzm) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, zzm.CREATOR);
                    break;
                case 13:
                    arrayList2 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 14:
                    str9 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 15:
                    str10 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 16:
                    arrayList3 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zze.CREATOR);
                    break;
                case 17:
                    z = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                case 18:
                    arrayList4 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zzo.CREATOR);
                    break;
                case 19:
                    arrayList5 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zzk.CREATOR);
                    break;
                case 20:
                    arrayList6 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt, zzo.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new CommonWalletObject(str, str2, str3, str4, str5, str6, str7, str8, i, arrayList, zzm, arrayList2, str9, str10, arrayList3, z, arrayList4, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CommonWalletObject[i];
    }
}
