package com.tencent.imsdk.tool.etc;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import org.json.JSONObject;

public class CommonUtil {
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static long timeEnd = 0;
    static long timeStart = 0;

    @Deprecated
    public static String generateHttpBaseQueryString(String url, String action, String lowerCase, HashMap<String, String> hashMap) {
        return null;
    }

    public static boolean ckIsEmpty(String s) {
        if (s == null || s.trim().equals("") || s.trim().equals(Constants.NULL_VERSION_ID)) {
            return true;
        }
        return false;
    }

    public static boolean ckNonEmpty(String... argvs) {
        for (String arg : argvs) {
            if (ckIsEmpty(arg)) {
                return true;
            }
        }
        return false;
    }

    public static void logEmptyPara(String... argvs) {
        for (String arg : argvs) {
            if (ckIsEmpty(arg)) {
                IMLogger.w(arg);
            }
        }
    }

    public static final String arrayToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            buff.append(bytes[i] + " ");
        }
        return buff.toString();
    }

    @SuppressLint({"DefaultLocale"})
    public static String bytes2HexString(byte[] _bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (_bytes == null || _bytes.length <= 0) {
            IMLogger.d("on CommonUtil.bytes2HexString _bytes is null !");
            return null;
        }
        for (byte b : _bytes) {
            String hv = Integer.toHexString(b & 255).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte char2Byte(char ch) {
        if (ch >= '0' && ch <= '9') {
            return (byte) (ch - '0');
        }
        if (ch >= 'a' && ch <= 'f') {
            return (byte) ((ch - 'a') + 10);
        }
        if (ch < 'A' || ch > 'F') {
            return 0;
        }
        return (byte) ((ch - 'A') + 10);
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((char2Byte(str.charAt(i * 2)) * 16) + char2Byte(str.charAt((i * 2) + 1)));
        }
        return bytes;
    }

    public static String bytes2BinString(byte[] _bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (_bytes == null || _bytes.length <= 0) {
            IMLogger.d("on CommonUtil.bytes2BinString _bytes is null !");
            return null;
        }
        for (byte b : _bytes) {
            String hv = Integer.toBinaryString(b & 255);
            for (int j = 8; j > hv.length(); j--) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String generateQueryString(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        String aQueryParam = "";
        if (params.size() <= 0) {
            return aQueryParam;
        }
        for (String aParamName : params.keySet()) {
            aQueryParam = aQueryParam + aParamName + HttpRequest.HTTP_REQ_ENTITY_MERGE + encode(params.get(aParamName)) + HttpRequest.HTTP_REQ_ENTITY_JOIN;
        }
        return aQueryParam.substring(0, aQueryParam.length() - 1);
    }

    public static void testTimeBegain() {
        timeStart = System.currentTimeMillis();
    }

    public static long calcUsedTime() {
        timeEnd = System.currentTimeMillis();
        long usedTime = timeEnd - timeStart;
        IMLogger.d("useEdTime:" + usedTime);
        return usedTime;
    }

    public static String generateQueryJson(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        return new JSONObject(params).toString();
    }

    public static String encode(String value) {
        String encoded = "";
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        StringBuffer buf = new StringBuffer(encoded.length());
        int i = 0;
        while (i < encoded.length()) {
            char focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
            i++;
        }
        return buf.toString();
    }

    public static boolean strIsInList(String srcStr, List<String> orgList) {
        if (orgList == null) {
            return true;
        }
        for (String strList : orgList) {
            if (strList.equals(srcStr)) {
                return false;
            }
        }
        return true;
    }

    public static String bytes2HexStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        char[] buf = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf[(i * 2) + 1] = digits[b & 15];
            buf[(i * 2) + 0] = digits[((byte) (b >>> 4)) & 15];
        }
        return new String(buf);
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static String getNoQueryUrl(String source) {
        try {
            URL sUrl = new URL(source);
            return new URL(sUrl.getProtocol(), sUrl.getHost(), sUrl.getPort(), sUrl.getPath()).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRandomString(int len) {
        char[] ch = new char[len];
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            ch[i] = (char) (rd.nextInt(9) + 97);
        }
        return new String(ch);
    }

    public static Vector<KVPair> bundleToVector(Bundle b) {
        Vector<KVPair> tempVector = new Vector<>();
        if (b != null) {
            for (String key : b.keySet()) {
                KVPair item = new KVPair();
                item.key = key;
                Object value = b.get(key);
                if (value != null) {
                    item.value = value.toString();
                    tempVector.add(item);
                }
            }
        }
        return tempVector;
    }

    public static String convertIfNull(String value) {
        return value != null ? value : Constants.NULL_VERSION_ID;
    }
}
