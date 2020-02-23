package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.vision.barcode.Barcode;

public final class zzd implements Parcelable.Creator<Barcode.CalendarDateTime> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    i6 = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    i5 = zzb.zzg(parcel, readInt);
                    break;
                case 4:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
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
        return new Barcode.CalendarDateTime(i6, i5, i4, i3, i2, i, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode.CalendarDateTime[i];
    }
}
