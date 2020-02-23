package com.tencent.component.plugin;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public final class PluginClassLoader {
    private static final String TAG = "PluginClassLoader";
    private static final ConcurrentHashMap<String, PluginClassLoader> sClassLoaderMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public static final ConcurrentHashMap<String, ClassLoader> sCorePluginClassLoaderMap = new ConcurrentHashMap<>();
    private static final UniqueLock<String> sUniqueLock = new UniqueLock<>();
    private final ClassLoader mClassLoader;
    private final ConcurrentHashMap<String, Class<?>> mClassMap = new ConcurrentHashMap<>();
    private final File mFile;
    private final long mInitFileSize;
    private final long mInitFileTime;

    public PluginClassLoader(Context context, String path, PluginInfo pluginInfo) {
        ClassLoader pluginDexClassLoader;
        long j = 0;
        if (TextUtils.isEmpty(path)) {
            pluginDexClassLoader = context.getClassLoader();
        } else {
            pluginDexClassLoader = new PluginDexClassLoader(path, ensureDir(pluginInfo.dexOptimizeDir), ensureDir(pluginInfo.nativeLibraryDir), context.getClassLoader());
        }
        this.mClassLoader = pluginDexClassLoader;
        this.mFile = TextUtils.isEmpty(path) ? null : new File(path);
        this.mInitFileSize = this.mFile == null ? 0 : this.mFile.length();
        this.mInitFileTime = this.mFile != null ? this.mFile.lastModified() : j;
        if (pluginInfo.corePlugin) {
            sCorePluginClassLoaderMap.put(pluginInfo.pluginId, this.mClassLoader);
        }
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        if (TextUtils.isEmpty(className)) {
            return null;
        }
        Class<?> clazz = this.mClassMap.get(className);
        if (clazz != null) {
            return clazz;
        }
        Class<?> clazz2 = this.mClassLoader.loadClass(className);
        Class<?> prevClass = this.mClassMap.putIfAbsent(className, clazz2);
        if (prevClass != null) {
            return prevClass;
        }
        return clazz2;
    }

    public void setPlugin(Plugin plugin) {
        if (plugin != null && (this.mClassLoader instanceof PluginDexClassLoader)) {
            ((PluginDexClassLoader) this.mClassLoader).classLoaderInterceptor = plugin.getClassLoaderInterceptor();
        }
    }

    public boolean isUpToDate() {
        boolean isUpdateToDate = true;
        if (this.mFile != null) {
            boolean exist = this.mFile.exists();
            long fileSize = this.mFile.length();
            long lastModified = this.mFile.lastModified();
            if (!(exist && fileSize == this.mInitFileSize && lastModified == this.mInitFileTime)) {
                isUpdateToDate = false;
            }
            if (!isUpdateToDate) {
                LogUtil.e(TAG, "plugin class loader not update to date (mFile.exists():" + exist + "| mFile.length():" + fileSize + " |mInitFileSize:" + this.mInitFileSize + " |mFile.lastModified():" + lastModified + " |mInitFileTime:" + this.mInitFileTime + h.b);
            }
        }
        return isUpdateToDate;
    }

    private static boolean isDirValid(File dir) {
        return dir != null && dir.isDirectory() && dir.exists();
    }

    private static String ensureDir(String dir) {
        if (dir != null) {
            ensureDir(new File(dir));
        }
        return dir;
    }

    private static boolean ensureDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (isDirValid(dir)) {
            return true;
        }
        FileUtil.delete(dir);
        return dir.mkdirs();
    }

    public static void removeClassLoader(PluginInfo pluginInfo) {
        String pluginId = pluginInfo.pluginId;
        String pluginPath = pluginInfo.installPath;
        if (pluginId != null && sClassLoaderMap.containsKey(pluginId)) {
            Lock lock = sUniqueLock.obtain(pluginPath);
            lock.lock();
            try {
                LogUtil.i(TAG, "remove class loader :" + pluginId);
                sClassLoaderMap.remove(pluginId);
            } finally {
                lock.unlock();
            }
        }
    }

    public static PluginClassLoader obtainClassLoader(Context context, PluginInfo pluginInfo) {
        String pluginId = pluginInfo.pluginId;
        String pluginPath = pluginInfo.installPath;
        if (pluginId == null) {
            pluginId = "";
        }
        PluginClassLoader classLoader = sClassLoaderMap.get(pluginId);
        if (classLoader != null) {
            PluginClassLoader pluginClassLoader = classLoader;
            return classLoader;
        }
        Lock lock = sUniqueLock.obtain(pluginPath);
        lock.lock();
        try {
            PluginClassLoader classLoader2 = sClassLoaderMap.get(pluginId);
            if (classLoader2 != null) {
                lock.unlock();
                PluginClassLoader pluginClassLoader2 = classLoader2;
                return classLoader2;
            }
            PluginClassLoader classLoader3 = new PluginClassLoader(context.getApplicationContext(), pluginPath, pluginInfo);
            try {
                sClassLoaderMap.put(pluginId, classLoader3);
                lock.unlock();
                PluginClassLoader pluginClassLoader3 = classLoader3;
                return classLoader3;
            } catch (Throwable th) {
                th = th;
                PluginClassLoader pluginClassLoader4 = classLoader3;
                lock.unlock();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            lock.unlock();
            throw th;
        }
    }

    private static class PluginDexClassLoader extends DexClassLoader {
        public volatile PluginClassLoaderInterceptor classLoaderInterceptor;

        public PluginDexClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
            super(dexPath, optimizedDirectory, libraryPath, parent);
        }

        /* access modifiers changed from: package-private */
        public Class<?> loadSelfClassOnly(String className) {
            Class<?> clazz = findLoadedClass(className);
            if (clazz != null) {
                return clazz;
            }
            boolean intercept = true;
            if (this.classLoaderInterceptor != null) {
                intercept = this.classLoaderInterceptor.interceptClass(className);
            }
            if (!intercept) {
                return clazz;
            }
            try {
                return findClass(className);
            } catch (ClassNotFoundException e) {
                return clazz;
            }
        }

        /* access modifiers changed from: protected */
        public Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
            Set<Map.Entry<String, ClassLoader>> entrySet;
            Class<?> clazz = null;
            try {
                clazz = loadSelfClassOnly(className);
            } catch (Exception e) {
            }
            if (clazz == null && (entrySet = PluginClassLoader.sCorePluginClassLoaderMap.entrySet()) != null) {
                for (Map.Entry<String, ClassLoader> entry : entrySet) {
                    ClassLoader value = entry.getValue();
                    if (!(value == null || value == this || !(value instanceof PluginDexClassLoader))) {
                        try {
                            clazz = ((PluginDexClassLoader) value).loadSelfClassOnly(className);
                        } catch (Exception e2) {
                        }
                        if (clazz != null) {
                            break;
                        }
                    }
                }
            }
            if (clazz == null) {
                return getParent().loadClass(className);
            }
            return clazz;
        }
    }
}
