package com.tencent.bugly.proguard;

import java.util.ArrayList;

/* compiled from: BUGLY */
public final class ao extends k implements Cloneable {
    private static ArrayList<an> b;
    public ArrayList<an> a = null;

    public final void a(j jVar) {
        jVar.a(this.a, 0);
    }

    public final void a(i iVar) {
        if (b == null) {
            b = new ArrayList<>();
            b.add(new an());
        }
        this.a = (ArrayList) iVar.a(b, 0, true);
    }

    public final void a(StringBuilder sb, int i) {
    }
}
