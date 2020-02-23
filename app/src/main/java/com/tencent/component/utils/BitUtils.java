package com.tencent.component.utils;

public final class BitUtils {
    public static final int add(int source, int sub) {
        return source | sub;
    }

    public static final boolean has(int source, int sub) {
        return sub == (source & sub);
    }

    public static final int remove(int source, int sub) {
        return (source & sub) ^ source;
    }

    public static final int log2(int source) {
        return (int) (Math.log((double) source) / Math.log(2.0d));
    }
}
