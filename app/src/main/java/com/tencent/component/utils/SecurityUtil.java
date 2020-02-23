package com.tencent.component.utils;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
    private static final long INITIALCRC = -1;
    private static final long POLY64REV = -7661587058870466123L;
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static long[] sCrcTable = new long[256];

    private SecurityUtil() {
    }

    static {
        for (int i = 0; i < 256; i++) {
            long part = (long) i;
            for (int j = 0; j < 8; j++) {
                part = (part >> 1) ^ ((((int) part) & 1) != 0 ? POLY64REV : 0);
            }
            sCrcTable[i] = part;
        }
    }

    public static String encrypt(String source) {
        return encrypt(source, "MD5");
    }

    public static String encrypt(String source, String algorithm) {
        if (source == null) {
            return null;
        }
        return encrypt(source.getBytes(), algorithm);
    }

    public static String encrypt(byte[] source) {
        return encrypt(source, "MD5");
    }

    public static String encrypt(byte[] source, String algorithm) {
        if (source == null || source.length == 0) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(source);
            return bytes2HexStr(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(File file) {
        return encrypt(file, "MD5");
    }

    public static String encrypt(File file, String algorithm) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        try {
            return encryptOrThrow(file, algorithm);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String encryptOrThrow(File file) throws IOException, NoSuchAlgorithmException {
        return encryptOrThrow(file, "MD5");
    }

    public static String encryptOrThrow(File file, String algorithm) throws IOException, NoSuchAlgorithmException {
        if (file == null) {
            return null;
        }
        FileInputStream fis = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            FileInputStream fis2 = new FileInputStream(file);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int count = fis2.read(buffer);
                    if (count <= 0) {
                        break;
                    }
                    digest.update(buffer, 0, count);
                }
                String result = bytes2HexStr(digest.digest());
                if (fis2 == null) {
                    return result;
                }
                try {
                    fis2.close();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return result;
                }
            } catch (Throwable th) {
                th = th;
                fis = fis2;
            }
        } catch (Throwable th2) {
            th = th2;
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static String bytes2HexStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        char[] buf = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf[(i * 2) + 1] = digits[b & 15];
            buf[i * 2] = digits[((byte) (b >>> 4)) & 15];
        }
        return new String(buf);
    }

    public static byte[] encryptTea(byte[] key, String datas) {
        return new TEA(key).encrypt(datas);
    }

    public static byte[] encryptTea(byte[] key, byte[] datas) {
        return new TEA(key).encrypt(datas);
    }

    public static byte[] decryptTea(byte[] key, byte[] crypt) {
        return new TEA(key).decrypt(crypt);
    }

    public static String decryptTeaToString(byte[] key, byte[] crypt) {
        return new TEA(key).decryptToString(crypt);
    }

    public static long crc64Long(String in) {
        if (in == null || in.length() == 0) {
            return 0;
        }
        return crc64Long(getBytes(in));
    }

    public static long crc64Long(byte[] buffer) {
        long crc = -1;
        for (byte b : buffer) {
            crc = sCrcTable[(((int) crc) ^ b) & 255] ^ (crc >> 8);
        }
        return crc;
    }

    public static byte[] getBytes(String in) {
        byte[] result = new byte[(in.length() * 2)];
        int output = 0;
        for (char ch : in.toCharArray()) {
            int output2 = output + 1;
            result[output] = (byte) (ch & 255);
            output = output2 + 1;
            result[output2] = (byte) (ch >> 8);
        }
        return result;
    }

    @SuppressLint({"NewApi"})
    public static void injectJs(WebView webView, Object obj, String name) {
        Class<WebView> cls = WebView.class;
        try {
            cls.getMethod("addJavascript" + "Interface", new Class[]{Object.class, String.class}).invoke(webView, new Object[]{obj, name});
            if (PlatformUtil.version() >= 11) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
    }

    public static void removeJs(WebView webView, String name) {
        try {
            WebView.class.getMethod("removeJavascriptInterface", new Class[]{Object.class, String.class}).invoke(webView, new Object[]{name});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
    }
}
