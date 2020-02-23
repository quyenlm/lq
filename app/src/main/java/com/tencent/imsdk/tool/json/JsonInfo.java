package com.tencent.imsdk.tool.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class JsonInfo {
    private static volatile ConcurrentHashMap<Class, ArrayList<Field>> fields = new ConcurrentHashMap<>();

    public static ArrayList<Field> getFields(Class type) {
        if (fields.containsKey(type)) {
            return fields.get(type);
        }
        ArrayList<Field> result = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers()) && field.isAnnotationPresent(JsonProp.class)) {
                result.add(field);
            }
        }
        Class superClass = type.getSuperclass();
        if (superClass != null) {
            ExtendList(result, getFields(superClass));
        }
        fields.put(type, result);
        return result;
    }

    public static void ExtendList(ArrayList<Field> dst, ArrayList<Field> src) {
        Iterator<Field> it = src.iterator();
        while (it.hasNext()) {
            dst.add(it.next());
        }
    }

    public static ArrayList<Field> getFields(Object obj) {
        return getFields(obj.getClass());
    }
}
