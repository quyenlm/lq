package com.tencent.apollo;

import android.content.Context;
import android.util.Log;
import com.appsflyer.share.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ApolloVoiceConfig {
    private static String LOGTAG = "ApolloVoice";
    private static final String cfgName = "ApolloVoice/config.json";
    private static String dyCfgPath = "/com.tencent.gcloud.gvoice/config/gvoice.cfg";
    private static Context mainContext;
    private static String storageCfgPath = null;

    public static void SetContext(Context ctxt) {
        mainContext = ctxt;
        File file = mainContext.getExternalFilesDir((String) null);
        if (file != null) {
            storageCfgPath = file.getAbsolutePath() + Constants.URL_PATH_DELIMITER + cfgName;
        } else {
            Log.w(LOGTAG, "getExternalFilesDir failed !!!");
        }
    }

    public static boolean IsSDCardCfgExist() {
        return new File(storageCfgPath).exists();
    }

    public static String DynamicCfgPath() {
        String cfgPath = "invalied";
        try {
            File sdDir = mainContext.getFilesDir();
            if (sdDir != null) {
                cfgPath = sdDir.toString() + dyCfgPath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(LOGTAG, "Read Dynamic  : " + cfgPath);
        return cfgPath;
    }

    public static String JSONCfg(boolean bsdcard_cfg) {
        InputStream fileInput;
        FileInputStream fileInputStream;
        if (bsdcard_cfg) {
            try {
                fileInputStream = new FileInputStream(storageCfgPath);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return null;
            }
            try {
                Log.i(LOGTAG, "Read config file from storage : " + storageCfgPath);
                fileInput = fileInputStream;
            } catch (Exception e2) {
                e = e2;
                FileInputStream fileInputStream2 = fileInputStream;
                e.printStackTrace();
                return null;
            }
        } else {
            fileInput = mainContext.getResources().getAssets().open(cfgName);
        }
        int len = fileInput.available();
        byte[] pbyte = new byte[len];
        int nRead = 0;
        while (nRead < len) {
            int nret = fileInput.read(pbyte, nRead, len - nRead);
            if (nret == -1) {
                break;
            }
            nRead += nret;
        }
        fileInput.close();
        String Result = new String(pbyte, "UTF-8");
        Log.i(LOGTAG, "####Get config :" + Result);
        return Result;
    }
}
