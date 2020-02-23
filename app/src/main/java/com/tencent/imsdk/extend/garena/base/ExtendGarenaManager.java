package com.tencent.imsdk.extend.garena.base;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.facebook.FBClient;
import com.beetalk.sdk.plugin.GGPluginCallback;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.pay.android.GGAndroidPaymentPlatform;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMHttpClient;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtendGarenaManager {
    private static final String RETURN_ERROR_MSG_TAG = "share error,";
    private static final String VERSION = "1.16.0";
    protected static IMHttpClient httpClient = null;
    private static volatile ExtendGarenaManager instance = null;
    private final String PLUGIN_NAME = "imsdkgarena";
    GGPluginCallback<PluginResult> garenaCallback = new GGPluginCallback<PluginResult>() {
        public void onPluginResult(PluginResult pluginResult) {
            try {
                IMLogger.d("Garena subChannel:" + ExtendGarenaManager.this.imsdkCallbackFromPlatform + " Method:" + ExtendGarenaManager.this.imsdkCallbackFromMethod + " return back");
                HashMap<String, Object> res = new HashMap<>();
                res.put("source", pluginResult.source);
                res.put(DownloadDBHelper.FLAG, Integer.valueOf(pluginResult.flag));
                res.put("message", pluginResult.message);
                if (pluginResult.source.equals(SDKConstants.PLUGIN_KEYS.FACEBOOK_REQUEST_ME)) {
                    try {
                        res.put("message", new JSONObject(pluginResult.message));
                    } catch (JSONException e) {
                        if (ExtendGarenaManager.this.mSTBuilder != null) {
                            ExtendGarenaManager.this.mSTBuilder.setMethod("onPluginResult").setResult("catch JSONException : " + e.getMessage()).create().reportEvent();
                        }
                    }
                }
                res.put("status", String.valueOf(pluginResult.status));
                if (ExtendGarenaManager.this.imsdkShareCallback != null) {
                    if (pluginResult == null || pluginResult.flag != 0) {
                        String returnStr = pluginResult.message;
                        if (TextUtils.isEmpty(returnStr)) {
                            returnStr = "Garena Share Fail";
                        }
                        IMException unused = ExtendGarenaManager.this.mException = new IMException(3, ExtendGarenaManager.RETURN_ERROR_MSG_TAG + returnStr);
                        if (pluginResult.flag == 2002) {
                            ExtendGarenaManager.this.imsdkShareCallback.onError(IMRetCode.rebuild(ExtendGarenaManager.this.mException, IMRetCode.CANCELED, pluginResult.flag, returnStr, (String) null));
                        } else {
                            ExtendGarenaManager.this.imsdkShareCallback.onError(IMRetCode.rebuild(ExtendGarenaManager.this.mException, IMRetCode.RETURN_THIRD, pluginResult.flag, returnStr, (String) null));
                        }
                    } else {
                        IMResult ret = new IMResult();
                        ret.retCode = IMRetCode.SUCCESS;
                        ret.retMsg = pluginResult.message;
                        ExtendGarenaManager.this.imsdkShareCallback.onSuccess(IMRetCode.rebuildForSuccess(ret));
                    }
                } else if (ExtendGarenaManager.this.imsdkFriendCallback != null) {
                    if (pluginResult == null || pluginResult.flag != 0) {
                        String returnStr2 = pluginResult.message;
                        if (TextUtils.isEmpty(returnStr2)) {
                            returnStr2 = "Garena ShareToFriend Fail";
                        }
                        IMException unused2 = ExtendGarenaManager.this.mException = new IMException(3, ExtendGarenaManager.RETURN_ERROR_MSG_TAG + returnStr2);
                        if (pluginResult.flag == 2002) {
                            ExtendGarenaManager.this.imsdkFriendCallback.onError(IMRetCode.rebuild(ExtendGarenaManager.this.mException, IMRetCode.CANCELED, pluginResult.flag, returnStr2, (String) null));
                        } else {
                            ExtendGarenaManager.this.imsdkFriendCallback.onError(IMRetCode.rebuild(ExtendGarenaManager.this.mException, IMRetCode.RETURN_THIRD, pluginResult.flag, returnStr2, (String) null));
                        }
                    } else {
                        IMFriendResult ret2 = new IMFriendResult();
                        ret2.retCode = IMRetCode.SUCCESS;
                        ret2.retMsg = pluginResult.message;
                        ExtendGarenaManager.this.imsdkFriendCallback.onSuccess(IMRetCode.rebuildForSuccess(ret2));
                    }
                }
                IMLogger.d(ExtendGarenaManager.this.imsdkCallbackFromPlatform + " " + ExtendGarenaManager.this.imsdkCallbackFromMethod + ":" + res.toString());
            } catch (Exception e2) {
                IMLogger.e(e2.getMessage());
                if (ExtendGarenaManager.this.mSTBuilder != null) {
                    ExtendGarenaManager.this.mSTBuilder.setMethod("onPluginResult").setResult("catch Exception : " + e2.getMessage()).create().reportEvent();
                }
            }
        }
    };
    public String imsdkCallbackFromMethod = "";
    public String imsdkCallbackFromPlatform = "";
    public IMCallback<IMFriendResult> imsdkFriendCallback;
    public IMCallback<IMResult> imsdkShareCallback;
    private Activity mContext;
    /* access modifiers changed from: private */
    public IMException mException = null;
    /* access modifiers changed from: private */
    public InnerStat.Builder mSTBuilder;

    public static ExtendGarenaManager getInstance() {
        if (instance == null) {
            synchronized (ExtendGarenaManager.class) {
                if (instance == null) {
                    instance = new ExtendGarenaManager();
                }
            }
        }
        return instance;
    }

    public GGPluginCallback<PluginResult> getGarenaCallback() {
        return this.garenaCallback;
    }

    public void setIMSDKShareCallback(String platform, String method, IMCallback<IMResult> imsdkCall) {
        this.imsdkCallbackFromPlatform = platform;
        this.imsdkCallbackFromMethod = method;
        this.imsdkShareCallback = imsdkCall;
        this.imsdkFriendCallback = null;
    }

    public void setIMSDKFriendCallback(String platform, String method, IMCallback<IMFriendResult> imsdkCall) {
        this.imsdkCallbackFromPlatform = platform;
        this.imsdkCallbackFromMethod = method;
        this.imsdkFriendCallback = imsdkCall;
        this.imsdkShareCallback = null;
    }

    public void initialize(Activity context) {
        this.mContext = context;
        this.mSTBuilder = new InnerStat.Builder(context, "1.16.0", GGPlatform.GGGetSDKVersion(), "imsdkgarena");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4658) {
            try {
                GGAndroidPaymentPlatform.onActivityResult(data);
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
        } else if (this.mContext != null) {
            GGPlatform.handleActivityResult(this.mContext, requestCode, resultCode, data);
        } else {
            IMLogger.e("context is null");
        }
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onDestroy() {
        FBClient.onActivityDestory();
    }
}
