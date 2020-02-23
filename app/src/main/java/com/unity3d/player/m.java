package com.unity3d.player;

import java.lang.reflect.Method;
import java.util.HashMap;

final class m {
    private HashMap a = new HashMap();
    private Class b = null;
    private Object c = null;

    class a {
        public Class[] a;
        public Method b = null;

        public a(Class[] clsArr) {
            this.a = clsArr;
        }
    }

    public m(Class cls, Object obj) {
        this.b = cls;
        this.c = obj;
    }

    private void a(String str, a aVar) {
        try {
            aVar.b = this.b.getMethod(str, aVar.a);
        } catch (Exception e) {
            e.Log(6, "Exception while trying to get method " + str + ". " + e.getLocalizedMessage());
            aVar.b = null;
        }
    }

    public final Object a(String str, Object... objArr) {
        Object obj;
        if (!this.a.containsKey(str)) {
            e.Log(6, "No definition for method " + str + " can be found");
            return null;
        }
        a aVar = (a) this.a.get(str);
        if (aVar.b == null) {
            a(str, aVar);
        }
        if (aVar.b == null) {
            e.Log(6, "Unable to create method: " + str);
            return null;
        }
        try {
            obj = objArr.length == 0 ? aVar.b.invoke(this.c, new Object[0]) : aVar.b.invoke(this.c, objArr);
        } catch (Exception e) {
            e.Log(6, "Error trying to call delegated method " + str + ". " + e.getLocalizedMessage());
            obj = null;
        }
        return obj;
    }

    public final void a(String str, Class[] clsArr) {
        this.a.put(str, new a(clsArr));
    }
}
