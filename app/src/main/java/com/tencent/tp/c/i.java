package com.tencent.tp.c;

import android.content.Context;
import android.os.Environment;
import com.appsflyer.share.Constants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class i {
    public static String a(Context context) {
        String b = b(context, "tmp");
        if (b != null) {
            return b(b);
        }
        return null;
    }

    public static String a(Context context, String str) throws IOException {
        return new File(context.getFilesDir(), str).getAbsolutePath();
    }

    public static void a(String str, String str2) throws IOException {
        File file = new File(str);
        File file2 = new File(str2);
        if (file2.exists()) {
            file2.delete();
        }
        file.renameTo(file2);
    }

    public static boolean a(String str) throws IOException {
        return new File(str).exists();
    }

    public static boolean a(String str, int i) throws IOException {
        if (i < 1) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.length() == ((long) i);
    }

    public static String b(Context context, String str) {
        try {
            return a(context, str);
        } catch (IOException e) {
            return null;
        }
    }

    public static String b(String str) {
        int lastIndexOf = str.lastIndexOf(Constants.URL_PATH_DELIMITER);
        return lastIndexOf > -1 ? str.substring(0, lastIndexOf) : str;
    }

    public static boolean b(Context context) {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String c(Context context) throws IOException {
        if (!b(context)) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String c(Context context, String str) throws IOException {
        return !b(context) ? a(context, str) : Environment.getExternalStorageDirectory().getPath() + Constants.URL_PATH_DELIMITER + str;
    }

    public static String c(String str) {
        int lastIndexOf = str.lastIndexOf(Constants.URL_PATH_DELIMITER);
        return lastIndexOf > -1 ? str.substring(lastIndexOf + 1, str.length()) : str;
    }

    public static String d(Context context) throws IOException {
        if (!b(context)) {
            return null;
        }
        String path = context.getExternalFilesDir((String) null).getPath();
        return path.substring(0, path.lastIndexOf(47));
    }

    public static void d(String str) throws IOException {
        i(b(str));
    }

    public static void e(String str) throws IOException {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void f(String str) {
        try {
            e(str);
        } catch (IOException e) {
        }
    }

    public static String[] g(String str) throws IOException {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (File name : listFiles) {
                arrayList.add(name.getName());
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static long h(String str) throws IOException {
        File file = new File(str);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    private static void i(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
