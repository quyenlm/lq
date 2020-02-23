package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.banalytics.BATrackerConst;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.tencent.component.debug.FileTracerConfig;

public final class zzq implements Parcelable.Creator<LocationRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 102;
        long j = 3600000;
        long j2 = BATrackerConst.TRACKER_WAKE_UP_INTERVAL;
        boolean z = false;
        long j3 = FileTracerConfig.FOREVER;
        int i2 = Integer.MAX_VALUE;
        float f = 0.0f;
        long j4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    j = zzb.zzi(parcel, readInt);
                    break;
                case 3:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    j3 = zzb.zzi(parcel, readInt);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 8:
                    j4 = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new LocationRequest(i, j, j2, z, j3, i2, f, j4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LocationRequest[i];
    }
}
