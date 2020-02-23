package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.DriveId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParentDriveIdSet extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<ParentDriveIdSet> CREATOR = new zzn();
    final List<zzq> zzaPO;

    public ParentDriveIdSet() {
        this(new ArrayList());
    }

    ParentDriveIdSet(List<zzq> list) {
        this.zzaPO = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaPO, false);
        zzd.zzI(parcel, zze);
    }

    public final Set<DriveId> zzB(long j) {
        HashSet hashSet = new HashSet();
        for (zzq next : this.zzaPO) {
            hashSet.add(new DriveId(next.zzaMh, next.zzaMi, j, next.zzaMj));
        }
        return hashSet;
    }
}
