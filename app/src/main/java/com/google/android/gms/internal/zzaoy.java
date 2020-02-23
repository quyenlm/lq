package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzaoy implements Parcelable.Creator<zzaow> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        Account account = null;
        String str = null;
        zzapb[] zzapbArr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzapbArr = (zzapb[]) zzb.zzb(parcel, readInt, zzapb.CREATOR);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    account = (Account) zzb.zza(parcel, readInt, Account.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaow(zzapbArr, str, z, account);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaow[i];
    }
}
