package com.google.android.gms.internal;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.ActivityRecognitionResult;

public final class zzaua implements Parcelable.Creator<zzati> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzaue zzaue = null;
        zzauh zzauh = null;
        zzatg zzatg = null;
        zzate zzate = null;
        DataHolder dataHolder = null;
        zzasz zzasz = null;
        Location location = null;
        zzasx zzasx = null;
        zzast zzast = null;
        ActivityRecognitionResult activityRecognitionResult = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    activityRecognitionResult = (ActivityRecognitionResult) zzb.zza(parcel, readInt, ActivityRecognitionResult.CREATOR);
                    break;
                case 3:
                    zzast = (zzast) zzb.zza(parcel, readInt, zzast.CREATOR);
                    break;
                case 4:
                    zzasx = (zzasx) zzb.zza(parcel, readInt, zzasx.CREATOR);
                    break;
                case 5:
                    location = (Location) zzb.zza(parcel, readInt, Location.CREATOR);
                    break;
                case 6:
                    zzasz = (zzasz) zzb.zza(parcel, readInt, zzasz.CREATOR);
                    break;
                case 7:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                case 8:
                    zzate = (zzate) zzb.zza(parcel, readInt, zzate.CREATOR);
                    break;
                case 9:
                    zzatg = (zzatg) zzb.zza(parcel, readInt, zzatg.CREATOR);
                    break;
                case 10:
                    zzauh = (zzauh) zzb.zza(parcel, readInt, zzauh.CREATOR);
                    break;
                case 11:
                    zzaue = (zzaue) zzb.zza(parcel, readInt, zzaue.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzati(activityRecognitionResult, zzast, zzasx, location, zzasz, dataHolder, zzate, zzatg, zzauh, zzaue);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzati[i];
    }
}
