package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternItem extends zza {
    public static final Parcelable.Creator<PatternItem> CREATOR = new zzi();
    private static final String TAG = PatternItem.class.getSimpleName();
    private final int type;
    @Nullable
    private final Float zzbnL;

    public PatternItem(int i, @Nullable Float f) {
        boolean z = true;
        if (i != 1 && (f == null || f.floatValue() < 0.0f)) {
            z = false;
        }
        String valueOf = String.valueOf(f);
        zzbo.zzb(z, (Object) new StringBuilder(String.valueOf(valueOf).length() + 45).append("Invalid PatternItem: type=").append(i).append(" length=").append(valueOf).toString());
        this.type = i;
        this.zzbnL = f;
    }

    @Nullable
    static List<PatternItem> zzF(@Nullable List<PatternItem> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (PatternItem next : list) {
            if (next != null) {
                switch (next.type) {
                    case 0:
                        next = new Dash(next.zzbnL.floatValue());
                        break;
                    case 1:
                        next = new Dot();
                        break;
                    case 2:
                        next = new Gap(next.zzbnL.floatValue());
                        break;
                    default:
                        Log.w(TAG, new StringBuilder(37).append("Unknown PatternItem type: ").append(next.type).toString());
                        break;
                }
            } else {
                next = null;
            }
            arrayList.add(next);
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) obj;
        return this.type == patternItem.type && zzbe.equal(this.zzbnL, patternItem.zzbnL);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.type), this.zzbnL});
    }

    public String toString() {
        int i = this.type;
        String valueOf = String.valueOf(this.zzbnL);
        return new StringBuilder(String.valueOf(valueOf).length() + 39).append("[PatternItem: type=").append(i).append(" length=").append(valueOf).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.type);
        zzd.zza(parcel, 3, this.zzbnL, false);
        zzd.zzI(parcel, zze);
    }
}
