package com.tencent.tp;

import android.content.Context;
import android.os.Build;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class n {
    private static List a;

    public static boolean a(Context context, String str) {
        if (a == null) {
            ArrayList arrayList = new ArrayList();
            try {
                String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
                if (strArr == null) {
                    return false;
                }
                for (String add : strArr) {
                    arrayList.add(add);
                }
                a = arrayList;
            } catch (Throwable th) {
                arrayList.clear();
            }
        }
        for (String compareTo : a) {
            if (compareTo.compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(Context context, String str) {
        Method method;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        Method[] methods = context.getClass().getMethods();
        int i = 0;
        while (true) {
            if (i >= methods.length) {
                method = null;
                break;
            } else if (methods[i].getName().equals("checkSelfPermission")) {
                method = methods[i];
                break;
            } else {
                i++;
            }
        }
        if (method == null) {
            return false;
        }
        try {
            Object invoke = method.invoke(context, new Object[]{str});
            return (invoke instanceof Integer) && ((Integer) invoke).intValue() == 0;
        } catch (Throwable th) {
            return false;
        }
    }
}
