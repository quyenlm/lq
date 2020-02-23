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

public class GGLiveChannelStreamVerifyPlugin extends GGLiveBasePlugin<String, DataModel.GGLiveChannelStreamVerifyRet> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_VERIFY_CHANNEL_STREAM;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, String streamKey, String error) {
        DataModel.GGLiveChannelStreamVerifyRet ret = new DataModel.GGLiveChannelStreamVerifyRet();
        ret.flag = GGLiveConstants.getErrorCode(error);
        GGPluginManager.getInstance().publishResult(ret, activity, getId());
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(final Activity activity, String streamKey, String sessionToken) {
        JSONObject postData = new JSONObject();
        try {
            postData.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionToken);
            postData.put(GGLiveConstants.PARAM.STREAM_KEY, streamKey);
        } catch (JSONException e) {
        }
        new HttpRequestTask(postData, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
            /* access modifiers changed from: protected */
            public void onSuccess(JSONObject replyData) {
                DataModel.GGLiveChannelStreamVerifyRet ret = new DataModel.GGLiveChannelStreamVerifyRet();
                ret.flag = GGErrorCode.SUCCESS.getCode().intValue();
                if (replyData != null) {
                    String status = replyData.optString("status");
                    ret.statusString = status;
                    if (GGLiveConstants.STREAM_STATUS.INIT.equals(status)) {
                        ret.status = 1;
                    } else if (GGLiveConstants.STREAM_STATUS.STREAMING.equals(status)) {
                        ret.status = 2;
                    } else if (GGLiveConstants.STREAM_STATUS.BANNED.equals(status)) {
                        ret.status = 3;
                    } else if (GGLiveConstants.STREAM_STATUS.FINISHED.equals(status)) {
                        ret.status = 4;
                    } else if (GGLiveConstants.STREAM_STATUS.EXPIRED.equals(status)) {
                        ret.status = 5;
                    } else if (GGLiveConstants.STREAM_STATUS.UNUPDATED.equals(status)) {
                        ret.status = 6;
                    } else if (GGLiveConstants.STREAM_STATUS.DISCONNECTED.equals(status)) {
                        ret.status = 7;
                    } else if (GGLiveConstants.STREAM_STATUS.KICKED.equals(status)) {
                        ret.status = 8;
                    } else {
                        ret.status = 99;
                    }
                }
                GGPluginManager.getInstance().publishResult(ret, activity, GGLiveChannelStreamVerifyPlugin.this.getId());
            }

            /* access modifiers changed from: protected */
            public void onError(String error) {
                DataModel.GGLiveChannelStreamVerifyRet ret = new DataModel.GGLiveChannelStreamVerifyRet();
                if (GGLiveConstants.ERROR_CODE.ERROR_TOKEN_INVALID.equalsIgnoreCase(error)) {
                    ret.flag = GGErrorCode.GGLIVE_INVALID_STREAM_KEY.getCode().intValue();
                } else if (GGLiveConstants.ERROR_CODE.ERROR_TOKEN_EXPIRED.equalsIgnoreCase(error)) {
                    ret.flag = GGErrorCode.GGLIVE_STREAM_KEY_EXPIRED.getCode().intValue();
                } else {
                    ret.flag = GGLiveConstants.getErrorCode(error);
                }
                GGPluginManager.getInstance().publishResult(ret, activity, GGLiveChannelStreamVerifyPlugin.this.getId());
            }

            public void onTimeout() {
                DataModel.GGLiveChannelStreamVerifyRet ret = new DataModel.GGLiveChannelStreamVerifyRet();
                ret.flag = GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue();
                GGPluginManager.getInstance().publishResult(ret, activity, GGLiveChannelStreamVerifyPlugin.this.getId());
            }
        }).executeParallel(SDKConstants.getGGLiveVerifyChannelStreamUrl());
    }
}
