package com.google.android.gms.internal;

import android.util.Log;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.tencent.tp.a.h;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

final class zh<T> {
    /* access modifiers changed from: private */
    public final Class<T> zzcjG;
    private final Constructor<T> zzcjH;
    private final boolean zzcjI;
    private final boolean zzcjJ;
    private final Map<String, String> zzcjK;
    private final Map<String, Method> zzcjL;
    private final Map<String, Method> zzcjM;
    private final Map<String, Field> zzcjN;

    public zh(Class<T> cls) {
        Constructor<T> constructor;
        this.zzcjG = cls;
        this.zzcjI = cls.isAnnotationPresent(ThrowOnExtraProperties.class);
        this.zzcjJ = !cls.isAnnotationPresent(IgnoreExtraProperties.class);
        this.zzcjK = new HashMap();
        this.zzcjM = new HashMap();
        this.zzcjL = new HashMap();
        this.zzcjN = new HashMap();
        try {
            constructor = cls.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            constructor = null;
        }
        this.zzcjH = constructor;
        for (Method method : cls.getMethods()) {
            if ((method.getName().startsWith("get") || method.getName().startsWith("is")) ? method.getDeclaringClass().equals(Object.class) ? false : !Modifier.isPublic(method.getModifiers()) ? false : Modifier.isStatic(method.getModifiers()) ? false : method.getReturnType().equals(Void.TYPE) ? false : method.getParameterTypes().length != 0 ? false : !method.isAnnotationPresent(Exclude.class) : false) {
                String zza = zza(method);
                zzhe(zza);
                method.setAccessible(true);
                if (this.zzcjL.containsKey(zza)) {
                    String valueOf = String.valueOf(method.getName());
                    throw new DatabaseException(valueOf.length() != 0 ? "Found conflicting getters for name: ".concat(valueOf) : new String("Found conflicting getters for name: "));
                }
                this.zzcjL.put(zza, method);
            }
        }
        for (Field field : cls.getFields()) {
            if (field.getDeclaringClass().equals(Object.class) ? false : !Modifier.isPublic(field.getModifiers()) ? false : Modifier.isStatic(field.getModifiers()) ? false : Modifier.isTransient(field.getModifiers()) ? false : !field.isAnnotationPresent(Exclude.class)) {
                zzhe(zza(field));
            }
        }
        Class<? super T> cls2 = cls;
        while (true) {
            for (Method method2 : cls2.getDeclaredMethods()) {
                if (!method2.getName().startsWith("set") ? false : method2.getDeclaringClass().equals(Object.class) ? false : Modifier.isStatic(method2.getModifiers()) ? false : !method2.getReturnType().equals(Void.TYPE) ? false : method2.getParameterTypes().length != 1 ? false : !method2.isAnnotationPresent(Exclude.class)) {
                    String zza2 = zza(method2);
                    String str = this.zzcjK.get(zza2.toLowerCase());
                    if (str == null) {
                        continue;
                    } else if (!str.equals(zza2)) {
                        String valueOf2 = String.valueOf(method2.getName());
                        throw new DatabaseException(valueOf2.length() != 0 ? "Found setter with invalid case-sensitive name: ".concat(valueOf2) : new String("Found setter with invalid case-sensitive name: "));
                    } else {
                        Method method3 = this.zzcjM.get(zza2);
                        if (method3 == null) {
                            method2.setAccessible(true);
                            this.zzcjM.put(zza2, method2);
                        } else {
                            zd.zzb(method2.getDeclaringClass().isAssignableFrom(method3.getDeclaringClass()), "Expected override from a base class");
                            zd.zzb(method2.getReturnType().equals(Void.TYPE), "Expected void return type");
                            zd.zzb(method3.getReturnType().equals(Void.TYPE), "Expected void return type");
                            Class[] parameterTypes = method2.getParameterTypes();
                            Class[] parameterTypes2 = method3.getParameterTypes();
                            zd.zzb(parameterTypes.length == 1, "Expected exactly one parameter");
                            zd.zzb(parameterTypes2.length == 1, "Expected exactly one parameter");
                            if (!(method2.getName().equals(method3.getName()) && parameterTypes[0].equals(parameterTypes2[0]))) {
                                String valueOf3 = String.valueOf(method2.getName());
                                String valueOf4 = String.valueOf(method3.getName());
                                String valueOf5 = String.valueOf(method3.getDeclaringClass().getName());
                                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf3).length() + 69 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length()).append("Found a conflicting setters with name: ").append(valueOf3).append(" (conflicts with ").append(valueOf4).append(" defined on ").append(valueOf5).append(h.b).toString());
                            }
                        }
                    }
                }
            }
            for (Field field2 : cls2.getDeclaredFields()) {
                String zza3 = zza(field2);
                if (this.zzcjK.containsKey(zza3.toLowerCase()) && !this.zzcjN.containsKey(zza3)) {
                    field2.setAccessible(true);
                    this.zzcjN.put(zza3, field2);
                }
            }
            Class<? super T> superclass = cls2.getSuperclass();
            if (superclass != null && !superclass.equals(Object.class)) {
                cls2 = superclass;
            }
        }
        if (this.zzcjK.isEmpty()) {
            String valueOf6 = String.valueOf(cls.getName());
            throw new DatabaseException(valueOf6.length() != 0 ? "No properties to serialize found on class ".concat(valueOf6) : new String("No properties to serialize found on class "));
        }
    }

    private static String zza(AccessibleObject accessibleObject) {
        if (accessibleObject.isAnnotationPresent(PropertyName.class)) {
            return ((PropertyName) accessibleObject.getAnnotation(PropertyName.class)).value();
        }
        return null;
    }

    private static String zza(Field field) {
        String zza = zza((AccessibleObject) field);
        return zza != null ? zza : field.getName();
    }

    private static String zza(Method method) {
        String zza = zza((AccessibleObject) method);
        if (zza != null) {
            return zza;
        }
        String name = method.getName();
        String[] strArr = {"get", "set", "is"};
        String str = null;
        int i = 0;
        while (i < 3) {
            String str2 = strArr[i];
            if (!name.startsWith(str2)) {
                str2 = str;
            }
            i++;
            str = str2;
        }
        if (str == null) {
            String valueOf = String.valueOf(name);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unknown Bean prefix for method: ".concat(valueOf) : new String("Unknown Bean prefix for method: "));
        }
        char[] charArray = name.substring(str.length()).toCharArray();
        int i2 = 0;
        while (i2 < charArray.length && Character.isUpperCase(charArray[i2])) {
            charArray[i2] = Character.toLowerCase(charArray[i2]);
            i2++;
        }
        return new String(charArray);
    }

    private static Type zza(Type type, Map<TypeVariable<Class<T>>, Type> map) {
        if (!(type instanceof TypeVariable)) {
            return type;
        }
        Type type2 = map.get(type);
        if (type2 != null) {
            return type2;
        }
        String valueOf = String.valueOf(type);
        throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 23).append("Could not resolve type ").append(valueOf).toString());
    }

    private final void zzhe(String str) {
        String put = this.zzcjK.put(str.toLowerCase(), str);
        if (put != null && !str.equals(put)) {
            String valueOf = String.valueOf(str.toLowerCase());
            throw new DatabaseException(valueOf.length() != 0 ? "Found two getters or fields with conflicting case sensitivity for property: ".concat(valueOf) : new String("Found two getters or fields with conflicting case sensitivity for property: "));
        }
    }

    public final Map<String, Object> zzas(T t) {
        Object obj;
        if (!this.zzcjG.isAssignableFrom(t.getClass())) {
            String valueOf = String.valueOf(t.getClass());
            String valueOf2 = String.valueOf(this.zzcjG);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 59 + String.valueOf(valueOf2).length()).append("Can't serialize object of class ").append(valueOf).append(" with BeanMapper for class ").append(valueOf2).toString());
        }
        HashMap hashMap = new HashMap();
        for (String next : this.zzcjK.values()) {
            if (this.zzcjL.containsKey(next)) {
                try {
                    obj = this.zzcjL.get(next).invoke(t, new Object[0]);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            } else {
                Field field = this.zzcjN.get(next);
                if (field == null) {
                    String valueOf3 = String.valueOf(next);
                    throw new IllegalStateException(valueOf3.length() != 0 ? "Bean property without field or getter:".concat(valueOf3) : new String("Bean property without field or getter:"));
                }
                try {
                    obj = field.get(t);
                } catch (IllegalAccessException e3) {
                    throw new RuntimeException(e3);
                }
            }
            hashMap.put(next, zg.zzao(obj));
        }
        return hashMap;
    }

    public final T zze(Map<String, Object> map, Map<TypeVariable<Class<T>>, Type> map2) {
        if (this.zzcjH == null) {
            String valueOf = String.valueOf(this.zzcjG.getName());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 49).append("Class ").append(valueOf).append(" is missing a constructor with no arguments").toString());
        }
        try {
            T newInstance = this.zzcjH.newInstance(new Object[0]);
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                if (this.zzcjM.containsKey(str)) {
                    Method method = this.zzcjM.get(str);
                    Type[] genericParameterTypes = method.getGenericParameterTypes();
                    if (genericParameterTypes.length != 1) {
                        throw new IllegalStateException("Setter does not have exactly one parameter");
                    }
                    try {
                        method.invoke(newInstance, new Object[]{zg.zza(next.getValue(), zza(genericParameterTypes[0], map2))});
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e2) {
                        throw new RuntimeException(e2);
                    }
                } else if (this.zzcjN.containsKey(str)) {
                    Field field = this.zzcjN.get(str);
                    try {
                        field.set(newInstance, zg.zza(next.getValue(), zza(field.getGenericType(), map2)));
                    } catch (IllegalAccessException e3) {
                        throw new RuntimeException(e3);
                    }
                } else {
                    String valueOf2 = String.valueOf(this.zzcjG.getName());
                    String sb = new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(valueOf2).length()).append("No setter/field for ").append(str).append(" found on class ").append(valueOf2).toString();
                    if (this.zzcjK.containsKey(str.toLowerCase())) {
                        sb = String.valueOf(sb).concat(" (fields/setters are case sensitive!)");
                    }
                    if (this.zzcjI) {
                        throw new DatabaseException(sb);
                    } else if (this.zzcjJ) {
                        Log.w("ClassMapper", sb);
                    }
                }
            }
            return newInstance;
        } catch (InstantiationException e4) {
            throw new RuntimeException(e4);
        } catch (IllegalAccessException e5) {
            throw new RuntimeException(e5);
        } catch (InvocationTargetException e6) {
            throw new RuntimeException(e6);
        }
    }
}
