package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.cache.StorageCache;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.HttpParam;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import com.garena.pay.android.GGErrorCode;
import com.garena.pay.android.view.WebDialog;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class GGLiveLoginPlugin extends GGLiveBasePlugin<Void, PluginResult> {
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
        return SDKConstants.PLUGIN_KEYS.GGLIVE_LOGIN;
    }

    /* access modifiers changed from: protected */
    public void onError(Activity activity, Void data, String error) {
        BBLogger.d("failed to retrieve garena live token: %s", error);
        startLogin(activity, new SharedPrefStorage(activity));
    }

    /* access modifiers changed from: protected */
    public void onTokenReady(Activity activity, Void data, String sessionToken) {
        BBLogger.d("garena live token available", new Object[0]);
        PluginResult result = new PluginResult();
        result.status = 0;
        result.flag = GGErrorCode.SUCCESS.getCode().intValue();
        result.source = getId();
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    private void startLogin(Activity activity, StorageCache cache) {
        BBLogger.d("proceed to garena live login", new Object[0]);
        new GLiveLoginWebDialog(activity, cache).show();
    }

    private class GLiveLoginWebDialog extends WebDialog implements DialogInterface.OnDismissListener {
        private static final String API = "/ui/login?app_id=10042&redirect_uri=gop10042://";
        private static final String PARAM_ERROR = "error";
        private static final String PARAM_SESSION_KEY = "session_key";
        private static final String REDIRECT = "gop10042://";
        private final Activity mActivity;
        /* access modifiers changed from: private */
        public final StorageCache mCache;
        private final PluginResult mResult = new PluginResult();

        GLiveLoginWebDialog(Activity activity, StorageCache cache) {
            super((Context) activity, SDKConstants.getSsoURL() + API, 16973840);
            this.mActivity = activity;
            this.mCache = cache;
            this.mResult.status = -1;
            this.mResult.flag = GGErrorCode.USER_CANCELLED.getCode().intValue();
            this.mResult.source = GGLiveLoginPlugin.this.getId();
            setOnDismissListener(this);
        }

        /* access modifiers changed from: protected */
        public WebViewClient createWebViewClient() {
            return new WebDialog.DialogWebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    BBLogger.d("Redirect URL: %s", url);
                    if (!url.startsWith(GLiveLoginWebDialog.REDIRECT)) {
                        return false;
                    }
                    GLiveLoginWebDialog.this.onLoginResult(url);
                    return true;
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    GLiveLoginWebDialog.this.onError();
                }
            };
        }

        /* access modifiers changed from: private */
        public void onLoginResult(String url) {
            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
            String sessionKey = sanitizer.getValue(PARAM_SESSION_KEY);
            if (!TextUtils.isEmpty(sessionKey)) {
                GGLiveLoginPlugin.this.saveSessionToken(this.mCache, sessionKey);
                GGLiveLoginPlugin.this.requestRefreshToken(this.mCache, sessionKey);
                requestUid(sessionKey);
                this.mResult.status = 0;
                this.mResult.flag = GGErrorCode.SUCCESS.getCode().intValue();
                this.mResult.message = null;
            } else {
                String error = sanitizer.getValue("error");
                this.mResult.status = -1;
                this.mResult.flag = GGErrorCode.GOP_ERROR_SERVER.getCode().intValue();
                this.mResult.message = error;
            }
            dismiss();
        }

        private void requestUid(String sessionKey) {
            BBLogger.d("requesting garene live uid", new Object[0]);
            new HttpRequestTask((List<HttpParam>) Collections.singletonList(new HttpParam("Cookie", "sso_session=" + sessionKey)), (JSONObject) null, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
                /* access modifiers changed from: protected */
                public void onSuccess(JSONObject replyData) {
                    JSONObject account;
                    if (replyData == null || (account = replyData.optJSONObject(GGLiveConstants.PARAM.ACCOUNT_INFO)) == null) {
                        BBLogger.d("failed to request garena live uid: empty data", new Object[0]);
                        return;
                    }
                    long uid = account.optLong(GGLiveConstants.PARAM.UID);
                    GLiveLoginWebDialog.this.mCache.putGGLiveUid(uid);
                    BBLogger.d("saved garena live uid: %d", Long.valueOf(uid));
                }

                /* access modifiers changed from: protected */
                public void onError(String error) {
                    BBLogger.d("failed to request garena live uid: %s", error);
                }

                public void onTimeout() {
                    BBLogger.d("failed to request garena live uid: timeout", new Object[0]);
                }
            }).executeParallel(SDKConstants.getGGLiveGetAccountInfoUrl());
        }

        /* access modifiers changed from: private */
        public void onError() {
            this.mResult.status = -1;
            this.mResult.flag = GGErrorCode.NETWORK_CONNECTION_EXCEPTION.getCode().intValue();
            this.mResult.message = null;
            dismiss();
        }

        public void onDismiss(DialogInterface dialog) {
            GGPluginManager.getInstance().publishResult(this.mResult, this.mActivity, GGLiveLoginPlugin.this.getId());
        }
    }
}
