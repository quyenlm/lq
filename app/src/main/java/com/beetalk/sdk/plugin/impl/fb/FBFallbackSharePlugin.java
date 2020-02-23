package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.facebook.FBPostItem;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.garena.pay.android.GGErrorCode;

public class FBFallbackSharePlugin extends FBSharePlugin {
    public void onError(final Exception e, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                int intValue = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
                this.status = intValue;
                this.flag = intValue;
                this.message = e.getMessage();
                this.source = SDKConstants.PLUGIN_KEYS.FACEBOOK_SHARE_FALLBACK;
            }
        }, activity, getId());
    }

    /* access modifiers changed from: protected */
    public boolean needCheckFBInstalled() {
        return false;
    }

    public void onSuccess(final Activity activity) {
        FBPostItem data = (FBPostItem) this.mData;
        Uri contentUri = null;
        Uri imageUri = null;
        try {
            if (!TextUtils.isEmpty(data.link)) {
                contentUri = Uri.parse(data.link);
            }
            if (!TextUtils.isEmpty(data.mediaUrl)) {
                imageUri = Uri.parse(data.mediaUrl);
            }
            ShareDialog shareDialog = new ShareDialog(activity);
            ShareLinkContent shareLinkContent = ((ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentTitle(data.name).setContentDescription(data.description).setContentUrl(contentUri)).setImageUrl(imageUri).build();
            shareDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
                public void onSuccess(Sharer.Result result) {
                    GGPluginManager.getInstance().publishResult(FBFallbackSharePlugin.this.generateResult(GGErrorCode.SUCCESS.getCode().intValue(), String.format("Successfully posted %s", new Object[]{result.getPostId()})), activity, FBFallbackSharePlugin.this.getId());
                }

                public void onCancel() {
                    GGPluginManager.getInstance().publishResult(FBFallbackSharePlugin.this.generateResult(GGErrorCode.USER_CANCELLED.getCode().intValue(), "User cancelled"), activity, FBFallbackSharePlugin.this.getId());
                }

                public void onError(FacebookException error) {
                    if (error instanceof FacebookOperationCanceledException) {
                        GGPluginManager.getInstance().publishResult(FBFallbackSharePlugin.this.generateResult(GGErrorCode.USER_CANCELLED.getCode().intValue(), "User cancelled"), activity, FBFallbackSharePlugin.this.getId());
                    } else {
                        GGPluginManager.getInstance().publishResult(FBFallbackSharePlugin.this.generateResult(GGErrorCode.UNKNOWN_ERROR.getCode().intValue(), error == null ? "Unknown Error" : error.getMessage()), activity, FBFallbackSharePlugin.this.getId());
                    }
                }
            });
            shareDialog.show(shareLinkContent);
        } catch (Exception e) {
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.ERROR.getCode().intValue(), "Error Uri."), activity, getId());
        }
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_SHARE_FALLBACK;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FB_FALLBACK_SHARE;
    }
}
