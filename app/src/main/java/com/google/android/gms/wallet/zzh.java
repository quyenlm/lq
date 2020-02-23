package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh implements Parcelable.Creator<FullWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Cart cart = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    cart = (Cart) zzb.zza(parcel, readInt, Cart.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new FullWalletRequest(str2, str, cart);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FullWalletRequest[i];
    }
}
