package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends zza implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationResult> CREATOR = new zzr();
    static final List<Location> zzbic = Collections.emptyList();
    private final List<Location> zzbid;

    LocationResult(List<Location> list) {
        this.zzbid = list;
    }

    public static LocationResult create(List<Location> list) {
        if (list == null) {
            list = zzbic;
        }
        return new LocationResult(list);
    }

    public static LocationResult extractResult(Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        return (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.zzbid.size() != this.zzbid.size()) {
            return false;
        }
        Iterator<Location> it = this.zzbid.iterator();
        for (Location time : locationResult.zzbid) {
            if (it.next().getTime() != time.getTime()) {
                return false;
            }
        }
        return true;
    }

    public final Location getLastLocation() {
        int size = this.zzbid.size();
        if (size == 0) {
            return null;
        }
        return this.zzbid.get(size - 1);
    }

    @NonNull
    public final List<Location> getLocations() {
        return this.zzbid;
    }

    public final int hashCode() {
        int i = 17;
        Iterator<Location> it = this.zzbid.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            long time = it.next().getTime();
            i = ((int) (time ^ (time >>> 32))) + (i2 * 31);
        }
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbid);
        return new StringBuilder(String.valueOf(valueOf).length() + 27).append("LocationResult[locations: ").append(valueOf).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getLocations(), false);
        zzd.zzI(parcel, zze);
    }
}
