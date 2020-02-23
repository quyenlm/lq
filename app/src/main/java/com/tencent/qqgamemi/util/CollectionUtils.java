package com.tencent.qqgamemi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtils {
    public static <E> int sizeOf(Collection<E> collection) {
        if (collection == null) {
            return -1;
        }
        return collection.size();
    }

    public static <E> int safeSizeOf(Collection<E> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static <T> int sizeOf(T[] array) {
        if (array == null) {
            return -1;
        }
        return array.length;
    }

    public static <T> boolean isEmptyArray(T[] array) {
        return array == null || array.length == 0;
    }

    public static <E> boolean isEmptyList(Collection<E> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

    public static <T> T getItem(List<T> list, int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.size() == 0;
    }

    public static boolean isEmpty(Object[] objs) {
        return objs == null || objs.length == 0;
    }

    public static <T> T get(Collection<T> c, int index) {
        if (isEmpty((Collection<?>) c) || index < 0 || index >= c.size()) {
            return null;
        }
        if (c instanceof List) {
            return ((List) c).get(index);
        }
        return new ArrayList<>(c).get(index);
    }

    public static <T> ArrayList<T> convertToArrayList(List<T> list) {
        if (list instanceof ArrayList) {
            return (ArrayList) list;
        }
        ArrayList<T> arrayList = new ArrayList<>();
        for (T item : list) {
            arrayList.add(item);
        }
        return arrayList;
    }
}
