package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ek {
    private final String zzaxs;
    private final List<em> zzbKW;
    private final Map<String, List<ei>> zzbKX;
    private final int zzbKY;

    private ek(List<em> list, Map<String, List<ei>> map, String str, int i) {
        this.zzbKW = Collections.unmodifiableList(list);
        this.zzbKX = Collections.unmodifiableMap(map);
        this.zzaxs = str;
        this.zzbKY = i;
    }

    public static el zzDz() {
        return new el();
    }

    public final String getVersion() {
        return this.zzaxs;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbKW);
        String valueOf2 = String.valueOf(this.zzbKX);
        return new StringBuilder(String.valueOf(valueOf).length() + 17 + String.valueOf(valueOf2).length()).append("Rules: ").append(valueOf).append("  Macros: ").append(valueOf2).toString();
    }

    public final List<em> zzCX() {
        return this.zzbKW;
    }

    public final Map<String, List<ei>> zzDA() {
        return this.zzbKX;
    }
}
