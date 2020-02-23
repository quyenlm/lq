package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;
import java.util.Locale;

public final class zzp extends zza {
    public static final Parcelable.Creator<zzp> CREATOR = new zzq();
    private int zzaNm;

    public zzp(int i) {
        this.zzaNm = i;
    }

    public final boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return zzbe.equal(Integer.valueOf(this.zzaNm), Integer.valueOf(((zzp) obj).zzaNm));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaNm)});
    }

    public final String toString() {
        return String.format(Locale.US, "TransferProgressOptions[type=%d]", new Object[]{Integer.valueOf(this.zzaNm)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaNm);
        zzd.zzI(parcel, zze);
    }
}
