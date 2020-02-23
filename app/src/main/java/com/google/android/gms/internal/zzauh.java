package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzauh extends zza implements Weather {
    public static final Parcelable.Creator<zzauh> CREATOR = new zzaui();
    private final float zzaot;
    private final float zzaou;
    private final float zzaov;
    private final int zzaow;
    private final int[] zzaox;

    public zzauh(float f, float f2, float f3, int i, int[] iArr) {
        this.zzaot = f;
        this.zzaou = f2;
        this.zzaov = f3;
        this.zzaow = i;
        this.zzaox = iArr;
    }

    private static float zza(int i, float f) {
        switch (i) {
            case 1:
                return f;
            case 2:
                return (5.0f * (f - 32.0f)) / 9.0f;
            default:
                zzeq.zza("WeatherImpl", "Invalid temperature unit %s", (Object) Integer.valueOf(i));
                throw new IllegalArgumentException("Invalid temperature unit");
        }
    }

    public final int[] getConditions() {
        return this.zzaox;
    }

    public final float getDewPoint(int i) {
        return zza(i, this.zzaov);
    }

    public final float getFeelsLikeTemperature(int i) {
        return zza(i, this.zzaou);
    }

    public final int getHumidity() {
        return this.zzaow;
    }

    public final float getTemperature(int i) {
        return zza(i, this.zzaot);
    }

    public final String toString() {
        boolean z = true;
        StringBuilder sb = new StringBuilder();
        sb.append("Temp=").append(getTemperature(1)).append("F/").append(getTemperature(2)).append("C, Feels=").append(getFeelsLikeTemperature(1)).append("F/").append(getFeelsLikeTemperature(2)).append("C, Dew=").append(getDewPoint(1)).append("F/").append(getDewPoint(2)).append("C, Humidity=").append(getHumidity()).append(", Condition=");
        if (getConditions() == null) {
            sb.append("unknown");
        } else {
            sb.append("[");
            int[] conditions = getConditions();
            int length = conditions.length;
            int i = 0;
            while (i < length) {
                int i2 = conditions[i];
                if (!z) {
                    sb.append(",");
                }
                sb.append(i2);
                i++;
                z = false;
            }
            sb.append("]");
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzaot);
        zzd.zza(parcel, 3, this.zzaou);
        zzd.zza(parcel, 4, this.zzaov);
        zzd.zzc(parcel, 5, getHumidity());
        zzd.zza(parcel, 6, getConditions(), false);
        zzd.zzI(parcel, zze);
    }
}
