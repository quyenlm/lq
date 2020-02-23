package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.vision.barcode.Barcode;

public final class zzf implements Parcelable.Creator<Barcode.ContactInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Barcode.Address[] addressArr = null;
        String[] strArr = null;
        Barcode.Email[] emailArr = null;
        Barcode.Phone[] phoneArr = null;
        String str = null;
        String str2 = null;
        Barcode.PersonName personName = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    personName = (Barcode.PersonName) zzb.zza(parcel, readInt, Barcode.PersonName.CREATOR);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    phoneArr = (Barcode.Phone[]) zzb.zzb(parcel, readInt, Barcode.Phone.CREATOR);
                    break;
                case 6:
                    emailArr = (Barcode.Email[]) zzb.zzb(parcel, readInt, Barcode.Email.CREATOR);
                    break;
                case 7:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 8:
                    addressArr = (Barcode.Address[]) zzb.zzb(parcel, readInt, Barcode.Address.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Barcode.ContactInfo(personName, str2, str, phoneArr, emailArr, strArr, addressArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Barcode.ContactInfo[i];
    }
}
