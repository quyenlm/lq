package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelInfoUpdateData;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLiveChannelInfoUpdatePlugin extends GGLiveBasePlugin<GGLiveChannelInfoUpdateData, PluginResult> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_UPDATE_CHANNEL_INFO;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, GGLiveChannelInfoUpdateData data, String error) {
        PluginResult result = new PluginResult();
        result.source = getId();
        result.status = -1;
        result.flag = GGLiveConstants.getErrorCode(error);
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(final Activity activity, GGLiveChannelInfoUpdateData data, String sessionToken) {
        JSONObject postData = new JSONObject();
        try {
            postData.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionToken);
            if (data != null) {
                postData.put("name", data.name);
                postData.put("description", data.desc);
            }
        } catch (JSONException e) {
        }
        new HttpRequestTask(postData, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
            /* access modifiers changed from: protected */
            public void onSuccess(JSONObject replyData) {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelInfoUpdatePlugin.this.getId();
                result.status = 0;
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoUpdatePlugin.this.getId());
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelInfoUpdatePlugin.this.getId();
                result.status = -1;
                result.flag = GGLiveConstants.getErrorCode(error);
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoUpdatePlugin.this.getId());
            }

            public void onTimeout() {
                PluginResult result = new PluginResult();
                result.source = GGLiveChannelInfoUpdatePlugin.this.getId();
                result.status = -1;
                result.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoUpdatePlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveUpdateChannelInfoUrl());
    }
}
