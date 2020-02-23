package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public final class zzcdr extends zza implements Geofence {
    public static final Parcelable.Creator<zzcdr> CREATOR = new zzcds();
    private final String zzQx;
    private final int zzbhF;
    private final short zzbhH;
    private final double zzbhI;
    private final double zzbhJ;
    private final float zzbhK;
    private final int zzbhL;
    private final int zzbhM;
    private final long zzbjf;

    public zzcdr(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        if (str == null || str.length() > 100) {
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "requestId is null or too long: ".concat(valueOf) : new String("requestId is null or too long: "));
        } else if (f <= 0.0f) {
            throw new IllegalArgumentException(new StringBuilder(31).append("invalid radius: ").append(f).toString());
        } else if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException(new StringBuilder(42).append("invalid latitude: ").append(d).toString());
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException(new StringBuilder(43).append("invalid longitude: ").append(d2).toString());
        } else {
            int i4 = i & 7;
            if (i4 == 0) {
                throw new IllegalArgumentException(new StringBuilder(46).append("No supported transition specified: ").append(i).toString());
            }
            this.zzbhH = s;
            this.zzQx = str;
            this.zzbhI = d;
            this.zzbhJ = d2;
            this.zzbhK = f;
            this.zzbjf = j;
            this.zzbhF = i4;
            this.zzbhL = i2;
            this.zzbhM = i3;
        }
    }

    public static zzcdr zzk(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        zzcdr createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return createFromParcel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzcdr)) {
            return false;
        }
        zzcdr zzcdr = (zzcdr) obj;
        if (this.zzbhK != zzcdr.zzbhK) {
            return false;
        }
        if (this.zzbhI != zzcdr.zzbhI) {
            return false;
        }
        if (this.zzbhJ != zzcdr.zzbhJ) {
            return false;
        }
        return this.zzbhH == zzcdr.zzbhH;
    }

    public final String getRequestId() {
        return this.zzQx;
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzbhI);
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzbhJ);
        return ((((((((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzbhK)) * 31) + this.zzbhH) * 31) + this.zzbhF;
    }

    public final String toString() {
        String str;
        Locale locale = Locale.US;
        Object[] objArr = new Object[9];
        switch (this.zzbhH) {
            case 1:
                str = "CIRCLE";
                break;
            default:
                str = null;
                break;
        }
        objArr[0] = str;
        objArr[1] = this.zzQx;
        objArr[2] = Integer.valueOf(this.zzbhF);
        objArr[3] = Double.valueOf(this.zzbhI);
        objArr[4] = Double.valueOf(this.zzbhJ);
        objArr[5] = Float.valueOf(this.zzbhK);
        objArr[6] = Integer.valueOf(this.zzbhL / 1000);
        objArr[7] = Integer.valueOf(this.zzbhM);
        objArr[8] = Long.valueOf(this.zzbjf);
        return String.format(locale, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, getRequestId(), false);
        zzd.zza(parcel, 2, this.zzbjf);
        zzd.zza(parcel, 3, this.zzbhH);
        zzd.zza(parcel, 4, this.zzbhI);
        zzd.zza(parcel, 5, this.zzbhJ);
        zzd.zza(parcel, 6, this.zzbhK);
        zzd.zzc(parcel, 7, this.zzbhF);
        zzd.zzc(parcel, 8, this.zzbhL);
        zzd.zzc(parcel, 9, this.zzbhM);
        zzd.zzI(parcel, zze);
    }
}
