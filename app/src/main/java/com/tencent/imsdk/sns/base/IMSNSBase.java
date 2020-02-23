package com.tencent.imsdk.sns.base;

import android.content.Context;
import com.tencent.imsdk.IMConfig;

public abstract class IMSNSBase {
    public static final int STRICT_LOGIN_ERROR_CODE = -258;
    protected static final int SUCCESS_FLAG = 1;
    protected static final String SUCCESS_MSG = "SUCCESS";
    /* access modifiers changed from: protected */
    public static IMHttpClient httpClient = null;
    protected static Object mLock = new Object();
    /* access modifiers changed from: protected */
    public Context currentContext = null;

    public boolean setContext(Context context) {
        boolean z;
        int timeout;
        this.currentContext = context;
        IMConfig.initialize(context);
        synchronized (mLock) {
            if (httpClient == null) {
                httpClient = new IMHttpClient();
                if (this.currentContext != null && (timeout = IMConfig.getDefaultTimeout()) > 0) {
                    httpClient.getAsyncHttpClient().setTimeout(timeout);
                }
            }
            z = this.currentContext != null;
        }
        return z;
    }

    public boolean isInitialized() {
        return this.currentContext != null;
    }
}
