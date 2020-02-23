package com.tencent.mtt.spcialcall;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Vibrator;
import android.widget.FrameLayout;
import com.tencent.mtt.engine.AppEngine;
import com.tencent.mtt.engine.IWebView;
import com.tencent.mtt.engine.IWebViewClient;

public abstract class WebViewSpBase extends FrameLayout implements IWebView {
    public IWebViewClientSp mWebViewClient;

    /* access modifiers changed from: protected */
    public abstract void initDownload();

    /* access modifiers changed from: protected */
    public abstract void initWebChromClient();

    /* access modifiers changed from: protected */
    public abstract void initWebSetting();

    /* access modifiers changed from: protected */
    public abstract void initWebView();

    /* access modifiers changed from: protected */
    public abstract void initWebViewClient();

    public static WebViewSpBase createWebView(Context context, IWebViewClientSp pfc) {
        return new WebViewSp(context, pfc);
    }

    private WebViewSpBase(Context context) {
        super(context);
    }

    public WebViewSpBase(Context context, IWebViewClientSp pfc) {
        super(context);
        setWebViewClient(pfc);
    }

    public void init() {
        initWebView();
        initWebSetting();
        initWebViewClient();
        initWebChromClient();
        initDownload();
    }

    public void setWebViewClient(IWebViewClient client) {
        this.mWebViewClient = (IWebViewClientSp) client;
    }

    public IWebViewClientSp getWebViewClient() {
        return this.mWebViewClient;
    }

    /* access modifiers changed from: protected */
    public void performLongClick(String url, Bitmap bitmap) {
        performViberate();
        if (bitmap != null || url != null) {
            showImageSaveDialog(url, bitmap);
        }
    }

    public void showImageSaveDialog(String url, Bitmap bitmap) {
        this.mWebViewClient.showImageSaveDialog(url, bitmap);
    }

    public void performViberate() {
        boolean enableVibrator = true;
        AudioManager audioService = (AudioManager) AppEngine.getInstance().getContext().getSystemService("audio");
        if (audioService == null || audioService.getVibrateSetting(0) == 0) {
            enableVibrator = false;
        }
        if (enableVibrator) {
            ((Vibrator) AppEngine.getInstance().getContext().getSystemService("vibrator")).vibrate(new long[]{10, 20}, -1);
        }
    }

    public boolean canGoBack() {
        return false;
    }

    public boolean canGoForward() {
        return false;
    }

    public void back(boolean isKeyBack) {
    }

    public void forward() {
    }

    public void active() {
    }

    public void deactive() {
        clearFocus();
    }

    public void destroy() {
    }

    public String getTitle() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public void loadUrl(String url) {
    }

    public void reload() {
    }

    public void stopLoading() {
    }
}
