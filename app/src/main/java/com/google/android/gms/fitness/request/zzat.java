package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.Session;
import java.util.ArrayList;

public final class zzat implements Parcelable.Creator<SessionInsertRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        ArrayList<DataPoint> arrayList = null;
        ArrayList<DataSet> arrayList2 = null;
        Session session = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    session = (Session) zzb.zza(parcel, readInt, Session.CREATOR);
                    break;
                case 2:
                    arrayList2 = zzb.zzc(parcel, readInt, DataSet.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, DataPoint.CREATOR);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, readInt);
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
        return new SessionInsertRequest(i, session, arrayList2, arrayList, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new SessionInsertRequest[i];
    }
}
