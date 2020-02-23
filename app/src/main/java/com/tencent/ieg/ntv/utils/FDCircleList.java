package com.tencent.ieg.ntv.utils;

import java.util.ArrayList;

public class FDCircleList<T> {
    private int _count;
    private ArrayList<T> _data;
    private int _inner_size;
    private int startIdx;

    public FDCircleList(int size) {
        reset(size);
    }

    private int getRealIdx(int idx) {
        if (idx > this._inner_size) {
            return -1;
        }
        int resIdx = this.startIdx + idx;
        if (resIdx >= this._inner_size) {
            return resIdx - this._inner_size;
        }
        return resIdx;
    }

    public int getLastIdx() {
        return getRealIdx(this._count - 1);
    }

    public int getNextIdx() {
        return getRealIdx(this._count);
    }

    public void push(T obj) {
        this._data.set(getNextIdx(), obj);
        gotoNext();
    }

    public T getNextObj() {
        return this._data.get(getNextIdx());
    }

    public void gotoNext() {
        if (getNextIdx() != this.startIdx || this._count <= 0) {
            this._count++;
            return;
        }
        this.startIdx++;
        if (this.startIdx >= this._inner_size) {
            this.startIdx -= this._inner_size;
        }
    }

    public T getDataWithIdx(int idx) {
        return this._data.get(getRealIdx(idx));
    }

    public void destroy() {
        this._data.clear();
        this._data = null;
        this.startIdx = 0;
        this._count = 0;
    }

    public void clear() {
        this.startIdx = 0;
        this._count = 0;
    }

    public void reset(int size) {
        this._data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this._data.add((Object) null);
        }
        this.startIdx = 0;
        this._count = 0;
        this._inner_size = size;
    }

    public boolean isFull() {
        return getNextIdx() == this.startIdx;
    }

    public int count() {
        return this._count;
    }

    public ArrayList<T> getAllData() {
        ArrayList<T> res = new ArrayList<>();
        for (int i = 0; i < count(); i++) {
            res.add(getDataWithIdx(i));
        }
        return res;
    }
}
