package com.tencent.ieg.ntv.ctrl;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.ctrl.player.PlayerController;
import com.tencent.ieg.ntv.utils.Logger;

public class ReplayCtrl {
    /* access modifiers changed from: private */
    public static final String TAG = ReplayCtrl.class.getSimpleName();
    private static ReplayCtrl sInstance;
    private ImageView mBtnBack;
    private ImageView mBtnForward;
    private Context mContext;
    /* access modifiers changed from: private */
    public boolean mNeedClearHistory;
    /* access modifiers changed from: private */
    public String mUrl;
    /* access modifiers changed from: private */
    public WebView mWebView;
    private ConstraintLayout parentCtn;
    private int tabIndex;

    public static ReplayCtrl getInstance() {
        if (sInstance == null) {
            sInstance = new ReplayCtrl();
        }
        return sInstance;
    }

    public void initReplayUI(ConstraintLayout ctn, WebView wv, Context context, ImageView btnBack, ImageView btnForward) {
        this.mContext = context;
        this.parentCtn = ctn;
        this.mWebView = wv;
        this.mBtnBack = btnBack;
        this.mBtnForward = btnForward;
        if (this.mWebView != null) {
            this.mWebView.setWebViewClient(new webViewClient());
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.getSettings().setDomStorageEnabled(true);
        }
        if (this.parentCtn != null) {
            this.parentCtn.setVisibility(8);
        }
    }

    public void onShow(boolean show, String url) {
        if (this.parentCtn != null && this.mWebView != null) {
            if (show) {
                this.mUrl = url;
                this.parentCtn.setVisibility(0);
                this.mWebView.onResume();
                this.mWebView.loadUrl(url);
                this.mNeedClearHistory = true;
            } else {
                this.parentCtn.setVisibility(8);
                this.mWebView.loadUrl("about:blank");
            }
            handleOthers(show);
        }
    }

    public void onBtnsClick(int btnId) {
        if (btnId == R.id.replay_btn_back) {
            if (this.mWebView != null && this.mWebView.canGoBack()) {
                this.mWebView.goBack();
            }
        } else if (btnId == R.id.replay_btn_forward) {
            if (this.mWebView != null && this.mWebView.canGoForward()) {
                this.mWebView.goForward();
            }
        } else if (btnId == R.id.replay_btn_refresh) {
            onShow(true, this.mUrl);
        } else if (btnId == R.id.replay_btn_close) {
            onShow(false, "");
        }
        updateBtnsState();
    }

    private void handleOthers(boolean show) {
        if (this.tabIndex != 0) {
            return;
        }
        if (show) {
            PlayerController.getInstance().pause();
        } else {
            PlayerController.getInstance().resume();
        }
    }

    public void synTabIndex(int idx) {
        this.tabIndex = idx;
    }

    /* access modifiers changed from: private */
    public void updateBtnsState() {
        if (this.mWebView != null && this.mBtnBack != null && this.mBtnForward != null) {
            if (this.mWebView.canGoBack()) {
                Logger.d(TAG, "updateBtnsState canGoBack.");
                this.mBtnBack.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.webview_back));
            } else {
                Logger.d(TAG, "updateBtnsState cannotGoBack.");
                this.mBtnBack.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.webview_back_disable));
            }
            if (this.mWebView.canGoForward()) {
                Logger.d(TAG, "updateBtnsState canGoForward.");
                this.mBtnForward.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.webview_forward));
                return;
            }
            Logger.d(TAG, "updateBtnsState cannotGoForward.");
            this.mBtnForward.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.webview_forward_disable));
        }
    }

    private class webViewClient extends WebViewClient {
        private webViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d(ReplayCtrl.TAG, "shouldOverrideUrlLoading in.");
            view.loadUrl(url);
            ReplayCtrl.this.updateBtnsState();
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Logger.d(ReplayCtrl.TAG, "onPageStarted in.");
            ReplayCtrl.this.updateBtnsState();
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            Logger.d(ReplayCtrl.TAG, "onPageFinished in. url: " + url);
            if (url.equals("about:blank")) {
                ReplayCtrl.this.mWebView.clearHistory();
                ReplayCtrl.this.mWebView.onPause();
            } else if (ReplayCtrl.this.mNeedClearHistory && url.equals(ReplayCtrl.this.mUrl)) {
                Logger.d(ReplayCtrl.TAG, "equals url: " + url);
                ReplayCtrl.this.mWebView.clearHistory();
                boolean unused = ReplayCtrl.this.mNeedClearHistory = false;
            }
            ReplayCtrl.this.updateBtnsState();
            super.onPageFinished(view, url);
        }
    }
}
