package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import java.util.Collections;
import java.util.List;

public final class zzaa extends zza {
    public static final Parcelable.Creator<zzaa> CREATOR = new zzab();
    private final PendingIntent mPendingIntent;
    private final String mTag;
    private final List<String> zzbix;

    zzaa(@Nullable List<String> list, @Nullable PendingIntent pendingIntent, String str) {
        this.zzbix = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.mPendingIntent = pendingIntent;
        this.mTag = str;
    }

    public static zzaa zzB(List<String> list) {
        zzbo.zzb(list, (Object) "geofence can't be null.");
        zzbo.zzb(!list.isEmpty(), (Object) "Geofences must contains at least one id.");
        return new zzaa(list, (PendingIntent) null, "");
    }

    public static zzaa zzb(PendingIntent pendingIntent) {
        zzbo.zzb(pendingIntent, (Object) "PendingIntent can not be null.");
        return new zzaa((List<String>) null, pendingIntent, "");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzb(parcel, 1, this.zzbix, false);
        zzd.zza(parcel, 2, (Parcelable) this.mPendingIntent, i, false);
        zzd.zza(parcel, 3, this.mTag, false);
        zzd.zzI(parcel, zze);
    }
}
