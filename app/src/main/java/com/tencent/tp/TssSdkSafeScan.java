package com.tencent.tp;

import android.content.Context;
import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TssSdkSafeScan {
    private static void scan(Context context, boolean z, boolean z2, boolean z3) {
        Field field;
        Method method = null;
        w wVar = new w(context, z, z2, z3);
        if (Build.VERSION.SDK_INT > 10) {
            Method[] methods = wVar.getClass().getMethods();
            Field[] fields = wVar.getClass().getFields();
            int i = 0;
            while (true) {
                if (i >= fields.length) {
                    field = null;
                    break;
                } else if (fields[i].getName().equals("THREAD_POOL_EXECUTOR")) {
                    field = fields[i];
                    break;
                } else {
                    i++;
                }
            }
            int i2 = 0;
            while (true) {
                if (i2 >= methods.length) {
                    break;
                } else if (methods[i2].getName().equals("executeOnExecutor")) {
                    method = methods[i2];
                    break;
                } else {
                    i2++;
                }
            }
            if (method == null || field == null) {
                wVar.execute(new Void[0]);
            } else {
                try {
                    method.invoke(wVar, new Object[]{field.get((Object) null), null});
                } catch (Throwable th) {
                }
            }
        } else {
            wVar.execute(new Void[0]);
        }
        try {
            m.b("info: on scan done.");
        } catch (Exception e) {
        }
    }

    public static void scan(boolean z, boolean z2, boolean z3) {
        Context appContext = TssSdkRuntime.getAppContext();
        if (appContext != null) {
            scan(appContext, z, z2, z3);
        }
    }
}
