package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.facebook.FBPostItem;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.garena.pay.android.GGErrorCode;
import com.tencent.mna.KartinRet;

public class FBSharePlugin extends BaseFBPlugin<FBPostItem, PluginResult> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_SHARE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FB_SHARE_PLUGIN;
    }

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, FBPostItem data) {
        if (data == null) {
            publishResult(activity, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
        } else if (!needCheckFBInstalled() || Helper.isPackageInstalled("com.facebook.katana", activity)) {
            super.executeAction(activity, data);
        } else {
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNSUPPORTED_API.getCode().intValue(), "Facebook App is not installed."), activity, getId());
        }
    }

    /* access modifiers changed from: protected */
    public boolean needCheckFBInstalled() {
        return true;
    }

    public void onError(Exception e, Activity activity) {
        PluginResult result = new PluginResult();
        result.source = getId();
        result.status = -1;
        result.flag = -1;
        result.message = e.getMessage();
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }

    public void onSuccess(final Activity activity) {
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            ShareDialog shareDialog = new ShareDialog(activity);
            shareDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
                public void onSuccess(Sharer.Result result) {
                    GGPluginManager.getInstance().publishResult(FBSharePlugin.this.generateResult(GGErrorCode.SUCCESS.getCode().intValue(), KartinRet.KARTIN_REASON_NORMAL_ENGLISH), activity, FBSharePlugin.this.getId());
                }

                public void onCancel() {
                    GGPluginManager.getInstance().publishResult(FBSharePlugin.this.generateResult(GGErrorCode.USER_CANCELLED.getCode().intValue(), "User Cancelled."), activity, FBSharePlugin.this.getId());
                }

                public void onError(FacebookException error) {
                    PluginResult result = new PluginResult();
                    int intValue = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
                    result.status = intValue;
                    result.flag = intValue;
                    result.source = FBSharePlugin.this.getId();
                    result.message = error != null ? error.getMessage() : "Unknown Error.";
                    GGPluginManager.getInstance().publishResult(result, activity, FBSharePlugin.this.getId());
                }
            });
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeByteArray(((FBPostItem) this.mData).data, 0, ((FBPostItem) this.mData).data.length);
            } catch (Exception e) {
                BBLogger.e(e);
            } catch (OutOfMemoryError e2) {
                BBLogger.e("Failed to create bitmap: out of memory", new Object[0]);
                GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.ERROR_IN_PARAMS.getCode().intValue(), "Failed to create bitmap: out of memory"), activity, getId());
                return;
            }
            if (bitmap != null) {
                shareDialog.show(new SharePhotoContent.Builder().addPhoto(new SharePhoto.Builder().setBitmap(bitmap).build()).build());
                return;
            }
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "Failed to create bitmap"), activity, getId());
            return;
        }
        GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNSUPPORTED_API.getCode().intValue(), "Facebook App is not installed."), activity, getId());
    }
}
