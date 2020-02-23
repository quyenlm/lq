package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class PlaceFilter extends zza {
    public static final Parcelable.Creator<PlaceFilter> CREATOR = new zzh();
    private static final PlaceFilter zzbjt = new PlaceFilter();
    private final Set<String> zzbjA;
    private List<Integer> zzbju;
    private boolean zzbjv;
    private List<zzo> zzbjw;
    private List<String> zzbjx;
    private final Set<Integer> zzbjy;
    private final Set<zzo> zzbjz;

    @Deprecated
    public static final class zza {
        private Collection<Integer> zzbjB;
        private Collection<zzo> zzbjC;
        private String[] zzbjD;
        private boolean zzbjv;

        private zza() {
            this.zzbjB = null;
            this.zzbjv = false;
            this.zzbjC = null;
            this.zzbjD = null;
        }
    }

    public PlaceFilter() {
        this(false, (Collection<String>) null);
    }

    private PlaceFilter(@Nullable Collection<Integer> collection, boolean z, @Nullable Collection<String> collection2, @Nullable Collection<zzo> collection3) {
        this((List<Integer>) zzh((Collection) null), z, zzh(collection2), (List<zzo>) zzh((Collection) null));
    }

    PlaceFilter(@Nullable List<Integer> list, boolean z, @Nullable List<String> list2, @Nullable List<zzo> list3) {
        this.zzbju = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.zzbjv = z;
        this.zzbjw = list3 == null ? Collections.emptyList() : Collections.unmodifiableList(list3);
        this.zzbjx = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.zzbjy = zzC(this.zzbju);
        this.zzbjz = zzC(this.zzbjw);
        this.zzbjA = zzC(this.zzbjx);
    }

    public PlaceFilter(boolean z, @Nullable Collection<String> collection) {
        this((Collection<Integer>) null, z, collection, (Collection<zzo>) null);
    }

    @Deprecated
    public static PlaceFilter zzvS() {
        new zza();
        return new PlaceFilter((List<Integer>) null, false, (List<String>) null, (List<zzo>) null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlaceFilter)) {
            return false;
        }
        PlaceFilter placeFilter = (PlaceFilter) obj;
        return this.zzbjy.equals(placeFilter.zzbjy) && this.zzbjv == placeFilter.zzbjv && this.zzbjz.equals(placeFilter.zzbjz) && this.zzbjA.equals(placeFilter.zzbjA);
    }

    public final Set<String> getPlaceIds() {
        return this.zzbjA;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbjy, Boolean.valueOf(this.zzbjv), this.zzbjz, this.zzbjA});
    }

    public final boolean isRestrictedToPlacesOpenNow() {
        return this.zzbjv;
    }

    public final String toString() {
        zzbg zzt = zzbe.zzt(this);
        if (!this.zzbjy.isEmpty()) {
            zzt.zzg("types", this.zzbjy);
        }
        zzt.zzg("requireOpenNow", Boolean.valueOf(this.zzbjv));
        if (!this.zzbjA.isEmpty()) {
            zzt.zzg("placeIds", this.zzbjA);
        }
        if (!this.zzbjz.isEmpty()) {
            zzt.zzg("requestedUserDataTypes", this.zzbjz);
        }
        return zzt.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbju, false);
        zzd.zza(parcel, 3, this.zzbjv);
        zzd.zzc(parcel, 4, this.zzbjw, false);
        zzd.zzb(parcel, 6, this.zzbjx, false);
        zzd.zzI(parcel, zze);
    }
}
