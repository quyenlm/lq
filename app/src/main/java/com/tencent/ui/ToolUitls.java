package com.tencent.ui;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import java.lang.reflect.InvocationTargetException;

public class ToolUitls {
    public static boolean getAppOps(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            Object object = context.getSystemService("appops");
            try {
                return ((Integer) object.getClass().getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(object, new Object[]{46, Integer.valueOf(Binder.getCallingUid()), context.getApplicationContext().getPackageName()})).intValue() == 0;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
        return false;
    }
}
