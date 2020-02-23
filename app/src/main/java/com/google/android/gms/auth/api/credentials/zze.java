package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zze implements Parcelable.Creator<CredentialRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        CredentialPickerConfig credentialPickerConfig = null;
        CredentialPickerConfig credentialPickerConfig2 = null;
        String[] strArr = null;
        boolean z2 = false;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 2:
                    strArr = zzb.zzA(parcel, readInt);
                    break;
                case 3:
                    credentialPickerConfig2 = (CredentialPickerConfig) zzb.zza(parcel, readInt, CredentialPickerConfig.CREATOR);
                    break;
                case 4:
                    credentialPickerConfig = (CredentialPickerConfig) zzb.zza(parcel, readInt, CredentialPickerConfig.CREATOR);
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
        return new CredentialRequest(i, z2, strArr, credentialPickerConfig2, credentialPickerConfig, z, str2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CredentialRequest[i];
    }
}
