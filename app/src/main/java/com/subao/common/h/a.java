package com.subao.common.h;

import android.support.annotation.Nullable;
import com.subao.common.e.b;
import java.util.List;

/* compiled from: AccelGameListManager */
public class a {
    @Nullable
    private static List<b> a;

    @Nullable
    public static synchronized List<b> a() {
        List<b> list;
        synchronized (a.class) {
            list = a;
        }
        return list;
    }

    public static synchronized void a(@Nullable List<b> list) {
        synchronized (a.class) {
            a = list;
        }
    }
}
