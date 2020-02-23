package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import com.tencent.component.debug.FileTracerReader;
import com.tencent.component.plugin.server.PluginConstant;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;
import org.apache.http.cookie.ClientCookie;

public final class MultiDex {
    private static final String CODE_CACHE_NAME = "code_cache";
    private static final String CODE_CACHE_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String NO_KEY_PREFIX = "";
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<File> installedApk = new HashSet();

    private MultiDex() {
    }

    public static void install(Context context) {
        Log.i(TAG, "Installing application");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
        } else if (Build.VERSION.SDK_INT < 4) {
            throw new RuntimeException("MultiDex installation failed. SDK " + Build.VERSION.SDK_INT + " is unsupported. Min SDK version is " + 4 + ".");
        } else {
            try {
                ApplicationInfo applicationInfo = getApplicationInfo(context);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                doInstallation(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "", true);
                Log.i(TAG, "install done");
            } catch (Exception e) {
                Log.e(TAG, "MultiDex installation failure", e);
                throw new RuntimeException("MultiDex installation failed (" + e.getMessage() + ").");
            }
        }
    }

    public static void installInstrumentation(Context instrumentationContext, Context targetContext) {
        Log.i(TAG, "Installing instrumentation");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
        } else if (Build.VERSION.SDK_INT < 4) {
            throw new RuntimeException("MultiDex installation failed. SDK " + Build.VERSION.SDK_INT + " is unsupported. Min SDK version is " + 4 + ".");
        } else {
            try {
                ApplicationInfo instrumentationInfo = getApplicationInfo(instrumentationContext);
                if (instrumentationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available for instrumentation, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                ApplicationInfo applicationInfo = getApplicationInfo(targetContext);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                String instrumentationPrefix = instrumentationContext.getPackageName() + ".";
                File dataDir = new File(applicationInfo.dataDir);
                doInstallation(targetContext, new File(instrumentationInfo.sourceDir), dataDir, instrumentationPrefix + "secondary-dexes", instrumentationPrefix, false);
                doInstallation(targetContext, new File(applicationInfo.sourceDir), dataDir, "secondary-dexes", "", false);
                Log.i(TAG, "Installation done");
            } catch (Exception e) {
                Log.e(TAG, "MultiDex installation failure", e);
                throw new RuntimeException("MultiDex installation failed (" + e.getMessage() + ").");
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void doInstallation(android.content.Context r11, java.io.File r12, java.io.File r13, java.lang.String r14, java.lang.String r15, boolean r16) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalAccessException, java.lang.NoSuchFieldException, java.lang.reflect.InvocationTargetException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.ClassNotFoundException, java.lang.InstantiationException {
        /*
            java.util.Set<java.io.File> r8 = installedApk
            monitor-enter(r8)
            java.util.Set<java.io.File> r7 = installedApk     // Catch:{ all -> 0x006f }
            boolean r7 = r7.contains(r12)     // Catch:{ all -> 0x006f }
            if (r7 == 0) goto L_0x000d
            monitor-exit(r8)     // Catch:{ all -> 0x006f }
        L_0x000c:
            return
        L_0x000d:
            java.util.Set<java.io.File> r7 = installedApk     // Catch:{ all -> 0x006f }
            r7.add(r12)     // Catch:{ all -> 0x006f }
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x006f }
            r9 = 20
            if (r7 <= r9) goto L_0x0060
            java.lang.String r7 = "MultiDex"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x006f }
            r9.<init>()     // Catch:{ all -> 0x006f }
            java.lang.String r10 = "MultiDex is not guaranteed to work in SDK version "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x006f }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = ": SDK version higher than "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            r10 = 20
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = " should be backed by "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = "runtime with built-in multidex capabilty but it's not the "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = "case here: java.vm.version=\""
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = "java.vm.version"
            java.lang.String r10 = java.lang.System.getProperty(r10)     // Catch:{ all -> 0x006f }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r10 = "\""
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x006f }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x006f }
            android.util.Log.w(r7, r9)     // Catch:{ all -> 0x006f }
        L_0x0060:
            java.lang.ClassLoader r5 = r11.getClassLoader()     // Catch:{ RuntimeException -> 0x0072 }
            if (r5 != 0) goto L_0x007c
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Context class loader is null. Must be running in test mode. Skip patching."
            android.util.Log.e(r7, r9)     // Catch:{ all -> 0x006f }
            monitor-exit(r8)     // Catch:{ all -> 0x006f }
            goto L_0x000c
        L_0x006f:
            r7 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x006f }
            throw r7
        L_0x0072:
            r2 = move-exception
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching."
            android.util.Log.w(r7, r9, r2)     // Catch:{ all -> 0x006f }
            monitor-exit(r8)     // Catch:{ all -> 0x006f }
            goto L_0x000c
        L_0x007c:
            clearOldDexDir(r11)     // Catch:{ Throwable -> 0x0097 }
        L_0x007f:
            java.io.File r1 = getDexDir(r11, r13, r14)     // Catch:{ all -> 0x006f }
            android.support.multidex.MultiDexExtractor r3 = new android.support.multidex.MultiDexExtractor     // Catch:{ all -> 0x006f }
            r3.<init>(r12, r1)     // Catch:{ all -> 0x006f }
            r0 = 0
            r7 = 0
            java.util.List r4 = r3.load(r11, r15, r7)     // Catch:{ all -> 0x00a4 }
            installSecondaryDexes(r5, r1, r4)     // Catch:{ IOException -> 0x00a0 }
        L_0x0091:
            r3.close()     // Catch:{ IOException -> 0x00b9 }
        L_0x0094:
            if (r0 == 0) goto L_0x00bf
            throw r0     // Catch:{ all -> 0x006f }
        L_0x0097:
            r6 = move-exception
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning."
            android.util.Log.w(r7, r9, r6)     // Catch:{ all -> 0x006f }
            goto L_0x007f
        L_0x00a0:
            r2 = move-exception
            if (r16 != 0) goto L_0x00a9
            throw r2     // Catch:{ all -> 0x00a4 }
        L_0x00a4:
            r7 = move-exception
            r3.close()     // Catch:{ IOException -> 0x00bc }
        L_0x00a8:
            throw r7     // Catch:{ all -> 0x006f }
        L_0x00a9:
            java.lang.String r7 = "MultiDex"
            java.lang.String r9 = "Failed to install extracted secondary dex files, retrying with forced extraction"
            android.util.Log.w(r7, r9, r2)     // Catch:{ all -> 0x00a4 }
            r7 = 1
            java.util.List r4 = r3.load(r11, r15, r7)     // Catch:{ all -> 0x00a4 }
            installSecondaryDexes(r5, r1, r4)     // Catch:{ all -> 0x00a4 }
            goto L_0x0091
        L_0x00b9:
            r2 = move-exception
            r0 = r2
            goto L_0x0094
        L_0x00bc:
            r2 = move-exception
            r0 = r2
            goto L_0x00a8
        L_0x00bf:
            monitor-exit(r8)     // Catch:{ all -> 0x006f }
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.multidex.MultiDex.doInstallation(android.content.Context, java.io.File, java.io.File, java.lang.String, java.lang.String, boolean):void");
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getApplicationInfo();
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    isMultidexCapable = major > 2 || (major == 2 && Integer.parseInt(matcher.group(2)) >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i(TAG, "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<? extends File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException, SecurityException, ClassNotFoundException, InstantiationException {
        if (files.isEmpty()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            V19.install(loader, files, dexDir);
        } else if (Build.VERSION.SDK_INT >= 14) {
            V14.install(loader, files);
        } else {
            V4.install(loader, files);
        }
    }

    /* access modifiers changed from: private */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class cls = instance.getClass();
        while (cls != null) {
            try {
                Method method = cls.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File dexDir = new File(context.getFilesDir(), "secondary-dexes");
        if (dexDir.isDirectory()) {
            Log.i(TAG, "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
            File[] files = dexDir.listFiles();
            if (files == null) {
                Log.w(TAG, "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
                return;
            }
            for (File oldFile : files) {
                Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
                if (!oldFile.delete()) {
                    Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
                } else {
                    Log.i(TAG, "Deleted old file " + oldFile.getPath());
                }
            }
            if (!dexDir.delete()) {
                Log.w(TAG, "Failed to delete secondary dex dir " + dexDir.getPath());
            } else {
                Log.i(TAG, "Deleted old secondary dex dir " + dexDir.getPath());
            }
        }
    }

    private static File getDexDir(Context context, File dataDir, String secondaryFolderName) throws IOException {
        File cache = new File(dataDir, CODE_CACHE_NAME);
        try {
            mkdirChecked(cache);
        } catch (IOException e) {
            cache = new File(context.getFilesDir(), CODE_CACHE_NAME);
            mkdirChecked(cache);
        }
        File dexDir = new File(cache, secondaryFolderName);
        mkdirChecked(dexDir);
        return dexDir;
    }

    private static void mkdirChecked(File dir) throws IOException {
        dir.mkdir();
        if (!dir.isDirectory()) {
            File parent = dir.getParentFile();
            if (parent == null) {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". Parent file is null.");
            } else {
                Log.e(TAG, "Failed to create dir " + dir.getPath() + ". parent file is a dir " + parent.isDirectory() + ", a file " + parent.isFile() + ", exists " + parent.exists() + ", readable " + parent.canRead() + ", writable " + parent.canWrite());
            }
            throw new IOException("Failed to create directory " + dir.getPath());
        }
    }

    private static final class V19 {
        private V19() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator<IOException> it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", it.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions2.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
                IOException exception = new IOException("I/O exception during makeDexElement");
                exception.initCause(suppressedExceptions.get(0));
                throw exception;
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V14 {
        private static final int EXTRACTED_SUFFIX_LENGTH = FileTracerReader.ZIP_FILE_EXT.length();
        private final ElementConstructor elementConstructor;

        private interface ElementConstructor {
            Object newInstance(File file, DexFile dexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException;
        }

        private static class ICSElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            ICSElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, ZipFile.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
                return this.elementConstructor.newInstance(new Object[]{file, new ZipFile(file), dex});
            }
        }

        private static class JBMR11ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR11ElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, File.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
                return this.elementConstructor.newInstance(new Object[]{file, file, dex});
            }
        }

        private static class JBMR2ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR2ElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, Boolean.TYPE, File.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
                return this.elementConstructor.newInstance(new Object[]{file, Boolean.FALSE, file, dex});
            }
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) throws IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            Object[] elements = new V14().makeDexElements(additionalClassPathEntries);
            try {
                MultiDex.expandFieldArray(dexPathList, "dexElements", elements);
            } catch (NoSuchFieldException e) {
                Log.w(MultiDex.TAG, "Failed find field 'dexElements' attempting 'pathElements'", e);
                MultiDex.expandFieldArray(dexPathList, "pathElements", elements);
            }
        }

        private V14() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
            ElementConstructor constructor;
            Class<?> elementClass = Class.forName("dalvik.system.DexPathList$Element");
            try {
                constructor = new ICSElementConstructor(elementClass);
            } catch (NoSuchMethodException e) {
                try {
                    constructor = new JBMR11ElementConstructor(elementClass);
                } catch (NoSuchMethodException e2) {
                    constructor = new JBMR2ElementConstructor(elementClass);
                }
            }
            this.elementConstructor = constructor;
        }

        private Object[] makeDexElements(List<? extends File> files) throws IOException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
            Object[] elements = new Object[files.size()];
            for (int i = 0; i < elements.length; i++) {
                File file = (File) files.get(i);
                elements[i] = this.elementConstructor.newInstance(file, DexFile.loadDex(file.getPath(), optimizedPathFor(file), 0));
            }
            return elements;
        }

        private static String optimizedPathFor(File path) {
            File optimizedDirectory = path.getParentFile();
            String fileName = path.getName();
            return new File(optimizedDirectory, fileName.substring(0, fileName.length() - EXTRACTED_SUFFIX_LENGTH) + PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX).getPath();
        }
    }

    private static final class V4 {
        private V4() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, ClientCookie.PATH_ATTR);
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<? extends File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX, 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }
}
