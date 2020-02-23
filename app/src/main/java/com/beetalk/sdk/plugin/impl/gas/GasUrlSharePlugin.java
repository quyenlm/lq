package com.beetalk.sdk.plugin.impl.gas;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.GGTextShare;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.pay.android.GGErrorCode;

public class GasUrlSharePlugin extends GGPlugin<GGTextShare, PluginResult> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GAS_SHARE_URL;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GAS_SHARE_URL;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, GGTextShare data) {
        if (data == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
        } else if (!GGPlatform.GGIsPlatformInstalled(activity, 1)) {
            publishResult(activity, GGErrorCode.APP_NOT_INSTALLED.getCode().intValue());
        } else if (Helper.isSupportGasShare(activity)) {
            Intent intent = new Intent();
            intent.setClassName(Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity) ? SDKConstants.GAS_PACKAGE : SDKConstants.GAS_LITE_PACKAGE, SDKConstants.GAS_CLASSPATH_PROXY);
            intent.setAction(SDKConstants.GAS_ACTION_SHARE);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_MEDIA_TAG_NAME, data.getMediaTag());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_URL, data.getUrl());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_OPENID, GGLoginSession.getCurrentSession().getOpenId());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_ITEM_TYPE, 2);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_SHARE_TO, data.getScene());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_GAME_ID, data.getGameId());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_AUTO_AUTH, false);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_VERSION, GGPlatform.GGGetSDKVersion());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_ENV, SDKConstants.getEnvironment().toString());
            activity.startActivityForResult(intent, getRequestCode().intValue());
        } else {
            publishResult(activity, GGErrorCode.UNSUPPORTED_API.getCode().intValue());
        }
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        publishResult(ggPluginActivity, (resultCode == -1 ? GGErrorCode.SUCCESS.getCode() : GGErrorCode.UNKNOWN_ERROR.getCode()).intValue(), "");
        return true;
    }
}
