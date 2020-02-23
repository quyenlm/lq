package com.tencent.imsdk.config.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.config.ConfigDBManager;
import com.tencent.imsdk.sns.base.IMHttpClient;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.KVPair;
import com.tencent.imsdk.tool.etc.SharedPreferenceHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMConfigurate {
    private static String CONFIGURATION_URL = null;
    protected static final String CONFIG_VERSION = "config_version_num";
    private static final String DEFAULT_QUERY = "DEFAULT";
    private static final String VERSION_JSON_TAG = "iVersion";
    private static ConfigDBManager dbManager = null;
    private static Context gContext;
    private static IMHttpClient mHttpClient = null;

    public static void initialize(Context mContext) {
        gContext = mContext;
        IMConfig.initialize(mContext);
        CONFIGURATION_URL = IMConfig.getSdkServerUrl() + "/conf/get";
        dbManager = new ConfigDBManager(mContext);
        mHttpClient = new IMHttpClient();
        mHttpClient.initialize();
    }

    private static boolean needInitFirst() {
        if (dbManager == null) {
            IMLogger.e("Need execute initialize func first");
        }
        return dbManager != null;
    }

    public static String getValueByKey(String key) {
        return getValueByKey(key, "DEFAULT");
    }

    public static String getValueByKey(String key, String defaultValue) {
        if (!needInitFirst()) {
            return defaultValue;
        }
        String valueTmp = dbManager.get("key", key).value;
        return TextUtils.isEmpty(valueTmp) ? defaultValue : valueTmp;
    }

    public static void updateConfiguration() {
        updateConfiguration((IMCallback<List<KVPair>>) null);
    }

    public static void updateConfiguration(final IMCallback<List<KVPair>> callback) {
        if (needInitFirst()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(VERSION_JSON_TAG, SharedPreferenceHelper.getSharedPreferenceString(gContext, CONFIG_VERSION, "0"));
            mHttpClient.get(CONFIGURATION_URL, hashMap, new IMCallback<String>() {
                public void onSuccess(String result) {
                    if (callback != null) {
                        callback.onSuccess(IMConfigurate.optNetworkRequest(result));
                    }
                }

                public void onCancel() {
                    if (callback != null) {
                        callback.onCancel();
                    }
                }

                public void onError(IMException exception) {
                    if (callback != null) {
                        callback.onError(exception);
                    }
                }
            }, false);
        }
    }

    private static void printErrorMessage(Exception ex) {
        if (ex != null) {
            IMLogger.e(ex.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public static List<KVPair> optNetworkRequest(String requestRet) {
        List<KVPair> keyValueList = new ArrayList<>();
        try {
            JSONObject diffConfigJO = checkRequestRet(requestRet);
            if (diffConfigJO != null) {
                Iterator<String> iterator = diffConfigJO.keys();
                while (iterator.hasNext()) {
                    KVPair keyValueTmp = new KVPair();
                    keyValueTmp.key = iterator.next();
                    keyValueTmp.value = diffConfigJO.get(keyValueTmp.key).toString();
                    keyValueList.add(keyValueTmp);
                }
                dbManager.addOrUpdate(keyValueList);
                return keyValueList;
            }
            KVPair kv = new KVPair();
            kv.key = "retMsg";
            kv.value = requestRet;
            keyValueList.add(kv);
            return keyValueList;
        } catch (JSONException e) {
            printErrorMessage(e);
        } catch (FileNotFoundException e2) {
            printErrorMessage(e2);
        } catch (IOException e3) {
            printErrorMessage(e3);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0013, code lost:
        r1 = java.lang.Integer.parseInt(com.tencent.imsdk.tool.etc.SharedPreferenceHelper.getSharedPreferenceString(gContext, CONFIG_VERSION, "0"));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.json.JSONObject checkRequestRet(java.lang.String r6) throws org.json.JSONException, java.lang.NullPointerException, java.io.IOException {
        /*
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>(r6)
            java.lang.String r3 = "SUCCESS"
            java.lang.String r4 = "desc"
            java.lang.String r4 = r2.getString(r4)
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x003b
            android.content.Context r3 = gContext
            java.lang.String r4 = "config_version_num"
            java.lang.String r5 = "0"
            java.lang.String r3 = com.tencent.imsdk.tool.etc.SharedPreferenceHelper.getSharedPreferenceString(r3, r4, r5)
            int r1 = java.lang.Integer.parseInt(r3)
            java.lang.String r3 = "iVersion"
            int r0 = r2.getInt(r3)
            if (r0 <= r1) goto L_0x003b
            android.content.Context r3 = gContext
            java.lang.String r4 = java.lang.String.valueOf(r0)
            java.lang.String r5 = "0"
            com.tencent.imsdk.tool.etc.SharedPreferenceHelper.setSharedPreferenceString(r3, r4, r5)
            java.lang.String r3 = "diffConfig"
            org.json.JSONObject r3 = r2.optJSONObject(r3)
        L_0x003a:
            return r3
        L_0x003b:
            r3 = 0
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.config.api.IMConfigurate.checkRequestRet(java.lang.String):org.json.JSONObject");
    }
}
