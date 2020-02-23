package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.FileUploadPreferences;

public final class zzbog extends zza implements FileUploadPreferences {
    public static final Parcelable.Creator<zzbog> CREATOR = new zzboh();
    private int zzaOL;
    private int zzaOM;
    private boolean zzaON;

    public zzbog(int i, int i2, boolean z) {
        this.zzaOL = i;
        this.zzaOM = i2;
        this.zzaON = z;
    }

    private static boolean zzaP(int i) {
        switch (i) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    private static boolean zzaQ(int i) {
        switch (i) {
            case 256:
            case 257:
                return true;
            default:
                return false;
        }
    }

    public final int getBatteryUsagePreference() {
        if (!zzaQ(this.zzaOM)) {
            return 0;
        }
        return this.zzaOM;
    }

    public final int getNetworkTypePreference() {
        if (!zzaP(this.zzaOL)) {
            return 0;
        }
        return this.zzaOL;
    }

    public final boolean isRoamingAllowed() {
        return this.zzaON;
    }

    public final void setBatteryUsagePreference(int i) {
        if (!zzaQ(i)) {
            throw new IllegalArgumentException("Invalid battery usage preference value.");
        }
        this.zzaOM = i;
    }

    public final void setNetworkTypePreference(int i) {
        if (!zzaP(i)) {
            throw new IllegalArgumentException("Invalid data connection preference value.");
        }
        this.zzaOL = i;
    }

    public final void setRoamingAllowed(boolean z) {
        this.zzaON = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaOL);
        zzd.zzc(parcel, 3, this.zzaOM);
        zzd.zza(parcel, 4, this.zzaON);
        zzd.zzI(parcel, zze);
    }
}
