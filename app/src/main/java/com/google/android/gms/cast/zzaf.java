package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzaf implements Parcelable.Creator<MediaQueueItem> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        MediaInfo mediaInfo = null;
        int i = 0;
        boolean z = false;
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        double d2 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        double d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        long[] jArr = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    mediaInfo = (MediaInfo) zzb.zza(parcel, readInt, MediaInfo.CREATOR);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    d = zzb.zzn(parcel, readInt);
                    break;
                case 6:
                    d2 = zzb.zzn(parcel, readInt);
                    break;
                case 7:
                    d3 = zzb.zzn(parcel, readInt);
                    break;
                case 8:
                    jArr = zzb.zzx(parcel, readInt);
                    break;
                case 9:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new MediaQueueItem(mediaInfo, i, z, d, d2, d3, jArr, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaQueueItem[i];
    }
}
