package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.zze;
import com.google.android.gms.wallet.wobs.zzg;
import com.google.android.gms.wallet.wobs.zzk;
import com.google.android.gms.wallet.wobs.zzm;
import com.google.android.gms.wallet.wobs.zzq;
import java.util.ArrayList;

public final class zzo implements Parcelable.Creator<LoyaltyWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        int i = 0;
        ArrayList<zzq> arrayList = new ArrayList<>();
        zzm zzm = null;
        ArrayList<LatLng> arrayList2 = new ArrayList<>();
        String str11 = null;
        String str12 = null;
        ArrayList<zze> arrayList3 = new ArrayList<>();
        boolean z = false;
        ArrayList<com.google.android.gms.wallet.wobs.zzo> arrayList4 = new ArrayList<>();
        ArrayList<zzk> arrayList5 = new ArrayList<>();
        ArrayList<com.google.android.gms.wallet.wobs.zzo> arrayList6 = new ArrayList<>();
        zzg zzg = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    str7 = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str8 = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    str9 = zzb.zzq(parcel, readInt);
                    break;
                case 11:
                    str10 = zzb.zzq(parcel, readInt);
                    break;
                case 12:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 13:
                    arrayList = zzb.zzc(parcel, readInt, zzq.CREATOR);
                    break;
                case 14:
                    zzm = (zzm) zzb.zza(parcel, readInt, zzm.CREATOR);
                    break;
                case 15:
                    arrayList2 = zzb.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 16:
                    str11 = zzb.zzq(parcel, readInt);
                    break;
                case 17:
                    str12 = zzb.zzq(parcel, readInt);
                    break;
                case 18:
                    arrayList3 = zzb.zzc(parcel, readInt, zze.CREATOR);
                    break;
                case 19:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 20:
                    arrayList4 = zzb.zzc(parcel, readInt, com.google.android.gms.wallet.wobs.zzo.CREATOR);
                    break;
                case 21:
                    arrayList5 = zzb.zzc(parcel, readInt, zzk.CREATOR);
                    break;
                case 22:
                    arrayList6 = zzb.zzc(parcel, readInt, com.google.android.gms.wallet.wobs.zzo.CREATOR);
                    break;
                case 23:
                    zzg = (zzg) zzb.zza(parcel, readInt, zzg.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new LoyaltyWalletObject(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, i, arrayList, zzm, arrayList2, str11, str12, arrayList3, z, arrayList4, arrayList5, arrayList6, zzg);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LoyaltyWalletObject[i];
    }
}
