package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;

public final class zzag implements Parcelable.Creator<MediaStatus> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        MediaInfo mediaInfo = null;
        long j = 0;
        int i = 0;
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        int i2 = 0;
        int i3 = 0;
        long j2 = 0;
        long j3 = 0;
        double d2 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        boolean z = false;
        long[] jArr = null;
        int i4 = 0;
        int i5 = 0;
        String str = null;
        int i6 = 0;
        ArrayList<MediaQueueItem> arrayList = null;
        boolean z2 = false;
        AdBreakStatus adBreakStatus = null;
        VideoInfo videoInfo = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    mediaInfo = (MediaInfo) zzb.zza(parcel, readInt, MediaInfo.CREATOR);
                    break;
                case 3:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    d = zzb.zzn(parcel, readInt);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 9:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 10:
                    d2 = zzb.zzn(parcel, readInt);
                    break;
                case 11:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 12:
                    jArr = zzb.zzx(parcel, readInt);
                    break;
                case 13:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 14:
                    i5 = zzb.zzg(parcel, readInt);
                    break;
                case 15:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 16:
                    i6 = zzb.zzg(parcel, readInt);
                    break;
                case 17:
                    arrayList = zzb.zzc(parcel, readInt, MediaQueueItem.CREATOR);
                    break;
                case 18:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 19:
                    adBreakStatus = (AdBreakStatus) zzb.zza(parcel, readInt, AdBreakStatus.CREATOR);
                    break;
                case 20:
                    videoInfo = (VideoInfo) zzb.zza(parcel, readInt, VideoInfo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new MediaStatus(mediaInfo, j, i, d, i2, i3, j2, j3, d2, z, jArr, i4, i5, str, i6, arrayList, z2, adBreakStatus, videoInfo);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaStatus[i];
    }
}
