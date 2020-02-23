package com.tencent.component.utils.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class MultiHashMap<K, V> extends HashMap<K, HashSet<V>> {
    public boolean add(K key, V value) {
        if (value == null) {
            return false;
        }
        HashSet<V> array = (HashSet) get(key);
        if (array == null) {
            array = new HashSet<>();
            put(key, array);
        }
        return array.add(value);
    }

    public boolean delete(K key, V value) {
        boolean removed;
        if (value != null) {
            Collection<V> array = (Collection) get(key);
            if (array == null || !array.remove(value)) {
                removed = false;
            } else {
                removed = true;
            }
            if (array != null && array.isEmpty()) {
                remove(key);
            }
            return removed;
        } else if (remove(key) != null) {
            return true;
        } else {
            return false;
        }
    }

    public int sizeOf(K key) {
        Collection<V> array = (Collection) get(key);
        if (array == null) {
            return 0;
        }
        return array.size();
    }
}
