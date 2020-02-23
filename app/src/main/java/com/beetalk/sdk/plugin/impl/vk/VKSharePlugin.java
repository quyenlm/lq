package com.beetalk.sdk.plugin.impl.vk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.vk.VKShareDialog;
import com.beetalk.sdk.vk.VKPostItem;
import com.garena.pay.android.GGErrorCode;
import com.tencent.mna.KartinRet;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

public class VKSharePlugin extends VKBasePlugin<VKPostItem, PluginResult> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.VK_SHARE;
    }

    /* access modifiers changed from: protected */
    public void executeActionAuthorized(final Activity activity, VKPostItem data) {
        VKShareDialogBuilder builder = new VKShareDialogBuilder();
        builder.setText(data.message);
        if (data.imageData != null && data.imageData.length > 0) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.imageData, 0, data.imageData.length);
                if (bitmap != null) {
                    builder.setAttachmentImages(new VKUploadImage[]{new VKUploadImage(bitmap, VKImageParameters.jpgImage(0.8f))});
                }
            } catch (OutOfMemoryError e) {
                BBLogger.e("failed to decode bitmap from byte array", new Object[0]);
                onResultError(activity, GGErrorCode.DECODE_IMAGE_FAILED);
                return;
            }
        }
        if (!TextUtils.isEmpty(data.link)) {
            builder.setAttachmentLink("", data.link);
        }
        builder.setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
            public void onVkShareComplete(int postId) {
                BBLogger.d("vk share complete", new Object[0]);
                PluginResult result = new PluginResult();
                result.source = VKSharePlugin.this.getId();
                result.status = 0;
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                result.message = KartinRet.KARTIN_REASON_NORMAL_ENGLISH;
                GGPluginManager.getInstance().publishResult(result, activity, VKSharePlugin.this.getId());
            }

            public void onVkShareCancel() {
                BBLogger.d("vk share canceled", new Object[0]);
                VKSharePlugin.this.onResultError(activity, GGErrorCode.USER_CANCELLED);
            }

            public void onVkShareError(VKError error) {
                BBLogger.d("vk share failed: %s", error);
                switch (error.errorCode) {
                    case -102:
                        VKSharePlugin.this.onResultError(activity, GGErrorCode.USER_CANCELLED);
                        return;
                    default:
                        VKSharePlugin.this.onResultError(activity, GGErrorCode.NETWORK_EXCEPTION);
                        return;
                }
            }
        });
        BBLogger.d("launching vk share dialog", new Object[0]);
        builder.show(activity.getFragmentManager(), "VK_SHARE_DIALOG");
    }

    /* access modifiers changed from: private */
    public void onResultError(Activity activity, GGErrorCode error) {
        PluginResult result = new PluginResult();
        result.source = getId();
        result.status = -1;
        result.flag = error.getCode().intValue();
        result.message = error.getStringValue();
        GGPluginManager.getInstance().publishResult(result, activity, getId());
    }
}
