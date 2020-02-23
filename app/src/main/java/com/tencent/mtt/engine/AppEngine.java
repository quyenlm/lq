package com.tencent.mtt.engine;

import android.content.Context;

public class AppEngine {
    private static AppEngine mInstance = new AppEngine();
    public static boolean sCallMode = true;
    private Context mApplicationContext;
    private Context mContext;

    public static AppEngine getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.mApplicationContext = context.getApplicationContext();
    }

    public Context getApplicationContex() {
        return this.mApplicationContext;
    }
}
