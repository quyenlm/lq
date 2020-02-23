package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class abh {
    private List<byte[]> zzaKz;
    private long zzayS;
    private Map<String, Map<String, byte[]>> zzcnH;

    public abh(Map<String, Map<String, byte[]>> map, long j, List<byte[]> list) {
        this.zzcnH = map;
        this.zzayS = j;
        this.zzaKz = list;
    }

    public final long getTimestamp() {
        return this.zzayS;
    }

    public final void setTimestamp(long j) {
        this.zzayS = j;
    }

    public final Map<String, Map<String, byte[]>> zzKE() {
        return this.zzcnH;
    }

    public final boolean zzKF() {
        return this.zzcnH != null && !this.zzcnH.isEmpty();
    }

    public final boolean zzaj(String str, String str2) {
        return zzKF() && zzhF(str2) && zzak(str, str2) != null;
    }

    public final byte[] zzak(String str, String str2) {
        if (str == null || !zzhF(str2)) {
            return null;
        }
        return (byte[]) this.zzcnH.get(str2).get(str);
    }

    public final Set<String> zzal(String str, String str2) {
        TreeSet treeSet = new TreeSet();
        if (!zzhF(str2)) {
            return treeSet;
        }
        if (str == null || str.isEmpty()) {
            return this.zzcnH.get(str2).keySet();
        }
        for (String str3 : this.zzcnH.get(str2).keySet()) {
            if (str3.startsWith(str)) {
                treeSet.add(str3);
            }
        }
        return treeSet;
    }

    public final void zzh(Map<String, byte[]> map, String str) {
        if (this.zzcnH == null) {
            this.zzcnH = new HashMap();
        }
        this.zzcnH.put(str, map);
    }

    public final boolean zzhF(String str) {
        if (str == null) {
            return false;
        }
        return zzKF() && this.zzcnH.get(str) != null && !this.zzcnH.get(str).isEmpty();
    }

    public final List<byte[]> zzss() {
        return this.zzaKz;
    }
}
