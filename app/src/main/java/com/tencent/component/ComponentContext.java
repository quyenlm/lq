package com.tencent.component;

import android.content.Context;

public class ComponentContext {
    private static Context mContext;

    private ComponentContext() {
    }

    public static void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static final String getPackageName() {
        return getContext().getPackageName();
    }
}
