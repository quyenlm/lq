package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import java.util.List;

@Deprecated
public final class AppMetadata extends zza {
    public static final Parcelable.Creator<AppMetadata> CREATOR = new zzc();
    private final List<AppIdentifier> zzbwn;

    public AppMetadata(List<AppIdentifier> list) {
        this.zzbwn = (List) zzbo.zzb(list, (Object) "Must specify application identifiers");
        if (list.size() == 0) {
            throw new IllegalArgumentException(String.valueOf("Application identifiers cannot be empty"));
        }
    }

    public final List<AppIdentifier> getAppIdentifiers() {
        return this.zzbwn;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getAppIdentifiers(), false);
        zzd.zzI(parcel, zze);
    }
}
