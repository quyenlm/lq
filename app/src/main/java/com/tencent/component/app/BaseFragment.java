package com.tencent.component.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;

@PluginApi(since = 4)
public class BaseFragment extends Fragment implements Handler.Callback {
    private static final String TAG = "BaseFragment";
    private Application mApplication;
    protected Handler mMainHandler = new Handler(Looper.getMainLooper(), this);
    private Thread mMainThread = Looper.getMainLooper().getThread();
    private volatile Toast mNotifyToast;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            this.mApplication = activity.getApplication();
            if (isBaseApplication()) {
                ((BaseApplication) this.mApplication).dispatchFragmentAttachedInner(this, activity);
            }
        }
    }

    private boolean isBaseApplication() {
        return this.mApplication instanceof BaseApplication;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentCreatedInner(this, savedInstanceState);
        }
    }

    public void onStart() {
        super.onStart();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentStartedInner(this);
        }
    }

    public void onResume() {
        super.onResume();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentResumedInner(this);
        }
    }

    public void onPause() {
        super.onPause();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentPausedInner(this);
        }
    }

    public void onStop() {
        super.onStop();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentStoppedInner(this);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentSaveInstanceStateInner(this, outState);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentDestroyedInner(this);
        }
    }

    public void onDetach() {
        super.onDetach();
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentDetachedInner(this);
        }
    }

    @PluginApi(since = 4)
    public final void runOnUiThread(Runnable action) {
        if (!isMainThread()) {
            this.mMainHandler.post(action);
        } else {
            action.run();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentOnActivityResultInner(this, requestCode, resultCode, data);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isBaseApplication()) {
            ((BaseApplication) this.mApplication).dispatchFragmentActivityCreatedInner(this, savedInstanceState);
        }
    }

    @PluginApi(since = 4)
    public final void post(Runnable r) {
        this.mMainHandler.post(r);
    }

    @PluginApi(since = 4)
    public final void postDelayed(Runnable r, long delayMillis) {
        this.mMainHandler.postDelayed(r, delayMillis);
    }

    @PluginApi(since = 4)
    public final boolean isMainThread() {
        return this.mMainThread == Thread.currentThread();
    }

    @PluginApi(since = 300)
    public void showNotifyMessage(int resId) {
        showNotifyMessage(resId, 81);
    }

    @PluginApi(since = 300)
    public void showNotifyMessage(String msg) {
        showNotifyMessage(msg, 81);
    }

    @PluginApi(since = 300)
    public void showNotifyMessage(int resId, int gravity) {
        showNotifyMessage(resId == 0 ? null : getString(resId), gravity);
    }

    @PluginApi(since = 300)
    public void showNotifyMessage(final String msg, final int gravity) {
        if (msg != null && msg.length() != 0 && !isDetached() && getActivity() != null) {
            if (isMainThread()) {
                Toast toast = obtainNotifyToast();
                toast.setText(msg);
                toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
                toast.show();
                return;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = BaseFragment.this.obtainNotifyToast();
                    toast.setText(msg);
                    toast.setGravity(gravity, toast.getXOffset(), toast.getYOffset());
                    toast.show();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public Toast obtainNotifyToast() {
        if (this.mNotifyToast == null) {
            synchronized (this) {
                if (this.mNotifyToast == null) {
                    this.mNotifyToast = Toast.makeText(getActivity(), (CharSequence) null, 0);
                }
            }
        }
        return this.mNotifyToast;
    }

    @PluginApi(since = 300)
    public boolean handleMessage(Message message) {
        if (message == null) {
            return false;
        }
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            LogUtil.e(TAG, "recevie service callback but activity is null or finished!(" + getClass().getName() + h.b);
            return false;
        } else if (!isRemoving() && !isDetached()) {
            return handleMessageLogic(message);
        } else {
            LogUtil.e(TAG, "recevie service callback but fragment is isRemoving or isDetached!(" + getClass().getName() + h.b);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 300)
    public boolean handleMessageLogic(Message message) {
        return false;
    }
}
