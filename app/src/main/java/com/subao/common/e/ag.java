package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e.z;
import com.subao.common.g.c;

/* compiled from: QosRegionConfig */
public class ag extends z {
    @NonNull
    private final c a;

    protected ag(z.a aVar, @NonNull c cVar) {
        super(aVar);
        this.a = cVar;
    }

    public static void a(z.a aVar, @NonNull c cVar) {
        new ag(aVar, cVar).a((aa) null, true);
    }

    /* access modifiers changed from: protected */
    public void a(@Nullable aa aaVar) {
        super.a(aaVar);
        if (aaVar != null && aaVar.c != null && aaVar.c.length > 2) {
            this.a.b(0, "key_qos_config", new String(aaVar.c));
        }
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/qos_region";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "QosRegion";
    }
}
