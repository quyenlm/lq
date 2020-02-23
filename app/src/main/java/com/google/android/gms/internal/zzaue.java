package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.awareness.state.TimeIntervals;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzaue extends zza implements TimeIntervals {
    public static final Parcelable.Creator<zzaue> CREATOR = new zzauf();
    private final int[] zzaos;

    public zzaue(int[] iArr) {
        this.zzaos = iArr;
    }

    public final int[] getTimeIntervals() {
        return this.zzaos;
    }

    public final boolean hasTimeInterval(int i) {
        if (this.zzaos == null) {
            return false;
        }
        for (int i2 : this.zzaos) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TimeIntervals=");
        if (this.zzaos == null) {
            sb.append("unknown");
        } else {
            sb.append("[");
            boolean z = true;
            int[] iArr = this.zzaos;
            int length = iArr.length;
            int i = 0;
            while (i < length) {
                int i2 = iArr[i];
                if (!z) {
                    sb.append(", ");
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
        zzd.zza(parcel, 2, getTimeIntervals(), false);
        zzd.zzI(parcel, zze);
    }
}
