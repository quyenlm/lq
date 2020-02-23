package com.google.android.gms.internal;

import java.util.Random;

public final class zc {
    private static long zzcjA = 0;
    private static final int[] zzcjB = new int[12];
    private static final Random zzcjz = new Random();

    private static void zzJH() {
        int i = 11;
        while (i >= 0) {
            if (zzcjB[i] != 63) {
                zzcjB[i] = zzcjB[i] + 1;
                return;
            } else {
                zzcjB[i] = 0;
                i--;
            }
        }
    }

    public static synchronized String zzaz(long j) {
        String sb;
        synchronized (zc.class) {
            boolean z = j == zzcjA;
            zzcjA = j;
            char[] cArr = new char[8];
            StringBuilder sb2 = new StringBuilder(20);
            for (int i = 7; i >= 0; i--) {
                cArr[i] = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt((int) (j % 64));
                j /= 64;
            }
            sb2.append(cArr);
            if (!z) {
                for (int i2 = 0; i2 < 12; i2++) {
                    zzcjB[i2] = zzcjz.nextInt(64);
                }
            } else {
                zzJH();
            }
            for (int i3 = 0; i3 < 12; i3++) {
                sb2.append("-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".charAt(zzcjB[i3]));
            }
            sb = sb2.toString();
        }
        return sb;
    }
}
