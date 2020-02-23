package com.tencent.smtt.export.external;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.tp.a.h;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class DexClassLoaderProvider extends DexClassLoader {
    private static final String IS_FIRST_LOAD_DEX_FLAG_FILE = "is_first_load_dex_flag_file";
    private static final String LAST_DEX_NAME = "tbs_jars_fusion_dex.jar";
    private static final long LOAD_DEX_DELAY = 3000;
    private static final String LOGTAG = "dexloader";
    protected static DexClassLoader mClassLoaderOriginal = null;
    /* access modifiers changed from: private */
    public static Context mContext = null;
    /* access modifiers changed from: private */
    public static boolean mForceLoadDexFlag = false;
    private static DexClassLoaderProvider mInstance = null;
    private static String mRealDexPath = null;
    protected static Service mService = null;
    private SpeedyDexClassLoader mClassLoader = null;

    private static class SpeedyDexClassLoader extends BaseDexClassLoader {
        public SpeedyDexClassLoader(String str, File file, String str2, ClassLoader classLoader) {
            super(str, (File) null, str2, classLoader);
        }

        public Package definePackage(String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) {
            return super.definePackage(str, str2, str3, str4, str5, str6, str7, url);
        }

        public Class<?> findClass(String str) {
            return super.findClass(str);
        }

        public URL findResource(String str) {
            return super.findResource(str);
        }

        public Enumeration<URL> findResources(String str) {
            return super.findResources(str);
        }

        public synchronized Package getPackage(String str) {
            return super.getPackage(str);
        }

        public Package[] getPackages() {
            return super.getPackages();
        }

        public Class<?> loadClass(String str, boolean z) {
            return super.loadClass(str, z);
        }
    }

    private DexClassLoaderProvider(String str, String str2, String str3, ClassLoader classLoader, boolean z) {
        super(str, str2, str3, classLoader);
        if (z) {
            this.mClassLoader = new SpeedyDexClassLoader(mRealDexPath, (File) null, str3, classLoader);
        } else {
            this.mClassLoader = null;
        }
    }

    public static DexClassLoader createDexClassLoader(String str, String str2, String str3, ClassLoader classLoader, Context context) {
        mContext = context.getApplicationContext();
        mRealDexPath = str;
        int lastIndexOf = str.lastIndexOf(Constants.URL_PATH_DELIMITER);
        String str4 = str.substring(0, lastIndexOf + 1) + "fake_dex.jar";
        String substring = str.substring(lastIndexOf + 1);
        if (!supportSpeedyClassLoader() || !is_first_load_tbs_dex(str2, substring)) {
            mInstance = new DexClassLoaderProvider(str, str2, str3, classLoader, false);
        } else {
            set_first_load_tbs_dex(str2, substring);
            mInstance = new DexClassLoaderProvider(str4, str2, str3, classLoader, true);
            doAsyncDexLoad(substring, str, str2, str3, classLoader);
        }
        return mInstance;
    }

    private static void doAsyncDexLoad(final String str, final String str2, final String str3, final String str4, ClassLoader classLoader) {
        if (shouldUseDexLoaderService()) {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    try {
                        ArrayList arrayList = new ArrayList(4);
                        arrayList.add(0, str);
                        arrayList.add(1, str2);
                        arrayList.add(2, str3);
                        arrayList.add(3, str4);
                        Intent intent = new Intent(DexClassLoaderProvider.mContext, DexClassLoaderProviderService.class);
                        intent.putStringArrayListExtra("dex2oat", arrayList);
                        DexClassLoaderProvider.mContext.startService(intent);
                    } catch (SecurityException e) {
                        Log.e(DexClassLoaderProvider.LOGTAG, "start DexLoaderService exception", e);
                    } catch (Throwable th) {
                        Log.e(DexClassLoaderProvider.LOGTAG, "after shouldUseDexLoaderService exception: " + th);
                    }
                }
            }, LOAD_DEX_DELAY);
            return;
        }
        final String str5 = str2;
        final String str6 = str3;
        final String str7 = str4;
        final ClassLoader classLoader2 = classLoader;
        final String str8 = str;
        new Timer().schedule(new TimerTask() {
            public void run() {
                boolean z = true;
                try {
                    File file = new File(str5.replace(".jar", PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX));
                    if (!file.exists() || file.length() == 0) {
                        z = false;
                    }
                    File file2 = new File(str6);
                    File file3 = new File(str5);
                    boolean exists = file2.exists();
                    boolean isDirectory = file2.isDirectory();
                    boolean exists2 = file3.exists();
                    if (!exists || !isDirectory || !exists2) {
                        Log.d(DexClassLoaderProvider.LOGTAG, "dex loading exception(" + exists + ", " + isDirectory + ", " + exists2 + h.b);
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    new DexClassLoader(str5, str6, str7, classLoader2);
                    String.format("load_dex completed -- cl_cost: %d, existed: %b", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis), Boolean.valueOf(z)});
                    if (DexClassLoaderProvider.mForceLoadDexFlag && DexClassLoaderProvider.LAST_DEX_NAME.equals(str8) && DexClassLoaderProvider.mService != null) {
                        DexClassLoaderProvider.mService.stopSelf();
                    }
                } catch (Throwable th) {
                    Log.e(DexClassLoaderProvider.LOGTAG, "@AsyncDexLoad task exception: " + th);
                }
            }
        }, LOAD_DEX_DELAY);
    }

    private static boolean is_first_load_tbs_dex(String str, String str2) {
        return mForceLoadDexFlag || !new File(str, new StringBuilder().append(str2).append("_").append(IS_FIRST_LOAD_DEX_FLAG_FILE).toString()).exists();
    }

    static void setForceLoadDexFlag(boolean z, Service service) {
        mForceLoadDexFlag = z;
        mService = service;
    }

    private static void set_first_load_tbs_dex(String str, String str2) {
        File file = new File(str, str2 + "_" + IS_FIRST_LOAD_DEX_FLAG_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static boolean shouldUseDexLoaderService() {
        return !mForceLoadDexFlag && DexLoader.mCanUseDexLoaderProviderService;
    }

    private static boolean supportSpeedyClassLoader() {
        return Build.VERSION.SDK_INT != 21 || DexLoader.mCanUseDexLoaderProviderService;
    }

    private boolean useSelfClassloader() {
        return this.mClassLoader == null;
    }

    public void clearAssertionStatus() {
        if (useSelfClassloader()) {
            super.clearAssertionStatus();
        } else {
            this.mClassLoader.clearAssertionStatus();
        }
    }

    /* access modifiers changed from: protected */
    public Package definePackage(String str, String str2, String str3, String str4, String str5, String str6, String str7, URL url) {
        return useSelfClassloader() ? super.definePackage(str, str2, str3, str4, str5, str6, str7, url) : this.mClassLoader.definePackage(str, str2, str3, str4, str5, str6, str7, url);
    }

    /* access modifiers changed from: protected */
    public Class<?> findClass(String str) {
        return useSelfClassloader() ? super.findClass(str) : this.mClassLoader.findClass(str);
    }

    public String findLibrary(String str) {
        return useSelfClassloader() ? super.findLibrary(str) : this.mClassLoader.findLibrary(str);
    }

    /* access modifiers changed from: protected */
    public URL findResource(String str) {
        return useSelfClassloader() ? super.findResource(str) : this.mClassLoader.findResource(str);
    }

    /* access modifiers changed from: protected */
    public Enumeration<URL> findResources(String str) {
        return useSelfClassloader() ? super.findResources(str) : this.mClassLoader.findResources(str);
    }

    /* access modifiers changed from: protected */
    public synchronized Package getPackage(String str) {
        return useSelfClassloader() ? super.getPackage(str) : this.mClassLoader.getPackage(str);
    }

    /* access modifiers changed from: protected */
    public Package[] getPackages() {
        return useSelfClassloader() ? super.getPackages() : this.mClassLoader.getPackages();
    }

    public URL getResource(String str) {
        return useSelfClassloader() ? super.getResource(str) : this.mClassLoader.getResource(str);
    }

    public InputStream getResourceAsStream(String str) {
        return useSelfClassloader() ? getResourceAsStream(str) : this.mClassLoader.getResourceAsStream(str);
    }

    public Enumeration<URL> getResources(String str) {
        return useSelfClassloader() ? super.getResources(str) : this.mClassLoader.getResources(str);
    }

    public Class<?> loadClass(String str) {
        return useSelfClassloader() ? super.loadClass(str) : this.mClassLoader.loadClass(str);
    }

    /* access modifiers changed from: protected */
    public Class<?> loadClass(String str, boolean z) {
        return useSelfClassloader() ? super.loadClass(str, z) : this.mClassLoader.loadClass(str, z);
    }

    public void setClassAssertionStatus(String str, boolean z) {
        if (useSelfClassloader()) {
            super.setClassAssertionStatus(str, z);
        } else {
            this.mClassLoader.setClassAssertionStatus(str, z);
        }
    }

    public void setDefaultAssertionStatus(boolean z) {
        if (useSelfClassloader()) {
            super.setDefaultAssertionStatus(z);
        } else {
            this.mClassLoader.setDefaultAssertionStatus(z);
        }
    }

    public void setPackageAssertionStatus(String str, boolean z) {
        if (useSelfClassloader()) {
            super.setPackageAssertionStatus(str, z);
        } else {
            this.mClassLoader.setPackageAssertionStatus(str, z);
        }
    }

    public String toString() {
        return useSelfClassloader() ? super.toString() : this.mClassLoader.toString();
    }
}
