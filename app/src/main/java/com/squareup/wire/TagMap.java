package com.squareup.wire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

abstract class TagMap<T> {
    private static final Comparator<? super Map.Entry<Integer, ?>> COMPARATOR = new Comparator<Map.Entry<Integer, ?>>() {
        public int compare(Map.Entry<Integer, ?> o1, Map.Entry<Integer, ?> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    };
    private static final float RATIO_THRESHOLD = 0.75f;
    private static final int SIZE_THRESHOLD = 64;
    List<T> values;

    public abstract boolean containsKey(int i);

    public abstract T get(int i);

    public static <T> TagMap<T> of(Map<Integer, T> map) {
        int maxTag = maxTag(map);
        if (isCompact(map.size(), maxTag)) {
            return Compact.compactTagMapOf(map, maxTag);
        }
        return Sparse.sparseTagMapOf(map);
    }

    private static boolean isCompact(int size, int maxTag) {
        return maxTag <= 64 || ((float) size) / ((float) maxTag) > RATIO_THRESHOLD;
    }

    private static <T> int maxTag(Map<Integer, T> map) {
        int maxTag = -1;
        for (Integer intValue : map.keySet()) {
            int tag = intValue.intValue();
            if (tag > maxTag) {
                maxTag = tag;
            }
        }
        return maxTag;
    }

    private static <T> List<T> sortedValues(Map<Integer, T> map) {
        TreeSet<Map.Entry<Integer, T>> entries = new TreeSet<>(COMPARATOR);
        entries.addAll(map.entrySet());
        List<T> sortedValues = new ArrayList<>();
        Iterator<Map.Entry<Integer, T>> it = entries.iterator();
        while (it.hasNext()) {
            sortedValues.add(it.next().getValue());
        }
        return sortedValues;
    }

    protected TagMap(Map<Integer, T> map) {
        this.values = sortedValues(map);
    }

    public Collection<T> values() {
        return this.values;
    }

    static final class Compact<T> extends TagMap<T> {
        Object[] elementsByTag;
        int maxTag = -1;

        public static <T> Compact<T> compactTagMapOf(Map<Integer, T> map, int maxTag2) {
            return new Compact<>(map, maxTag2);
        }

        private Compact(Map<Integer, T> map, int maxTag2) {
            super(map);
            this.maxTag = maxTag2;
            this.elementsByTag = new Object[(maxTag2 + 1)];
            for (Map.Entry<Integer, T> entry : map.entrySet()) {
                Integer key = entry.getKey();
                if (key.intValue() <= 0) {
                    throw new IllegalArgumentException("Input map key is negative or zero");
                }
                this.elementsByTag[key.intValue()] = entry.getValue();
            }
        }

        public T get(int tag) {
            if (tag > this.maxTag) {
                return null;
            }
            return this.elementsByTag[tag];
        }

        public boolean containsKey(int tag) {
            if (tag <= this.maxTag && this.elementsByTag[tag] != null) {
                return true;
            }
            return false;
        }
    }

    static final class Sparse<T> extends TagMap<T> {
        Map<Integer, T> map;

        public static <T> Sparse<T> sparseTagMapOf(Map<Integer, T> map2) {
            return new Sparse<>(map2);
        }

        private Sparse(Map<Integer, T> map2) {
            super(map2);
            this.map = map2;
        }

        public T get(int tag) {
            return this.map.get(Integer.valueOf(tag));
        }

        public boolean containsKey(int tag) {
            return this.map.containsKey(Integer.valueOf(tag));
        }
    }
}
