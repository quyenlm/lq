package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.games.GamesActivityResultCodes;
import java.util.ArrayList;
import java.util.Arrays;

public final class zzaub extends zza {
    public static final Parcelable.Creator<zzaub> CREATOR = new zzauc();
    private static final int[] zzaop = {10002, 10003, 10004, 10005, 10006, GamesActivityResultCodes.RESULT_SEND_REQUEST_FAILED, 10008};
    private final int zzaoq;
    private final ArrayList<zzasv> zzaor;

    public zzaub(int i, ArrayList<zzasv> arrayList) {
        this.zzaoq = i;
        this.zzaor = arrayList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaub)) {
            return false;
        }
        zzaub zzaub = (zzaub) obj;
        if (this.zzaoq != zzaub.zzaoq) {
            return false;
        }
        if ((this.zzaor == null) ^ (zzaub.zzaor == null)) {
            return false;
        }
        if (this.zzaor != null) {
            if (this.zzaor.size() != zzaub.zzaor.size()) {
                return false;
            }
            ArrayList arrayList = this.zzaor;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                if (!zzaub.zzaor.contains((zzasv) obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final int hashCode() {
        int i;
        if (this.zzaor != null) {
            ArrayList arrayList = this.zzaor;
            int size = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                i3 += ((zzasv) obj).hashCode() * 13;
            }
            i = i3;
        } else {
            i = 0;
        }
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaoq), Integer.valueOf(i)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaoq);
        zzd.zzc(parcel, 3, this.zzaor, false);
        zzd.zzI(parcel, zze);
    }
}
