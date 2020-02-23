package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.lj;

public class FacebookAuthCredential extends AuthCredential {
    public static final Parcelable.Creator<FacebookAuthCredential> CREATOR = new zze();
    private final String zzbCx;

    FacebookAuthCredential(@NonNull String str) {
        this.zzbCx = zzbo.zzcF(str);
    }

    public static lj zza(@NonNull FacebookAuthCredential facebookAuthCredential) {
        zzbo.zzu(facebookAuthCredential);
        return new lj((String) null, facebookAuthCredential.zzbCx, facebookAuthCredential.getProvider(), (String) null, (String) null);
    }

    public String getProvider() {
        return FacebookAuthProvider.PROVIDER_ID;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbCx, false);
        zzd.zzI(parcel, zze);
    }
}
