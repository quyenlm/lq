package com.tencent.gsdk.utils.b;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: ReporterFactory */
final class g {
    private static final int[] a = {1, 2, 4};
    private static final SparseArray<e> b = new SparseArray<>(3);

    static {
        b.put(1, new b());
        b.put(2, new h());
        b.put(4, new c());
    }

    public static List<e> a(int i) {
        ArrayList arrayList = null;
        for (int i2 : a) {
            if ((i2 & i) != 0) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(b.get(i2));
            }
        }
        return arrayList != null ? arrayList : Collections.emptyList();
    }
}
