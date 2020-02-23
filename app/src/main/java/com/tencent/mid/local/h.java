package com.tencent.mid.local;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class h {
    private static h b = null;
    private Map<Integer, g> a;

    private h(Context context) {
        this.a = null;
        this.a = new HashMap(3);
        this.a.put(1, new f(context));
        this.a.put(2, new b(context));
        this.a.put(4, new d(context));
    }

    static synchronized h a(Context context) {
        h hVar;
        synchronized (h.class) {
            if (b == null) {
                b = new h(context);
            }
            hVar = b;
        }
        return hVar;
    }

    /* access modifiers changed from: package-private */
    public c a() {
        return a((List<Integer>) new ArrayList(Arrays.asList(new Integer[]{1, 2, 4})));
    }

    /* access modifiers changed from: package-private */
    public c a(List<Integer> list) {
        c d;
        if (list != null && list.size() >= 0) {
            for (Integer num : list) {
                g gVar = this.a.get(num);
                if (gVar != null && (d = gVar.d()) != null && d.a()) {
                    return d;
                }
            }
        }
        return new c();
    }
}
