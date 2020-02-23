package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzai implements Parcelable.Creator<Value> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        float f = 0.0f;
        byte[] bArr = null;
        float[] fArr = null;
        int[] iArr = null;
        Bundle bundle = null;
        String str = null;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 3:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 6:
                    iArr = zzb.zzw(parcel, readInt);
                    break;
                case 7:
                    fArr = zzb.zzy(parcel, readInt);
                    break;
                case 8:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 1000:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new Value(i2, i, z, f, str, bundle, iArr, fArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Value[i];
    }
}
