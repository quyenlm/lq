package com.beetalk.sdk.plugin.impl.gas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPhotoShare;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.impl.BasePhotoSharePlugin;
import com.garena.pay.android.GGErrorCode;
import java.io.File;

public class GasPhotoSharePlugin extends BasePhotoSharePlugin {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GAS_SHARE_PHOTO;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GAS_SHARE_PHOTO;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, GGPhotoShare data) {
        if (data == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
        } else if (!GGPlatform.GGIsPlatformInstalled(activity, 1)) {
            publishResult(activity, GGErrorCode.APP_NOT_INSTALLED.getCode().intValue());
        } else if (!Helper.isSupportGasShare(activity)) {
            publishResult(activity, GGErrorCode.UNSUPPORTED_API.getCode().intValue());
        } else {
            if (Helper.isSupportGasBigImageShare(activity)) {
                String filePath = data.getFile();
                if (!TextUtils.isEmpty(filePath)) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        BBLogger.i("File doesn't exist:" + filePath, new Object[0]);
                        publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "File doesn't exist");
                        return;
                    }
                    Uri uri = null;
                    try {
                        uri = FileProvider.getUriForFile(activity, "com.garena.android.fileprovider" + GGLoginSession.getCurrentSession().getApplicationId(), file);
                    } catch (Exception e) {
                        BBLogger.e(e);
                    }
                    if (uri == null) {
                        publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClassName(Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity) ? SDKConstants.GAS_PACKAGE : SDKConstants.GAS_LITE_PACKAGE, SDKConstants.GAS_CLASSPATH_PROXY);
                    intent.setAction(SDKConstants.GAS_ACTION_SHARE);
                    intent.setData(uri);
                    intent.addFlags(1);
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_MEDIA_TAG_NAME, data.getMediaTag());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_ITEM_TYPE, 1);
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_OPENID, GGLoginSession.getCurrentSession().getOpenId());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_SHARE_TO, data.getScene());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_ACTION, data.getMessageAction());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_IMG_DATA_LEN, (int) file.length());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_GAME_ID, data.getGameId());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_AUTO_AUTH, false);
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_VERSION, GGPlatform.GGGetSDKVersion());
                    intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_ENV, SDKConstants.getEnvironment().toString());
                    activity.startActivityForResult(intent, getRequestCode().intValue());
                    return;
                }
            }
            super.execute(activity, data);
        }
    }

    /* access modifiers changed from: protected */
    public String getIntentAction() {
        return SDKConstants.GAS_ACTION_SHARE;
    }
}
