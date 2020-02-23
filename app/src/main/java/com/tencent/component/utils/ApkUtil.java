package com.tencent.component.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ApkUtil {
    private static Class<AssetManager> CLASS_ASSET = AssetManager.class;
    private static Method METHOD_ADD_ASSET;

    public static class ApkInfo {
        public Drawable icon;
        public String name;
        public PackageInfo packageInfo;
        public String packageName;
        public float version;
    }

    static {
        try {
            METHOD_ADD_ASSET = CLASS_ASSET.getDeclaredMethod("addAssetPath", new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable e2) {
            e2.printStackTrace();
        }
    }

    public static Resources getResources(Context context, String apkPath) {
        if (!checkApkFile(apkPath)) {
            return null;
        }
        Resources resources = null;
        PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, 0);
        ApplicationInfo appInfo = pkgInfo == null ? null : pkgInfo.applicationInfo;
        if (appInfo != null) {
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                resources = context.getPackageManager().getResourcesForApplication(appInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (Throwable e2) {
                e2.printStackTrace();
            }
        }
        if (resources == null) {
            return getResourcesWithReflect(context, apkPath);
        }
        return resources;
    }

    private static Resources getResourcesWithReflect(Context context, String apkPath) {
        if (CLASS_ASSET == null || METHOD_ADD_ASSET == null || !checkApkFile(apkPath)) {
            return null;
        }
        try {
            AssetManager asset = CLASS_ASSET.newInstance();
            METHOD_ADD_ASSET.invoke(asset, new Object[]{apkPath});
            return new Resources(asset, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (Throwable e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static PackageInfo getPackageInfo(Context context, String apkPath) {
        return getPackageInfo(context, apkPath, 0);
    }

    public static PackageInfo getPackageInfo(Context context, String apkPath, int flags) {
        if (!checkApkFile(apkPath)) {
            return null;
        }
        PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, flags);
        if (pkgInfo == null) {
            return null;
        }
        if ((flags & 64) == 0 || pkgInfo.signatures != null) {
            return pkgInfo;
        }
        pkgInfo.signatures = Certificates.collectCertificates(apkPath);
        return pkgInfo;
    }

    public static ApplicationInfo getApplicationInfo(Context context, String apkPath) {
        return getApplicationInfo(context, apkPath, 0);
    }

    public static ApplicationInfo getApplicationInfo(Context context, String apkPath, int flags) {
        ApplicationInfo appInfo = null;
        if (checkApkFile(apkPath)) {
            PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, flags);
            if (pkgInfo != null) {
                appInfo = pkgInfo.applicationInfo;
            }
            if (appInfo != null) {
                appInfo.sourceDir = apkPath;
                appInfo.publicSourceDir = apkPath;
            }
        }
        return appInfo;
    }

    public static ApkInfo getApkInfo(Context context, String apkPath) {
        Drawable icon;
        if (!checkApkFile(apkPath)) {
            return null;
        }
        ApkInfo apkInfo = null;
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, 0);
            Resources res = getResources(context, apkPath);
            if (pkgInfo == null || res == null) {
                return null;
            }
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            String name = appInfo == null ? null : getString(res, appInfo.labelRes);
            if (appInfo == null) {
                icon = null;
            } else {
                icon = getDrawable(res, appInfo.icon);
            }
            if (icon == null && appInfo != null) {
                icon = context.getPackageManager().getApplicationIcon(appInfo);
            }
            ApkInfo apkInfo2 = new ApkInfo();
            try {
                apkInfo2.packageInfo = pkgInfo;
                apkInfo2.packageName = pkgInfo.packageName;
                apkInfo2.name = name;
                apkInfo2.icon = icon;
                apkInfo2.version = (float) pkgInfo.versionCode;
                return apkInfo2;
            } catch (Throwable th) {
                e = th;
                apkInfo = apkInfo2;
                e.printStackTrace();
                return apkInfo;
            }
        } catch (Throwable th2) {
            e = th2;
            e.printStackTrace();
            return apkInfo;
        }
    }

    private static Drawable getDrawable(Resources resources, int id) {
        try {
            return resources.getDrawable(id);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    private static String getString(Resources resources, int id) {
        try {
            return resources.getString(id);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    private static boolean checkApkFile(String apkPath) {
        if (apkPath == null || apkPath.length() == 0) {
            return false;
        }
        File apkFile = new File(apkPath);
        if (!apkFile.exists() || !apkFile.isFile()) {
            return false;
        }
        return true;
    }

    public static class Certificates {
        private static final String ANDROID_DEX_FILENAME = "classes.dex";
        private static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
        private static final boolean DEBUG_JAR = false;
        public static final String[] IMPORTANT_ENTRY = {ANDROID_MANIFEST_FILENAME, ANDROID_DEX_FILENAME};
        public static final String[] MANIFEST_ENTRY = {ANDROID_MANIFEST_FILENAME};
        private static final String TAG = "Certificates";
        private static WeakReference<byte[]> mReadBuffer;
        private static final Object mSync = new Object();

        public static Signature[] collectCertificates(String archivePath) {
            return collectCertificates(archivePath, false);
        }

        public static Signature[] collectCertificates(String archivePath, boolean importantOnly) {
            return collectCertificates(archivePath, importantOnly ? IMPORTANT_ENTRY : null);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: byte[]} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static android.content.pm.Signature[] collectCertificates(java.lang.String r19, java.lang.String... r20) {
            /*
                boolean r16 = isArchiveValid(r19)
                if (r16 != 0) goto L_0x0008
                r15 = 0
            L_0x0007:
                return r15
            L_0x0008:
                r15 = 0
                r13 = 0
                java.lang.Object r17 = mSync
                monitor-enter(r17)
                java.lang.ref.WeakReference<byte[]> r14 = mReadBuffer     // Catch:{ all -> 0x0098 }
                if (r14 == 0) goto L_0x001e
                r16 = 0
                mReadBuffer = r16     // Catch:{ all -> 0x0098 }
                java.lang.Object r16 = r14.get()     // Catch:{ all -> 0x0098 }
                r0 = r16
                byte[] r0 = (byte[]) r0     // Catch:{ all -> 0x0098 }
                r13 = r0
            L_0x001e:
                if (r13 != 0) goto L_0x002b
                r16 = 8192(0x2000, float:1.14794E-41)
                r0 = r16
                byte[] r13 = new byte[r0]     // Catch:{ all -> 0x0098 }
                java.lang.ref.WeakReference r14 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x0098 }
                r14.<init>(r13)     // Catch:{ all -> 0x0098 }
            L_0x002b:
                monitor-exit(r17)     // Catch:{ all -> 0x0098 }
                java.util.jar.JarFile r9 = new java.util.jar.JarFile     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r0 = r19
                r9.<init>(r0)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r3 = 0
                r0 = r20
                java.util.Enumeration r5 = createJarEntries(r9, r0)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
            L_0x003a:
                boolean r16 = r5.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r16 == 0) goto L_0x0109
                java.lang.Object r10 = r5.nextElement()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.util.jar.JarEntry r10 = (java.util.jar.JarEntry) r10     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r10 == 0) goto L_0x003a
                boolean r16 = r10.isDirectory()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r16 != 0) goto L_0x003a
                java.lang.String r12 = r10.getName()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r16 = "META-INF/"
                r0 = r16
                boolean r16 = r12.startsWith(r0)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r16 != 0) goto L_0x003a
                java.security.cert.Certificate[] r11 = loadCertificates(r9, r10, r13)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r11 != 0) goto L_0x009b
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17.<init>()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = "File "
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = " has no certificates at entry "
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = r10.getName()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = "; ignoring!"
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r17 = r17.toString()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                android.util.Log.e(r16, r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r9.close()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r15 = 0
                goto L_0x0007
            L_0x0098:
                r16 = move-exception
                monitor-exit(r17)     // Catch:{ all -> 0x0098 }
                throw r16
            L_0x009b:
                if (r3 != 0) goto L_0x009f
                r3 = r11
                goto L_0x003a
            L_0x009f:
                r7 = 0
            L_0x00a0:
                int r0 = r3.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16 = r0
                r0 = r16
                if (r7 >= r0) goto L_0x003a
                r6 = 0
                r8 = 0
            L_0x00a9:
                int r0 = r11.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16 = r0
                r0 = r16
                if (r8 >= r0) goto L_0x00bf
                r16 = r3[r7]     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r16 == 0) goto L_0x0103
                r16 = r3[r7]     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17 = r11[r8]     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                boolean r16 = r16.equals(r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                if (r16 == 0) goto L_0x0103
                r6 = 1
            L_0x00bf:
                if (r6 == 0) goto L_0x00cd
                int r0 = r3.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16 = r0
                int r0 = r11.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17 = r0
                r0 = r16
                r1 = r17
                if (r0 == r1) goto L_0x0106
            L_0x00cd:
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17.<init>()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = "File "
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = " has mismatched certificates at entry "
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = r10.getName()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = "; ignoring!"
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r17 = r17.toString()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                android.util.Log.e(r16, r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r9.close()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r15 = 0
                goto L_0x0007
            L_0x0103:
                int r8 = r8 + 1
                goto L_0x00a9
            L_0x0106:
                int r7 = r7 + 1
                goto L_0x00a0
            L_0x0109:
                r9.close()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.Object r17 = mSync     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                monitor-enter(r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                mReadBuffer = r14     // Catch:{ all -> 0x0134 }
                monitor-exit(r17)     // Catch:{ all -> 0x0134 }
                if (r3 == 0) goto L_0x015b
                int r0 = r3.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16 = r0
                if (r16 <= 0) goto L_0x015b
                int r2 = r3.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                int r0 = r3.length     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16 = r0
                r0 = r16
                android.content.pm.Signature[] r15 = new android.content.pm.Signature[r0]     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r7 = 0
            L_0x0122:
                if (r7 >= r2) goto L_0x0007
                android.content.pm.Signature r16 = new android.content.pm.Signature     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17 = r3[r7]     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                byte[] r17 = r17.getEncoded()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r16.<init>(r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r15[r7] = r16     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                int r7 = r7 + 1
                goto L_0x0122
            L_0x0134:
                r16 = move-exception
                monitor-exit(r17)     // Catch:{ all -> 0x0134 }
                throw r16     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
            L_0x0137:
                r4 = move-exception
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder
                r17.<init>()
                java.lang.String r18 = "Exception reading "
                java.lang.StringBuilder r17 = r17.append(r18)
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)
                java.lang.String r17 = r17.toString()
                r0 = r16
                r1 = r17
                android.util.Log.w(r0, r1, r4)
                r15 = 0
                goto L_0x0007
            L_0x015b:
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r17.<init>()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = "File "
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r18 = " has no certificates; ignoring!"
                java.lang.StringBuilder r17 = r17.append(r18)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                java.lang.String r17 = r17.toString()     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                android.util.Log.e(r16, r17)     // Catch:{ CertificateEncodingException -> 0x0137, IOException -> 0x0180, RuntimeException -> 0x01a4 }
                r15 = 0
                goto L_0x0007
            L_0x0180:
                r4 = move-exception
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder
                r17.<init>()
                java.lang.String r18 = "Exception reading "
                java.lang.StringBuilder r17 = r17.append(r18)
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)
                java.lang.String r17 = r17.toString()
                r0 = r16
                r1 = r17
                android.util.Log.w(r0, r1, r4)
                r15 = 0
                goto L_0x0007
            L_0x01a4:
                r4 = move-exception
                java.lang.String r16 = "Certificates"
                java.lang.StringBuilder r17 = new java.lang.StringBuilder
                r17.<init>()
                java.lang.String r18 = "Exception reading "
                java.lang.StringBuilder r17 = r17.append(r18)
                r0 = r17
                r1 = r19
                java.lang.StringBuilder r17 = r0.append(r1)
                java.lang.String r17 = r17.toString()
                r0 = r16
                r1 = r17
                android.util.Log.w(r0, r1, r4)
                r15 = 0
                goto L_0x0007
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.ApkUtil.Certificates.collectCertificates(java.lang.String, java.lang.String[]):android.content.pm.Signature[]");
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x007a A[SYNTHETIC, Splitter:B:21:0x007a] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00d8 A[SYNTHETIC, Splitter:B:30:0x00d8] */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x010d A[SYNTHETIC, Splitter:B:36:0x010d] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static java.security.cert.Certificate[] loadCertificates(java.util.jar.JarFile r7, java.util.jar.JarEntry r8, byte[] r9) {
            /*
                r3 = 0
                r1 = 0
                java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x004d, RuntimeException -> 0x00ab }
                java.io.InputStream r4 = r7.getInputStream(r8)     // Catch:{ IOException -> 0x004d, RuntimeException -> 0x00ab }
                r2.<init>(r4)     // Catch:{ IOException -> 0x004d, RuntimeException -> 0x00ab }
            L_0x000b:
                r4 = 0
                int r5 = r9.length     // Catch:{ IOException -> 0x0144, RuntimeException -> 0x0140, all -> 0x013d }
                int r4 = r2.read(r9, r4, r5)     // Catch:{ IOException -> 0x0144, RuntimeException -> 0x0140, all -> 0x013d }
                r5 = -1
                if (r4 != r5) goto L_0x000b
                if (r8 == 0) goto L_0x001a
                java.security.cert.Certificate[] r3 = r8.getCertificates()     // Catch:{ IOException -> 0x0144, RuntimeException -> 0x0140, all -> 0x013d }
            L_0x001a:
                if (r2 == 0) goto L_0x001f
                r2.close()     // Catch:{ Exception -> 0x0021 }
            L_0x001f:
                r1 = r2
            L_0x0020:
                return r3
            L_0x0021:
                r0 = move-exception
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Close IS Exception"
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r8.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r7.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r4, r5, r0)
                goto L_0x001f
            L_0x004d:
                r0 = move-exception
            L_0x004e:
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x010a }
                r5.<init>()     // Catch:{ all -> 0x010a }
                java.lang.String r6 = "Exception reading "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = r8.getName()     // Catch:{ all -> 0x010a }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = r7.getName()     // Catch:{ all -> 0x010a }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x010a }
                android.util.Log.w(r4, r5, r0)     // Catch:{ all -> 0x010a }
                if (r1 == 0) goto L_0x0020
                r1.close()     // Catch:{ Exception -> 0x007e }
                goto L_0x0020
            L_0x007e:
                r0 = move-exception
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Close IS Exception"
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r8.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r7.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r4, r5, r0)
                goto L_0x0020
            L_0x00ab:
                r0 = move-exception
            L_0x00ac:
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x010a }
                r5.<init>()     // Catch:{ all -> 0x010a }
                java.lang.String r6 = "Exception reading "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = r8.getName()     // Catch:{ all -> 0x010a }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r6 = r7.getName()     // Catch:{ all -> 0x010a }
                java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x010a }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x010a }
                android.util.Log.w(r4, r5, r0)     // Catch:{ all -> 0x010a }
                if (r1 == 0) goto L_0x0020
                r1.close()     // Catch:{ Exception -> 0x00dd }
                goto L_0x0020
            L_0x00dd:
                r0 = move-exception
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Close IS Exception"
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r8.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r7.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r4, r5, r0)
                goto L_0x0020
            L_0x010a:
                r3 = move-exception
            L_0x010b:
                if (r1 == 0) goto L_0x0110
                r1.close()     // Catch:{ Exception -> 0x0111 }
            L_0x0110:
                throw r3
            L_0x0111:
                r0 = move-exception
                java.lang.String r4 = "Certificates"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Close IS Exception"
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r8.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = " in "
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r6 = r7.getName()
                java.lang.StringBuilder r5 = r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r4, r5, r0)
                goto L_0x0110
            L_0x013d:
                r3 = move-exception
                r1 = r2
                goto L_0x010b
            L_0x0140:
                r0 = move-exception
                r1 = r2
                goto L_0x00ac
            L_0x0144:
                r0 = move-exception
                r1 = r2
                goto L_0x004e
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.ApkUtil.Certificates.loadCertificates(java.util.jar.JarFile, java.util.jar.JarEntry, byte[]):java.security.cert.Certificate[]");
        }

        private static boolean isArchiveValid(String archivePath) {
            if (archivePath == null || archivePath.length() == 0) {
                return false;
            }
            File file = new File(archivePath);
            if (!file.exists() || !file.isFile()) {
                return false;
            }
            return true;
        }

        private static Enumeration<JarEntry> createJarEntries(JarFile jarFile, String... entryNames) {
            if (entryNames == null || entryNames.length == 0) {
                return jarFile.entries();
            }
            return new JarFileEnumerator(jarFile, entryNames);
        }

        static class JarFileEnumerator implements Enumeration<JarEntry> {
            private final String[] entryNames;
            private int index = 0;
            private final JarFile jarFile;

            public JarFileEnumerator(JarFile jarFile2, String... entryNames2) {
                this.jarFile = jarFile2;
                this.entryNames = entryNames2;
            }

            public boolean hasMoreElements() {
                return this.index < this.entryNames.length;
            }

            public JarEntry nextElement() {
                JarFile jarFile2 = this.jarFile;
                String[] strArr = this.entryNames;
                int i = this.index;
                this.index = i + 1;
                return jarFile2.getJarEntry(strArr[i]);
            }
        }
    }
}
