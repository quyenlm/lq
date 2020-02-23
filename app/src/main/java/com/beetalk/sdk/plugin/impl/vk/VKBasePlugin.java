package com.beetalk.sdk.plugin.impl.vk;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.vk.VKAuthHelper;
import com.garena.pay.android.GGErrorCode;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.api.VKError;

public abstract class VKBasePlugin<S, T> extends GGPlugin<S, T> {
    private VKAuthHelper authHelper;
    private S data;

    /* access modifiers changed from: protected */
    public abstract void executeActionAuthorized(Activity activity, S s);

    /* access modifiers changed from: protected */
    public S getData() {
        return this.data;
    }

    public Integer getRequestCode() {
        return Integer.valueOf(VKServiceActivity.VKServiceType.Authorization.getOuterCode());
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, S data2) {
        if (data2 == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
            return;
        }
        BBLogger.d("checking vk session", new Object[0]);
        this.data = data2;
        this.authHelper = new VKAuthHelper(activity);
        this.authHelper.setOnAuthResultListener(new VKAuthHelper.OnAuthResultListener() {
            public void onLoggedIn() {
                VKBasePlugin.this.onLoginSuccess(activity);
            }

            public void onError(VKError error) {
                PluginResult result = new PluginResult();
                result.source = VKBasePlugin.this.getId();
                result.status = -1;
                if (error != null) {
                    switch (error.errorCode) {
                        case -102:
                            result.flag = GGErrorCode.USER_CANCELLED.getCode().intValue();
                            result.message = GGErrorCode.USER_CANCELLED.getStringValue();
                            break;
                        default:
                            result.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                            result.message = GGErrorCode.NETWORK_EXCEPTION.getStringValue();
                            break;
                    }
                } else {
                    result.flag = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
                    result.message = "Unknown Error.";
                }
                GGPluginManager.getInstance().publishResult(result, activity, VKBasePlugin.this.getId());
            }
        });
        this.authHelper.startLogin();
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data2) {
        if (this.authHelper != null) {
            this.authHelper.onActivityResult(resultCode, data2);
            return false;
        }
        publishResult(ggPluginActivity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
        return false;
    }

    /* access modifiers changed from: private */
    public void onLoginSuccess(Activity activity) {
        if (this.data == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
        } else {
            executeActionAuthorized(activity, this.data);
        }
    }
}
