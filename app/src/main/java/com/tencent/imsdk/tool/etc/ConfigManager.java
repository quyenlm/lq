package com.tencent.imsdk.tool.etc;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    public static final String MSDK_CONFIG_FILE = "iMSDKConfig.ini";

    public static String readValueByKey(Context ctx, String key) {
        return readValueByKey(ctx, MSDK_CONFIG_FILE, key);
    }

    public static String readValueByKey(Context ctx, String fileName, String key) {
        if (ctx == null) {
            IMLogger.e("currentContext is null");
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = ctx.getResources().getAssets().open(fileName);
            Properties properties = new Properties();
            properties.load(inputStream);
            String value = properties.getProperty(key, "");
            if (value == null || value.length() == 0) {
                IMLogger.d("no key: " + key);
                if (inputStream == null) {
                    return "";
                }
                try {
                    inputStream.close();
                    return "";
                } catch (Exception e) {
                    IMLogger.w("inputStream close exception = " + e.getMessage());
                    return "";
                }
            } else {
                String trim = value.trim();
                if (inputStream == null) {
                    return trim;
                }
                try {
                    inputStream.close();
                    return trim;
                } catch (Exception e2) {
                    IMLogger.w("inputStream close exception = " + e2.getMessage());
                    return trim;
                }
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            IMLogger.w("Please check your MSDK config file under /assets/");
            if (inputStream == null) {
                return "";
            }
            try {
                inputStream.close();
                return "";
            } catch (Exception e4) {
                IMLogger.w("inputStream close exception = " + e4.getMessage());
                return "";
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e5) {
                    IMLogger.w("inputStream close exception = " + e5.getMessage());
                }
            }
            throw th;
        }
    }
}
