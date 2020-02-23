package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.vk.sdk.api.VKApiConst;
import java.util.Arrays;

public final class zzb extends zza {
    public static final Parcelable.Creator<zzb> CREATOR = new zzaw();
    final int mLength;
    final int mOffset;

    public zzb(int i, int i2) {
        this.mOffset = i;
        this.mLength = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzb)) {
            return false;
        }
        zzb zzb = (zzb) obj;
        return zzbe.equal(Integer.valueOf(this.mOffset), Integer.valueOf(zzb.mOffset)) && zzbe.equal(Integer.valueOf(this.mLength), Integer.valueOf(zzb.mLength));
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mOffset), Integer.valueOf(this.mLength)});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg(VKApiConst.OFFSET, Integer.valueOf(this.mOffset)).zzg("length", Integer.valueOf(this.mLength)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.mOffset);
        zzd.zzc(parcel, 2, this.mLength);
        zzd.zzI(parcel, zze);
    }
}
