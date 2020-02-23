package com.tencent.qqgamemi.util;

import android.text.TextUtils;
import com.tencent.tp.a.h;
import java.io.UnsupportedEncodingException;

public class StringUtils {
    public static boolean isNetworkUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (isHttpUrl(url) || isHttpsUrl(url)) {
            return true;
        }
        return false;
    }

    public static boolean isHttpUrl(String url) {
        if (url == null || url.length() <= 6 || !url.substring(0, 7).equalsIgnoreCase("http://")) {
            return false;
        }
        return true;
    }

    public static boolean isHttpsUrl(String url) {
        if (url == null || url.length() <= 7 || !url.substring(0, 8).equalsIgnoreCase("https://")) {
            return false;
        }
        return true;
    }

    public static int compareVersion(String s1, String s2) {
        int i1;
        int i2;
        if (s1 == null && s2 == null) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }
        String[] arr1 = s1.split("[^a-zA-Z0-9]+");
        String[] arr2 = s2.split("[^a-zA-Z0-9]+");
        int ii = 0;
        int max = Math.min(arr1.length, arr2.length);
        while (ii <= max) {
            if (ii == arr1.length) {
                if (ii != arr2.length) {
                    return -1;
                }
                return 0;
            } else if (ii == arr2.length) {
                return 1;
            } else {
                try {
                    i1 = Integer.parseInt(arr1[ii]);
                } catch (Exception e) {
                    i1 = Integer.MAX_VALUE;
                }
                try {
                    i2 = Integer.parseInt(arr2[ii]);
                } catch (Exception e2) {
                    i2 = Integer.MAX_VALUE;
                }
                if (i1 != i2) {
                    return i1 - i2;
                }
                int i3 = arr1[ii].compareTo(arr2[ii]);
                if (i3 != 0) {
                    return i3;
                }
                ii++;
            }
        }
        return 0;
    }

    public static byte[] getUtf8(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupport utf-8 encoding", e);
        }
    }

    public static String richNumber(long value) {
        return value + "(0x" + Long.toHexString(value) + h.b;
    }

    public static <T> T transStringToSafe(String str, T defaultValue) {
        T transValue = defaultValue;
        if (str == null) {
            return transValue;
        }
        try {
            if (transValue instanceof Integer) {
                return Integer.valueOf(str);
            }
            if (transValue instanceof Float) {
                return Float.valueOf(str);
            }
            return transValue;
        } catch (Exception e) {
            T transValue2 = defaultValue;
            e.printStackTrace();
            return transValue2;
        }
    }

    public static int[] transStringToIntArray(String typeString) {
        if (TextUtils.isEmpty(typeString)) {
            return new int[0];
        }
        typeString.replace(" ", "");
        String[] str = typeString.split(",");
        int[] result = new int[str.length];
        int i = 0;
        while (i < str.length) {
            try {
                result[i] = Integer.valueOf(str[i].trim()).intValue();
                i++;
            } catch (Exception e) {
                throw new RuntimeException("transStringToIntArray excption", e);
            }
        }
        return result;
    }
}
