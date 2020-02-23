package com.google.android.gms.cast.framework;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;

public final class zzb implements Parcelable.Creator<CastOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        boolean z = false;
        CastMediaOptions castMediaOptions = null;
        boolean z2 = false;
        LaunchOptions launchOptions = null;
        boolean z3 = false;
        ArrayList<String> arrayList = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    arrayList = com.google.android.gms.common.internal.safeparcel.zzb.zzC(parcel, readInt);
                    break;
                case 4:
                    z3 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    launchOptions = (LaunchOptions) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, LaunchOptions.CREATOR);
                    break;
                case 6:
                    z2 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    castMediaOptions = (CastMediaOptions) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, CastMediaOptions.CREATOR);
                    break;
                case 8:
                    z = com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    d = com.google.android.gms.common.internal.safeparcel.zzb.zzn(parcel, readInt);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new CastOptions(str, arrayList, z3, launchOptions, z2, castMediaOptions, z, d);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CastOptions[i];
    }
}
