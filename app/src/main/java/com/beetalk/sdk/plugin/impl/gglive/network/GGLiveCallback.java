package com.beetalk.sdk.plugin.impl.gglive.network;

import android.text.TextUtils;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import org.json.JSONObject;

public abstract class GGLiveCallback implements HttpRequestTask.JsonCallback {
    /* access modifiers changed from: protected */
    public abstract void onError(String str);

    /* access modifiers changed from: protected */
    public abstract void onSuccess(JSONObject jSONObject);

    public final void onResultObtained(JSONObject response) {
        if (response == null) {
            onError(GGLiveConstants.ERROR_CODE.ERROR_NETWORK_EXCEPTION);
            return;
        }
        String result = response.optString(GGLiveConstants.PARAM.RESULT);
        if ("success".equals(result)) {
            onSuccess(response.optJSONObject(GGLiveConstants.PARAM.REPLY));
        } else if (TextUtils.isEmpty(result)) {
            onError("error_unknown");
        } else {
            onError(result);
        }
    }
}
