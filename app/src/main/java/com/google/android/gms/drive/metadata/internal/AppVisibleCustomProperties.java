package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppVisibleCustomProperties extends com.google.android.gms.common.internal.safeparcel.zza implements ReflectedParcelable, Iterable<zzc> {
    public static final Parcelable.Creator<AppVisibleCustomProperties> CREATOR = new zza();
    public static final AppVisibleCustomProperties zzaPG = new zza().zztm();
    private List<zzc> zzaPH;

    public static class zza {
        private final Map<CustomPropertyKey, zzc> zzaPI = new HashMap();

        public final zza zza(CustomPropertyKey customPropertyKey, String str) {
            zzbo.zzb(customPropertyKey, (Object) "key");
            this.zzaPI.put(customPropertyKey, new zzc(customPropertyKey, str));
            return this;
        }

        public final zza zza(zzc zzc) {
            zzbo.zzb(zzc, (Object) "property");
            this.zzaPI.put(zzc.zzaPJ, zzc);
            return this;
        }

        public final AppVisibleCustomProperties zztm() {
            return new AppVisibleCustomProperties(this.zzaPI.values());
        }
    }

    AppVisibleCustomProperties(Collection<zzc> collection) {
        zzbo.zzu(collection);
        this.zzaPH = new ArrayList(collection);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return zztl().equals(((AppVisibleCustomProperties) obj).zztl());
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaPH});
    }

    public final Iterator<zzc> iterator() {
        return this.zzaPH.iterator();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 2, this.zzaPH, false);
        zzd.zzI(parcel, zze);
    }

    public final Map<CustomPropertyKey, String> zztl() {
        HashMap hashMap = new HashMap(this.zzaPH.size());
        for (zzc next : this.zzaPH) {
            hashMap.put(next.zzaPJ, next.mValue);
        }
        return Collections.unmodifiableMap(hashMap);
    }
}
