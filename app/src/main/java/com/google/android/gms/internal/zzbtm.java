package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzbtm implements Parcelable.Creator<zzbtl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        ArrayList<String> arrayList = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str3 = null;
        String str4 = null;
        zzbtt zzbtt = null;
        zzbtr zzbtr = null;
        zzbtx zzbtx = null;
        zzbtz zzbtz = null;
        zzbub zzbub = null;
        zzbtv zzbtv = null;
        zzbtp zzbtp = null;
        zzbtj zzbtj = null;
        zzbth zzbth = null;
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
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    zzbtt = (zzbtt) zzb.zza(parcel, readInt, zzbtt.CREATOR);
                    break;
                case 9:
                    zzbtr = (zzbtr) zzb.zza(parcel, readInt, zzbtr.CREATOR);
                    break;
                case 10:
                    zzbtx = (zzbtx) zzb.zza(parcel, readInt, zzbtx.CREATOR);
                    break;
                case 11:
                    zzbtz = (zzbtz) zzb.zza(parcel, readInt, zzbtz.CREATOR);
                    break;
                case 12:
                    zzbub = (zzbub) zzb.zza(parcel, readInt, zzbub.CREATOR);
                    break;
                case 13:
                    zzbtv = (zzbtv) zzb.zza(parcel, readInt, zzbtv.CREATOR);
                    break;
                case 14:
                    zzbtp = (zzbtp) zzb.zza(parcel, readInt, zzbtp.CREATOR);
                    break;
                case 15:
                    zzbtj = (zzbtj) zzb.zza(parcel, readInt, zzbtj.CREATOR);
                    break;
                case 16:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 17:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 18:
                    zzbth = (zzbth) zzb.zza(parcel, readInt, zzbth.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbtl(str, str2, arrayList, z, z2, z3, str3, str4, zzbtt, zzbtr, zzbtx, zzbtz, zzbub, zzbtv, zzbtp, zzbtj, zzbth);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbtl[i];
    }
}
