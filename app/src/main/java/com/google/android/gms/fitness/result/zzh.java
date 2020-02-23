package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzae;
import java.util.ArrayList;

public final class zzh implements Parcelable.Creator<SessionReadResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        Status status = null;
        ArrayList<zzae> arrayList = null;
        ArrayList<Session> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList2 = zzb.zzc(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, zzae.CREATOR);
                    break;
                case 3:
                    status = (Status) zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new SessionReadResult(i, arrayList2, arrayList, status);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionReadResult[i];
    }
}
