package com.vk.sdk.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.vk.sdk.R;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;
import java.util.Locale;
import java.util.Map;

public class VKOpenAuthDialog implements DialogInterface.OnDismissListener {
    private static final String CANCEL = "cancel";
    private static final String ERROR = "error";
    private static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_EXTRA_API_VERSION = "version";
    public static final String VK_EXTRA_CLIENT_ID = "client_id";
    public static final String VK_EXTRA_REVOKE = "revoke";
    public static final String VK_EXTRA_SCOPE = "scope";
    public static final String VK_EXTRA_TOKEN_DATA = "extra-token-data";
    public static final String VK_EXTRA_VALIDATION_REQUEST = "extra-validation-request";
    public static final String VK_RESULT_INTENT_NAME = "com.vk.auth-token";
    protected Bundle mBundle;
    protected Intent mData;
    protected Dialog mDialog;
    protected View mProgress;
    protected int mReqCode;
    protected int mResCode = -1;
    protected View mView;
    @Nullable
    protected VKError mVkError;
    protected WebView mWebView;

    public void show(@NonNull Activity activity, Bundle bundle, int reqCode, @Nullable VKError vkError) {
        this.mVkError = vkError;
        this.mBundle = bundle;
        this.mReqCode = reqCode;
        this.mView = View.inflate(activity, R.layout.vk_open_auth_dialog, (ViewGroup) null);
        this.mProgress = this.mView.findViewById(R.id.progress);
        this.mWebView = (WebView) this.mView.findViewById(R.id.copyUrl);
        final Dialog dialog = new Dialog(activity, R.style.VKAlertDialog);
        dialog.setContentView(this.mView);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            dialog.getWindow().setStatusBarColor(0);
        }
        this.mDialog = dialog;
        this.mDialog.show();
        loadPage();
    }

    /* access modifiers changed from: private */
    @SuppressLint({"SetJavaScriptEnabled"})
    public void loadPage() {
        String urlToLoad = null;
        int i = 1;
        try {
            if (this.mVkError != null) {
                urlToLoad = this.mVkError.redirectUri;
            }
            if (urlToLoad == null) {
                int appId = this.mBundle.getInt("client_id", 0);
                String scope = this.mBundle.getString("scope");
                String apiV = this.mBundle.getString("version");
                boolean revoke = this.mBundle.getBoolean(VK_EXTRA_REVOKE, false);
                Locale locale = Locale.US;
                Object[] objArr = new Object[5];
                objArr[0] = Integer.valueOf(appId);
                objArr[1] = scope;
                objArr[2] = REDIRECT_URL;
                objArr[3] = apiV;
                if (!revoke) {
                    i = 0;
                }
                objArr[4] = Integer.valueOf(i);
                urlToLoad = String.format(locale, "https://oauth.vk.com/authorize?client_id=%s&scope=%s&redirect_uri=%s&display=mobile&v=%s&response_type=token&revoke=%d", objArr);
            }
            this.mWebView.setWebViewClient(new OAuthWebViewClient(this));
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.loadUrl(urlToLoad);
            this.mWebView.setBackgroundColor(0);
            if (Build.VERSION.SDK_INT >= 11) {
                this.mWebView.setLayerType(1, (Paint) null);
            }
            this.mWebView.setVerticalScrollBarEnabled(false);
            this.mWebView.setVisibility(4);
            this.mWebView.setOverScrollMode(2);
            this.mProgress.setVisibility(0);
        } catch (Exception e) {
            setResult(0);
            finish();
        }
    }

    private static class OAuthWebViewClient extends WebViewClient {
        boolean canShowPage = true;
        @NonNull
        final VKOpenAuthDialog vkOpenAuthDialog;

        public OAuthWebViewClient(@NonNull VKOpenAuthDialog vkOpenAuthDialog2) {
            this.vkOpenAuthDialog = vkOpenAuthDialog2;
        }

        /* access modifiers changed from: package-private */
        public boolean processUrl(String url) {
            if (!url.startsWith(VKOpenAuthDialog.REDIRECT_URL)) {
                return false;
            }
            Intent data = new Intent(VKOpenAuthDialog.VK_RESULT_INTENT_NAME);
            String extraData = url.substring(url.indexOf(35) + 1);
            data.putExtra(VKOpenAuthDialog.VK_EXTRA_TOKEN_DATA, extraData);
            Map<String, String> resultParams = VKUtil.explodeQueryString(extraData);
            if (this.vkOpenAuthDialog.mVkError != null) {
                data.putExtra(VKOpenAuthDialog.VK_EXTRA_VALIDATION_REQUEST, this.vkOpenAuthDialog.mVkError.request.registerObject());
            }
            if (resultParams == null || (!resultParams.containsKey("error") && !resultParams.containsKey("cancel"))) {
                this.vkOpenAuthDialog.setResult(-1, data);
            } else {
                this.vkOpenAuthDialog.setResult(0, data);
            }
            this.vkOpenAuthDialog.finish();
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (processUrl(url)) {
                return true;
            }
            this.canShowPage = true;
            return false;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            processUrl(url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (this.canShowPage) {
                if (this.vkOpenAuthDialog.mProgress != null) {
                    this.vkOpenAuthDialog.mProgress.setVisibility(8);
                }
                view.setVisibility(0);
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            this.canShowPage = false;
            try {
                new AlertDialog.Builder(view.getContext()).setMessage(description).setPositiveButton(R.string.vk_retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OAuthWebViewClient.this.vkOpenAuthDialog.loadPage();
                    }
                }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OAuthWebViewClient.this.vkOpenAuthDialog.finish();
                    }
                }).show();
            } catch (Exception e) {
            }
        }
    }

    public void onDismiss(DialogInterface dialog) {
        Activity activity = this.mView == null ? null : (Activity) this.mView.getContext();
        if (activity instanceof VKServiceActivity) {
            ((VKServiceActivity) activity).onActivityResultPublic(this.mReqCode, this.mResCode, this.mData);
        }
    }

    private void setResult(int code) {
        this.mResCode = code;
    }

    /* access modifiers changed from: private */
    public void setResult(int code, Intent data) {
        this.mResCode = code;
        this.mData = data;
    }

    /* access modifiers changed from: private */
    public void finish() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }
}
