package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzac implements Parcelable.Creator<MediaInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        ArrayList<AdBreakClipInfo> arrayList = null;
        ArrayList<AdBreakInfo> arrayList2 = null;
        String str = null;
        TextTrackStyle textTrackStyle = null;
        ArrayList<MediaTrack> arrayList3 = null;
        MediaMetadata mediaMetadata = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    mediaMetadata = (MediaMetadata) zzb.zza(parcel, readInt, MediaMetadata.CREATOR);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 7:
                    arrayList3 = zzb.zzc(parcel, readInt, MediaTrack.CREATOR);
                    break;
                case 8:
                    textTrackStyle = (TextTrackStyle) zzb.zza(parcel, readInt, TextTrackStyle.CREATOR);
                    break;
                case 9:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 10:
                    arrayList2 = zzb.zzc(parcel, readInt, AdBreakInfo.CREATOR);
                    break;
                case 11:
                    arrayList = zzb.zzc(parcel, readInt, AdBreakClipInfo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new MediaInfo(str3, i, str2, mediaMetadata, j, arrayList3, textTrackStyle, str, arrayList2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaInfo[i];
    }
}
