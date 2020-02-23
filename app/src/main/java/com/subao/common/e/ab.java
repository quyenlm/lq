package com.subao.common.e;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.subao.common.e.z;
import com.subao.common.g.c;

/* compiled from: PortalGeneralConfigDownloader */
public class ab extends ac {
    private final c a;

    protected ab(z.a aVar, c cVar) {
        super(aVar);
        this.a = cVar;
    }

    public static void a(z.a aVar, c cVar) {
        ac.a((ac) new ab(aVar, cVar));
    }

    /* access modifiers changed from: protected */
    public void a(@NonNull String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.a.b(str, str2);
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "general";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/general";
    }
}
