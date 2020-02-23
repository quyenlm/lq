package com.tencent.imsdk.unity.runtime;

import android.app.NativeActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.api.IMSDKApi;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerNativeActivity extends NativeActivity {
    protected UnityPlayer mUnityPlayer;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        getWindow().takeSurface((SurfaceHolder.Callback2) null);
        setTheme(16973831);
        getWindow().setFormat(2);
        this.mUnityPlayer = new UnityPlayer(this);
        if (this.mUnityPlayer.getSettings().getBoolean("hide_status_bar", true)) {
            getWindow().setFlags(1024, 1024);
        }
        setContentView(this.mUnityPlayer);
        this.mUnityPlayer.requestFocus();
        IMLogger.d("in imsdk start activity !!!");
        IMConfig.initialize(this);
        IMSDKApi.onCreate(this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mUnityPlayer.quit();
        super.onDestroy();
        IMSDKApi.onDestroy(this);
        IMSDKApi.Pay.dispose();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mUnityPlayer.pause();
        IMSDKApi.onPause(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mUnityPlayer.resume();
        IMSDKApi.onResume(this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        IMSDKApi.onNewIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IMSDKApi.Pay.handleActivityResult(requestCode, resultCode, data);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mUnityPlayer.configurationChanged(newConfig);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.mUnityPlayer.windowFocusChanged(hasFocus);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == 2) {
            return this.mUnityPlayer.injectEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }
}
