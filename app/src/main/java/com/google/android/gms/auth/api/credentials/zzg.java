package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzg implements Parcelable.Creator<HintRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        String[] strArr = null;
        boolean z2 = false;
        boolean z3 = false;
        CredentialPickerConfig credentialPickerConfig = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    credentialPickerConfig = (CredentialPickerConfig) zzb.zza(parcel, readInt, CredentialPickerConfig.CREATOR);
                    break;
                case 2:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 3:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new HintRequest(i, credentialPickerConfig, z3, z2, strArr, z, str2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new HintRequest[i];
    }
}
