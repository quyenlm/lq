package com.tencent.component.utils;

import java.util.LinkedList;

public class FixedLinkedList<V> extends LinkedList<V> {
    private final int mCapacity;
    private final boolean mTrimHead;

    public FixedLinkedList(int capacity) {
        this(capacity, true);
    }

    public FixedLinkedList(int capacity, boolean trimHead) {
        this.mCapacity = capacity;
        this.mTrimHead = trimHead;
    }

    public boolean add(V value) {
        if (value == null) {
            return false;
        }
        boolean add = super.add(value);
        ensureCapacity();
        return add;
    }

    public void add(int location, V value) {
        if (value != null) {
            super.add(location, value);
            ensureCapacity();
        }
    }

    private void ensureCapacity() {
        while (this.mCapacity > 0 && size() > this.mCapacity) {
            if (this.mTrimHead) {
                removeFirst();
            } else {
                removeLast();
            }
        }
    }
}
