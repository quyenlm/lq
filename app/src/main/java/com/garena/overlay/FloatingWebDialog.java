package com.garena.overlay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.msdk.R;
import com.garena.overlay.FloatingMenuActivity;
import com.garena.pay.android.view.WebDialog;
import org.apache.http.HttpHost;

public class FloatingWebDialog extends WebDialog {
    private View navBack;
    private View navClose;
    private View navForward;
    private View navRefresh;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.nav_back) {
                if (FloatingWebDialog.this.webView.canGoBack()) {
                    FloatingWebDialog.this.webView.goBack();
                }
            } else if (id == R.id.nav_forward) {
                if (FloatingWebDialog.this.webView.canGoForward()) {
                    FloatingWebDialog.this.webView.goForward();
                }
            } else if (id == R.id.nav_refresh) {
                FloatingWebDialog.this.webView.reload();
            } else if (id == R.id.nav_close) {
                FloatingWebDialog.this.dismiss();
            }
        }
    };

    public FloatingWebDialog(Context context, String url, FloatingMenuActivity.FileWebChromeClient webChromeClient) {
        super(context, url, 16973840);
        setCanceledOnTouchOutside(false);
        if (webChromeClient != null) {
            this.webView.setWebChromeClient(webChromeClient);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
    }

    /* access modifiers changed from: protected */
    public void initContentLayout() {
        this.contentLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.msdk_floating_webdialog, (ViewGroup) null);
        this.navBack = this.contentLayout.findViewById(R.id.nav_back);
        this.navBack.setOnClickListener(this.onClickListener);
        this.navBack.setAlpha(0.4f);
        this.navBack.setEnabled(false);
        this.navForward = this.contentLayout.findViewById(R.id.nav_forward);
        this.navForward.setOnClickListener(this.onClickListener);
        this.navForward.setAlpha(0.4f);
        this.navForward.setEnabled(false);
        this.navRefresh = this.contentLayout.findViewById(R.id.nav_refresh);
        this.navRefresh.setOnClickListener(this.onClickListener);
        this.navClose = this.contentLayout.findViewById(R.id.nav_close);
        this.navClose.setOnClickListener(this.onClickListener);
        this.needMargin = false;
    }

    /* access modifiers changed from: protected */
    public void calculateSize() {
        getWindow().setLayout(-1, -1);
    }

    /* access modifiers changed from: private */
    public void refreshNavState() {
        if (this.webView.canGoBack()) {
            this.navBack.setEnabled(true);
            this.navBack.setAlpha(1.0f);
        } else {
            this.navBack.setEnabled(false);
            this.navBack.setAlpha(0.4f);
        }
        if (this.webView.canGoForward()) {
            this.navForward.setEnabled(true);
            this.navForward.setAlpha(1.0f);
            return;
        }
        this.navForward.setEnabled(false);
        this.navForward.setAlpha(0.4f);
    }

    /* access modifiers changed from: protected */
    public WebViewClient createWebViewClient() {
        return new NavWebViewClient();
    }

    private class NavWebViewClient extends WebDialog.DialogWebViewClient {
        private NavWebViewClient() {
            super();
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            FloatingWebDialog.this.refreshNavState();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            BBLogger.d(url, new Object[0]);
            if (url.startsWith("intent:")) {
                try {
                    String packageName = url.substring(url.indexOf("package=") + "package=".length(), url.indexOf(";end"));
                    BBLogger.d("packageName = " + packageName, new Object[0]);
                    if (TextUtils.isEmpty(packageName)) {
                        return true;
                    }
                    FloatingWebDialog.this.getContext().getPackageManager().getPackageInfo(packageName, 1);
                    FloatingWebDialog.this.getContext().startActivity(Intent.parseUri(url, 1));
                    return true;
                } catch (Exception e) {
                    BBLogger.e(e);
                    return true;
                }
            } else if (url.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                try {
                    FloatingWebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    return true;
                } catch (Exception e2) {
                    BBLogger.e(e2);
                    return true;
                }
            }
        }
    }
}
