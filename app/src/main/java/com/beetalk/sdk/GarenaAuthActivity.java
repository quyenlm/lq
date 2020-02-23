package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.LocaleHelper;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.networking.CommonEventLoop;
import com.beetalk.sdk.networking.HttpParam;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.internal.ServerProtocol;
import com.garena.msdk.R;
import com.garena.pay.android.GGErrorCode;
import com.google.android.gms.drive.DriveFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GarenaAuthActivity extends Activity {
    protected static final String REQUEST_INFO_INTENT_KEY = "garena_request_info";
    protected static final String RESULT_INTENT_KEY = "garena_auth_result";
    private WebView mWebView;
    /* access modifiers changed from: private */
    public AuthClient.AuthClientRequest pendingRequest;
    /* access modifiers changed from: private */
    public String redirectUrl;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GGPlatform.initialize((Activity) this);
        this.mWebView = new WebView(this);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setLoadWithOverviewMode(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.clearCache(true);
        CookieManager.getInstance().removeAllCookie();
        if (TextUtils.isEmpty(GGPlatform.getGarenaLoginTitle())) {
            setTitle(R.string.beetalk_sdk_label_garena_login);
        } else {
            setTitle(GGPlatform.getGarenaLoginTitle());
        }
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        baseLayout.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
        setContentView(baseLayout);
        baseLayout.addView(this.mWebView, new LinearLayout.LayoutParams(-1, -1));
        this.mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                GarenaAuthActivity.this.onResult(AuthClient.Result.createErrorResult(GarenaAuthActivity.this.pendingRequest, GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue()));
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(SDKConstants.getGarenaOAuthRedirectUrl()) && !url.contains(GarenaAuthActivity.this.redirectUrl)) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                int index = url.indexOf("code=");
                if (index != -1) {
                    final String code = url.substring(index + 5);
                    BBLogger.i("web code 1%s %s", url, code);
                    CommonEventLoop.getInstance().post(new Runnable() {
                        public void run() {
                            GarenaAuthActivity.this.onAuthSuccess(code);
                        }
                    });
                    return true;
                }
                int index2 = url.indexOf("error=");
                if (index2 == -1) {
                    CommonEventLoop.getInstance().post(new Runnable() {
                        public void run() {
                            GarenaAuthActivity.this.onAuthFailed(GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
                        }
                    });
                    return true;
                } else if (!url.substring(index2 + 6).equals("access_denied")) {
                    return true;
                } else {
                    GarenaAuthActivity.this.onAuthCancelled();
                    return true;
                }
            }
        });
        this.mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                intent.setData(Uri.parse(url));
                GarenaAuthActivity.this.startActivity(intent);
            }
        });
        try {
            this.pendingRequest = (AuthClient.AuthClientRequest) getIntent().getSerializableExtra(REQUEST_INFO_INTENT_KEY);
            String appId = this.pendingRequest.getApplicationId();
            this.redirectUrl = SDKConstants.REDIRECT_URL_PREFIX + this.pendingRequest.getApplicationId() + "://auth/";
            List<HttpParam> params = new ArrayList<>();
            params.add(new HttpParam("redirect_uri", this.redirectUrl));
            params.add(new HttpParam("source", GGPlatform.getChannelSource().toString()));
            params.add(new HttpParam(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code"));
            params.add(new HttpParam("client_id", appId));
            params.add(new HttpParam(ServerProtocol.DIALOG_PARAM_DISPLAY, "embedded"));
            Locale current = LocaleHelper.getDefaultLocale(getApplicationContext());
            if (current != null) {
                params.add(new HttpParam("locale", LocaleHelper.getLocaleStringForServer(current)));
            }
            try {
                this.mWebView.loadUrl(SDKConstants.getGarenaOAuthUrl() + StringUtils.httpParamsToString(params));
            } catch (UnsupportedEncodingException e) {
                BBLogger.e(e);
                finish();
            }
        } catch (NullPointerException e2) {
            BBLogger.e(e2);
            finish();
        }
    }

    /* access modifiers changed from: private */
    public void onAuthSuccess(String token) {
        BBLogger.i("onAuthSuccess-user code %s", token);
        final AuthClient.Result outcome = requestUserToken(token, this.pendingRequest);
        this.mWebView.post(new Runnable() {
            public void run() {
                GarenaAuthActivity.this.onResult(outcome);
            }
        });
    }

    private AuthClient.Result requestUserToken(String userToken, AuthClient.AuthClientRequest pendingRequest2) {
        HashMap<String, String> data = new HashMap<>(3);
        data.put("grant_type", "authorization_code");
        data.put("code", userToken);
        data.put("redirect_uri", this.redirectUrl);
        data.put("source", GGPlatform.getChannelSource().toString());
        data.put("client_id", pendingRequest2.getApplicationId());
        data.put("client_secret", pendingRequest2.getApplicationKey());
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthServerExchangeTokenUrl(), (Map<String, String>) data);
        AuthClient.Result outcome = null;
        int errorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        if (result == null) {
            errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
        } else if (result.has("open_id")) {
            try {
                String openId = result.getString("open_id");
                String accessToken = result.getString("access_token");
                String refreshToken = result.getString("refresh_token");
                int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                AuthToken authToken = new AuthToken(accessToken, TokenProvider.GARENA_WEB_ANDROID);
                if (result.optInt("platform") == 3) {
                    authToken.setTokenProvider(TokenProvider.FACEBOOK);
                }
                authToken.setRefreshToken(refreshToken);
                authToken.setExpiryTimestamp(expireTime);
                outcome = AuthClient.Result.createTokenResult(pendingRequest2, authToken, openId);
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        } else {
            errorCode = GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue();
        }
        if (outcome == null) {
            return AuthClient.Result.createErrorResult(pendingRequest2, errorCode);
        }
        return outcome;
    }

    /* access modifiers changed from: private */
    public void onAuthCancelled() {
        setResult(0);
        finish();
    }

    /* access modifiers changed from: private */
    public void onAuthFailed(int errorCode) {
        BBLogger.i("onAuthFailed-failed", new Object[0]);
        final AuthClient.Result outcome = AuthClient.Result.createErrorResult(this.pendingRequest, errorCode);
        this.mWebView.post(new Runnable() {
            public void run() {
                GarenaAuthActivity.this.onResult(outcome);
            }
        });
    }

    /* access modifiers changed from: private */
    public void onResult(AuthClient.Result result) {
        GGLoginSession session;
        if (!(result == null || result.token == null || (session = GGLoginSession.getCurrentSession()) == null)) {
            session.getCache().putToken(result.token);
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(RESULT_INTENT_KEY, result);
        setResult(AuthClient.Result.isError(result.resultCode) ? 0 : -1, resultIntent);
        finish();
    }
}
