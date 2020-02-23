package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.internal.zzcri;
import java.util.ArrayList;
import java.util.HashSet;

public final class zzcrj implements Parcelable.Creator<zzcri> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str = null;
        zzcri.zza zza = null;
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        zzcri.zzb zzb = null;
        String str4 = null;
        String str5 = null;
        int i3 = 0;
        String str6 = null;
        zzcri.zzc zzc = null;
        boolean z = false;
        String str7 = null;
        zzcri.zzd zzd2 = null;
        String str8 = null;
        int i4 = 0;
        ArrayList<zzcri.zze> arrayList = null;
        ArrayList<zzcri.zzf> arrayList2 = null;
        int i5 = 0;
        int i6 = 0;
        String str9 = null;
        String str10 = null;
        ArrayList<zzcri.zzg> arrayList3 = null;
        boolean z2 = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    hashSet.add(1);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    hashSet.add(2);
                    break;
                case 3:
                    hashSet.add(3);
                    zza = (zzcri.zza) zzb.zza(parcel, readInt, zzcri.zza.CREATOR);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    hashSet.add(4);
                    break;
                case 5:
                    str3 = zzb.zzq(parcel, readInt);
                    hashSet.add(5);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    hashSet.add(6);
                    break;
                case 7:
                    hashSet.add(7);
                    zzb = (zzcri.zzb) zzb.zza(parcel, readInt, zzcri.zzb.CREATOR);
                    break;
                case 8:
                    str4 = zzb.zzq(parcel, readInt);
                    hashSet.add(8);
                    break;
                case 9:
                    str5 = zzb.zzq(parcel, readInt);
                    hashSet.add(9);
                    break;
                case 12:
                    i3 = zzb.zzg(parcel, readInt);
                    hashSet.add(12);
                    break;
                case 14:
                    str6 = zzb.zzq(parcel, readInt);
                    hashSet.add(14);
                    break;
                case 15:
                    hashSet.add(15);
                    zzc = (zzcri.zzc) zzb.zza(parcel, readInt, zzcri.zzc.CREATOR);
                    break;
                case 16:
                    z = zzb.zzc(parcel, readInt);
                    hashSet.add(16);
                    break;
                case 18:
                    str7 = zzb.zzq(parcel, readInt);
                    hashSet.add(18);
                    break;
                case 19:
                    hashSet.add(19);
                    zzd2 = (zzcri.zzd) zzb.zza(parcel, readInt, zzcri.zzd.CREATOR);
                    break;
                case 20:
                    str8 = zzb.zzq(parcel, readInt);
                    hashSet.add(20);
                    break;
                case 21:
                    i4 = zzb.zzg(parcel, readInt);
                    hashSet.add(21);
                    break;
                case 22:
                    arrayList = zzb.zzc(parcel, readInt, zzcri.zze.CREATOR);
                    hashSet.add(22);
                    break;
                case 23:
                    arrayList2 = zzb.zzc(parcel, readInt, zzcri.zzf.CREATOR);
                    hashSet.add(23);
                    break;
                case 24:
                    i5 = zzb.zzg(parcel, readInt);
                    hashSet.add(24);
                    break;
                case 25:
                    i6 = zzb.zzg(parcel, readInt);
                    hashSet.add(25);
                    break;
                case 26:
                    str9 = zzb.zzq(parcel, readInt);
                    hashSet.add(26);
                    break;
                case 27:
                    str10 = zzb.zzq(parcel, readInt);
                    hashSet.add(27);
                    break;
                case 28:
                    arrayList3 = zzb.zzc(parcel, readInt, zzcri.zzg.CREATOR);
                    hashSet.add(28);
                    break;
                case 29:
                    z2 = zzb.zzc(parcel, readInt);
                    hashSet.add(29);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzcri(hashSet, i, str, zza, str2, str3, i2, zzb, str4, str5, i3, str6, zzc, z, str7, zzd2, str8, i4, arrayList, arrayList2, i5, i6, str9, str10, arrayList3, z2);
        }
        throw new zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcri[i];
    }
}
