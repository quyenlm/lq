package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginResult;
import com.beetalk.sdk.plugin.impl.fb.data.FBMessageData;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.garena.pay.android.GGErrorCode;
import java.util.Collections;

public class FBGameMessagePlugin extends BaseFBPlugin<FBMessageData, PluginResult> {
    public void onError(final Exception e, Activity activity) {
        GGPluginManager.getInstance().publishResult(new PluginResult() {
            {
                this.source = FBGameMessagePlugin.this.getId();
                this.status = -1;
                this.flag = GGErrorCode.LOGIN_FAILED.getCode().intValue();
                this.message = e.getMessage();
            }
        }, activity, getId());
    }

    public void onSuccess(final Activity activity) {
        if (this.mData == null || ((FBMessageData) this.mData).uid <= 0) {
            PluginResult result = new PluginResult();
            result.source = getId();
            result.status = -1;
            result.flag = GGErrorCode.ERROR_IN_PARAMS.getCode().intValue();
            result.message = "User id is invalid";
            GGPluginManager.getInstance().publishResult(result, activity, getId());
            return;
        }
        GameRequestDialog requestDialog = new GameRequestDialog(activity);
        requestDialog.registerCallback(this.callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            public void onSuccess(GameRequestDialog.Result r) {
                PluginResult result = new PluginResult();
                result.source = FBGameMessagePlugin.this.getId();
                result.status = 0;
                result.flag = GGErrorCode.SUCCESS.getCode().intValue();
                result.message = "Successfully sent";
                GGPluginManager.getInstance().publishResult(result, activity, FBGameMessagePlugin.this.getId());
            }

            public void onCancel() {
                PluginResult result = new PluginResult();
                result.source = FBGameMessagePlugin.this.getId();
                result.status = -1;
                result.flag = GGErrorCode.USER_CANCELLED.getCode().intValue();
                result.message = GGErrorCode.USER_CANCELLED.getStringValue();
                GGPluginManager.getInstance().publishResult(result, activity, FBGameMessagePlugin.this.getId());
            }

            public void onError(FacebookException error) {
                PluginResult result = new PluginResult();
                result.source = FBGameMessagePlugin.this.getId();
                result.status = -1;
                result.flag = GGErrorCode.ERROR.getCode().intValue();
                if (error != null) {
                    result.message = error.getMessage();
                } else {
                    result.message = "Failed to send message";
                }
                GGPluginManager.getInstance().publishResult(result, activity, FBGameMessagePlugin.this.getId());
            }
        });
        GameRequestContent content = new GameRequestContent.Builder().setTitle(((FBMessageData) this.mData).title).setMessage(((FBMessageData) this.mData).description).setData(((FBMessageData) this.mData).data).setRecipients(Collections.singletonList(String.valueOf(((FBMessageData) this.mData).uid))).build();
        if (requestDialog.canShow(content)) {
            requestDialog.show(content);
            return;
        }
        PluginResult result2 = new PluginResult();
        result2.source = getId();
        result2.status = -1;
        result2.flag = GGErrorCode.UNSUPPORTED_API.getCode().intValue();
        result2.message = "Cannot show game request dialog";
        GGPluginManager.getInstance().publishResult(result2, activity, getId());
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.FACEBOOK_GAME_MESSAGE;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.FACEBOOK_GAME_MESSAGE_PLUGIN;
    }
}
