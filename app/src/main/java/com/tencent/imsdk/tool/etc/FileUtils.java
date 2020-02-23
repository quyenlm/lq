package com.tencent.imsdk.tool.etc;

import android.os.Environment;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class FileUtils {
    private static final String DIR_EXT_MAIN = "MSDK";
    private static final String FILE_LOG = "iMSDK.log";

    public static boolean hasExternalStorage() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static File getLogFile() {
        return new File(getExternalRootDir(), FILE_LOG);
    }

    public static File getExternalRootDir() {
        File childDir = new File(Environment.getExternalStorageDirectory(), "MSDK");
        if (!childDir.exists() && !childDir.mkdirs()) {
            System.out.println("mkdir external root dir failed");
        }
        return childDir;
    }

    public static String getUTF8XMLString(String xml) {
        String xmlUTF8 = "";
        try {
            String xmString = new String((xml).getBytes("UTF-8"), Charset.forName("UTF-8"));
            try {
                xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
                System.out.println("utf-8 encode ：" + xmlUTF8);
                String str = xmString;
            } catch (UnsupportedEncodingException e) {
                e = e;
                String str2 = xmString;
                e.printStackTrace();
                return xmlUTF8;
            }
        } catch (UnsupportedEncodingException e2) {
            e = e2;
            e.printStackTrace();
            return xmlUTF8;
        }
        return xmlUTF8;
    }
}
