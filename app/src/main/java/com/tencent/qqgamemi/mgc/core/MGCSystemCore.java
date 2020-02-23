package com.tencent.qqgamemi.mgc.core;

import android.content.Context;

public class MGCSystemCore {
    static final String LOG_TAG = "MGCCore";

    public static MGCContext create(Context context) {
        return MGCContext.create(context);
    }

    public static void init(Context context) {
        MGCContext.create(context).init();
    }
}
