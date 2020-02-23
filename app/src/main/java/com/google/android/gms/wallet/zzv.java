package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

public final class zzv implements Parcelable.Creator<OfferWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        CommonWalletObject commonWalletObject = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    commonWalletObject = (CommonWalletObject) zzb.zza(parcel, readInt, CommonWalletObject.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new OfferWalletObject(i, str2, str, commonWalletObject);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new OfferWalletObject[i];
    }
}
