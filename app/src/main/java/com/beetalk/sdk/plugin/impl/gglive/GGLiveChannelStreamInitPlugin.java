package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.data.GGLiveChannelStreamInitData;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLiveChannelStreamInitPlugin extends GGLiveBasePlugin<GGLiveChannelStreamInitData, DataModel.GGLiveChannelStreamInitRet> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_INIT_CHANNEL_STREAM;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, GGLiveChannelStreamInitData data, String error) {
        DataModel.GGLiveChannelStreamInitRet result = new DataModel.GGLiveChannelStreamInitRet();
        result.flag = GGLiveConstants.getErrorCode(error);
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(final Activity activity, GGLiveChannelStreamInitData data, String sessionToken) {
        JSONObject postData = new JSONObject();
        try {
            postData.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionToken);
            postData.put(GGLiveConstants.PARAM.REGION, data.region == null ? "" : data.region);
            postData.put(GGLiveConstants.PARAM.CATEGORY_ID, data.categoryId);
            postData.put(GGLiveConstants.PARAM.CLIENT_TYPE, GGLiveConstants.STREAMING_CLIENT_TYPE);
            postData.put(GGLiveConstants.PARAM.MACHINE_ID, GGLiveConstants.generateDeviceId(activity));
        } catch (JSONException e) {
        }
        new HttpRequestTask(postData, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
            /* access modifiers changed from: protected */
            public void onSuccess(JSONObject replyData) {
                DataModel.GGLiveChannelStreamInitRet result = new DataModel.GGLiveChannelStreamInitRet();
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                if (replyData != null) {
                    result.streamServerAddress = replyData.optString(GGLiveConstants.PARAM.STREAM_SERVER_ADDRESS);
                    result.streamKey = replyData.optString(GGLiveConstants.PARAM.STREAM_KEY);
                }
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelStreamInitPlugin.this.getId());
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                DataModel.GGLiveChannelStreamInitRet result = new DataModel.GGLiveChannelStreamInitRet();
                result.flag = GGLiveConstants.getErrorCode(error);
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelStreamInitPlugin.this.getId());
            }

            public void onTimeout() {
                DataModel.GGLiveChannelStreamInitRet result = new DataModel.GGLiveChannelStreamInitRet();
                result.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveChannelStreamInitPlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveInitChannelStreamUrl());
    }
}
