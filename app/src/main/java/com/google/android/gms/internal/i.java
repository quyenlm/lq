package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class i extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 1 || dpVarArr.length == 2);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        Matcher matcher = Pattern.compile(dpVarArr.length < 2 ? "" : zzcxp.zzd(dpVarArr[1])).matcher(dpVarArr[0].value());
        if (!matcher.find()) {
            return dv.zzbLt;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new eb(matcher.group()));
        return new dw(arrayList);
    }
}
