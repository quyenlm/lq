package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzg implements Parcelable.Creator<PlayerEntity> {
    public /* synthetic */ Object[] newArray(int i) {
        return new PlayerEntity[i];
    }

    /* renamed from: zzg */
    public PlayerEntity createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        Uri uri = null;
        Uri uri2 = null;
        long j = 0;
        int i = 0;
        long j2 = 0;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        com.google.android.gms.games.internal.player.zzb zzb = null;
        PlayerLevelInfo playerLevelInfo = null;
        boolean z = false;
        boolean z2 = false;
        String str6 = null;
        String str7 = null;
        Uri uri3 = null;
        String str8 = null;
        Uri uri4 = null;
        String str9 = null;
        int i2 = 0;
        long j3 = 0;
        boolean z3 = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 4:
                    uri2 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 5:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 6:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 14:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 15:
                    zzb = (com.google.android.gms.games.internal.player.zzb) zzb.zza(parcel, readInt, com.google.android.gms.games.internal.player.zzb.CREATOR);
                    break;
                case 16:
                    playerLevelInfo = (PlayerLevelInfo) zzb.zza(parcel, readInt, PlayerLevelInfo.CREATOR);
                    break;
                case 18:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 19:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 20:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 21:
                    str7 = zzb.zzq(parcel, readInt);
                    break;
                case 22:
                    uri3 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 23:
                    str8 = zzb.zzq(parcel, readInt);
                    break;
                case 24:
                    uri4 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 25:
                    str9 = zzb.zzq(parcel, readInt);
                    break;
                case 26:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 27:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 28:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PlayerEntity(str, str2, uri, uri2, j, i, j2, str3, str4, str5, zzb, playerLevelInfo, z, z2, str6, str7, uri3, str8, uri4, str9, i2, j3, z3);
    }
}
