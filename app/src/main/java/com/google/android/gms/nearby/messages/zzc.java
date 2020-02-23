package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzcph;
import com.google.android.gms.internal.zzcpl;
import com.google.android.gms.nearby.messages.internal.zzad;
import java.util.ArrayList;
import java.util.List;

public final class zzc implements Parcelable.Creator<MessageFilter> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ArrayList<zzcph> arrayList = null;
        boolean z = false;
        ArrayList<zzcpl> arrayList2 = null;
        ArrayList<zzad> arrayList3 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList3 = zzb.zzc(parcel, readInt, zzad.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, readInt, zzcpl.CREATOR);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    arrayList = zzb.zzc(parcel, readInt, zzcph.CREATOR);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
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
        return new MessageFilter(i2, (List<zzad>) arrayList3, (List<zzcpl>) arrayList2, z, (List<zzcph>) arrayList, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MessageFilter[i];
    }
}
