package com.tencent.mna.base.b;

import android.content.Context;
import android.util.Log;
import com.tencent.component.debug.FileTracerReader;
import com.tencent.component.plugin.server.PluginConstant;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/* compiled from: PatchDexHelper */
public class b {
    public static void a(Context context, File file) {
        if (context != null && file != null) {
            if (!file.getName().endsWith(PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX)) {
                if (!file.getName().startsWith("classes")) {
                    return;
                }
                if (!file.getName().endsWith(".apk") && !file.getName().endsWith(".jar") && !file.getName().endsWith(FileTracerReader.ZIP_FILE_EXT)) {
                    return;
                }
            }
            Log.i("HOT_FIX", "loadedDex add:" + file.getAbsolutePath());
            b(context, file);
        }
    }

    private static void b(Context context, File file) {
        File file2 = new File(context.getFilesDir().getAbsolutePath() + File.separator + "optimize_dex");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        try {
            PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
            Object a = a(b(a(new DexClassLoader(file.getAbsolutePath(), file2.getAbsolutePath(), (String) null, pathClassLoader))), b(a(pathClassLoader)));
            Object a2 = a(pathClassLoader);
            a(a2, a2.getClass(), "dexElements", a);
        } catch (Exception e) {
        }
    }

    private static void a(Object obj, Class<?> cls, String str, Object obj2) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }

    private static Object a(Object obj, Class<?> cls, String str) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    private static Object a(Object obj) {
        return a(obj, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object b(Object obj) {
        return a(obj, obj.getClass(), "dexElements");
    }

    private static Object a(Object obj, Object obj2) {
        Class<?> componentType = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        int length2 = Array.getLength(obj2);
        Object newInstance = Array.newInstance(componentType, length + length2);
        System.arraycopy(obj, 0, newInstance, 0, length);
        System.arraycopy(obj2, 0, newInstance, length, length2);
        return newInstance;
    }
}
