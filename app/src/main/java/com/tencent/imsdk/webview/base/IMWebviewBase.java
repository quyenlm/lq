package com.tencent.imsdk.webview.base;

import android.content.Context;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.webview.api.IMWebViewActionListener;

public abstract class IMWebviewBase {
    public static final int WEBVIEW_OPT_EVENT_TYPE_OPEN_URL = 1;
    public Context context;

    public abstract void back();

    public abstract void callJs(String str);

    public abstract boolean canGoBack();

    public abstract boolean canGoForward();

    public abstract void close();

    public abstract void forward();

    public abstract void init(Context context2);

    public abstract boolean isActivated();

    public abstract void openURL(String str, String str2, boolean z);

    public abstract void openURL(String str, String str2, boolean z, boolean z2);

    public abstract void optCmd(int i, Object... objArr);

    public abstract void reload();

    public abstract void setBackgroundColor(int i, int i2, int i3, int i4);

    public abstract void setOrientation(boolean z);

    public abstract void setPosition(int i, int i2, int i3, int i4);

    public abstract void setZoom(float f, float f2);

    public void setWebViewActionListener(IMWebViewActionListener listener) {
        IMLogger.w("not support setWebViewActionListener");
    }

    protected IMWebviewBase() {
    }

    public void initialize(Context ctx) {
        this.context = ctx;
    }

    public String toString() {
        return "IMWebviewBase{context=" + this.context + '}';
    }
}
