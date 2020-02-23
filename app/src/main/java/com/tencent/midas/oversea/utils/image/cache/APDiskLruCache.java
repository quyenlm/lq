package com.tencent.midas.oversea.utils.image.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APMD5;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class APDiskLruCache {
    private static long d = 5242880;
    private static final FilenameFilter h = new a();
    private final File a;
    private int b = 0;
    private int c = 0;
    private Bitmap.CompressFormat e = Bitmap.CompressFormat.PNG;
    private int f = 70;
    private final Map<String, String> g = Collections.synchronizedMap(new LinkedHashMap(32, 0.75f, true));

    private APDiskLruCache(File file, long j) {
        this.a = file;
        d = j;
    }

    private void a() {
        int i = 0;
        while (true) {
            int i2 = i;
            if ((this.b > 64 || ((long) this.c) > d) && i2 < 4) {
                Map.Entry next = this.g.entrySet().iterator().next();
                File file = new File((String) next.getValue());
                long length = file.length();
                this.g.remove(next.getKey());
                file.delete();
                this.b = this.g.size();
                this.c = (int) (((long) this.c) - length);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static void a(File file) {
        File[] listFiles = file.listFiles(h);
        for (File delete : listFiles) {
            delete.delete();
        }
    }

    private void a(String str, String str2) {
        this.g.put(str, str2);
        this.b = this.g.size();
        this.c = (int) (((long) this.c) + new File(str2).length());
    }

    private boolean a(Bitmap bitmap, String str) {
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str), 4096);
            try {
                boolean compress = bitmap.compress(this.e, this.f, bufferedOutputStream);
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                return compress;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedOutputStream = null;
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            throw th;
        }
    }

    public static void clearCache(Context context, String str) {
        a(getDiskCacheDir(context, str));
    }

    public static File getDiskCacheDir(Context context, String str) {
        String path = context.getCacheDir().getPath();
        APLog.i("getDiskCacheDir", "path:" + path);
        return new File(path + File.separator + str);
    }

    public static String getFilePath(File file, String str) {
        try {
            return file.getAbsolutePath() + File.separator + "imgcache_" + APMD5.toMd5(str.getBytes());
        } catch (Exception e2) {
            return null;
        }
    }

    public static String getSDCardDir() {
        File externalStorageDirectory = Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory() : null;
        if (externalStorageDirectory != null) {
            return externalStorageDirectory.toString();
        }
        return null;
    }

    public static APDiskLruCache openCache(Context context, File file) {
        if (!file.exists()) {
            file.mkdir();
        }
        if (!file.isDirectory() || !file.canWrite()) {
            return null;
        }
        return new APDiskLruCache(file, d);
    }

    public static APDiskLruCache openCache(Context context, File file, long j) {
        d = j;
        return openCache(context, file);
    }

    public void clearCache() {
        a(this.a);
    }

    public boolean containsKey(String str) {
        if (this.g.containsKey(str)) {
            return true;
        }
        String filePath = getFilePath(this.a, str);
        if (!new File(filePath).exists()) {
            return false;
        }
        a(str, filePath);
        return true;
    }

    public Bitmap get(String str) {
        Bitmap bitmap;
        synchronized (this.g) {
            try {
                String str2 = this.g.get(str);
                if (str2 != null) {
                    bitmap = BitmapFactory.decodeFile(str2);
                } else {
                    String filePath = getFilePath(this.a, str);
                    APLog.i("APDiskLruCache", "get existingFile :" + filePath);
                    if (new File(filePath).exists()) {
                        a(str, filePath);
                        bitmap = BitmapFactory.decodeFile(filePath);
                    }
                    bitmap = null;
                }
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return bitmap;
    }

    public String getFilePath(String str) {
        return getFilePath(this.a, str);
    }

    public void put(String str, Bitmap bitmap) {
        synchronized (this.g) {
            if (this.g.get(str) == null) {
                try {
                    String filePath = getFilePath(this.a, str);
                    APLog.i("APDiskLruCache", "filePath:" + filePath);
                    if (a(bitmap, filePath)) {
                        a(str, filePath);
                        a();
                    }
                } catch (FileNotFoundException | IOException e2) {
                }
            }
        }
    }
}
