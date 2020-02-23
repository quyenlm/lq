package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzq implements Parcelable.Creator<MaskedWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        InstrumentInfo[] instrumentInfoArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        zza zza = null;
        zza zza2 = null;
        String str = null;
        String[] strArr = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 5:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    zza2 = (zza) zzb.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 7:
                    zza = (zza) zzb.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 8:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) zzb.zzb(parcel, readInt, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) zzb.zzb(parcel, readInt, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) zzb.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress = (UserAddress) zzb.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) zzb.zzb(parcel, readInt, InstrumentInfo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new MaskedWallet(str3, str2, strArr, str, zza2, zza, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress2, userAddress, instrumentInfoArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
