package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.zzb;
import com.google.android.gms.drive.query.Filter;
import java.util.List;

public final class zzk implements zzj<Boolean> {
    private Boolean zzaRq = false;

    private zzk() {
    }

    public static boolean zza(Filter filter) {
        if (filter == null) {
            return false;
        }
        return ((Boolean) filter.zza(new zzk())).booleanValue();
    }

    public final /* synthetic */ Object zza(zzb zzb, Object obj) {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zza(zzx zzx, MetadataField metadataField, Object obj) {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zza(zzx zzx, List list) {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zzcU(String str) {
        if (!str.isEmpty()) {
            this.zzaRq = true;
        }
        return this.zzaRq;
    }

    public final /* synthetic */ Object zzd(MetadataField metadataField) {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zzd(MetadataField metadataField, Object obj) {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zztu() {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zztv() {
        return this.zzaRq;
    }

    public final /* synthetic */ Object zzv(Object obj) {
        return this.zzaRq;
    }
}
