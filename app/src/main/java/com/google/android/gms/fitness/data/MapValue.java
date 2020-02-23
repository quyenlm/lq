package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;

public class MapValue extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<MapValue> CREATOR = new zzw();
    private final int zzaUT;
    private final float zzaUU;
    private final int zzaku;

    public MapValue(int i, float f) {
        this(1, 2, f);
    }

    MapValue(int i, int i2, float f) {
        this.zzaku = i;
        this.zzaUT = i2;
        this.zzaUU = f;
    }

    public final float asFloat() {
        zzbo.zza(this.zzaUT == 2, (Object) "Value is not in float format");
        return this.zzaUU;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (!(obj instanceof MapValue)) {
                return false;
            }
            MapValue mapValue = (MapValue) obj;
            if (this.zzaUT == mapValue.zzaUT) {
                switch (this.zzaUT) {
                    case 2:
                        if (asFloat() != mapValue.asFloat()) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    default:
                        if (this.zzaUU != mapValue.zzaUU) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                }
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return (int) this.zzaUU;
    }

    public String toString() {
        switch (this.zzaUT) {
            case 2:
                return Float.toString(asFloat());
            default:
                return "unknown";
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaUT);
        zzd.zza(parcel, 2, this.zzaUU);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
