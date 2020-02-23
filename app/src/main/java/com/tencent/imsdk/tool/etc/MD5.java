package com.tencent.imsdk.tool.etc;

import com.tencent.imsdk.IMConfig;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class MD5 {
    public static String getMD5(String data) {
        if (data == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));
            byte[] m = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : m) {
                sb.append(Integer.toHexString((b & 255) | -256).substring(6));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            IMLogger.e("no support for md5 : " + e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e2) {
            IMLogger.e("no support for utf-8 encoding : " + e2.getMessage());
            return null;
        }
    }

    public static String getMd5(Map<String, String> params) {
        ArrayList<String> sorter = new ArrayList<>();
        for (String key : params.keySet()) {
            sorter.add(key);
        }
        Collections.sort(sorter);
        String valueString = "";
        Iterator<String> it = sorter.iterator();
        while (it.hasNext()) {
            String key2 = it.next();
            valueString = valueString + params.get(key2);
            IMLogger.d("key : " + key2 + ", value : " + params.get(key2));
        }
        String md5Key = IMConfig.getMD5Key();
        String md5String = getMD5(valueString + md5Key);
        IMLogger.d("md5 key : " + md5Key + ", value string : " + valueString + ", md5 string : " + md5String);
        return md5String;
    }
}
