package com.tencent.mna.base.utils;

import java.util.Random;

/* compiled from: CommonUtil */
public final class c {
    private static Random a = new Random();

    public static int a(int i, int i2) {
        return a.nextInt(i2) + i;
    }

    public static boolean a() {
        return a.nextBoolean();
    }

    public static long a(int i) {
        return ((long) i) & 4294967295L;
    }
}
