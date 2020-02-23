package com.tencent.component.utils.collections;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class WeakValueHashMap extends HashMap {
    private Set entrySet = null;
    /* access modifiers changed from: private */
    public Set hashEntrySet = null;
    /* access modifiers changed from: private */
    public ReferenceQueue queue = new ReferenceQueue();
    private transient Collection values = null;

    public int size() {
        return entrySet().size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        processQueue();
        return super.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return super.containsValue(WeakValue.create(value));
    }

    public Object get(Object key) {
        return getReferenceObject((WeakReference) super.get(key));
    }

    public Object put(Object key, Object value) {
        processQueue();
        return getReferenceObject((WeakValue) super.put(key, WeakValue.create(key, value, this.queue)));
    }

    public Object remove(Object key) {
        return getReferenceObject((WeakReference) super.remove(key));
    }

    private final Object getReferenceObject(WeakReference ref) {
        if (ref == null) {
            return null;
        }
        return ref.get();
    }

    /* access modifiers changed from: private */
    public void processQueue() {
        while (true) {
            WeakValue wv = (WeakValue) this.queue.poll();
            if (wv != null) {
                super.remove(wv.key);
            } else {
                return;
            }
        }
    }

    private static class WeakValue extends WeakReference {
        /* access modifiers changed from: private */
        public Object key;

        private WeakValue(Object value) {
            super(value);
        }

        /* access modifiers changed from: private */
        public static WeakValue create(Object value) {
            if (value == null) {
                return null;
            }
            return new WeakValue(value);
        }

        private WeakValue(Object key2, Object value, ReferenceQueue queue) {
            super(value, queue);
            this.key = key2;
        }

        /* access modifiers changed from: private */
        public static WeakValue create(Object key2, Object value, ReferenceQueue queue) {
            if (value == null) {
                return null;
            }
            return new WeakValue(key2, value, queue);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WeakValue)) {
                return false;
            }
            Object ref1 = get();
            Object ref2 = ((WeakValue) obj).get();
            if (ref1 == ref2) {
                return true;
            }
            if (ref1 == null || ref2 == null) {
                return false;
            }
            return ref1.equals(ref2);
        }

        public int hashCode() {
            Object ref = get();
            if (ref == null) {
                return 0;
            }
            return ref.hashCode();
        }
    }

    private class Entry implements Map.Entry {
        private Map.Entry ent;
        private Object value;

        Entry(Map.Entry ent2, Object value2) {
            this.ent = ent2;
            this.value = value2;
        }

        public Object getKey() {
            return this.ent.getKey();
        }

        public Object getValue() {
            return this.value;
        }

        public Object setValue(Object value2) {
            Object oldValue = this.value;
            this.value = value2;
            this.ent.setValue(WeakValue.create(getKey(), value2, WeakValueHashMap.this.queue));
            return oldValue;
        }

        private boolean valEquals(Object o1, Object o2) {
            if (o1 == null) {
                return o2 == null;
            }
            return o1.equals(o2);
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            if (!valEquals(this.ent.getKey(), e.getKey()) || !valEquals(this.value, e.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = 0;
            Object k = this.ent.getKey();
            int hashCode = k == null ? 0 : k.hashCode();
            if (this.value != null) {
                i = this.value.hashCode();
            }
            return hashCode ^ i;
        }
    }

    private class EntrySet extends AbstractSet {
        private EntrySet() {
        }

        public Iterator iterator() {
            WeakValueHashMap.this.processQueue();
            return new Iterator() {
                Iterator hashIterator = WeakValueHashMap.this.hashEntrySet.iterator();
                Entry next = null;

                public boolean hasNext() {
                    if (!this.hashIterator.hasNext()) {
                        return false;
                    }
                    Map.Entry ent = (Map.Entry) this.hashIterator.next();
                    WeakValue wv = (WeakValue) ent.getValue();
                    this.next = new Entry(ent, wv == null ? null : wv.get());
                    return true;
                }

                public Object next() {
                    if (this.next != null || hasNext()) {
                        Entry e = this.next;
                        this.next = null;
                        return e;
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    this.hashIterator.remove();
                }
            };
        }

        public boolean isEmpty() {
            return !iterator().hasNext();
        }

        public int size() {
            int j = 0;
            Iterator i = iterator();
            while (i.hasNext()) {
                j++;
                i.next();
            }
            return j;
        }

        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object ek = e.getKey();
            Object ev = e.getValue();
            Object hv = WeakValueHashMap.this.get(ek);
            if (hv == null) {
                if (ev != null || !WeakValueHashMap.this.containsKey(ek)) {
                    return false;
                }
                WeakValueHashMap.this.remove(ek);
                return true;
            } else if (!hv.equals(ev)) {
                return false;
            } else {
                WeakValueHashMap.this.remove(ek);
                return true;
            }
        }

        public int hashCode() {
            int h = 0;
            for (Map.Entry ent : WeakValueHashMap.this.hashEntrySet) {
                WeakValue wv = (WeakValue) ent.getValue();
                if (wv != null) {
                    Object k = ent.getKey();
                    h += (k == null ? 0 : k.hashCode()) ^ wv.hashCode();
                }
            }
            return h;
        }
    }

    public Set entrySet() {
        if (this.entrySet == null) {
            this.hashEntrySet = super.entrySet();
            this.entrySet = new EntrySet();
        }
        return this.entrySet;
    }

    public Collection values() {
        if (this.values == null) {
            this.values = new AbstractCollection() {
                public Iterator iterator() {
                    return new Iterator() {
                        private Iterator i = WeakValueHashMap.this.entrySet().iterator();

                        public boolean hasNext() {
                            return this.i.hasNext();
                        }

                        public Object next() {
                            return ((Entry) this.i.next()).getValue();
                        }

                        public void remove() {
                            this.i.remove();
                        }
                    };
                }

                public int size() {
                    return WeakValueHashMap.this.size();
                }

                public boolean contains(Object v) {
                    return WeakValueHashMap.this.containsValue(v);
                }
            };
        }
        return this.values;
    }
}
