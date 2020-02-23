package com.tencent.component.utils.collections;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WeakArrayList<T> extends AbstractList<T> {
    private static final Object NULL_VALUE = new Object();
    private Object[] data;
    private boolean enquedElement;
    private List<ReferenceListener> listeners;
    private final transient ReferenceQueue<T> queue;
    private int size;

    private static <T> T maskNull(T value) {
        return value == null ? NULL_VALUE : value;
    }

    private static <T> T unmaskNull(T value) {
        if (value == NULL_VALUE) {
            return null;
        }
        return value;
    }

    public WeakArrayList(int initialCapacity) {
        this.queue = new ReferenceQueue<>();
        this.enquedElement = false;
        this.listeners = null;
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    public WeakArrayList() {
        this(10);
    }

    public WeakArrayList(Collection<? extends T> c) {
        this.queue = new ReferenceQueue<>();
        this.enquedElement = false;
        this.listeners = null;
        this.data = new Object[c.size()];
        this.size = this.data.length;
        int i = 0;
        for (T t : c) {
            this.data[i] = createRef(t);
            i++;
        }
    }

    public String toString() {
        T obj;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            Reference<T> ref = (Reference) this.data[i];
            if (this.data[i] == null) {
                obj = null;
            } else {
                obj = ref.get();
            }
            buffer.append('{');
            buffer.append(obj == null ? null : obj.toString());
            buffer.append('}');
        }
        return buffer.toString();
    }

    private Reference<T> createRef(T obj) {
        return new WeakReference(maskNull(obj), this.queue);
    }

    public void ensureCapacity(int minCapacity) {
        this.modCount++;
        int oldCapacity = this.data.length;
        if (minCapacity > oldCapacity) {
            Object[] oldData = this.data;
            int newCapacity = ((oldCapacity * 3) / 2) + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            this.data = copyOf(oldData, newCapacity);
        }
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        if (original == null) {
            throw new NullPointerException("original == null");
        } else if (newLength >= 0) {
            return copyOfRange(original, 0, newLength);
        } else {
            throw new NegativeArraySizeException(Integer.toString(newLength));
        }
    }

    public static <T> T[] copyOfRange(T[] original, int start, int end) {
        int originalLength = original.length;
        if (start > end) {
            throw new IllegalArgumentException();
        } else if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            int resultLength = end - start;
            int copyLength = Math.min(resultLength, originalLength - start);
            T[] result = (Object[]) ((Object[]) Array.newInstance(original.getClass().getComponentType(), resultLength));
            System.arraycopy(original, start, result, 0, copyLength);
            return result;
        }
    }

    public void trimToSize() {
        this.modCount++;
        if (this.size < this.data.length) {
            this.data = copyOf(this.data, this.size);
        }
    }

    public int expurge() {
        int j;
        while (this.queue.poll() != null) {
            this.enquedElement = true;
        }
        if (this.enquedElement) {
            j = 0;
            for (int i = 0; i < this.size; i++) {
                Reference<? extends T> ref = (Reference) this.data[i];
                if (ref == null || ref.isEnqueued() || ref.get() == null) {
                    if (ref != null) {
                        ref.clear();
                    }
                    this.data[i] = null;
                } else {
                    if (i != j) {
                        this.data[j] = this.data[i];
                        this.data[i] = null;
                    }
                    j++;
                }
            }
            this.enquedElement = false;
        } else {
            j = this.size;
        }
        while (this.queue.poll() != null) {
            this.enquedElement = true;
        }
        int oldSize = this.size;
        this.size = j;
        if (j < oldSize) {
            fireReferenceRelease(oldSize - j);
        }
        return this.size;
    }

    /* access modifiers changed from: protected */
    public void assertRange(int index, boolean allowLast) {
        int csize = expurge();
        if (index < 0) {
            throw new IndexOutOfBoundsException("invalid negative value: " + Integer.toString(index));
        } else if (allowLast && index > csize) {
            throw new IndexOutOfBoundsException("index>" + csize + ": " + Integer.toString(index));
        } else if (!allowLast && index >= csize) {
            throw new IndexOutOfBoundsException("index>=" + csize + ": " + Integer.toString(index));
        }
    }

    public int size() {
        return expurge();
    }

    public T get(int index) {
        T value;
        do {
            assertRange(index, false);
            value = ((Reference) this.data[index]).get();
        } while (value == null);
        return unmaskNull(value);
    }

    public T set(int index, T element) {
        Reference<T> ref;
        T oldValue;
        do {
            assertRange(index, false);
            ref = (Reference) this.data[index];
            oldValue = ref.get();
        } while (oldValue == null);
        ref.clear();
        this.data[index] = createRef(element);
        this.modCount++;
        return unmaskNull(oldValue);
    }

    public void add(int index, T element) {
        assertRange(index, true);
        ensureCapacity(this.size + 1);
        System.arraycopy(this.data, index, this.data, index + 1, this.size - index);
        this.data[index] = createRef(element);
        this.size++;
        this.modCount++;
    }

    public T remove(int index) {
        Reference<T> ref;
        T oldValue;
        do {
            assertRange(index, false);
            ref = (Reference) this.data[index];
            oldValue = ref.get();
        } while (oldValue == null);
        ref.clear();
        System.arraycopy(this.data, index + 1, this.data, index, (this.size - index) - 1);
        this.data[this.size - 1] = null;
        this.size--;
        this.modCount++;
        return unmaskNull(oldValue);
    }

    public void addReferenceListener(ReferenceListener listener) {
        if (this.listeners == null) {
            this.listeners = new LinkedList();
        }
        List<ReferenceListener> list = this.listeners;
        synchronized (list) {
            list.add(listener);
        }
    }

    public void removeReferenceListener(ReferenceListener listener) {
        List<ReferenceListener> list = this.listeners;
        if (list != null) {
            synchronized (list) {
                list.remove(listener);
                if (list.isEmpty()) {
                    this.listeners = null;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void fireReferenceRelease(int released) {
        List<ReferenceListener> list = this.listeners;
        if (list != null && !list.isEmpty()) {
            for (ReferenceListener listener : list) {
                listener.referenceReleased(released);
            }
        }
    }
}
