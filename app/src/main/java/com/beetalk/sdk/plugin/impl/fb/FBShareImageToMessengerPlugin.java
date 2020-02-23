package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.garena.pay.android.GGErrorCode;

public class FBShareImageToMessengerPlugin extends BaseFBPlugin<byte[], PluginResult> {
    public void onError(final Exception e, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.source = FBShareImageToMessengerPlugin.this.getId();
                this.flag = -1;
                this.status = -1;
                this.message = e.getMessage();
            }
        }, activity, getId());
    }

    public void onSuccess(final Activity activity) {
        if (this.mData == null) {
            PluginResult result = new PluginResult();
            result.source = getId();
            result.flag = -1;
            result.status = -1;
            result.message = "The content url is empty";
            GGPluginManager.getInstance().publishResult(result, activity, getId());
        } else if (MessageDialog.canShow(SharePhotoContent.class)) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeByteArray((byte[]) this.mData, 0, ((byte[]) this.mData).length);
            } catch (Exception e) {
                BBLogger.e(e);
            } catch (OutOfMemoryError e2) {
                BBLogger.e("Failed to create bitmap: out of memory", new Object[0]);
                GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.ERROR_IN_PARAMS.getCode().intValue(), "Failed to create bitmap: out of memory"), activity, getId());
                return;
            }
            if (bitmap != null) {
                SharePhotoContent.Builder sharePhotoContent = new SharePhotoContent.Builder().addPhoto(new SharePhoto.Builder().setBitmap(bitmap).build());
                MessageDialog messageDialog = new MessageDialog(activity);
                messageDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
                    public void onSuccess(Sharer.Result res) {
                        PluginResult result = new PluginResult();
                        result.source = FBShareImageToMessengerPlugin.this.getId();
                        result.flag = 0;
                        result.status = 0;
                        result.message = "Successfully send msg";
                        GGPluginManager.getInstance().publishResult(result, activity, FBShareImageToMessengerPlugin.this.getId());
                    }

                    public void onCancel() {
                        PluginResult result = new PluginResult();
                        result.source = FBShareImageToMessengerPlugin.this.getId();
                        result.flag = -1;
                        result.status = -1;
                        result.message = "Send msg cancelled";
                        GGPluginManager.getInstance().publishResult(result, activity, FBShareImageToMessengerPlugin.this.getId());
                    }

                    public void onError(FacebookException error) {
                        PluginResult result = new PluginResult();
                        result.source = FBShareImageToMessengerPlugin.this.getId();
                        result.flag = -1;
                        result.status = -1;
                        if (error != null) {
                            result.message = error.getMessage();
                        } else {
                            result.message = "Send msg Failed";
                        }
                        GGPluginManager.getInstance().publishResult(result, activity, FBShareImageToMessengerPlugin.this.getId());
                    }
                });
                messageDialog.show(sharePhotoContent.build());
                return;
            }
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), "Failed to create bitmap"), activity, getId());
        } else {
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNSUPPORTED_API.getCode().intValue(), "Cannot show Messenger dialog"), activity, getId());
        }
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_MESSENGER_SEND_IMAGE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FACEBOOK_MESSENGER_SEND_IMAGE_PLUGIN;
    }
}
