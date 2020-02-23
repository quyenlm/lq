package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.vision.barcode.Barcode;

public final class zze implements Parcelable.Creator<Barcode.CalendarEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Barcode.CalendarDateTime calendarDateTime = null;
        Barcode.CalendarDateTime calendarDateTime2 = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    calendarDateTime2 = (Barcode.CalendarDateTime) zzb.zza(parcel, readInt, Barcode.CalendarDateTime.CREATOR);
                    break;
                case 8:
                    calendarDateTime = (Barcode.CalendarDateTime) zzb.zza(parcel, readInt, Barcode.CalendarDateTime.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Barcode.CalendarEvent(str5, str4, str3, str2, str, calendarDateTime2, calendarDateTime);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode.CalendarEvent[i];
    }
}
