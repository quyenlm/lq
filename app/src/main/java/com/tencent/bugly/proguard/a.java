package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.vk.sdk.api.VKApiConst;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: BUGLY */
public class a {
    private static Proxy e;
    protected HashMap<String, HashMap<String, byte[]>> a = new HashMap<>();
    protected String b;
    i c;
    private HashMap<String, Object> d;

    public static aj a(int i) {
        if (i == 1) {
            return new ai();
        }
        if (i == 3) {
            return new ah();
        }
        return null;
    }

    a() {
        new HashMap();
        this.d = new HashMap<>();
        this.b = "GBK";
        this.c = new i();
    }

    public static void a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        }
    }

    public static void a(InetAddress inetAddress, int i) {
        if (inetAddress == null) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i));
        }
    }

    public void a(String str) {
        this.b = str;
    }

    public static at a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        at atVar = new at();
        atVar.a = userInfoBean.e;
        atVar.e = userInfoBean.j;
        atVar.d = userInfoBean.c;
        atVar.c = userInfoBean.d;
        atVar.g = com.tencent.bugly.crashreport.common.info.a.b().i();
        atVar.h = userInfoBean.o == 1;
        switch (userInfoBean.b) {
            case 1:
                atVar.b = 1;
                break;
            case 2:
                atVar.b = 4;
                break;
            case 3:
                atVar.b = 2;
                break;
            case 4:
                atVar.b = 3;
                break;
            default:
                if (userInfoBean.b >= 10 && userInfoBean.b < 20) {
                    atVar.b = (byte) userInfoBean.b;
                    break;
                } else {
                    x.e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.b));
                    return null;
                }
                break;
        }
        atVar.f = new HashMap();
        if (userInfoBean.p >= 0) {
            atVar.f.put("C01", new StringBuilder().append(userInfoBean.p).toString());
        }
        if (userInfoBean.q >= 0) {
            atVar.f.put("C02", new StringBuilder().append(userInfoBean.q).toString());
        }
        if (userInfoBean.r != null && userInfoBean.r.size() > 0) {
            for (Map.Entry next : userInfoBean.r.entrySet()) {
                atVar.f.put("C03_" + ((String) next.getKey()), next.getValue());
            }
        }
        if (userInfoBean.s != null && userInfoBean.s.size() > 0) {
            for (Map.Entry next2 : userInfoBean.s.entrySet()) {
                atVar.f.put("C04_" + ((String) next2.getKey()), next2.getValue());
            }
        }
        atVar.f.put("A36", new StringBuilder().append(!userInfoBean.l).toString());
        atVar.f.put("F02", new StringBuilder().append(userInfoBean.g).toString());
        atVar.f.put("F03", new StringBuilder().append(userInfoBean.h).toString());
        atVar.f.put("F04", userInfoBean.j);
        atVar.f.put("F05", new StringBuilder().append(userInfoBean.i).toString());
        atVar.f.put("F06", userInfoBean.m);
        atVar.f.put("F10", new StringBuilder().append(userInfoBean.k).toString());
        x.c("summary type %d vm:%d", Byte.valueOf(atVar.b), Integer.valueOf(atVar.f.size()));
        return atVar;
    }

    public static Proxy b() {
        return e;
    }

    public static String a(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayList.size(); i++) {
            String str = arrayList.get(i);
            if (str.equals("java.lang.Integer") || str.equals("int")) {
                str = "int32";
            } else if (str.equals("java.lang.Boolean") || str.equals("boolean")) {
                str = "bool";
            } else if (str.equals("java.lang.Byte") || str.equals("byte")) {
                str = "char";
            } else if (str.equals("java.lang.Double") || str.equals("double")) {
                str = "double";
            } else if (str.equals("java.lang.Float") || str.equals("float")) {
                str = "float";
            } else if (str.equals("java.lang.Long") || str.equals(VKApiConst.LONG)) {
                str = "int64";
            } else if (str.equals("java.lang.Short") || str.equals("short")) {
                str = "short";
            } else if (str.equals("java.lang.Character")) {
                throw new IllegalArgumentException("can not support java.lang.Character");
            } else if (str.equals("java.lang.String")) {
                str = "string";
            } else if (str.equals("java.util.List")) {
                str = HttpRequestParams.NOTICE_LIST;
            } else if (str.equals("java.util.Map")) {
                str = "map";
            }
            arrayList.set(i, str);
        }
        Collections.reverse(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str2 = arrayList.get(i2);
            if (str2.equals(HttpRequestParams.NOTICE_LIST)) {
                arrayList.set(i2 - 1, "<" + arrayList.get(i2 - 1));
                arrayList.set(0, arrayList.get(0) + ">");
            } else if (str2.equals("map")) {
                arrayList.set(i2 - 1, "<" + arrayList.get(i2 - 1) + ",");
                arrayList.set(0, arrayList.get(0) + ">");
            } else if (str2.equals("Array")) {
                arrayList.set(i2 - 1, "<" + arrayList.get(i2 - 1));
                arrayList.set(0, arrayList.get(0) + ">");
            }
        }
        Collections.reverse(arrayList);
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next());
        }
        return stringBuffer.toString();
    }

    public <T> void a(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        } else {
            j jVar = new j();
            jVar.a(this.b);
            jVar.a((Object) t, 0);
            byte[] a2 = l.a(jVar.a());
            HashMap hashMap = new HashMap(1);
            ArrayList arrayList = new ArrayList(1);
            a((ArrayList<String>) arrayList, (Object) t);
            hashMap.put(a((ArrayList<String>) arrayList), a2);
            this.d.remove(str);
            this.a.put(str, hashMap);
        }
    }

    public static au a(List<UserInfoBean> list, int i) {
        if (list == null || list.size() == 0) {
            return null;
        }
        com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
        if (b2 == null) {
            return null;
        }
        b2.t();
        au auVar = new au();
        auVar.b = b2.d;
        auVar.c = b2.h();
        ArrayList<at> arrayList = new ArrayList<>();
        for (UserInfoBean a2 : list) {
            at a3 = a(a2);
            if (a3 != null) {
                arrayList.add(a3);
            }
        }
        auVar.d = arrayList;
        auVar.e = new HashMap();
        auVar.e.put("A7", b2.f);
        auVar.e.put("A6", b2.s());
        auVar.e.put("A5", b2.r());
        auVar.e.put("A2", new StringBuilder().append(b2.p()).toString());
        auVar.e.put("A1", new StringBuilder().append(b2.p()).toString());
        auVar.e.put("A24", b2.h);
        auVar.e.put("A17", new StringBuilder().append(b2.q()).toString());
        auVar.e.put("A15", b2.w());
        auVar.e.put("A13", new StringBuilder().append(b2.x()).toString());
        auVar.e.put("F08", b2.v);
        auVar.e.put("F09", b2.w);
        Map<String, String> G = b2.G();
        if (G != null && G.size() > 0) {
            for (Map.Entry next : G.entrySet()) {
                auVar.e.put("C04_" + ((String) next.getKey()), next.getValue());
            }
        }
        switch (i) {
            case 1:
                auVar.a = 1;
                break;
            case 2:
                auVar.a = 2;
                break;
            default:
                x.e("unknown up type %d ", Integer.valueOf(i));
                return null;
        }
        return auVar;
    }

    public static <T extends k> T a(byte[] bArr, Class<T> cls) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            T t = (k) cls.newInstance();
            i iVar = new i(bArr);
            iVar.a("utf-8");
            t.a(iVar);
            return t;
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static ap a(Context context, int i, byte[] bArr) {
        com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
        StrategyBean c2 = com.tencent.bugly.crashreport.common.strategy.a.a().c();
        if (b2 == null || c2 == null) {
            x.e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            ap apVar = new ap();
            synchronized (b2) {
                apVar.a = 1;
                apVar.b = b2.f();
                apVar.c = b2.c;
                apVar.d = b2.j;
                apVar.e = b2.l;
                b2.getClass();
                apVar.f = "3.0.0";
                apVar.g = i;
                apVar.h = bArr == null ? "".getBytes() : bArr;
                apVar.i = b2.g;
                apVar.j = b2.h;
                apVar.k = new HashMap();
                apVar.l = b2.e();
                apVar.m = c2.p;
                apVar.o = b2.h();
                apVar.p = b.c(context);
                apVar.q = System.currentTimeMillis();
                apVar.r = b2.k();
                apVar.s = b2.j();
                apVar.t = b2.m();
                apVar.u = b2.l();
                apVar.v = b2.n();
                apVar.w = apVar.p;
                b2.getClass();
                apVar.n = "com.tencent.bugly";
                apVar.k.put("A26", b2.y());
                apVar.k.put("A60", b2.z());
                apVar.k.put("A61", b2.A());
                apVar.k.put("A62", new StringBuilder().append(b2.R()).toString());
                apVar.k.put("A63", new StringBuilder().append(b2.S()).toString());
                apVar.k.put("F11", new StringBuilder().append(b2.z).toString());
                apVar.k.put("F12", new StringBuilder().append(b2.y).toString());
                apVar.k.put("G1", b2.u());
                apVar.k.put("A64", b2.T());
                if (b2.B) {
                    apVar.k.put("G2", b2.L());
                    apVar.k.put("G3", b2.M());
                    apVar.k.put("G4", b2.N());
                    apVar.k.put("G5", b2.O());
                    apVar.k.put("G6", b2.P());
                    apVar.k.put("G7", Long.toString(b2.Q()));
                }
                apVar.k.put("D3", b2.k);
                if (com.tencent.bugly.b.b != null) {
                    for (com.tencent.bugly.a next : com.tencent.bugly.b.b) {
                        if (!(next.versionKey == null || next.version == null)) {
                            apVar.k.put(next.versionKey, next.version);
                        }
                    }
                }
                apVar.k.put("G15", z.b("G15", ""));
                apVar.k.put("D4", z.b("D4", "0"));
            }
            u a2 = u.a();
            if (!(a2 == null || a2.a || bArr == null)) {
                apVar.h = z.a(apVar.h, 2, 1, c2.u);
                if (apVar.h == null) {
                    x.e("reqPkg sbuffer error!", new Object[0]);
                    return null;
                }
            }
            Map<String, String> F = b2.F();
            if (F != null) {
                for (Map.Entry next2 : F.entrySet()) {
                    apVar.k.put(next2.getKey(), next2.getValue());
                }
            }
            return apVar;
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private void a(ArrayList<String> arrayList, Object obj) {
        if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            } else if (Array.getLength(obj) > 0) {
                arrayList.add("java.util.List");
                a(arrayList, Array.get(obj, 0));
            } else {
                arrayList.add("Array");
                arrayList.add("?");
            }
        } else if (obj instanceof Array) {
            throw new IllegalArgumentException("can not support Array, please use List");
        } else if (obj instanceof List) {
            arrayList.add("java.util.List");
            List list = (List) obj;
            if (list.size() > 0) {
                a(arrayList, list.get(0));
            } else {
                arrayList.add("?");
            }
        } else if (obj instanceof Map) {
            arrayList.add("java.util.Map");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Object next = map.keySet().iterator().next();
                Object obj2 = map.get(next);
                arrayList.add(next.getClass().getName());
                a(arrayList, obj2);
                return;
            }
            arrayList.add("?");
            arrayList.add("?");
        } else {
            arrayList.add(obj.getClass().getName());
        }
    }

    public byte[] a() {
        j jVar = new j(0);
        jVar.a(this.b);
        jVar.a(this.a, 0);
        return l.a(jVar.a());
    }

    public void a(byte[] bArr) {
        this.c.a(bArr);
        this.c.a(this.b);
        HashMap hashMap = new HashMap(1);
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("", new byte[0]);
        hashMap.put("", hashMap2);
        this.a = this.c.a(hashMap, 0, false);
    }

    public static byte[] a(Object obj) {
        try {
            d dVar = new d();
            dVar.c();
            dVar.a("utf-8");
            dVar.b(1);
            dVar.b("RqdServer");
            dVar.c("sync");
            dVar.a(ProductAction.ACTION_DETAIL, obj);
            return dVar.a();
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static aq a(byte[] bArr, boolean z) {
        aq aqVar;
        if (bArr != null) {
            try {
                d dVar = new d();
                dVar.c();
                dVar.a("utf-8");
                dVar.a(bArr);
                Object b2 = dVar.b(ProductAction.ACTION_DETAIL, new aq());
                if (aq.class.isInstance(b2)) {
                    aqVar = aq.class.cast(b2);
                } else {
                    aqVar = null;
                }
                if (z || aqVar == null || aqVar.c == null || aqVar.c.length <= 0) {
                    return aqVar;
                }
                x.c("resp buf %d", Integer.valueOf(aqVar.c.length));
                aqVar.c = z.b(aqVar.c, 2, 1, StrategyBean.d);
                if (aqVar.c != null) {
                    return aqVar;
                }
                x.e("resp sbuffer error!", new Object[0]);
                return null;
            } catch (Throwable th) {
                if (!x.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(k kVar) {
        try {
            j jVar = new j();
            jVar.a("utf-8");
            kVar.a(jVar);
            return jVar.b();
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
