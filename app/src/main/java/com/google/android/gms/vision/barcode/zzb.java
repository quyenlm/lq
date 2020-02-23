package com.google.android.gms.vision.barcode;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.vision.barcode.Barcode;

public final class zzb implements Parcelable.Creator<Barcode> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = com.google.android.gms.common.internal.safeparcel.zzb.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        int i2 = 0;
        Point[] pointArr = null;
        Barcode.Email email = null;
        Barcode.Phone phone = null;
        Barcode.Sms sms = null;
        Barcode.WiFi wiFi = null;
        Barcode.UrlBookmark urlBookmark = null;
        Barcode.GeoPoint geoPoint = null;
        Barcode.CalendarEvent calendarEvent = null;
        Barcode.ContactInfo contactInfo = null;
        Barcode.DriverLicense driverLicense = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    i = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    str = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str2 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    i2 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    pointArr = (Point[]) com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt, Point.CREATOR);
                    break;
                case 7:
                    email = (Barcode.Email) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.Email.CREATOR);
                    break;
                case 8:
                    phone = (Barcode.Phone) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.Phone.CREATOR);
                    break;
                case 9:
                    sms = (Barcode.Sms) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.Sms.CREATOR);
                    break;
                case 10:
                    wiFi = (Barcode.WiFi) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.WiFi.CREATOR);
                    break;
                case 11:
                    urlBookmark = (Barcode.UrlBookmark) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.UrlBookmark.CREATOR);
                    break;
                case 12:
                    geoPoint = (Barcode.GeoPoint) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.GeoPoint.CREATOR);
                    break;
                case 13:
                    calendarEvent = (Barcode.CalendarEvent) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.CalendarEvent.CREATOR);
                    break;
                case 14:
                    contactInfo = (Barcode.ContactInfo) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.ContactInfo.CREATOR);
                    break;
                case 15:
                    driverLicense = (Barcode.DriverLicense) com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, readInt, Barcode.DriverLicense.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, readInt);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.zzb.zzF(parcel, zzd);
        return new Barcode(i, str, str2, i2, pointArr, email, phone, sms, wiFi, urlBookmark, geoPoint, calendarEvent, contactInfo, driverLicense);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode[i];
    }
}
