package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzc implements Parcelable.Creator<CredentialPickerConfig> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 2:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new CredentialPickerConfig(i2, z3, z2, z, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CredentialPickerConfig[i];
    }
}
