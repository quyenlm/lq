package com.google.android.gms.internal;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzblj extends Metadata {
    private final MetadataBundle zzaNJ;

    public zzblj(MetadataBundle metadataBundle) {
        this.zzaNJ = metadataBundle;
    }

    public final /* synthetic */ Object freeze() {
        return new zzblj(this.zzaNJ.zztq());
    }

    public final boolean isDataValid() {
        return this.zzaNJ != null;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzaNJ);
        return new StringBuilder(String.valueOf(valueOf).length() + 17).append("Metadata [mImpl=").append(valueOf).append("]").toString();
    }

    public final <T> T zza(MetadataField<T> metadataField) {
        return this.zzaNJ.zza(metadataField);
    }
}
