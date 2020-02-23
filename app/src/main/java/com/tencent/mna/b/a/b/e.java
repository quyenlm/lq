package com.tencent.mna.b.a.b;

import com.tencent.mna.b.a.c.c;
import com.tencent.mna.base.utils.h;

/* compiled from: SpeedComparatorContext */
public class e {
    private b a;

    public e(b bVar) {
        this.a = bVar;
    }

    public int a(c cVar, c cVar2) {
        if (this.a != null) {
            return this.a.compare(cVar, cVar2);
        }
        h.d("SpeedComparatorContext exception: comparator is null");
        return 0;
    }
}
