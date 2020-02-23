package com.google.android.gms.internal;

import com.tencent.tp.a.h;
import java.util.List;
import java.util.Map;

final class pa {
    /* access modifiers changed from: private */
    public final List<String> zzcbt;
    /* access modifiers changed from: private */
    public final Map<String, Object> zzcbu;

    public pa(List<String> list, Map<String, Object> map) {
        this.zzcbt = list;
        this.zzcbu = map;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof pa)) {
            return false;
        }
        pa paVar = (pa) obj;
        if (this.zzcbt.equals(paVar.zzcbt)) {
            return this.zzcbu.equals(paVar.zzcbu);
        }
        return false;
    }

    public final int hashCode() {
        return (this.zzcbt.hashCode() * 31) + this.zzcbu.hashCode();
    }

    public final String toString() {
        String valueOf = String.valueOf(ok.zzR(this.zzcbt));
        String valueOf2 = String.valueOf(this.zzcbu);
        return new StringBuilder(String.valueOf(valueOf).length() + 11 + String.valueOf(valueOf2).length()).append(valueOf).append(" (params: ").append(valueOf2).append(h.b).toString();
    }
}
