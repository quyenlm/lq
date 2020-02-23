package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

public class FBInvitePlugin extends BaseFBPlugin<FBInviteData, PluginResult> {

    public static class FBInviteData {
        public String appLinkUrl;
        public String message;
        public String previewImageUrl;
        public String title;
    }

    public void onError(final Exception e, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.source = FBInvitePlugin.this.getId();
                this.flag = -1;
                this.status = -1;
                this.message = e.getMessage();
            }
        }, activity, getId());
    }

    public void onSuccess(final Activity activity) {
        GameRequestDialog gameRequestDialog = new GameRequestDialog(activity);
        gameRequestDialog.registerCallback(this.callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            public void onSuccess(GameRequestDialog.Result res) {
                PluginResult result = new PluginResult();
                result.source = FBInvitePlugin.this.getId();
                result.flag = 0;
                result.status = 0;
                result.message = "Successfully shared";
                GGPluginManager.getInstance().publishResult(result, activity, FBInvitePlugin.this.getId());
            }

            public void onCancel() {
                PluginResult result = new PluginResult();
                result.source = FBInvitePlugin.this.getId();
                result.flag = -1;
                result.status = -1;
                result.message = "Share cancelled";
                GGPluginManager.getInstance().publishResult(result, activity, FBInvitePlugin.this.getId());
            }

            public void onError(FacebookException error) {
                PluginResult result = new PluginResult();
                result.source = FBInvitePlugin.this.getId();
                result.flag = -1;
                result.status = -1;
                if (error != null) {
                    result.message = error.getMessage();
                } else {
                    result.message = "Share Failed";
                }
                GGPluginManager.getInstance().publishResult(result, activity, FBInvitePlugin.this.getId());
            }
        });
        gameRequestDialog.show(new GameRequestContent.Builder().setTitle(((FBInviteData) this.mData).title).setMessage(((FBInviteData) this.mData).message).build());
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_INVITE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FB_INVITE_PLUGIN;
    }
}
