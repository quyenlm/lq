package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.fb.data.FBMessageData;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.garena.pay.android.GGErrorCode;

public class FBMessagePlugin extends BaseFBPlugin<FBMessageData, PluginResult> {
    public void onError(final Exception e, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.source = FBMessagePlugin.this.getId();
                this.flag = -1;
                this.status = -1;
                this.message = e.getMessage();
            }
        }, activity, getId());
    }

    public void onSuccess(final Activity activity) {
        if (this.mData == null || TextUtils.isEmpty(((FBMessageData) this.mData).contentUrl)) {
            PluginResult result = new PluginResult();
            result.source = getId();
            result.flag = -1;
            result.status = -1;
            result.message = "The content url is empty";
            GGPluginManager.getInstance().publishResult(result, activity, getId());
            return;
        }
        MessageDialog messageDialog = new MessageDialog(activity);
        messageDialog.registerCallback(this.callbackManager, new FacebookCallback<Sharer.Result>() {
            public void onSuccess(Sharer.Result res) {
                PluginResult result = new PluginResult();
                result.source = FBMessagePlugin.this.getId();
                result.flag = 0;
                result.status = 0;
                result.message = "Successfully send msg";
                GGPluginManager.getInstance().publishResult(result, activity, FBMessagePlugin.this.getId());
            }

            public void onCancel() {
                PluginResult result = new PluginResult();
                result.source = FBMessagePlugin.this.getId();
                result.flag = -1;
                result.status = -1;
                result.message = "Send msg cancelled";
                GGPluginManager.getInstance().publishResult(result, activity, FBMessagePlugin.this.getId());
            }

            public void onError(FacebookException error) {
                PluginResult result = new PluginResult();
                result.source = FBMessagePlugin.this.getId();
                result.flag = -1;
                result.status = -1;
                if (error != null) {
                    result.message = error.getMessage();
                } else {
                    result.message = "Send msg Failed";
                }
                GGPluginManager.getInstance().publishResult(result, activity, FBMessagePlugin.this.getId());
            }
        });
        ShareLinkContent.Builder shareLinkContent = (ShareLinkContent.Builder) new ShareLinkContent.Builder().setContentTitle(((FBMessageData) this.mData).title).setContentDescription(((FBMessageData) this.mData).description).setContentUrl(Uri.parse(((FBMessageData) this.mData).contentUrl));
        if (!TextUtils.isEmpty(((FBMessageData) this.mData).imageUrl)) {
            shareLinkContent.setImageUrl(Uri.parse(((FBMessageData) this.mData).imageUrl));
        }
        if (MessageDialog.canShow(ShareLinkContent.class)) {
            messageDialog.show(shareLinkContent.build());
        } else {
            GGPluginManager.getInstance().publishResult(generateResult(GGErrorCode.UNSUPPORTED_API.getCode().intValue(), "Cannot show Messenger dialog"), activity, getId());
        }
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_MESSAGE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FACEBOOK_MESSAGE_PLUGIN;
    }
}
