package com.subao.common;

import com.tencent.imsdk.framework.consts.InnerErrorCode;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.List;

/* compiled from: Misc */
public class e {
    public static final SecureRandom a = new SecureRandom();

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException | RuntimeException e) {
            }
        }
    }

    public static byte[] a(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read <= 0) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static long a(byte[] bArr, int i, int i2, long j) {
        if (i2 > bArr.length) {
            i2 = bArr.length;
        }
        while (true) {
            if (i >= i2) {
                break;
            }
            byte b = bArr[i];
            if (Character.isDigit(b)) {
                j = (long) (b - 48);
                for (int i3 = i + 1; i3 < i2; i3++) {
                    byte b2 = bArr[i3];
                    if (!Character.isDigit(b2)) {
                        break;
                    }
                    j = (10 * j) + ((long) (b2 - 48));
                }
            } else {
                i++;
            }
        }
        return j;
    }

    public static boolean a(int i) {
        int i2 = i % InnerErrorCode.SDK_ERROR_BASIC_XG;
        return i2 >= 10000 && i2 <= 19999;
    }

    public static <T> boolean a(T t, T t2) {
        if (t == t2) {
            return true;
        }
        if (t == null || t2 == null) {
            return false;
        }
        return t.equals(t2);
    }

    public static boolean a(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static boolean a(List list, List list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            return false;
        }
        if (list.size() != list2.size()) {
            return false;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!a(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String a(String str) {
        return b(str, "UTF-8");
    }

    public static String b(String str, String str2) {
        if (str == null || str.length() == 0) {
            return "";
        }
        try {
            return URLEncoder.encode(str, str2);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
