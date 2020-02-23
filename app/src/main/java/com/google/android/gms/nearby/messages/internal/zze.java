package com.google.android.gms.nearby.messages.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.nearby.messages.Distance;
import java.util.Arrays;
import java.util.Locale;

public final class zze extends zza implements Distance {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    private int accuracy;
    private int zzaku;
    private double zzbyZ;

    public zze(int i, double d) {
        this(1, 1, Double.NaN);
    }

    zze(int i, int i2, double d) {
        this.zzaku = i;
        this.accuracy = i2;
        this.zzbyZ = d;
    }

    public final int compareTo(@NonNull Distance distance) {
        if (!Double.isNaN(getMeters()) || !Double.isNaN(distance.getMeters())) {
            return Double.compare(getMeters(), distance.getMeters());
        }
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zze)) {
            return false;
        }
        zze zze = (zze) obj;
        return getAccuracy() == zze.getAccuracy() && compareTo((Distance) zze) == 0;
    }

    public final int getAccuracy() {
        return this.accuracy;
    }

    public final double getMeters() {
        return this.zzbyZ;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(getAccuracy()), Double.valueOf(getMeters())});
    }

    public final String toString() {
        String str;
        Locale locale = Locale.US;
        Object[] objArr = new Object[2];
        objArr[0] = Double.valueOf(this.zzbyZ);
        switch (this.accuracy) {
            case 1:
                str = "LOW";
                break;
            default:
                str = "UNKNOWN";
                break;
        }
        objArr[1] = str;
        return String.format(locale, "(%.1fm, %s)", objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        zzd.zzc(parcel, 2, this.accuracy);
        zzd.zza(parcel, 3, this.zzbyZ);
        zzd.zzI(parcel, zze);
    }
}
