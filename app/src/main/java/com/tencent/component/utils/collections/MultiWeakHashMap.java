package com.tencent.component.utils.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.WeakHashMap;

public class MultiWeakHashMap<K, V> {
    private HashMap<K, WeakHashMap<V, Object>> map;

    public MultiWeakHashMap() {
        this.map = new HashMap<>();
    }

    public MultiWeakHashMap(int capacity) {
        this.map = new HashMap<>(capacity);
    }

    public Collection<V> get(K key) {
        WeakHashMap<V, Object> values = this.map.get(key);
        if (values == null) {
            return null;
        }
        return values.keySet();
    }

    public void put(K key, V value) {
        if (value != null) {
            WeakHashMap<V, Object> values = this.map.get(key);
            if (values == null) {
                values = new WeakHashMap<>();
                this.map.put(key, values);
            }
            values.put(value, this);
        }
    }

    public void remove(K key) {
        this.map.remove(key);
    }

    public void remove(K key, V value) {
        WeakHashMap<V, Object> values = this.map.get(key);
        if (values != null) {
            values.remove(value);
        }
    }

    public Collection<K> keySet() {
        return this.map.keySet();
    }

    public int size() {
        return this.map.size();
    }

    public int size(K key) {
        WeakHashMap<V, Object> values = this.map.get(key);
        if (values != null) {
            return values.size();
        }
        return 0;
    }

    public void clear() {
        this.map.clear();
    }
}
