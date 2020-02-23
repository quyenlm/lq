package com.google.android.gms.vision;

import android.util.SparseArray;

public final class zzc {
    private static int zzbMY = 0;
    private static final Object zzuF = new Object();
    private SparseArray<Integer> zzbMZ = new SparseArray<>();
    private SparseArray<Integer> zzbNa = new SparseArray<>();

    public final int zzbL(int i) {
        int i2;
        synchronized (zzuF) {
            Integer num = this.zzbMZ.get(i);
            if (num != null) {
                i2 = num.intValue();
            } else {
                i2 = zzbMY;
                zzbMY++;
                this.zzbMZ.append(i, Integer.valueOf(i2));
                this.zzbNa.append(i2, Integer.valueOf(i));
            }
        }
        return i2;
    }

    public final int zzbM(int i) {
        int intValue;
        synchronized (zzuF) {
            intValue = this.zzbNa.get(i).intValue();
        }
        return intValue;
    }
}
