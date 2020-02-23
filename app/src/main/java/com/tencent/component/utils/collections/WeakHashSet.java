package com.tencent.component.utils.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashSet<E> extends AbstractSet<E> implements Set<E> {
    private static final Object PRESENT = new Object();
    private transient WeakHashMap<E, Object> map;

    public WeakHashSet() {
        this.map = new WeakHashMap<>();
    }

    public WeakHashSet(Collection<? extends E> c) {
        this.map = new WeakHashMap<>(Math.max(((int) (((float) c.size()) / 0.75f)) + 1, 16));
        addAll(c);
    }

    public WeakHashSet(int initialCapacity, float loadFactor) {
        this.map = new WeakHashMap<>(initialCapacity, loadFactor);
    }

    public WeakHashSet(int initialCapacity) {
        this.map = new WeakHashMap<>(initialCapacity);
    }

    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean contains(Object o) {
        return this.map.containsKey(o);
    }

    public boolean add(E o) {
        return this.map.put(o, PRESENT) == null;
    }

    public boolean remove(Object o) {
        return this.map.remove(o) == PRESENT;
    }

    public void clear() {
        this.map.clear();
    }
}
