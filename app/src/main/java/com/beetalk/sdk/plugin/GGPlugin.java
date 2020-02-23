package com.beetalk.sdk.plugin;

import android.app.Activity;
import android.content.Intent;

public abstract class GGPlugin<S, T> {
    private GGPluginCallback<T> callback;

    /* access modifiers changed from: protected */
    public abstract void executeAction(Activity activity, S s);

    public abstract String getId();

    public abstract Integer getRequestCode();

    public abstract boolean onActivityResult(Activity activity, int i, Intent intent);

    public boolean embedInActivity() {
        return true;
    }

    public final void setCallback(GGPluginCallback<T> callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: protected */
    public void publishResult(Activity activity, int code) {
        GGPluginManager.getInstance().publishResult(generateResult(code, ""), activity, getId());
    }

    /* access modifiers changed from: protected */
    public void publishResult(Activity activity, int code, String msg) {
        GGPluginManager.getInstance().publishResult(generateResult(code, msg), activity, getId());
    }

    /* access modifiers changed from: protected */
    public PluginResult generateResult(final int code, final String strMessage) {
        return new PluginResult() {
            {
                int i = code;
                this.status = i;
                this.flag = i;
                this.source = GGPlugin.this.getId();
                this.message = strMessage;
            }
        };
    }
}
