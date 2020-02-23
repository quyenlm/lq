package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;

public class DateSorter {
    public static int DAY_COUNT = 5;
    private android.webkit.DateSorter a;
    private IX5DateSorter b;

    static {
        if (a()) {
        }
    }

    public DateSorter(Context context) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            this.a = new android.webkit.DateSorter(context);
        } else {
            this.b = a2.c().h(context);
        }
    }

    private static boolean a() {
        br a2 = br.a();
        return a2 != null && a2.b();
    }

    public long getBoundary(int i) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? this.a.getBoundary(i) : this.b.getBoundary(i);
    }

    public int getIndex(long j) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? this.a.getIndex(j) : this.b.getIndex(j);
    }

    public String getLabel(int i) {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? this.a.getLabel(i) : this.b.getLabel(i);
    }
}
