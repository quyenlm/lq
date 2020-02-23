package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GGLiveHeartBeatPlugin extends GGLiveBasePlugin<Void, DataModel.GGLiveHeartbeatRet> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_HEARTBEAT;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, Void data, String error) {
        DataModel.GGLiveHeartbeatRet result = new DataModel.GGLiveHeartbeatRet();
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
                DataModel.GGLiveHeartbeatRet result = new DataModel.GGLiveHeartbeatRet();
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                if (replyData != null) {
                    result.numberViewer = replyData.optInt(GGLiveConstants.PARAM.NUMBER_VIEWER);
                }
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveHeartBeatPlugin.this.getId());
                if (replyData != null) {
                    String newSessionToken = replyData.optString(GGLiveConstants.PARAM.SESSION_TOKEN);
                    if (!TextUtils.isEmpty(newSessionToken)) {
                        GGLiveHeartBeatPlugin.this.saveSessionToken(new SharedPrefStorage(activity), newSessionToken);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                DataModel.GGLiveHeartbeatRet result = new DataModel.GGLiveHeartbeatRet();
                result.flag = GGLiveConstants.getErrorCode(error);
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveHeartBeatPlugin.this.getId());
            }

            public void onTimeout() {
                DataModel.GGLiveHeartbeatRet result = new DataModel.GGLiveHeartbeatRet();
                result.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                GGPluginManager.getInstance().publishResult(result, activity, GGLiveHeartBeatPlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveHeartbeatUrl());
    }
}
