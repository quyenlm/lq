package com.tencent.ieg.ntv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.amazonaws.services.s3.util.Mimetypes;
import com.tencent.ieg.ntv.R;

public class WebViewContentFragment extends BaseContentFragment {
    private boolean _onShow = false;
    private String mUrl = null;
    private WebView mWebView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.webview_page, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this._onShow) {
            this._onShow = false;
            loadUrl();
        }
    }

    public void setUrl(String url) {
        if (url.equals(this.mUrl) || this.mUrl == null) {
            this.mUrl = url;
            return;
        }
        this.mUrl = url;
        onShow();
    }

    private void initWebView() {
        View view = getView();
        if (view == null) {
            return;
        }
        if (this.mWebView == null) {
            this.mWebView = new WebView(view.getContext().getApplicationContext());
            this.mWebView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.web_ctn);
            if (layout != null) {
                layout.addView(this.mWebView);
            }
            this.mWebView.setWebViewClient(new EmbeddedWebViewClient());
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.getSettings().setDomStorageEnabled(true);
            this.mWebView.setBackgroundColor(0);
            return;
        }
        this.mWebView.onResume();
    }

    public void onShow() {
        super.onShow();
        if (getView() != null) {
            loadUrl();
        } else {
            this._onShow = true;
        }
    }

    private void loadUrl() {
        initWebView();
        if (this.mWebView != null) {
            this.mWebView.loadUrl(this.mUrl);
        }
    }

    public void onHidden() {
        super.onHidden();
        if (getView() != null && this.mWebView != null) {
            this.mWebView.onPause();
        }
    }

    public void onDestroy() {
        if (this.mWebView != null) {
            this.mWebView.loadDataWithBaseURL((String) null, "", Mimetypes.MIMETYPE_HTML, "utf-8", (String) null);
            this.mWebView.clearHistory();
            ((ViewGroup) this.mWebView.getParent()).removeView(this.mWebView);
            this.mWebView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    private class EmbeddedWebViewClient extends WebViewClient {
        private EmbeddedWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
