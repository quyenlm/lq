package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

final class zzi {
    static MetadataField<?> zza(MetadataBundle metadataBundle) {
        Set<MetadataField<?>> zztr = metadataBundle.zztr();
        if (zztr.size() == 1) {
            return zztr.iterator().next();
        }
        throw new IllegalArgumentException("bundle should have exactly 1 populated field");
    }
}
