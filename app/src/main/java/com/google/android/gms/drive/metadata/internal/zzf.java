package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.zzbrc;
import com.google.android.gms.internal.zzbrn;
import com.google.android.gms.internal.zzbrp;
import com.google.android.gms.internal.zzbrx;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzf {
    private static final Map<String, MetadataField<?>> zzaPK = new HashMap();
    private static final Map<String, zzg> zzaPL = new HashMap();

    static {
        zzb((MetadataField<?>) zzbrc.zzaPQ);
        zzb((MetadataField<?>) zzbrc.zzaQw);
        zzb((MetadataField<?>) zzbrc.zzaQn);
        zzb((MetadataField<?>) zzbrc.zzaQu);
        zzb((MetadataField<?>) zzbrc.zzaQx);
        zzb((MetadataField<?>) zzbrc.zzaQd);
        zzb((MetadataField<?>) zzbrc.zzaQc);
        zzb((MetadataField<?>) zzbrc.zzaQe);
        zzb((MetadataField<?>) zzbrc.zzaQf);
        zzb((MetadataField<?>) zzbrc.zzaQg);
        zzb((MetadataField<?>) zzbrc.zzaQa);
        zzb((MetadataField<?>) zzbrc.zzaQi);
        zzb((MetadataField<?>) zzbrc.zzaQj);
        zzb((MetadataField<?>) zzbrc.zzaQk);
        zzb((MetadataField<?>) zzbrc.zzaQs);
        zzb((MetadataField<?>) zzbrc.zzaPR);
        zzb((MetadataField<?>) zzbrc.zzaQp);
        zzb((MetadataField<?>) zzbrc.zzaPT);
        zzb((MetadataField<?>) zzbrc.zzaQb);
        zzb((MetadataField<?>) zzbrc.zzaPU);
        zzb((MetadataField<?>) zzbrc.zzaPV);
        zzb((MetadataField<?>) zzbrc.zzaPW);
        zzb((MetadataField<?>) zzbrc.zzaPX);
        zzb((MetadataField<?>) zzbrc.zzaQm);
        zzb((MetadataField<?>) zzbrc.zzaQh);
        zzb((MetadataField<?>) zzbrc.zzaQo);
        zzb((MetadataField<?>) zzbrc.zzaQq);
        zzb((MetadataField<?>) zzbrc.zzaQr);
        zzb((MetadataField<?>) zzbrc.zzaQt);
        zzb((MetadataField<?>) zzbrc.zzaQy);
        zzb((MetadataField<?>) zzbrc.zzaQz);
        zzb((MetadataField<?>) zzbrc.zzaPZ);
        zzb((MetadataField<?>) zzbrc.zzaPY);
        zzb((MetadataField<?>) zzbrc.zzaQv);
        zzb((MetadataField<?>) zzbrc.zzaQl);
        zzb((MetadataField<?>) zzbrc.zzaPS);
        zzb((MetadataField<?>) zzbrc.zzaQA);
        zzb((MetadataField<?>) zzbrc.zzaQB);
        zzb((MetadataField<?>) zzbrc.zzaQC);
        zzb((MetadataField<?>) zzbrc.zzaQD);
        zzb((MetadataField<?>) zzbrc.zzaQE);
        zzb((MetadataField<?>) zzbrc.zzaQF);
        zzb((MetadataField<?>) zzbrc.zzaQG);
        zzb((MetadataField<?>) zzbrp.zzaQI);
        zzb((MetadataField<?>) zzbrp.zzaQK);
        zzb((MetadataField<?>) zzbrp.zzaQL);
        zzb((MetadataField<?>) zzbrp.zzaQM);
        zzb((MetadataField<?>) zzbrp.zzaQJ);
        zzb((MetadataField<?>) zzbrp.zzaQN);
        zzb((MetadataField<?>) zzbrx.zzaQP);
        zzb((MetadataField<?>) zzbrx.zzaQQ);
        zza(zzo.zzaPP);
        zza(zzbrn.zzaQH);
    }

    private static void zza(zzg zzg) {
        if (zzaPL.put(zzg.zzto(), zzg) != null) {
            String valueOf = String.valueOf(zzg.zzto());
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 46).append("A cleaner for key ").append(valueOf).append(" has already been registered").toString());
        }
    }

    public static void zzb(DataHolder dataHolder) {
        for (zzg zzc : zzaPL.values()) {
            zzc.zzc(dataHolder);
        }
    }

    private static void zzb(MetadataField<?> metadataField) {
        if (zzaPK.containsKey(metadataField.getName())) {
            String valueOf = String.valueOf(metadataField.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Duplicate field name registered: ".concat(valueOf) : new String("Duplicate field name registered: "));
        } else {
            zzaPK.put(metadataField.getName(), metadataField);
        }
    }

    public static MetadataField<?> zzcR(String str) {
        return zzaPK.get(str);
    }

    public static Collection<MetadataField<?>> zztn() {
        return Collections.unmodifiableCollection(zzaPK.values());
    }
}
