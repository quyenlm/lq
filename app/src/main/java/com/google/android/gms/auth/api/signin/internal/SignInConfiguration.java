package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;

public final class SignInConfiguration extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<SignInConfiguration> CREATOR = new zzw();
    private int versionCode;
    private final String zzamu;
    private GoogleSignInOptions zzamv;

    SignInConfiguration(int i, String str, GoogleSignInOptions googleSignInOptions) {
        this.versionCode = i;
        this.zzamu = zzbo.zzcF(str);
        this.zzamv = googleSignInOptions;
    }

    public SignInConfiguration(String str, GoogleSignInOptions googleSignInOptions) {
        this(3, str, googleSignInOptions);
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            SignInConfiguration signInConfiguration = (SignInConfiguration) obj;
            if (!this.zzamu.equals(signInConfiguration.zzamu)) {
                return false;
            }
            if (this.zzamv == null) {
                if (signInConfiguration.zzamv != null) {
                    return false;
                }
            } else if (!this.zzamv.equals(signInConfiguration.zzamv)) {
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public final int hashCode() {
        return new zzo().zzo(this.zzamu).zzo(this.zzamv).zzmJ();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.versionCode);
        zzd.zza(parcel, 2, this.zzamu, false);
        zzd.zza(parcel, 5, (Parcelable) this.zzamv, i, false);
        zzd.zzI(parcel, zze);
    }

    public final GoogleSignInOptions zzmL() {
        return this.zzamv;
    }
}
