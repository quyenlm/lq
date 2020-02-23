package com.tencent.mtt.spcialcall;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import com.tencent.mtt.spcialcall.sdk.RecommendParams;

public class WebViewClientWrapper extends WebViewClient {
    private WebViewClient mClient;
    private DownloadListener mDownloadListener;
    private boolean mInjectRecomend;
    private RecommendParams mRecomendParams = new RecommendParams();

    public void setWebViewClient(WebViewClient client) {
        this.mClient = client;
    }

    public void setInjectRecommend(boolean injectRecommend) {
        this.mInjectRecomend = injectRecommend;
    }

    public void setFromType(int fromType) {
        this.mRecomendParams.put("from", new StringBuilder(String.valueOf(fromType)).toString());
    }

    public void setUin(String uin) {
        this.mRecomendParams.put(RecommendParams.KEY_UIN, uin);
    }

    public void setPublicAccountUin(String pubUin) {
        this.mRecomendParams.put(RecommendParams.KEY_PUB_UIN, pubUin);
    }

    public void setDownloadListener(DownloadListener l) {
        this.mDownloadListener = l;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!url.startsWith("http://mdc.html5.qq.com/d/directdown.jsp") || this.mDownloadListener == null) {
            boolean shouldOverride = false;
            if (this.mClient != null) {
                shouldOverride = this.mClient.shouldOverrideUrlLoading(view, url);
            }
            if (shouldOverride) {
                return shouldOverride;
            }
            this.mRecomendParams.put(RecommendParams.KEY_REFFER, view.getUrl());
            return shouldOverride;
        }
        WebView downloadWebView = new WebView(view.getContext());
        downloadWebView.setWebViewClient(new WebViewClient());
        downloadWebView.setDownloadListener(this.mDownloadListener);
        downloadWebView.loadUrl(url);
        return true;
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.mClient != null) {
            this.mClient.onPageStarted(view, url, favicon);
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (this.mClient != null) {
            this.mClient.onPageFinished(view, url);
        }
        if (this.mInjectRecomend) {
            MttApi.insertRecommend(view, this.mRecomendParams);
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (this.mClient != null) {
            this.mClient.onLoadResource(view, url);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.mClient != null) {
            return this.mClient.shouldInterceptRequest(view, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (this.mClient != null) {
            this.mClient.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.mClient != null) {
            this.mClient.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.mClient != null) {
            this.mClient.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.mClient != null) {
            this.mClient.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.mClient != null) {
            this.mClient.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.mClient != null) {
            this.mClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (this.mClient != null) {
            return this.mClient.shouldOverrideKeyEvent(view, event);
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.mClient != null) {
            this.mClient.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.mClient != null) {
            this.mClient.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (this.mClient != null) {
            this.mClient.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }
}
