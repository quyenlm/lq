package com.tencent.imsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public abstract class IMProxyTask {
    private Context context = null;

    public abstract void onActivityResult(int i, int i2, Intent intent);

    public abstract void onPostProxy(Activity activity);

    public abstract void onPreProxy();

    public IMProxyTask(Context context2) {
        this.context = context2;
    }

    public Context getContext() {
        return this.context;
    }

    public void onDestroy() {
    }

    public void onResume(Activity activity) {
    }

    public void onPause(Activity activity) {
    }
}
