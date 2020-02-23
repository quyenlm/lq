package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.internal.zzn;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzd implements Parcelable.Creator<GoogleSignInOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzn> arrayList = null;
        String str = null;
        String str2 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        Account account = null;
        ArrayList<Scope> arrayList2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, readInt, Scope.CREATOR);
                    break;
                case 3:
                    account = (Account) zzb.zza(parcel, readInt, Account.CREATOR);
                    break;
                case 4:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 9:
                    arrayList = zzb.zzc(parcel, readInt, zzn.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new GoogleSignInOptions(i, arrayList2, account, z3, z2, z, str2, str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleSignInOptions[i];
    }
}
