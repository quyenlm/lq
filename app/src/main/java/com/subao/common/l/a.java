package com.subao.common.l;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.j.e;
import com.subao.common.j.k;

/* compiled from: QosHelper */
public class a {
    @NonNull
    public static String a(@Nullable k.a aVar) {
        byte[] b = k.b(aVar);
        if (b == null) {
            b = k.a(aVar);
        }
        return e.a(b);
    }
}
