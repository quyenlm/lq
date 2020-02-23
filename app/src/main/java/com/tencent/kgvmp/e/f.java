package com.tencent.kgvmp.e;

import android.util.Log;
import com.tencent.kgvmp.e.b;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class f {
    private static int a = 1;
    private static boolean b = false;
    private static FileOutputStream c;

    public static void a(int i) {
        a = i;
    }

    public static void a(String str, String str2) {
        if (a >= 3 || b) {
            if (str2 == null) {
                str2 = "";
            }
            if (b) {
                b(b.a(b.a.PATTERN2.getFormat()) + " " + str + " : " + str2 + "\n");
            }
            Log.d(str, str2);
        }
    }

    public static boolean a(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (file.exists() && file.canRead()) {
            b = true;
            try {
                if (file.length() > 10485760) {
                    file.delete();
                    file.createNewFile();
                }
                c = new FileOutputStream(file, true);
            } catch (Exception e) {
                e.printStackTrace();
                b = false;
            }
        }
        return b;
    }

    private static void b(String str) {
        try {
            if (c != null) {
                c.write(str.getBytes());
                c.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void b(String str, String str2) {
        if (a >= 4 || b) {
            if (str2 == null) {
                str2 = "";
            }
            if (b) {
                b(b.a(b.a.PATTERN2.getFormat()) + " " + str + " : " + str2 + "\n");
            }
            Log.i(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (a >= 6 || b) {
            if (str2 == null) {
                str2 = "";
            }
            if (b) {
                b(b.a(b.a.PATTERN2.getFormat()) + " " + str + " : " + str2 + "\n");
            }
            Log.e(str, str2);
        }
    }
}
