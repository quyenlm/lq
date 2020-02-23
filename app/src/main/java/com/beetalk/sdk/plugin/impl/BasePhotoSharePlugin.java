package com.beetalk.sdk.plugin.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPhotoShare;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.PluginResult;
import com.garena.pay.android.GGErrorCode;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public abstract class BasePhotoSharePlugin extends GGPlugin<GGPhotoShare, PluginResult> {
    private static final int MAX_BITMAP_SIZE = 2073600;

    /* access modifiers changed from: protected */
    public abstract String getIntentAction();

    /* access modifiers changed from: protected */
    public void execute(Activity activity, GGPhotoShare data) {
        Bitmap bitmap;
        byte[] imageData = null;
        try {
            String filePath = data.getFile();
            if (!TextUtils.isEmpty(filePath)) {
                File file = new File(filePath);
                if (!file.exists()) {
                    BBLogger.i("File doesn't exist:" + filePath, new Object[0]);
                    publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "File doesn't exist");
                    return;
                }
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    int width = options.outWidth;
                    int height = options.outHeight;
                    int sampleSize = 1;
                    while (width * height > MAX_BITMAP_SIZE) {
                        width /= 2;
                        height /= 2;
                        sampleSize *= 2;
                    }
                    options.inJustDecodeBounds = false;
                    int count = 0;
                    do {
                        options.inSampleSize = sampleSize;
                        bitmap = BitmapFactory.decodeFile(filePath, options);
                        sampleSize *= 2;
                        count++;
                        if (bitmap != null || count >= 3) {
                            imageData = getCompressedBytes(bitmap, SDKConstants.MAX_IMG_DATA_LENGTH_BYTES, 85);
                        }
                        options.inSampleSize = sampleSize;
                        bitmap = BitmapFactory.decodeFile(filePath, options);
                        sampleSize *= 2;
                        count++;
                        break;
                    } while (count >= 3);
                    imageData = getCompressedBytes(bitmap, SDKConstants.MAX_IMG_DATA_LENGTH_BYTES, 85);
                } catch (Exception e) {
                    BBLogger.e(e);
                }
            }
            if (imageData == null) {
                imageData = data.getImgData();
                if (imageData == null) {
                    publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "fail to load image");
                    return;
                }
            }
            if (imageData.length > 500000) {
                try {
                    imageData = getCompressedBytes(BitmapFactory.decodeByteArray(imageData, 0, imageData.length), SDKConstants.MAX_IMG_DATA_LENGTH_BYTES, 85);
                } catch (Exception e2) {
                    BBLogger.e(e2);
                    publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), e2.getMessage());
                    return;
                }
            }
            Intent intent = new Intent();
            intent.setClassName(Helper.isPackageInstalled(SDKConstants.GAS_PACKAGE, activity) ? SDKConstants.GAS_PACKAGE : SDKConstants.GAS_LITE_PACKAGE, SDKConstants.GAS_CLASSPATH_PROXY);
            intent.setAction(getIntentAction());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_IMG_DATA, imageData);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_MEDIA_TAG_NAME, data.getMediaTag());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_ITEM_TYPE, 1);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_OPENID, GGLoginSession.getCurrentSession().getOpenId());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_SHARE_TO, data.getScene());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_ACTION, data.getMessageAction());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_IMG_DATA_LEN, imageData.length);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_GAME_ID, data.getGameId());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SHARE_AUTO_AUTH, false);
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_VERSION, GGPlatform.GGGetSDKVersion());
            intent.putExtra(SDKConstants.SHARE_BUNDLE_TAG.COM_GARENA_MSDK_GAME_SDK_ENV, SDKConstants.getEnvironment().toString());
            activity.startActivityForResult(intent, getRequestCode().intValue());
        } catch (Exception e3) {
            BBLogger.e(e3);
            publishResult(activity, GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), e3.getMessage());
        }
    }

    private static byte[] getCompressedBytes(Bitmap bitmap, int sizeLimit, int compressionQuality) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteStream);
        byte[] bytes = byteStream.toByteArray();
        while (bytes.length > sizeLimit && compressionQuality >= 30) {
            byteStream.reset();
            compressionQuality = (int) (((double) compressionQuality) * 0.85d);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteStream);
            bytes = byteStream.toByteArray();
        }
        try {
            byteStream.close();
        } catch (IOException e) {
            BBLogger.e(e);
        }
        return bytes;
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        publishResult(ggPluginActivity, (resultCode == -1 ? GGErrorCode.SUCCESS.getCode() : GGErrorCode.UNKNOWN_ERROR.getCode()).intValue(), "");
        return true;
    }
}
