package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public final class zzb implements Parcelable.Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        Bundle bundle = null;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    f7 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 2:
                    f6 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 3:
                    i3 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    i2 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    f5 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 7:
                    f4 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 8:
                    bundle = com.google.android.gms.common.internal.safeparcel.zzb.zzs(parcel, readInt);
                    break;
                case 9:
                    f3 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 10:
                    f2 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                case 11:
                    f = com.google.android.gms.common.internal.safeparcel.zzb.zzl(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new zza(f7, f6, i3, i2, i, f5, f4, bundle, f3, f2, f);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
