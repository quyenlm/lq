package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public final class zzi implements Parcelable.Creator<SessionStopResult> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        ArrayList<Session> arrayList = null;
        Status status = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    status = (Status) zzb.zza(parcel, readInt, Status.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, Session.CREATOR);
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
        return new SessionStopResult(i, status, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionStopResult[i];
    }
}
