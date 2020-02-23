package com.garena.game.kgvn;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.unity3d.player.UnityPlayer;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        this.mUnityPlayer = new UnityPlayer(this);
        setContentView(this.mUnityPlayer);
        this.mUnityPlayer.requestFocus();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mUnityPlayer.quit();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mUnityPlayer.pause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mUnityPlayer.resume();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mUnityPlayer.start();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mUnityPlayer.stop();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mUnityPlayer.lowMemory();
    }

    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == 15) {
            this.mUnityPlayer.lowMemory();
        }
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
