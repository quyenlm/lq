package com.tencent.msdk.a.a;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class h {
    private static h b = null;
    private Map<Integer, g> a;
    private d c;

    private h(Context context) {
        this.a = null;
        this.c = null;
        this.a = new HashMap(3);
        this.a.put(1, new f(context));
        this.a.put(2, new b(context));
        this.a.put(4, new e(context));
    }

    public static synchronized h a(Context context) {
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
    public d a() {
        if (this.c == null || !this.c.a()) {
            this.c = a((List<Integer>) new ArrayList(Arrays.asList(new Integer[]{1, 2, 4})));
        }
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public d a(List<Integer> list) {
        d e;
        if (list != null && list.size() > 0) {
            for (Integer num : list) {
                g gVar = this.a.get(num);
                if (gVar != null && (e = gVar.e()) != null && e.a()) {
                    return e;
                }
            }
        }
        return new d();
    }

    public void a(d dVar) {
        this.c = dVar;
        for (Map.Entry<Integer, g> value : this.a.entrySet()) {
            ((g) value.getValue()).a(dVar);
        }
    }
}
