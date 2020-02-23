package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class vf<T> {
    public T value;
    public Map<wp, vf<T>> zzceA = new HashMap();

    /* access modifiers changed from: package-private */
    public final String toString(String str) {
        String valueOf = String.valueOf(this.value);
        String sb = new StringBuilder(String.valueOf(str).length() + 10 + String.valueOf(valueOf).length()).append(str).append("<value>: ").append(valueOf).append("\n").toString();
        if (this.zzceA.isEmpty()) {
            return new StringBuilder(String.valueOf(sb).length() + 7 + String.valueOf(str).length()).append(sb).append(str).append("<empty>").toString();
        }
        Iterator<Map.Entry<wp, vf<T>>> it = this.zzceA.entrySet().iterator();
        while (true) {
            String str2 = sb;
            if (!it.hasNext()) {
                return str2;
            }
            Map.Entry next = it.next();
            String valueOf2 = String.valueOf(str2);
            String valueOf3 = String.valueOf(next.getKey());
            String valueOf4 = String.valueOf(((vf) next.getValue()).toString(String.valueOf(str).concat("\t")));
            sb = new StringBuilder(String.valueOf(valueOf2).length() + 3 + String.valueOf(str).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length()).append(valueOf2).append(str).append(valueOf3).append(":\n").append(valueOf4).append("\n").toString();
        }
    }
}
