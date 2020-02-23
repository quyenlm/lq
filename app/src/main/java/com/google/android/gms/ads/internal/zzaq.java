package com.google.android.gms.ads.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzaq implements Parcelable.Creator<zzap> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        float f = 0.0f;
        boolean z = false;
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    z4 = zzb.zzc(parcel, readInt);
                    break;
                case 3:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzap(z4, z3, str, z2, f, i, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzap[i];
    }
}
