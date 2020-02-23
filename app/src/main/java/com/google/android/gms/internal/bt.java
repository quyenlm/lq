package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class bt extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        try {
            return new ds(Boolean.valueOf(Pattern.compile(zzcxp.zzd(dpVarArr[1]), dpVarArr.length < 3 ? false : "true".equalsIgnoreCase(zzcxp.zzd(dpVarArr[2])) ? 66 : 64).matcher(zzcxp.zzd(dpVarArr[0])).find()));
        } catch (PatternSyntaxException e) {
            return new ds(false);
        }
    }
}
