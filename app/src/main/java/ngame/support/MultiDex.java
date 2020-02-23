package ngame.support;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.unity3d.player.UnityPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MultiDex {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_DIR_NAME = "dex";
    private static final String DEX_OPT_DIR_NAME = "dex_opt";
    private static final String TAG = "NGame.MultiDex";
    private static final String UNITY_OBJECT_NAME = "MultiDexHelper";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    /* access modifiers changed from: private */
    public static Handler mMainHandler = null;
    private static boolean sHasInited = false;

    private MultiDex() {
    }

    public static void unityInstall(String callbackMethod, String dexFileName) {
        Log.d(TAG, "unityInstall callbackMethod:" + callbackMethod);
        UnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, callbackMethod, install(UnityPlayer.currentActivity.getApplicationContext(), dexFileName) ? "true" : "false");
    }

    public static void unityInstallAsync(String callbackMethod, String dexFileName) {
        Log.d(TAG, "unityInstallAsync");
        final String sCallbackMethod = callbackMethod;
        mMainHandler = new Handler() {
            public void handleMessage(Message msg) {
                boolean result = true;
                if (msg.arg1 != 1) {
                    result = false;
                }
                Log.d(MultiDex.TAG, "unityInstallAsync handleMessage:" + result);
                UnityPlayer.UnitySendMessage(MultiDex.UNITY_OBJECT_NAME, sCallbackMethod, result ? "true" : "false");
            }
        };
        final String sDexFileName = dexFileName;
        new Thread(new Runnable() {
            public void run() {
                Log.d(MultiDex.TAG, "unityInstallAsync run");
                boolean result = MultiDex.install(UnityPlayer.currentActivity.getApplicationContext(), sDexFileName);
                Message msg = new Message();
                msg.arg1 = result ? 1 : 0;
                MultiDex.mMainHandler.sendMessage(msg);
            }
        }).start();
    }

    private static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[16384];
        while (true) {
            int read = is.read(buffer);
            if (read == -1) {
                break;
            }
            md.update(buffer, 0, read);
        }
        byte[] digest = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (byte b : digest) {
            hexString.append(Integer.toHexString(b & 255));
        }
        return hexString.toString();
    }

    private static boolean compareMD5(InputStream is1, InputStream is2) throws NoSuchAlgorithmException, IOException {
        return getMD5(is1).equals(getMD5(is2));
    }

    private static String copyFromAssetsToAppDir(Context context, String assetFileName, File dexDir) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
        if (!dexDir.exists()) {
            dexDir.mkdir();
        }
        InputStream in = context.getAssets().open(assetFileName);
        File dexFile = new File(dexDir.getAbsolutePath(), assetFileName);
        if (ApkUtil.isApkModified(context)) {
            if (dexFile.exists()) {
                dexFile.delete();
            }
            OutputStream out = new FileOutputStream(dexFile);
            byte[] buffer = new byte[16384];
            while (true) {
                int read = in.read(buffer);
                if (read == -1) {
                    break;
                }
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
        }
        return dexFile.getAbsolutePath();
    }

    public static boolean install(Context context) {
        return install(context, "secondary.dex");
    }

    public static boolean install(Context context, String assetFileName) {
        if (sHasInited) {
            Log.d(TAG, "sHasInited");
            return true;
        }
        try {
            ClassLoader loader = context.getClassLoader();
            File dexDir = context.getDir(DEX_DIR_NAME, 0);
            Log.d(TAG, "dexDir:" + dexDir.getAbsolutePath());
            String installedPath = copyFromAssetsToAppDir(context, assetFileName, dexDir);
            List<File> files = new ArrayList<>();
            files.add(new File(installedPath));
            File optDir = context.getDir(DEX_OPT_DIR_NAME, 0);
            Log.d(TAG, "optDir:" + optDir.getAbsolutePath());
            installSecondaryDexes(loader, optDir, files);
            ApkUtil.saveApkSignature(context);
            sHasInited = true;
            return true;
        } catch (Throwable e) {
            Log.d(TAG, "install dex:" + assetFileName + " failed, error:" + Log.getStackTraceString(e));
            return false;
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
        Log.d(TAG, "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
        if (files.isEmpty()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            V19.install(loader, files, dexDir);
        } else {
            V14.install(loader, files, dexDir);
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
        Log.d(TAG, "combined dexFile length:" + combined.length);
        jlrField.set(instance, combined);
    }

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator<IOException> it = suppressedExceptions.iterator();
                if (it.hasNext()) {
                    IOException e = it.next();
                    Log.d(MultiDex.TAG, "Exception in makeDexElement:" + Log.getStackTraceString(e));
                    throw e;
                }
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            Method makeDexElements;
            try {
                makeDexElements = MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Log.d(MultiDex.TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
                try {
                    makeDexElements = MultiDex.findMethod(dexPathList, "makeDexElements", List.class, File.class, List.class);
                } catch (NoSuchMethodException e2) {
                    Log.d(MultiDex.TAG, "NoSuchMethodException: makeDexElements(List,File,List) failure");
                    try {
                        makeDexElements = MultiDex.findMethod(dexPathList, "makePathElements", List.class, File.class, List.class);
                    } catch (NoSuchMethodException e22) {
                        Log.d(MultiDex.TAG, "NoSuchMethodException: makePathElements(List,File,List) failure");
                        throw e22;
                    }
                }
            }
            return (Object[]) makeDexElements.invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V14 {
        private V14() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
        }
    }
}
