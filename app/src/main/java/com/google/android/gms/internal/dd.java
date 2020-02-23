package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Map;

public final class dd {
    private final Map<String, dm> zzbKZ;
    private final dm zzbLa;

    private dd(Map<String, dm> map, dm dmVar) {
        this.zzbKZ = Collections.unmodifiableMap(map);
        this.zzbLa = dmVar;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbKZ);
        String valueOf2 = String.valueOf(this.zzbLa);
        return new StringBuilder(String.valueOf(valueOf).length() + 32 + String.valueOf(valueOf2).length()).append("Properties: ").append(valueOf).append(" pushAfterEvaluate: ").append(valueOf2).toString();
    }

    public final Map<String, dm> zzCZ() {
        return this.zzbKZ;
    }
}
