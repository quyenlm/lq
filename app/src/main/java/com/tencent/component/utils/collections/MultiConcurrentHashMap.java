package com.tencent.component.utils.collections;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MultiConcurrentHashMap<K, V> extends ConcurrentHashMap<K, ConcurrentLinkedQueue<V>> {
    public boolean add(K key, V value) {
        if (value == null) {
            return false;
        }
        ConcurrentLinkedQueue<V> array = (ConcurrentLinkedQueue) get(key);
        if (array == null) {
            array = new ConcurrentLinkedQueue<>();
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
