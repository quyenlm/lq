package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzaag implements Parcelable.Creator<zzaae> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        Bundle bundle = null;
        zzir zzir = null;
        zziv zziv = null;
        String str = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        zzaje zzaje = null;
        Bundle bundle2 = null;
        int i2 = 0;
        ArrayList<String> arrayList = null;
        Bundle bundle3 = null;
        boolean z = false;
        int i3 = 0;
        int i4 = 0;
        float f = 0.0f;
        String str5 = null;
        long j = 0;
        String str6 = null;
        ArrayList<String> arrayList2 = null;
        String str7 = null;
        zzon zzon = null;
        ArrayList<String> arrayList3 = null;
        long j2 = 0;
        String str8 = null;
        float f2 = 0.0f;
        boolean z2 = false;
        int i5 = 0;
        int i6 = 0;
        boolean z3 = false;
        boolean z4 = false;
        String str9 = null;
        String str10 = null;
        boolean z5 = false;
        int i7 = 0;
        Bundle bundle4 = null;
        String str11 = null;
        zzky zzky = null;
        boolean z6 = false;
        Bundle bundle5 = null;
        String str12 = null;
        String str13 = null;
        String str14 = null;
        boolean z7 = false;
        ArrayList<Integer> arrayList4 = null;
        String str15 = null;
        ArrayList<String> arrayList5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 3:
                    zzir = (zzir) zzb.zza(parcel, readInt, zzir.CREATOR);
                    break;
                case 4:
                    zziv = (zziv) zzb.zza(parcel, readInt, zziv.CREATOR);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) zzb.zza(parcel, readInt, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) zzb.zza(parcel, readInt, PackageInfo.CREATOR);
                    break;
                case 8:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 11:
                    zzaje = (zzaje) zzb.zza(parcel, readInt, zzaje.CREATOR);
                    break;
                case 12:
                    bundle2 = zzb.zzs(parcel, readInt);
                    break;
                case 13:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 14:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 15:
                    bundle3 = zzb.zzs(parcel, readInt);
                    break;
                case 16:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 18:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 19:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 20:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 21:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 25:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 26:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 27:
                    arrayList2 = zzb.zzC(parcel, readInt);
                    break;
                case 28:
                    str7 = zzb.zzq(parcel, readInt);
                    break;
                case 29:
                    zzon = (zzon) zzb.zza(parcel, readInt, zzon.CREATOR);
                    break;
                case 30:
                    arrayList3 = zzb.zzC(parcel, readInt);
                    break;
                case 31:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 33:
                    str8 = zzb.zzq(parcel, readInt);
                    break;
                case 34:
                    f2 = zzb.zzl(parcel, readInt);
                    break;
                case 35:
                    i5 = zzb.zzg(parcel, readInt);
                    break;
                case 36:
                    i6 = zzb.zzg(parcel, readInt);
                    break;
                case 37:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 38:
                    z4 = zzb.zzc(parcel, readInt);
                    break;
                case 39:
                    str9 = zzb.zzq(parcel, readInt);
                    break;
                case 40:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 41:
                    str10 = zzb.zzq(parcel, readInt);
                    break;
                case 42:
                    z5 = zzb.zzc(parcel, readInt);
                    break;
                case 43:
                    i7 = zzb.zzg(parcel, readInt);
                    break;
                case 44:
                    bundle4 = zzb.zzs(parcel, readInt);
                    break;
                case 45:
                    str11 = zzb.zzq(parcel, readInt);
                    break;
                case 46:
                    zzky = (zzky) zzb.zza(parcel, readInt, zzky.CREATOR);
                    break;
                case 47:
                    z6 = zzb.zzc(parcel, readInt);
                    break;
                case 48:
                    bundle5 = zzb.zzs(parcel, readInt);
                    break;
                case 49:
                    str12 = zzb.zzq(parcel, readInt);
                    break;
                case 50:
                    str13 = zzb.zzq(parcel, readInt);
                    break;
                case 51:
                    str14 = zzb.zzq(parcel, readInt);
                    break;
                case 52:
                    z7 = zzb.zzc(parcel, readInt);
                    break;
                case 53:
                    arrayList4 = zzb.zzB(parcel, readInt);
                    break;
                case 54:
                    str15 = zzb.zzq(parcel, readInt);
                    break;
                case 55:
                    arrayList5 = zzb.zzC(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaae(i, bundle, zzir, zziv, str, applicationInfo, packageInfo, str2, str3, str4, zzaje, bundle2, i2, arrayList, bundle3, z, i3, i4, f, str5, j, str6, arrayList2, str7, zzon, arrayList3, j2, str8, f2, z2, i5, i6, z3, z4, str9, str10, z5, i7, bundle4, str11, zzky, z6, bundle5, str12, str13, str14, z7, arrayList4, str15, arrayList5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaae[i];
    }
}
