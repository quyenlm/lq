package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.pay.android.GGErrorCode;

public class GGLiveLogoutPlugin extends GGPlugin<Void, PluginResult> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GGLIVE_LOGOUT;
    }

    public Integer getRequestCode() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, Void data) {
        new SharedPrefStorage(activity).clearGGLiveSession();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(activity);
            CookieManager.getInstance().removeAllCookie();
        } else {
            CookieManager.getInstance().removeAllCookies((ValueCallback) null);
        }
        PluginResult result = new PluginResult();
        result.status = 0;
        result.flag = GGErrorCode.SUCCESS.getCode().intValue();
        result.source = getId();
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}
