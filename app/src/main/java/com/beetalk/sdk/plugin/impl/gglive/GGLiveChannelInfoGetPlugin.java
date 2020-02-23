package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLiveChannelInfoGetPlugin extends GGLiveBasePlugin<Void, DataModel.GGLiveGetChannelInfoRet> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_GET_CHANNEL_INFO;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, Void data, String error) {
        DataModel.GGLiveGetChannelInfoRet result = new DataModel.GGLiveGetChannelInfoRet();
        result.flag = GGLiveConstants.getErrorCode(error);
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(final Activity activity, Void data, String sessionToken) {
        JSONObject postData = new JSONObject();
        try {
            postData.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionToken);
        } catch (JSONException e) {
        }
        new HttpRequestTask(postData, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
            /* access modifiers changed from: protected */
            public void onSuccess(JSONObject replyData) {
                DataModel.GGLiveGetChannelInfoRet result = new DataModel.GGLiveGetChannelInfoRet();
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                if (replyData != null) {
                    result.name = replyData.optString("name");
                    result.desc = replyData.optString("description");
                }
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoGetPlugin.this.getId());
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                DataModel.GGLiveGetChannelInfoRet result = new DataModel.GGLiveGetChannelInfoRet();
                result.flag = GGLiveConstants.getErrorCode(error);
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoGetPlugin.this.getId());
            }

            public void onTimeout() {
                DataModel.GGLiveGetChannelInfoRet result = new DataModel.GGLiveGetChannelInfoRet();
                result.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelInfoGetPlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveGetChannelInfoUrl());
    }
}
