package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class zza<T> implements MetadataField<T> {
    private final String zzaPB;
    private final Set<String> zzaPC;
    private final Set<String> zzaPD;
    private final int zzaPE;

    protected zza(String str, int i) {
        this.zzaPB = (String) zzbo.zzb(str, (Object) "fieldName");
        this.zzaPC = Collections.singleton(str);
        this.zzaPD = Collections.emptySet();
        this.zzaPE = i;
    }

    protected zza(String str, Collection<String> collection, Collection<String> collection2, int i) {
        this.zzaPB = (String) zzbo.zzb(str, (Object) "fieldName");
        this.zzaPC = Collections.unmodifiableSet(new HashSet(collection));
        this.zzaPD = Collections.unmodifiableSet(new HashSet(collection2));
        this.zzaPE = i;
    }

    public final String getName() {
        return this.zzaPB;
    }

    public String toString() {
        return this.zzaPB;
    }

    public final T zza(DataHolder dataHolder, int i, int i2) {
        if (zzb(dataHolder, i, i2)) {
            return zzc(dataHolder, i, i2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Bundle bundle, T t);

    public final void zza(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2) {
        zzbo.zzb(dataHolder, (Object) "dataHolder");
        zzbo.zzb(metadataBundle, (Object) "bundle");
        if (zzb(dataHolder, i, i2)) {
            metadataBundle.zzc(this, zzc(dataHolder, i, i2));
        }
    }

    public final void zza(T t, Bundle bundle) {
        zzbo.zzb(bundle, (Object) "bundle");
        if (t == null) {
            bundle.putString(this.zzaPB, (String) null);
        } else {
            zza(bundle, t);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zzb(com.google.android.gms.common.data.DataHolder r4, int r5, int r6) {
        /*
            r3 = this;
            java.util.Set<java.lang.String> r0 = r3.zzaPC
            java.util.Iterator r1 = r0.iterator()
        L_0x0006:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x0020
            java.lang.Object r0 = r1.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = r4.zzcv(r0)
            if (r2 == 0) goto L_0x001e
            boolean r0 = r4.zzh(r0, r5, r6)
            if (r0 == 0) goto L_0x0006
        L_0x001e:
            r0 = 0
        L_0x001f:
            return r0
        L_0x0020:
            r0 = 1
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.drive.metadata.zza.zzb(com.google.android.gms.common.data.DataHolder, int, int):boolean");
    }

    /* access modifiers changed from: protected */
    public abstract T zzc(DataHolder dataHolder, int i, int i2);

    public final T zzp(Bundle bundle) {
        zzbo.zzb(bundle, (Object) "bundle");
        if (bundle.get(this.zzaPB) != null) {
            return zzq(bundle);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract T zzq(Bundle bundle);

    public final Collection<String> zztk() {
        return this.zzaPC;
    }
}
