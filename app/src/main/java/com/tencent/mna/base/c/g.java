package com.tencent.mna.base.c;

import com.tencent.mna.base.utils.h;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ReporterImpl */
class g extends e {
    protected Map<String, String> a = new ConcurrentHashMap();
    protected c b;

    g(c cVar) {
        this.b = cVar;
    }

    /* access modifiers changed from: package-private */
    public d b(String str, String str2) {
        this.a.put(str, str2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public d b(String str, String str2, String str3) {
        if (this.a.containsKey(str)) {
            a(str, this.a.get(str) + str3 + str2);
        } else {
            a(str, str2);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        b.a(this.b.getName(), true, 0, -1, this.a, this.b.isRealTime());
        a(this.a);
    }

    /* access modifiers changed from: package-private */
    public void a(Map<String, String> map) {
        if (h.a() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("report event:").append(this.b.getName()).append(",");
            for (Map.Entry next : map.entrySet()) {
                sb.append((String) next.getKey()).append(":[").append((String) next.getValue()).append("];");
            }
            h.a(sb.toString());
        }
    }
}
