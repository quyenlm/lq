package com.unity3d.player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

final class ReflectionHelper {
    protected static boolean LOG = false;
    protected static final boolean LOGV = false;
    private static a[] a = new a[4096];

    private static class a {
        public volatile Member a;
        private final Class b;
        private final String c;
        private final String d;
        private final int e = (((((this.b.hashCode() + 527) * 31) + this.c.hashCode()) * 31) + this.d.hashCode());

        a(Class cls, String str, String str2) {
            this.b = cls;
            this.c = str;
            this.d = str2;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.e == aVar.e && this.d.equals(aVar.d) && this.c.equals(aVar.c) && this.b.equals(aVar.b);
        }

        public final int hashCode() {
            return this.e;
        }
    }

    ReflectionHelper() {
    }

    private static float a(Class cls, Class cls2) {
        if (cls.equals(cls2)) {
            return 1.0f;
        }
        if (!cls.isPrimitive() && !cls2.isPrimitive()) {
            try {
                if (cls.asSubclass(cls2) != null) {
                    return 0.5f;
                }
            } catch (ClassCastException e) {
            }
            try {
                if (cls2.asSubclass(cls) != null) {
                    return 0.1f;
                }
            } catch (ClassCastException e2) {
            }
        }
        return 0.0f;
    }

    private static float a(Class cls, Class[] clsArr, Class[] clsArr2) {
        if (clsArr2.length == 0) {
            return 0.1f;
        }
        if ((clsArr == null ? 0 : clsArr.length) + 1 != clsArr2.length) {
            return 0.0f;
        }
        float f = 1.0f;
        if (clsArr != null) {
            int length = clsArr.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                f *= a(clsArr[i], clsArr2[i2]);
                i++;
                i2++;
            }
        }
        return f * a(cls, clsArr2[clsArr2.length - 1]);
    }

    private static Class a(String str, int[] iArr) {
        while (true) {
            if (iArr[0] >= str.length()) {
                break;
            }
            int i = iArr[0];
            iArr[0] = i + 1;
            char charAt = str.charAt(i);
            if (charAt != '(' && charAt != ')') {
                if (charAt == 'L') {
                    int indexOf = str.indexOf(59, iArr[0]);
                    if (indexOf != -1) {
                        String substring = str.substring(iArr[0], indexOf);
                        iArr[0] = indexOf + 1;
                        try {
                            return Class.forName(substring.replace('/', '.'));
                        } catch (ClassNotFoundException e) {
                        }
                    }
                } else if (charAt == 'Z') {
                    return Boolean.TYPE;
                } else {
                    if (charAt == 'I') {
                        return Integer.TYPE;
                    }
                    if (charAt == 'F') {
                        return Float.TYPE;
                    }
                    if (charAt == 'V') {
                        return Void.TYPE;
                    }
                    if (charAt == 'B') {
                        return Byte.TYPE;
                    }
                    if (charAt == 'S') {
                        return Short.TYPE;
                    }
                    if (charAt == 'J') {
                        return Long.TYPE;
                    }
                    if (charAt == 'D') {
                        return Double.TYPE;
                    }
                    if (charAt == '[') {
                        return Array.newInstance(a(str, iArr), 0).getClass();
                    }
                    e.Log(5, "! parseType; " + charAt + " is not known!");
                }
            }
        }
        return null;
    }

    private static void a(a aVar, Member member) {
        aVar.a = member;
        a[aVar.hashCode() & (a.length - 1)] = aVar;
    }

    private static boolean a(a aVar) {
        a aVar2 = a[aVar.hashCode() & (a.length - 1)];
        if (!aVar.equals(aVar2)) {
            return false;
        }
        aVar.a = aVar2.a;
        return true;
    }

    private static Class[] a(String str) {
        Class a2;
        int[] iArr = {0};
        ArrayList arrayList = new ArrayList();
        while (iArr[0] < str.length() && (a2 = a(str, iArr)) != null) {
            arrayList.add(a2);
        }
        Class[] clsArr = new Class[arrayList.size()];
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            clsArr[i] = (Class) it.next();
            i++;
        }
        return clsArr;
    }

    protected static Constructor getConstructorID(Class cls, String str) {
        Constructor constructor;
        Constructor constructor2;
        Constructor constructor3 = null;
        a aVar = new a(cls, "", str);
        if (a(aVar)) {
            constructor2 = (Constructor) aVar.a;
        } else {
            Class[] a2 = a(str);
            float f = 0.0f;
            Constructor[] constructors = cls.getConstructors();
            int length = constructors.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    constructor = constructor3;
                    break;
                }
                constructor = constructors[i];
                float a3 = a(Void.TYPE, constructor.getParameterTypes(), a2);
                if (a3 > f) {
                    if (a3 == 1.0f) {
                        break;
                    }
                } else {
                    a3 = f;
                    constructor = constructor3;
                }
                i++;
                f = a3;
                constructor3 = constructor;
            }
            a(aVar, (Member) constructor);
            constructor2 = constructor;
        }
        if (constructor2 != null) {
            return constructor2;
        }
        throw new NoSuchMethodError("<init>" + str + " in class " + cls.getName());
    }

    protected static Field getFieldID(Class cls, String str, String str2, boolean z) {
        Field field;
        float f;
        Field field2 = null;
        a aVar = new a(cls, str, str2);
        if (a(aVar)) {
            field2 = (Field) aVar.a;
        } else {
            Class[] a2 = a(str2);
            float f2 = 0.0f;
            while (true) {
                if (cls == null) {
                    break;
                }
                Field[] declaredFields = cls.getDeclaredFields();
                int length = declaredFields.length;
                int i = 0;
                Field field3 = field2;
                while (true) {
                    if (i >= length) {
                        field = field3;
                        break;
                    }
                    field = declaredFields[i];
                    if (z == Modifier.isStatic(field.getModifiers()) && field.getName().compareTo(str) == 0) {
                        f = a((Class) field.getType(), (Class[]) null, a2);
                        if (f > f2) {
                            if (f == 1.0f) {
                                f2 = f;
                                break;
                            }
                            i++;
                            f2 = f;
                            field3 = field;
                        }
                    }
                    f = f2;
                    field = field3;
                    i++;
                    f2 = f;
                    field3 = field;
                }
                if (f2 == 1.0f || cls.isPrimitive() || cls.isInterface() || cls.equals(Object.class) || cls.equals(Void.TYPE)) {
                    field2 = field;
                } else {
                    cls = cls.getSuperclass();
                    field2 = field;
                }
            }
            field2 = field;
            a(aVar, (Member) field2);
        }
        if (field2 != null) {
            return field2;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = cls.getName();
        throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", objArr));
    }

    protected static Method getMethodID(Class cls, String str, String str2, boolean z) {
        Method method;
        float f;
        Method method2 = null;
        a aVar = new a(cls, str, str2);
        if (a(aVar)) {
            method2 = (Method) aVar.a;
        } else {
            Class[] a2 = a(str2);
            float f2 = 0.0f;
            while (true) {
                if (cls == null) {
                    break;
                }
                Method[] declaredMethods = cls.getDeclaredMethods();
                int length = declaredMethods.length;
                int i = 0;
                Method method3 = method2;
                while (true) {
                    if (i >= length) {
                        method = method3;
                        break;
                    }
                    method = declaredMethods[i];
                    if (z == Modifier.isStatic(method.getModifiers()) && method.getName().compareTo(str) == 0) {
                        f = a((Class) method.getReturnType(), method.getParameterTypes(), a2);
                        if (f > f2) {
                            if (f == 1.0f) {
                                f2 = f;
                                break;
                            }
                            i++;
                            f2 = f;
                            method3 = method;
                        }
                    }
                    f = f2;
                    method = method3;
                    i++;
                    f2 = f;
                    method3 = method;
                }
                if (f2 == 1.0f || cls.isPrimitive() || cls.isInterface() || cls.equals(Object.class) || cls.equals(Void.TYPE)) {
                    method2 = method;
                } else {
                    cls = cls.getSuperclass();
                    method2 = method;
                }
            }
            method2 = method;
            a(aVar, (Member) method2);
        }
        if (method2 != null) {
            return method2;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = cls.getName();
        throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", objArr));
    }

    /* access modifiers changed from: private */
    public static native void nativeProxyFinalize(int i);

    /* access modifiers changed from: private */
    public static native Object nativeProxyInvoke(int i, String str, Object[] objArr);

    protected static Object newProxyInstance(int i, Class cls) {
        return newProxyInstance(i, new Class[]{cls});
    }

    protected static Object newProxyInstance(final int i, final Class[] clsArr) {
        return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), clsArr, new InvocationHandler() {
            /* access modifiers changed from: protected */
            public final void finalize() {
                try {
                    ReflectionHelper.nativeProxyFinalize(i);
                } finally {
                    super.finalize();
                }
            }

            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return ReflectionHelper.nativeProxyInvoke(i, method.getName(), objArr);
            }
        });
    }
}
