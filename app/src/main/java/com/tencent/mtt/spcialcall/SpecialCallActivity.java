package com.tencent.mtt.spcialcall;

import MTT.ThirdAppInfo;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import com.tencent.mtt.common.utils.MttResources;
import com.tencent.mtt.engine.AppEngine;
import com.tencent.mtt.engine.http.HttpUtils;

public class SpecialCallActivity extends Activity {
    public static final int FILE_UPLOAD = 1014;
    public static final int FLAG_HARDWARE_ACCELERATED = 16777216;
    private static final String MX2 = "Meizu_M040";
    private BrowserWindowSP mBrowserWindow;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private FrameLayout mCustomViewContainer;
    private String mEntryUrl;
    private boolean mIsWebViewInit = false;
    private boolean mNeedKill = false;
    private BroadcastReceiver mReceiver;
    protected ValueCallback<Uri> uploadFile;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFormat(-3);
        String mark = String.valueOf(Build.MANUFACTURER) + "_" + Build.MODEL;
        if (Build.VERSION.SDK_INT > 10 && !MX2.equals(mark)) {
            window.addFlags(16777216);
        }
        AppEngine.sCallMode = true;
        AppEngine.getInstance().setContext(this);
        setContentView(MttResources.getLayoutId("thrdcall_window"));
        window.setLayout(-1, -1);
        try {
            ExtraInfo.setDefaultFunc(getIntent());
        } catch (Exception e) {
        }
        this.mEntryUrl = getIntent().getDataString();
        this.mBrowserWindow = new BrowserWindowSP(this, this.mEntryUrl, getIntent());
        BrowserWindowSP.updateScreenSize(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mBrowserWindow != null) {
            BrowserWindowSP.updateScreenSize(this);
            this.mBrowserWindow.onSreenOritationChage();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        AppEngine.sCallMode = true;
        if (this.uploadFile != null) {
            this.uploadFile.onReceiveValue((Object) null);
            this.uploadFile = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mBrowserWindow.active();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mBrowserWindow.deActive();
        getWindow().setWindowAnimations(MttResources.getStyleId("ThrdCallActivityAnimationNone"));
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!this.mIsWebViewInit && this.mBrowserWindow != null) {
            this.mIsWebViewInit = true;
            this.mBrowserWindow.delayInitWebView();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.mCustomViewContainer != null) {
            try {
                ((ViewGroup) getWindow().getDecorView()).removeView(this.mCustomViewContainer);
                this.mCustomViewContainer = null;
                if (this.mCustomViewCallback == null) {
                    return true;
                }
                this.mCustomViewCallback.onCustomViewHidden();
                this.mCustomViewCallback = null;
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return true;
            }
        } else {
            doExit();
            return true;
        }
    }

    public void doExit() {
        ThirdAppInfo appInfo = new ThirdAppInfo();
        appInfo.sAppName = ExtraInfo.getOriPkg();
        if (this.mBrowserWindow != null) {
            appInfo.iPv = (long) this.mBrowserWindow.getPv();
        }
        HttpUtils.post(appInfo);
        getWindow().setWindowAnimations(MttResources.getStyleId("ThrdCallActivityAnimationOut"));
        finish();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1014:
                    if (this.uploadFile != null) {
                        this.uploadFile.onReceiveValue((data == null || resultCode != -1) ? null : data.getData());
                        this.uploadFile = null;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        try {
            this.mBrowserWindow.destroy();
            if (this.mReceiver != null) {
                unregisterReceiver(this.mReceiver);
            }
        } catch (Exception e) {
        }
        if (this.mNeedKill) {
            Process.killProcess(Process.myPid());
        }
    }

    public void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (this.mCustomViewContainer != null) {
            this.mCustomViewContainer.removeAllViews();
        }
        if (this.mCustomViewCallback != null) {
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        if (this.mCustomViewContainer == null) {
            this.mCustomViewContainer = new FrameLayout(this);
            this.mCustomViewContainer.setBackgroundColor(-16777216);
            decor.addView(this.mCustomViewContainer);
        }
        this.mCustomViewContainer.addView(view);
        this.mCustomViewCallback = callback;
    }
}
