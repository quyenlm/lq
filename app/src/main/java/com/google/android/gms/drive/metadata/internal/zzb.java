package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.zza;
import java.util.Collection;

public class zzb extends zza<Boolean> {
    public zzb(String str, int i) {
        super(str, i);
    }

    public zzb(String str, Collection<String> collection, Collection<String> collection2, int i) {
        super(str, collection, collection2, 7000000);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Bundle bundle, Object obj) {
        bundle.putBoolean(getName(), ((Boolean) obj).booleanValue());
    }

    /* access modifiers changed from: protected */
    /* renamed from: zze */
    public Boolean zzc(DataHolder dataHolder, int i, int i2) {
        return Boolean.valueOf(dataHolder.zze(getName(), i, i2));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzq(Bundle bundle) {
        return Boolean.valueOf(bundle.getBoolean(getName()));
    }
}
