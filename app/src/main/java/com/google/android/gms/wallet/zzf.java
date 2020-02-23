package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzf implements Parcelable.Creator<FullWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        PaymentMethodToken paymentMethodToken = null;
        InstrumentInfo[] instrumentInfoArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] strArr = null;
        zza zza = null;
        zza zza2 = null;
        String str = null;
        ProxyCard proxyCard = null;
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
                    proxyCard = (ProxyCard) zzb.zza(parcel, readInt, ProxyCard.CREATOR);
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
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 9:
                    userAddress2 = (UserAddress) zzb.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) zzb.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) zzb.zzb(parcel, readInt, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) zzb.zza(parcel, readInt, PaymentMethodToken.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new FullWallet(str3, str2, proxyCard, str, zza2, zza, strArr, userAddress2, userAddress, instrumentInfoArr, paymentMethodToken);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FullWallet[i];
    }
}
