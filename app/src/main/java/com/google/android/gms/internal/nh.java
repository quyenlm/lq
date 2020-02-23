package com.google.android.gms.internal;

import com.tencent.tp.a.h;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public abstract class nh<K, V> implements Iterable<Map.Entry<K, V>> {
    public abstract boolean containsKey(K k);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof nh)) {
            return false;
        }
        nh nhVar = (nh) obj;
        if (!getComparator().equals(nhVar.getComparator())) {
            return false;
        }
        if (size() != nhVar.size()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = nhVar.iterator();
        while (it.hasNext()) {
            if (!((Map.Entry) it.next()).equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public abstract V get(K k);

    public abstract Comparator<K> getComparator();

    public int hashCode() {
        int hashCode = getComparator().hashCode();
        Iterator it = iterator();
        while (true) {
            int i = hashCode;
            if (!it.hasNext()) {
                return i;
            }
            hashCode = ((Map.Entry) it.next()).hashCode() + (i * 31);
        }
    }

    public abstract boolean isEmpty();

    public abstract Iterator<Map.Entry<K, V>> iterator();

    public abstract int size();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        Iterator it = iterator();
        boolean z = true;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(h.a);
            sb.append(entry.getKey());
            sb.append("=>");
            sb.append(entry.getValue());
            sb.append(h.b);
        }
        sb.append("};");
        return sb.toString();
    }

    public abstract K zzFx();

    public abstract K zzFy();

    public abstract Iterator<Map.Entry<K, V>> zzFz();

    public abstract nh<K, V> zzX(K k);

    public abstract K zzY(K k);

    public abstract void zza(ns<K, V> nsVar);

    public abstract nh<K, V> zzg(K k, V v);
}
