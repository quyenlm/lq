package com.tencent.component.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
    private Handler.Callback mHandlerCallback = new Handler.Callback() {
        public boolean handleMessage(Message message) {
            if (message != null && !BaseActivity.this.isFinishing()) {
                return BaseActivity.this.handleMessageLogic(message);
            }
            return false;
        }
    };
    protected Handler mMainHandler = new Handler(Looper.getMainLooper(), this.mHandlerCallback);
    private Thread mMainThread = Looper.getMainLooper().getThread();
    private boolean mResumed = false;
    private boolean mStarted = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityCreatedInner(this, savedInstanceState);
        }
    }

    private boolean isBaseApplication() {
        return getApplication() instanceof BaseApplication;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mResumed = true;
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityResumedInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mStarted = true;
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityStartedInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mResumed = false;
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityPausedInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mStarted = false;
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityStoppedInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityDestroyedInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivitySaveInstanceStateInner(this, outState);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityResultInner(this, requestCode, resultCode, data);
        }
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (isBaseApplication()) {
            ((BaseApplication) getApplication()).dispatchActivityUserLeaveHintInner(this);
        }
    }

    /* access modifiers changed from: protected */
    public boolean handleMessageLogic(Message message) {
        return false;
    }

    public final void post(Runnable r) {
        this.mMainHandler.post(r);
    }

    public final void postDelayed(Runnable r, long delayMillis) {
        this.mMainHandler.postDelayed(r, delayMillis);
    }

    public final void removeCallbacks(Runnable r) {
        this.mMainHandler.removeCallbacks(r);
    }

    public final boolean isMainThread() {
        return this.mMainThread == Thread.currentThread();
    }

    public final boolean isActivityResumed() {
        return this.mResumed;
    }

    public final boolean isActivityStarted() {
        return this.mStarted;
    }

    public final Handler getMainHandler() {
        return this.mMainHandler;
    }
}
