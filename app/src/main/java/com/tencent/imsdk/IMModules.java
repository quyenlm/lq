package com.tencent.imsdk;

import com.tencent.imsdk.tool.etc.IMLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;

public class IMModules {
    private static HashMap<String, Object> modules = new HashMap<>();
    private static LinkedList<String> unavailables = new LinkedList<>();

    private static final class Holder {
        static final IMModules instance = new IMModules();

        private Holder() {
        }
    }

    public static IMModules getInstance() {
        return Holder.instance;
    }

    public Object getModule(String name) {
        Object obj;
        Object obj2 = modules.get(name);
        if (obj2 != null) {
            return obj2;
        }
        if (unavailables.contains(name)) {
            return null;
        }
        try {
            obj = hackConstructor(Class.forName(name));
        } catch (ClassNotFoundException e) {
            obj = null;
        }
        if (obj != null) {
            modules.put(name, obj);
            return obj;
        }
        unavailables.add(name);
        return obj;
    }

    public Object getModule(Class cls) {
        return getModule(cls.getName());
    }

    private IMModules() {
    }

    private Object hackConstructor(Class cls) {
        Constructor constructor = cls.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Object n = null;
        try {
            n = constructor.newInstance(new Object[0]);
        } catch (IllegalAccessException e) {
            IMLogger.e(e.getMessage());
        } catch (InstantiationException e2) {
            IMLogger.e(e2.getMessage());
        } catch (InvocationTargetException e3) {
            IMLogger.e(e3.getMessage());
        }
        constructor.setAccessible(false);
        return n;
    }
}
