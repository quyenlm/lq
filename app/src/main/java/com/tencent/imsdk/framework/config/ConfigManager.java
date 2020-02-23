package com.tencent.imsdk.framework.config;

import android.content.Context;
import com.tencent.imsdk.tool.encrypt.DesUtils;
import com.tencent.imsdk.tool.encrypt.TEACoding;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public class ConfigManager {
    public static String readValueByKey(Context ctx, String key) {
        return readValueByKey(ctx, Config.IMSDK_CONFIG_FILE, key);
    }

    public static String readValueByKey(Context ctx, String fileName, String key) {
        if (ctx == null) {
            IMLogger.e("context is null");
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
                    IMLogger.d(" inputStream colse error = " + e.getMessage());
                    return "";
                }
            } else {
                String encrypt = properties.getProperty(Config.KEY_ENCRYPT_SWITCH, "false");
                if (encrypt != null && encrypt.length() != 0 && "true".endsWith(encrypt) && key.contains("_KEY")) {
                    value = new String(new TEACoding(DesUtils.CONFIG_KEY).decodeFromBase64Str(value.trim()), Charset.forName("UTF-8"));
                }
                String trim = value.trim();
                if (inputStream == null) {
                    return trim;
                }
                try {
                    inputStream.close();
                    return trim;
                } catch (Exception e2) {
                    IMLogger.d(" inputStream colse error = " + e2.getMessage());
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
                IMLogger.d(" inputStream colse error = " + e4.getMessage());
                return "";
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e5) {
                    IMLogger.d(" inputStream colse error = " + e5.getMessage());
                }
            }
            throw th;
        }
    }
}
