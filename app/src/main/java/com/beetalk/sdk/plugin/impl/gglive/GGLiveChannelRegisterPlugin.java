package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelRegisterData;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLiveChannelRegisterPlugin extends GGLiveBasePlugin<GGLiveChannelRegisterData, PluginResult> {
    public /* bridge */ /* synthetic */ boolean embedInActivity() {
        return super.embedInActivity();
    }

    public /* bridge */ /* synthetic */ Integer getRequestCode() {
        return super.getRequestCode();
    }

    public /* bridge */ /* synthetic */ boolean onActivityResult(Activity activity, int i, Intent intent) {
        return super.onActivityResult(activity, i, intent);
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GGLIVE_REGISTER_CHANNEL;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, GGLiveChannelRegisterData data, String error) {
        PluginResult result = new PluginResult();
        result.source = getId();
        result.flag = GGLiveConstants.getErrorCode(error);
        result.status = -1;
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(final Activity activity, GGLiveChannelRegisterData data, String sessionToken) {
        JSONObject postData = new JSONObject();
        try {
            postData.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionToken);
            postData.put("name", data.name == null ? "" : data.name);
            postData.put("description", data.desc == null ? "" : data.desc);
        } catch (JSONException e) {
        }
        new HttpRequestTask(postData, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
            /* access modifiers changed from: protected */
            public void onSuccess(JSONObject replyData) {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelRegisterPlugin.this.getId();
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                result.status = 0;
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelRegisterPlugin.this.getId());
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelRegisterPlugin.this.getId();
                result.flag = GGLiveConstants.getErrorCode(error);
                result.status = -1;
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelRegisterPlugin.this.getId());
            }

            public void onTimeout() {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelRegisterPlugin.this.getId();
                result.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                result.status = -1;
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelRegisterPlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveRegisterChannelUrl());
    }
}
