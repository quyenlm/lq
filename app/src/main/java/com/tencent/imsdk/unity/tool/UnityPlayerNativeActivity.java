package com.tencent.imsdk.unity.tool;

import android.app.NativeActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.tencent.imsdk.IMConfig;
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
        IMConfig.initialize(this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mUnityPlayer.configurationChanged(newConfig);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.mUnityPlayer.windowFocusChanged(hasFocus);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return this.mUnityPlayer.injectEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == 2) {
            return this.mUnityPlayer.injectEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
