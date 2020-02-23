package com.tencent.component.utils.collections;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

public class MultiSparseArray<E> {
    private SparseArray<ArrayList<E>> array;

    public MultiSparseArray() {
        this.array = new SparseArray<>();
    }

    public MultiSparseArray(int capacity) {
        this.array = new SparseArray<>(capacity);
    }

    public List<E> get(int key) {
        return this.array.get(key);
    }

    public void put(int key, E value) {
        if (value != null) {
            List<E> list = get(key);
            if (list == null) {
                list = new ArrayList<>();
                this.array.put(key, (ArrayList) list);
            }
            if (!list.contains(value)) {
                list.add(value);
            }
        }
    }

    public int keySize() {
        return this.array.size();
    }

    public int keyAt(int index) {
        return this.array.keyAt(index);
    }

    public List<E> valueAt(int index) {
        return this.array.valueAt(index);
    }

    public void remove(int key) {
        this.array.remove(key);
    }

    public void remove(int key, E value) {
        List<E> list = get(key);
        if (list != null) {
            list.remove(value);
        }
    }

    public void clear() {
        this.array.clear();
    }
}
