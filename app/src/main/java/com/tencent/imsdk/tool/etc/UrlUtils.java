package com.tencent.imsdk.tool.etc;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.sns.base.IMHttpClient;
import com.tencent.imsdk.sns.base.IMUrlResult;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;

public class UrlUtils {
    protected static String GUID_URL_LIST_URL = (IMConfig.getSdkServerUrl() + "/url/getUrl");
    static final int IMSDK_SYSTEM_ERROR_CODE = 3;
    private static IMHttpClient mClient;

    public static boolean initialize(Context context) {
        GUID_URL_LIST_URL = IMConfig.getSdkServerUrl() + "/url/getUrl";
        mClient = new IMHttpClient();
        return context != null;
    }

    public static void shortUrl(String url, IMCallback<IMUrlResult> callback) {
        shortUrl(url, (String) null, callback);
    }

    public static void shortUrl(String url, String urlTypeMark, final IMCallback<IMUrlResult> callback) {
        if (mClient == null) {
            mClient = new IMHttpClient();
        }
        Map<String, String> params = new HashMap<>();
        params.put("sUrl", url);
        if (urlTypeMark != null) {
            params.put("urlTypeMark", urlTypeMark);
        }
        params.put("iChannel", "5");
        mClient.get(GUID_URL_LIST_URL, params, new IMCallback<String>() {
            public void onSuccess(String result) {
                try {
                    callback.onSuccess(new IMUrlResult(result));
                } catch (JSONException e) {
                    callback.onError(new IMException(3, e.getMessage(), 3, IMErrorMsg.getMessageByCode(3)));
                }
            }

            public void onCancel() {
                callback.onCancel();
            }

            public void onError(IMException exception) {
                callback.onError(exception);
            }
        });
    }
}
