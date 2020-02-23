package com.beetalk.sdk.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

public class CacheHelper {
    public static final String CACHE_IMAGE_FOLDER = "cache_images";
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    private static final int MAX_SIZE = 50000000;
    public static final int TIME_DAY = 86400;
    public static final int TIME_HOUR = 3600;
    private static Map<String, CacheHelper> mInstanceMap = new HashMap();
    private ACacheManager mCache;

    public static CacheHelper get(Context ctx) {
        return get(ctx, "CacheHelper");
    }

    public static CacheHelper get(Context ctx, String cacheName) {
        return get(new File(ctx.getCacheDir(), cacheName), 50000000, Integer.MAX_VALUE);
    }

    public static CacheHelper get(File cacheDir) {
        return get(cacheDir, 50000000, Integer.MAX_VALUE);
    }

    public static CacheHelper get(Context ctx, long max_size, int max_count) {
        return get(new File(ctx.getCacheDir(), "ACache"), max_size, max_count);
    }

    public static CacheHelper get(File cacheDir, long max_size, int max_count) {
        CacheHelper manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager != null) {
            return manager;
        }
        CacheHelper manager2 = new CacheHelper(cacheDir, max_size, max_count);
        mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager2);
        return manager2;
    }

    private static String myPid() {
        return "_" + Process.myPid();
    }

    private CacheHelper(File cacheDir, long max_size, int max_count) {
        if (cacheDir.exists() || cacheDir.mkdirs()) {
            this.mCache = new ACacheManager(cacheDir, max_size, max_count);
            return;
        }
        throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030 A[SYNTHETIC, Splitter:B:15:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0044 A[SYNTHETIC, Splitter:B:22:0x0044] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            java.io.File r1 = r4.newFile(r7)
            r2 = 0
            java.io.BufferedWriter r3 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x002a }
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ IOException -> 0x002a }
            r4.<init>(r1)     // Catch:{ IOException -> 0x002a }
            r5 = 1024(0x400, float:1.435E-42)
            r3.<init>(r4, r5)     // Catch:{ IOException -> 0x002a }
            r3.write(r8)     // Catch:{ IOException -> 0x0058, all -> 0x0055 }
            if (r3 == 0) goto L_0x001e
            r3.flush()     // Catch:{ IOException -> 0x0025 }
            r3.close()     // Catch:{ IOException -> 0x0025 }
        L_0x001e:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            r4.put(r1)
            r2 = r3
        L_0x0024:
            return
        L_0x0025:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x001e
        L_0x002a:
            r0 = move-exception
        L_0x002b:
            r0.printStackTrace()     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0036
            r2.flush()     // Catch:{ IOException -> 0x003c }
            r2.close()     // Catch:{ IOException -> 0x003c }
        L_0x0036:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            r4.put(r1)
            goto L_0x0024
        L_0x003c:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0036
        L_0x0041:
            r4 = move-exception
        L_0x0042:
            if (r2 == 0) goto L_0x004a
            r2.flush()     // Catch:{ IOException -> 0x0050 }
            r2.close()     // Catch:{ IOException -> 0x0050 }
        L_0x004a:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r5 = r6.mCache
            r5.put(r1)
            throw r4
        L_0x0050:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x004a
        L_0x0055:
            r4 = move-exception
            r2 = r3
            goto L_0x0042
        L_0x0058:
            r0 = move-exception
            r2 = r3
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.put(java.lang.String, java.lang.String):void");
    }

    public void put(String key, String value, int saveTime) {
        put(key, Utils.newStringWithDateInfo(saveTime, value));
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0065 A[SYNTHETIC, Splitter:B:33:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0076 A[SYNTHETIC, Splitter:B:41:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getAsString(java.lang.String r10) {
        /*
            r9 = this;
            r7 = 0
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r8 = r9.mCache
            java.io.File r2 = r8.get(r10)
            boolean r8 = r2.exists()
            if (r8 != 0) goto L_0x000e
        L_0x000d:
            return r7
        L_0x000e:
            r6 = 0
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x005f }
            java.io.FileReader r8 = new java.io.FileReader     // Catch:{ IOException -> 0x005f }
            r8.<init>(r2)     // Catch:{ IOException -> 0x005f }
            r4.<init>(r8)     // Catch:{ IOException -> 0x005f }
            java.lang.String r5 = ""
        L_0x001c:
            java.lang.String r0 = r4.readLine()     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            if (r0 == 0) goto L_0x0034
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            r8.<init>()     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            java.lang.String r5 = r8.toString()     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            goto L_0x001c
        L_0x0034:
            boolean r8 = com.beetalk.sdk.helper.CacheHelper.Utils.isDue((java.lang.String) r5)     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            if (r8 != 0) goto L_0x004e
            java.lang.String r7 = com.beetalk.sdk.helper.CacheHelper.Utils.clearDateInfo((java.lang.String) r5)     // Catch:{ IOException -> 0x0087, all -> 0x0084 }
            if (r4 == 0) goto L_0x0043
            r4.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0043:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x0049:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0043
        L_0x004e:
            r6 = 1
            if (r4 == 0) goto L_0x0054
            r4.close()     // Catch:{ IOException -> 0x005a }
        L_0x0054:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x005a:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0054
        L_0x005f:
            r1 = move-exception
        L_0x0060:
            r1.printStackTrace()     // Catch:{ all -> 0x0073 }
            if (r3 == 0) goto L_0x0068
            r3.close()     // Catch:{ IOException -> 0x006e }
        L_0x0068:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x006e:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0068
        L_0x0073:
            r7 = move-exception
        L_0x0074:
            if (r3 == 0) goto L_0x0079
            r3.close()     // Catch:{ IOException -> 0x007f }
        L_0x0079:
            if (r6 == 0) goto L_0x007e
            r9.remove(r10)
        L_0x007e:
            throw r7
        L_0x007f:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0079
        L_0x0084:
            r7 = move-exception
            r3 = r4
            goto L_0x0074
        L_0x0087:
            r1 = move-exception
            r3 = r4
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.getAsString(java.lang.String):java.lang.String");
    }

    public void put(String key, JSONObject value) {
        put(key, value.toString());
    }

    public void put(String key, JSONObject value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    public JSONObject getAsJSONObject(String key) {
        String JSONString = getAsString(key);
        if (TextUtils.isEmpty(JSONString)) {
            return null;
        }
        try {
            return new JSONObject(JSONString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(String key, JSONArray value) {
        put(key, value.toString());
    }

    public void put(String key, JSONArray value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    public JSONArray getAsJSONArray(String key) {
        try {
            return new JSONArray(getAsString(key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029 A[SYNTHETIC, Splitter:B:15:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003d A[SYNTHETIC, Splitter:B:22:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r7, byte[] r8) {
        /*
            r6 = this;
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            java.io.File r1 = r4.newFile(r7)
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0023 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0023 }
            r3.write(r8)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            if (r3 == 0) goto L_0x0017
            r3.flush()     // Catch:{ IOException -> 0x001e }
            r3.close()     // Catch:{ IOException -> 0x001e }
        L_0x0017:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            r4.put(r1)
            r2 = r3
        L_0x001d:
            return
        L_0x001e:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0017
        L_0x0023:
            r0 = move-exception
        L_0x0024:
            r0.printStackTrace()     // Catch:{ all -> 0x003a }
            if (r2 == 0) goto L_0x002f
            r2.flush()     // Catch:{ IOException -> 0x0035 }
            r2.close()     // Catch:{ IOException -> 0x0035 }
        L_0x002f:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r4 = r6.mCache
            r4.put(r1)
            goto L_0x001d
        L_0x0035:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x002f
        L_0x003a:
            r4 = move-exception
        L_0x003b:
            if (r2 == 0) goto L_0x0043
            r2.flush()     // Catch:{ IOException -> 0x0049 }
            r2.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0043:
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r5 = r6.mCache
            r5.put(r1)
            throw r4
        L_0x0049:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0043
        L_0x004e:
            r4 = move-exception
            r2 = r3
            goto L_0x003b
        L_0x0051:
            r0 = move-exception
            r2 = r3
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.put(java.lang.String, byte[]):void");
    }

    public void put(String key, byte[] value, int saveTime) {
        put(key, Utils.newByteArrayWithDateInfo(saveTime, value));
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0074 A[SYNTHETIC, Splitter:B:47:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0079  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getAsBinary(java.lang.String r11) {
        /*
            r10 = this;
            r6 = 0
            r0 = 0
            r5 = 0
            com.beetalk.sdk.helper.CacheHelper$ACacheManager r7 = r10.mCache     // Catch:{ Exception -> 0x005d }
            java.io.File r4 = r7.get(r11)     // Catch:{ Exception -> 0x005d }
            boolean r7 = r4.exists()     // Catch:{ Exception -> 0x005d }
            if (r7 != 0) goto L_0x001f
            if (r0 == 0) goto L_0x0014
            r0.close()     // Catch:{ IOException -> 0x001a }
        L_0x0014:
            if (r5 == 0) goto L_0x0019
            r10.remove(r11)
        L_0x0019:
            return r6
        L_0x001a:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0014
        L_0x001f:
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x005d }
            java.lang.String r7 = "r"
            r1.<init>(r4, r7)     // Catch:{ Exception -> 0x005d }
            long r8 = r1.length()     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            int r7 = (int) r8     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            byte[] r2 = new byte[r7]     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            r1.read(r2)     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            boolean r7 = com.beetalk.sdk.helper.CacheHelper.Utils.isDue((byte[]) r2)     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            if (r7 != 0) goto L_0x004b
            byte[] r6 = com.beetalk.sdk.helper.CacheHelper.Utils.clearDateInfo((byte[]) r2)     // Catch:{ Exception -> 0x0085, all -> 0x0082 }
            if (r1 == 0) goto L_0x003f
            r1.close()     // Catch:{ IOException -> 0x0046 }
        L_0x003f:
            if (r5 == 0) goto L_0x0044
            r10.remove(r11)
        L_0x0044:
            r0 = r1
            goto L_0x0019
        L_0x0046:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x003f
        L_0x004b:
            r5 = 1
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ IOException -> 0x0058 }
        L_0x0051:
            if (r5 == 0) goto L_0x0056
            r10.remove(r11)
        L_0x0056:
            r0 = r1
            goto L_0x0019
        L_0x0058:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0051
        L_0x005d:
            r3 = move-exception
        L_0x005e:
            r3.printStackTrace()     // Catch:{ all -> 0x0071 }
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x006c }
        L_0x0066:
            if (r5 == 0) goto L_0x0019
            r10.remove(r11)
            goto L_0x0019
        L_0x006c:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0066
        L_0x0071:
            r6 = move-exception
        L_0x0072:
            if (r0 == 0) goto L_0x0077
            r0.close()     // Catch:{ IOException -> 0x007d }
        L_0x0077:
            if (r5 == 0) goto L_0x007c
            r10.remove(r11)
        L_0x007c:
            throw r6
        L_0x007d:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0077
        L_0x0082:
            r6 = move-exception
            r0 = r1
            goto L_0x0072
        L_0x0085:
            r3 = move-exception
            r0 = r1
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.getAsBinary(java.lang.String):byte[]");
    }

    public void put(String key, Serializable value) {
        put(key, value, -1);
    }

    public void put(String key, Serializable value, int saveTime) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos2 = new ObjectOutputStream(baos);
                try {
                    oos2.writeObject(value);
                    byte[] data = baos.toByteArray();
                    if (saveTime != -1) {
                        put(key, data, saveTime);
                    } else {
                        put(key, data);
                    }
                    try {
                        oos2.close();
                        ObjectOutputStream objectOutputStream = oos2;
                        ByteArrayOutputStream byteArrayOutputStream = baos;
                    } catch (IOException e) {
                        ObjectOutputStream objectOutputStream2 = oos2;
                        ByteArrayOutputStream byteArrayOutputStream2 = baos;
                    }
                } catch (Exception e2) {
                    e = e2;
                    oos = oos2;
                    ByteArrayOutputStream byteArrayOutputStream3 = baos;
                } catch (Throwable th) {
                    th = th;
                    oos = oos2;
                    ByteArrayOutputStream byteArrayOutputStream4 = baos;
                    try {
                        oos.close();
                    } catch (IOException e3) {
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                ByteArrayOutputStream byteArrayOutputStream5 = baos;
                try {
                    e.printStackTrace();
                    try {
                        oos.close();
                    } catch (IOException e5) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    oos.close();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                ByteArrayOutputStream byteArrayOutputStream6 = baos;
                oos.close();
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            e.printStackTrace();
            oos.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0032 A[SYNTHETIC, Splitter:B:24:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0037 A[SYNTHETIC, Splitter:B:27:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0048 A[SYNTHETIC, Splitter:B:35:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004d A[SYNTHETIC, Splitter:B:38:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getAsObject(java.lang.String r9) {
        /*
            r8 = this;
            r6 = 0
            byte[] r2 = r8.getAsBinary(r9)
            if (r2 == 0) goto L_0x0021
            r0 = 0
            r4 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x002c }
            r1.<init>(r2)     // Catch:{ Exception -> 0x002c }
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            r5.<init>(r1)     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            java.lang.Object r6 = r5.readObject()     // Catch:{ Exception -> 0x0065, all -> 0x005e }
            if (r1 == 0) goto L_0x001c
            r1.close()     // Catch:{ IOException -> 0x0022 }
        L_0x001c:
            if (r5 == 0) goto L_0x0021
            r5.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0021:
            return r6
        L_0x0022:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x001c
        L_0x0027:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0021
        L_0x002c:
            r3 = move-exception
        L_0x002d:
            r3.printStackTrace()     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x0035
            r0.close()     // Catch:{ IOException -> 0x0040 }
        L_0x0035:
            if (r4 == 0) goto L_0x0021
            r4.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x0021
        L_0x003b:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0021
        L_0x0040:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0035
        L_0x0045:
            r7 = move-exception
        L_0x0046:
            if (r0 == 0) goto L_0x004b
            r0.close()     // Catch:{ IOException -> 0x0051 }
        L_0x004b:
            if (r4 == 0) goto L_0x0050
            r4.close()     // Catch:{ IOException -> 0x0056 }
        L_0x0050:
            throw r7
        L_0x0051:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x004b
        L_0x0056:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0050
        L_0x005b:
            r7 = move-exception
            r0 = r1
            goto L_0x0046
        L_0x005e:
            r7 = move-exception
            r4 = r5
            r0 = r1
            goto L_0x0046
        L_0x0062:
            r3 = move-exception
            r0 = r1
            goto L_0x002d
        L_0x0065:
            r3 = move-exception
            r4 = r5
            r0 = r1
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.getAsObject(java.lang.String):java.lang.Object");
    }

    public void put(String key, Bitmap value) {
        put(key, Utils.Bitmap2Bytes(value));
    }

    public void put(String key, Bitmap value, int saveTime) {
        put(key, Utils.Bitmap2Bytes(value), saveTime);
    }

    public Bitmap getAsBitmap(String key) {
        if (getAsBinary(key) == null) {
            return null;
        }
        return Utils.Bytes2Bimap(getAsBinary(key));
    }

    public void put(String key, Drawable value) {
        put(key, Utils.drawable2Bitmap(value));
    }

    public void put(String key, Drawable value, int saveTime) {
        put(key, Utils.drawable2Bitmap(value), saveTime);
    }

    public Drawable getAsDrawable(String key) {
        if (getAsBinary(key) == null) {
            return null;
        }
        return Utils.bitmap2Drawable(Utils.Bytes2Bimap(getAsBinary(key)));
    }

    public File file(String key) {
        File f = this.mCache.newFile(key);
        if (f.exists()) {
            return f;
        }
        return null;
    }

    public boolean remove(String key) {
        return this.mCache.remove(key);
    }

    public void clear() {
        this.mCache.clear();
    }

    public class ACacheManager {
        /* access modifiers changed from: private */
        public final AtomicInteger cacheCount;
        protected File cacheDir;
        /* access modifiers changed from: private */
        public final AtomicLong cacheSize;
        private final int countLimit;
        /* access modifiers changed from: private */
        public final Map<File, Long> lastUsageDates;
        private final long sizeLimit;

        private ACacheManager(File cacheDir2, long sizeLimit2, int countLimit2) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = cacheDir2;
            this.sizeLimit = sizeLimit2;
            this.countLimit = countLimit2;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() {
                public void run() {
                    int size = 0;
                    int count = 0;
                    File[] cachedFiles = ACacheManager.this.cacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size = (int) (((long) size) + ACacheManager.this.calculateSize(cachedFile));
                            count++;
                            ACacheManager.this.lastUsageDates.put(cachedFile, Long.valueOf(cachedFile.lastModified()));
                        }
                        ACacheManager.this.cacheSize.set((long) size);
                        ACacheManager.this.cacheCount.set(count);
                    }
                }
            }).start();
        }

        /* access modifiers changed from: private */
        public void put(File file) {
            int curCacheCount = this.cacheCount.get();
            while (curCacheCount + 1 > this.countLimit) {
                this.cacheSize.addAndGet(-removeNext());
                curCacheCount = this.cacheCount.addAndGet(-1);
            }
            this.cacheCount.addAndGet(1);
            long valueSize = calculateSize(file);
            long curCacheSize = this.cacheSize.get();
            while (curCacheSize + valueSize > this.sizeLimit) {
                curCacheSize = this.cacheSize.addAndGet(-removeNext());
            }
            this.cacheSize.addAndGet(valueSize);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
        }

        /* access modifiers changed from: private */
        public File get(String key) {
            File file = newFile(key);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
            return file;
        }

        /* access modifiers changed from: private */
        public File newFile(String key) {
            return new File(this.cacheDir, key.hashCode() + "");
        }

        /* access modifiers changed from: private */
        public boolean remove(String key) {
            return get(key).delete();
        }

        /* access modifiers changed from: private */
        public void clear() {
            this.lastUsageDates.clear();
            this.cacheSize.set(0);
            File[] files = this.cacheDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.io.File} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.io.File} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: java.lang.Long} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private long removeNext() {
            /*
                r16 = this;
                r0 = r16
                java.util.Map<java.io.File, java.lang.Long> r9 = r0.lastUsageDates
                boolean r9 = r9.isEmpty()
                if (r9 == 0) goto L_0x000d
                r4 = 0
            L_0x000c:
                return r4
            L_0x000d:
                r8 = 0
                r7 = 0
                r0 = r16
                java.util.Map<java.io.File, java.lang.Long> r9 = r0.lastUsageDates
                java.util.Set r2 = r9.entrySet()
                r0 = r16
                java.util.Map<java.io.File, java.lang.Long> r10 = r0.lastUsageDates
                monitor-enter(r10)
                java.util.Iterator r11 = r2.iterator()     // Catch:{ all -> 0x0070 }
            L_0x0020:
                boolean r9 = r11.hasNext()     // Catch:{ all -> 0x0070 }
                if (r9 == 0) goto L_0x005b
                java.lang.Object r3 = r11.next()     // Catch:{ all -> 0x0070 }
                java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x0070 }
                if (r7 != 0) goto L_0x003f
                java.lang.Object r9 = r3.getKey()     // Catch:{ all -> 0x0070 }
                r0 = r9
                java.io.File r0 = (java.io.File) r0     // Catch:{ all -> 0x0070 }
                r7 = r0
                java.lang.Object r9 = r3.getValue()     // Catch:{ all -> 0x0070 }
                r0 = r9
                java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ all -> 0x0070 }
                r8 = r0
                goto L_0x0020
            L_0x003f:
                java.lang.Object r6 = r3.getValue()     // Catch:{ all -> 0x0070 }
                java.lang.Long r6 = (java.lang.Long) r6     // Catch:{ all -> 0x0070 }
                long r12 = r6.longValue()     // Catch:{ all -> 0x0070 }
                long r14 = r8.longValue()     // Catch:{ all -> 0x0070 }
                int r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
                if (r9 >= 0) goto L_0x0020
                r8 = r6
                java.lang.Object r9 = r3.getKey()     // Catch:{ all -> 0x0070 }
                r0 = r9
                java.io.File r0 = (java.io.File) r0     // Catch:{ all -> 0x0070 }
                r7 = r0
                goto L_0x0020
            L_0x005b:
                monitor-exit(r10)     // Catch:{ all -> 0x0070 }
                r0 = r16
                long r4 = r0.calculateSize(r7)
                boolean r9 = r7.delete()
                if (r9 == 0) goto L_0x000c
                r0 = r16
                java.util.Map<java.io.File, java.lang.Long> r9 = r0.lastUsageDates
                r9.remove(r7)
                goto L_0x000c
            L_0x0070:
                r9 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x0070 }
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.helper.CacheHelper.ACacheManager.removeNext():long");
        }

        /* access modifiers changed from: private */
        public long calculateSize(File file) {
            return file.length();
        }
    }

    private static class Utils {
        private static final char mSeparator = ' ';

        private Utils() {
        }

        /* access modifiers changed from: private */
        public static boolean isDue(String str) {
            return isDue(str.getBytes());
        }

        /* access modifiers changed from: private */
        public static boolean isDue(byte[] data) {
            String[] strs = getDateInfoFromDate(data);
            if (strs != null && strs.length == 2) {
                String saveTimeStr = strs[0];
                while (saveTimeStr.startsWith("0")) {
                    saveTimeStr = saveTimeStr.substring(1, saveTimeStr.length());
                }
                if (System.currentTimeMillis() > (1000 * Long.valueOf(strs[1]).longValue()) + Long.valueOf(saveTimeStr).longValue()) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        public static String newStringWithDateInfo(int second, String strInfo) {
            return createDateInfo(second) + strInfo;
        }

        /* access modifiers changed from: private */
        public static byte[] newByteArrayWithDateInfo(int second, byte[] data2) {
            byte[] data1 = createDateInfo(second).getBytes();
            byte[] retdata = new byte[(data1.length + data2.length)];
            System.arraycopy(data1, 0, retdata, 0, data1.length);
            System.arraycopy(data2, 0, retdata, data1.length, data2.length);
            return retdata;
        }

        /* access modifiers changed from: private */
        public static String clearDateInfo(String strInfo) {
            if (strInfo == null || !hasDateInfo(strInfo.getBytes())) {
                return strInfo;
            }
            return strInfo.substring(strInfo.indexOf(32) + 1, strInfo.length());
        }

        /* access modifiers changed from: private */
        public static byte[] clearDateInfo(byte[] data) {
            if (hasDateInfo(data)) {
                return copyOfRange(data, indexOf(data, mSeparator) + 1, data.length);
            }
            return data;
        }

        private static boolean hasDateInfo(byte[] data) {
            return data != null && data.length > 15 && data[13] == 45 && indexOf(data, mSeparator) > 14;
        }

        private static String[] getDateInfoFromDate(byte[] data) {
            if (!hasDateInfo(data)) {
                return null;
            }
            return new String[]{new String(copyOfRange(data, 0, 13)), new String(copyOfRange(data, 14, indexOf(data, mSeparator)))};
        }

        private static int indexOf(byte[] data, char c) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == c) {
                    return i;
                }
            }
            return -1;
        }

        private static byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            if (newLength < 0) {
                throw new IllegalArgumentException(from + " > " + to);
            }
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }

        private static String createDateInfo(int second) {
            String currentTime = System.currentTimeMillis() + "";
            while (currentTime.length() < 13) {
                currentTime = "0" + currentTime;
            }
            return currentTime + "-" + second + mSeparator;
        }

        /* access modifiers changed from: private */
        public static byte[] Bitmap2Bytes(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }

        /* access modifiers changed from: private */
        public static Bitmap Bytes2Bimap(byte[] b) {
            if (b.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }

        /* access modifiers changed from: private */
        public static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);
            return bitmap;
        }

        /* access modifiers changed from: private */
        public static Drawable bitmap2Drawable(Bitmap bm) {
            if (bm == null) {
                return null;
            }
            return new BitmapDrawable(bm);
        }
    }
}
