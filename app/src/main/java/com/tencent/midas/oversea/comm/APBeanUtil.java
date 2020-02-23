package com.tencent.midas.oversea.comm;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class APBeanUtil {
    public static void copyProperties(Object obj, Object obj2) {
        copyPropertiesExclude(obj, obj2, (String[]) null, true);
    }

    public static void copyPropertiesExclude(Object obj, Object obj2, String[] strArr, boolean z) {
        Method[] declaredMethods;
        Method[] methodArr;
        Method findMethodByName;
        Object invoke;
        List asList = (strArr == null || strArr.length <= 0) ? null : Arrays.asList(strArr);
        if (z) {
            Method[] methods = obj.getClass().getMethods();
            declaredMethods = obj2.getClass().getMethods();
            methodArr = methods;
        } else {
            Method[] declaredMethods2 = obj.getClass().getDeclaredMethods();
            declaredMethods = obj2.getClass().getDeclaredMethods();
            methodArr = declaredMethods2;
        }
        for (Method method : methodArr) {
            String name = method.getName();
            if (name.contains("get") && ((asList == null || !asList.contains(name.substring(3).toLowerCase())) && (findMethodByName = findMethodByName(declaredMethods, "set" + name.substring(3))) != null && (invoke = method.invoke(obj, new Object[0])) != null && (!(invoke instanceof Collection) || ((Collection) invoke).size() > 0))) {
                findMethodByName.invoke(obj2, new Object[]{invoke});
            }
        }
    }

    public static void copyPropertiesInclude(Object obj, Object obj2, String[] strArr, boolean z) {
        Method[] declaredMethods;
        Method[] methodArr;
        Method findMethodByName;
        Object invoke;
        if (strArr != null && strArr.length > 0) {
            List asList = Arrays.asList(strArr);
            if (z) {
                Method[] methods = obj.getClass().getMethods();
                declaredMethods = obj2.getClass().getMethods();
                methodArr = methods;
            } else {
                Method[] declaredMethods2 = obj.getClass().getDeclaredMethods();
                declaredMethods = obj2.getClass().getDeclaredMethods();
                methodArr = declaredMethods2;
            }
            for (Method method : methodArr) {
                String name = method.getName();
                if (name.contains("get")) {
                    String substring = name.substring(3);
                    if (asList.contains(substring.substring(0, 1).toLowerCase() + substring.substring(1)) && (findMethodByName = findMethodByName(declaredMethods, "set" + name.substring(3))) != null && (invoke = method.invoke(obj, new Object[0])) != null && (!(invoke instanceof Collection) || ((Collection) invoke).size() > 0)) {
                        findMethodByName.invoke(obj2, new Object[]{invoke});
                    }
                }
            }
        }
    }

    public static Method findMethodByName(Method[] methodArr, String str) {
        for (int i = 0; i < methodArr.length; i++) {
            if (methodArr[i].getName().equals(str)) {
                return methodArr[i];
            }
        }
        return null;
    }
}
