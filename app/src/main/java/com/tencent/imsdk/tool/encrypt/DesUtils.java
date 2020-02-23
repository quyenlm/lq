package com.tencent.imsdk.tool.encrypt;

import com.tencent.imsdk.tool.etc.HexUtil;
import java.nio.charset.Charset;

public class DesUtils {
    public static final byte[] CONFIG_KEY = {68, 67, 53, 48, 53, 66, 69, 55, 65, 56, 66, 68, 68, 56, 54, 52};
    public static final byte[] REQUEST_KEY = {109, 115, 100, 107, 109, 115, 100, 107, 109, 115, 100, 107, 109, 115, 100, 107};

    public static String encryptToHex(String key) {
        byte[] a = key.getBytes(Charset.forName("UTF-8"));
        StringBuilder sb = new StringBuilder();
        int length = a.length;
        for (int i = 0; i < length; i++) {
            sb.append("0x" + HexUtil.byte2HexStr(a[i]) + ",");
        }
        return "" + sb.toString();
    }
}
