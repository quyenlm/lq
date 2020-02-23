package com.tencent.imsdk;

import android.app.Activity;
import android.content.Context;

public abstract class IMPermissionProxyTask {
    private Context context = null;

    public abstract void onPostProxy(Activity activity);

    public abstract void onPreProxy();

    public abstract void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);

    public IMPermissionProxyTask(Context context2) {
        this.context = context2;
    }

    public Context getContext() {
        return this.context;
    }
}
