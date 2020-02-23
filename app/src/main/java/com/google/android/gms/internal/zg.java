package com.google.android.gms.internal;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.GenericTypeIndicator;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class zg {
    private static final ConcurrentMap<Class<?>, zh<?>> zzcjF = new ConcurrentHashMap();

    public static Map<String, Object> zzG(Map<String, Object> map) {
        Object zzao = zzao(map);
        zd.zzaF(zzao instanceof Map);
        return (Map) zzao;
    }

    public static <T> T zza(Object obj, GenericTypeIndicator<T> genericTypeIndicator) {
        Type genericSuperclass = genericTypeIndicator.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            if (parameterizedType.getRawType().equals(GenericTypeIndicator.class)) {
                return zza(obj, parameterizedType.getActualTypeArguments()[0]);
            }
            String valueOf = String.valueOf(genericSuperclass);
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 47).append("Not a direct subclass of GenericTypeIndicator: ").append(valueOf).toString());
        }
        String valueOf2 = String.valueOf(genericSuperclass);
        throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 47).append("Not a direct subclass of GenericTypeIndicator: ").append(valueOf2).toString());
    }

    public static <T> T zza(Object obj, Class<T> cls) {
        return zzb(obj, cls);
    }

    /* access modifiers changed from: private */
    public static <T> T zza(Object obj, Type type) {
        if (obj == null) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class cls = (Class) parameterizedType.getRawType();
            if (List.class.isAssignableFrom(cls)) {
                Type type2 = parameterizedType.getActualTypeArguments()[0];
                if (obj instanceof List) {
                    List<Object> list = (List) obj;
                    T arrayList = new ArrayList(list.size());
                    for (Object zza : list) {
                        arrayList.add(zza(zza, type2));
                    }
                    return arrayList;
                }
                String valueOf = String.valueOf(obj.getClass());
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 47).append("Expected a List while deserializing, but got a ").append(valueOf).toString());
            } else if (Map.class.isAssignableFrom(cls)) {
                Type type3 = parameterizedType.getActualTypeArguments()[0];
                Type type4 = parameterizedType.getActualTypeArguments()[1];
                if (!type3.equals(String.class)) {
                    String valueOf2 = String.valueOf(type3);
                    throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 70).append("Only Maps with string keys are supported, but found Map with key type ").append(valueOf2).toString());
                }
                Map<String, Object> zzap = zzap(obj);
                T hashMap = new HashMap();
                for (Map.Entry next : zzap.entrySet()) {
                    hashMap.put((String) next.getKey(), zza(next.getValue(), type4));
                }
                return hashMap;
            } else if (Collection.class.isAssignableFrom(cls)) {
                throw new DatabaseException("Collections are not supported, please use Lists instead");
            } else {
                Map<String, Object> zzap2 = zzap(obj);
                zh zzf = zzf(cls);
                HashMap hashMap2 = new HashMap();
                TypeVariable[] typeParameters = zzf.zzcjG.getTypeParameters();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length != typeParameters.length) {
                    throw new IllegalStateException("Mismatched lengths for type variables and actual types");
                }
                for (int i = 0; i < typeParameters.length; i++) {
                    hashMap2.put(typeParameters[i], actualTypeArguments[i]);
                }
                return zzf.zze(zzap2, hashMap2);
            }
        } else if (type instanceof Class) {
            return zzb(obj, (Class) type);
        } else {
            if (type instanceof WildcardType) {
                throw new DatabaseException("Generic wildcard types are not supported");
            } else if (type instanceof GenericArrayType) {
                throw new DatabaseException("Generic Arrays are not supported, please use Lists instead");
            } else {
                String valueOf3 = String.valueOf(type);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf3).length() + 26).append("Unknown type encountered: ").append(valueOf3).toString());
            }
        }
    }

    public static Object zzan(Object obj) {
        return zzao(obj);
    }

    /* access modifiers changed from: private */
    public static <T> Object zzao(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof Number) {
            if ((t instanceof Float) || (t instanceof Double)) {
                double doubleValue = ((Number) t).doubleValue();
                return (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d || Math.floor(doubleValue) != doubleValue) ? Double.valueOf(doubleValue) : Long.valueOf(((Number) t).longValue());
            } else if (t instanceof Short) {
                throw new DatabaseException("Shorts are not supported, please use int or long");
            } else if (!(t instanceof Byte)) {
                return t;
            } else {
                throw new DatabaseException("Bytes are not supported, please use int or long");
            }
        } else if ((t instanceof String) || (t instanceof Boolean)) {
            return t;
        } else {
            if (t instanceof Character) {
                throw new DatabaseException("Characters are not supported, please strings");
            } else if (t instanceof Map) {
                HashMap hashMap = new HashMap();
                for (Map.Entry entry : ((Map) t).entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        hashMap.put((String) key, zzao(entry.getValue()));
                    } else {
                        throw new DatabaseException("Maps with non-string keys are not supported");
                    }
                }
                return hashMap;
            } else if (t instanceof Collection) {
                if (t instanceof List) {
                    List<Object> list = (List) t;
                    ArrayList arrayList = new ArrayList(list.size());
                    for (Object zzao : list) {
                        arrayList.add(zzao(zzao));
                    }
                    return arrayList;
                }
                throw new DatabaseException("Serializing Collections is not supported, please use Lists instead");
            } else if (!t.getClass().isArray()) {
                return t instanceof Enum ? ((Enum) t).name() : zzf(t.getClass()).zzas(t);
            } else {
                throw new DatabaseException("Serializing Arrays is not supported, please use Lists instead");
            }
        }
    }

    private static Map<String, Object> zzap(Object obj) {
        if (obj instanceof Map) {
            return (Map) obj;
        }
        String valueOf = String.valueOf(obj.getClass());
        throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 46).append("Expected a Map while deserializing, but got a ").append(valueOf).toString());
    }

    private static Double zzaq(Object obj) {
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        if (obj instanceof Long) {
            Double valueOf = Double.valueOf(((Long) obj).doubleValue());
            if (valueOf.longValue() == ((Long) obj).longValue()) {
                return valueOf;
            }
            String valueOf2 = String.valueOf(obj);
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 97).append("Loss of precision while converting number to double: ").append(valueOf2).append(". Did you mean to use a 64-bit long instead?").toString());
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            String valueOf3 = String.valueOf(obj.getClass().getName());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf3).length() + 44).append("Failed to convert a value of type ").append(valueOf3).append(" to double").toString());
        }
    }

    private static <T> T zzb(Object obj, Class<T> cls) {
        if (obj == null) {
            return null;
        }
        if (cls.isPrimitive() || Number.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls)) {
            if (Integer.class.isAssignableFrom(cls) || Integer.TYPE.isAssignableFrom(cls)) {
                if (obj instanceof Integer) {
                    return (Integer) obj;
                }
                if ((obj instanceof Long) || (obj instanceof Double)) {
                    double doubleValue = ((Number) obj).doubleValue();
                    if (doubleValue >= -2.147483648E9d && doubleValue <= 2.147483647E9d) {
                        return Integer.valueOf(((Number) obj).intValue());
                    }
                    throw new DatabaseException(new StringBuilder(124).append("Numeric value out of 32-bit integer range: ").append(doubleValue).append(". Did you mean to use a long or double instead of an int?").toString());
                }
                String valueOf = String.valueOf(obj.getClass().getName());
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 41).append("Failed to convert a value of type ").append(valueOf).append(" to int").toString());
            } else if (Boolean.class.isAssignableFrom(cls) || Boolean.TYPE.isAssignableFrom(cls)) {
                if (obj instanceof Boolean) {
                    return (Boolean) obj;
                }
                String valueOf2 = String.valueOf(obj.getClass().getName());
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 43).append("Failed to convert value of type ").append(valueOf2).append(" to boolean").toString());
            } else if (Double.class.isAssignableFrom(cls) || Double.TYPE.isAssignableFrom(cls)) {
                return zzaq(obj);
            } else {
                if (Long.class.isAssignableFrom(cls) || Long.TYPE.isAssignableFrom(cls)) {
                    if (obj instanceof Integer) {
                        return Long.valueOf(((Integer) obj).longValue());
                    }
                    if (obj instanceof Long) {
                        return (Long) obj;
                    }
                    if (obj instanceof Double) {
                        Double d = (Double) obj;
                        if (d.doubleValue() >= -9.223372036854776E18d && d.doubleValue() <= 9.223372036854776E18d) {
                            return Long.valueOf(d.longValue());
                        }
                        String valueOf3 = String.valueOf(d);
                        throw new DatabaseException(new StringBuilder(String.valueOf(valueOf3).length() + 89).append("Numeric value out of 64-bit long range: ").append(valueOf3).append(". Did you mean to use a double instead of a long?").toString());
                    }
                    String valueOf4 = String.valueOf(obj.getClass().getName());
                    throw new DatabaseException(new StringBuilder(String.valueOf(valueOf4).length() + 42).append("Failed to convert a value of type ").append(valueOf4).append(" to long").toString());
                } else if (Float.class.isAssignableFrom(cls) || Float.TYPE.isAssignableFrom(cls)) {
                    return Float.valueOf(zzaq(obj).floatValue());
                } else {
                    if (Short.class.isAssignableFrom(cls) || Short.TYPE.isAssignableFrom(cls)) {
                        throw new DatabaseException("Deserializing to shorts is not supported");
                    } else if (Byte.class.isAssignableFrom(cls) || Byte.TYPE.isAssignableFrom(cls)) {
                        throw new DatabaseException("Deserializing to bytes is not supported");
                    } else if (Character.class.isAssignableFrom(cls) || Character.TYPE.isAssignableFrom(cls)) {
                        throw new DatabaseException("Deserializing to char is not supported");
                    } else {
                        String valueOf5 = String.valueOf(cls);
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf5).length() + 24).append("Unknown primitive type: ").append(valueOf5).toString());
                    }
                }
            }
        } else if (String.class.isAssignableFrom(cls)) {
            if (obj instanceof String) {
                return (String) obj;
            }
            String valueOf6 = String.valueOf(obj.getClass().getName());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf6).length() + 42).append("Failed to convert value of type ").append(valueOf6).append(" to String").toString());
        } else if (cls.isArray()) {
            throw new DatabaseException("Converting to Arrays is not supported, please use Listsinstead");
        } else if (cls.getTypeParameters().length > 0) {
            String valueOf7 = String.valueOf(cls.getName());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf7).length() + 75).append("Class ").append(valueOf7).append(" has generic type parameters, please use GenericTypeIndicator instead").toString());
        } else if (cls.equals(Object.class)) {
            return obj;
        } else {
            if (cls.isEnum()) {
                return zzc(obj, cls);
            }
            zh<T> zzf = zzf(cls);
            if (obj instanceof Map) {
                return zzf.zze(zzap(obj), Collections.emptyMap());
            }
            String valueOf8 = String.valueOf(obj.getClass().getName());
            String valueOf9 = String.valueOf(cls.getName());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf8).length() + 38 + String.valueOf(valueOf9).length()).append("Can't convert object of type ").append(valueOf8).append(" to type ").append(valueOf9).toString());
        }
    }

    private static <T> T zzc(Object obj, Class<T> cls) {
        if (obj instanceof String) {
            String str = (String) obj;
            try {
                return Enum.valueOf(cls, str);
            } catch (IllegalArgumentException e) {
                String valueOf = String.valueOf(cls.getName());
                throw new DatabaseException(new StringBuilder(String.valueOf(valueOf).length() + 42 + String.valueOf(str).length()).append("Could not find enum value of ").append(valueOf).append(" for value \"").append(str).append("\"").toString());
            }
        } else {
            String valueOf2 = String.valueOf(cls);
            String valueOf3 = String.valueOf(obj.getClass());
            throw new DatabaseException(new StringBuilder(String.valueOf(valueOf2).length() + 57 + String.valueOf(valueOf3).length()).append("Expected a String while deserializing to enum ").append(valueOf2).append(" but got a ").append(valueOf3).toString());
        }
    }

    private static <T> zh<T> zzf(Class<T> cls) {
        zh<T> zhVar = (zh) zzcjF.get(cls);
        if (zhVar != null) {
            return zhVar;
        }
        zh<T> zhVar2 = new zh<>(cls);
        zzcjF.put(cls, zhVar2);
        return zhVar2;
    }
}
