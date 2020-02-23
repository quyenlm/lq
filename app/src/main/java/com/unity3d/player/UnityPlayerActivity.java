package com.unity3d.player;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer;

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return keyEvent.getAction() == 2 ? this.mUnityPlayer.injectEvent(keyEvent) : super.dispatchKeyEvent(keyEvent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mUnityPlayer.configurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.mUnityPlayer = new UnityPlayer(this);
        setContentView(this.mUnityPlayer);
        this.mUnityPlayer.requestFocus();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mUnityPlayer.quit();
        super.onDestroy();
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return this.mUnityPlayer.injectEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.mUnityPlayer.injectEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.mUnityPlayer.injectEvent(keyEvent);
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mUnityPlayer.lowMemory();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        setIntent(intent);
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mUnityPlayer.injectEvent(motionEvent);
    }

    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        if (i == 15) {
            this.mUnityPlayer.lowMemory();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mUnityPlayer.windowFocusChanged(z);
    }
}
