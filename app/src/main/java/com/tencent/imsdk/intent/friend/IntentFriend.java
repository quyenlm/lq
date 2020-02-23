package com.tencent.imsdk.intent.friend;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Patterns;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.android.friend.tools.IMRetCode;
import com.tencent.imsdk.android.friend.tools.IntentModuleFactory;
import com.tencent.imsdk.sns.api.IMLaunchHandler;
import com.tencent.imsdk.sns.base.IMFriendBase;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLaunchResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class IntentFriend extends IMFriendBase implements IMLaunchHandler {
    /* access modifiers changed from: protected */
    public abstract Intent initIntent();

    /* access modifiers changed from: protected */
    public Intent prepareTextIntent(Intent intent, IMFriendContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public Intent prepareLinkIntent(Intent intent, IMFriendContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public Intent prepareImageIntent(Intent intent, IMFriendContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return true;
    }

    public void sendMessage(final IMFriendContent info, final IMCallback<IMFriendResult> listener) {
        IMLogger.d("intent friend sendMessage start " + info.type);
        if (info == null) {
            IMRetCode.retByIMSDK(11, 11, "intentfriend share text or image only one value can be passed !!", listener);
        } else if (info.type == 1) {
            if (T.ckIsEmpty(info.content)) {
                IMRetCode.retByIMSDK(11, 11, "share text need context", listener);
            } else {
                send2Intent(listener, prepareTextIntent(IntentModuleFactory.getInstance(this.currentContext).getTextIntent(initIntent(), info.content), info));
            }
        } else if (info.type != 5) {
            IMRetCode.retByIMSDK(7, 7, "intentfriend share only support text or image, plz check your type !!", listener);
        } else if (T.ckIsEmpty(info.imagePath)) {
            IMRetCode.retByIMSDK(11, 11, "share image need image path", listener);
        } else if (Patterns.WEB_URL.matcher(info.imagePath).matches()) {
            IntentModuleFactory.getInstance(this.currentContext).shareNetworkImage(info.imagePath, new IMCallback<String>() {
                public void onSuccess(String path) {
                    IntentFriend.this.send2Intent(listener, IntentFriend.this.prepareImageIntent(IntentModuleFactory.getInstance(IntentFriend.this.currentContext).getImageIntent(IntentFriend.this.initIntent(), path), info));
                }

                public void onCancel() {
                    listener.onCancel();
                }

                public void onError(IMException exception) {
                    IMRetCode.retByIMSDK(3, 3, "intentfriend share error = " + exception.getMessage(), listener);
                }
            });
        } else {
            send2Intent(listener, prepareImageIntent(IntentModuleFactory.getInstance(this.currentContext).getImageIntent(initIntent(), info.imagePath), info));
        }
    }

    public void invite(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent invite not support !!", callback);
    }

    public void sendText(IMFriendContent info, IMCallback<IMFriendResult> listener) {
        IMRetCode.retByIMSDK(7, 7, "intent sendText not support !!", listener);
    }

    public void sendLink(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent sendLink not support !!", callback);
    }

    public void sendImage(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent sendImage not support !!", callback);
    }

    /* access modifiers changed from: protected */
    public void send2Intent(IMCallback<IMFriendResult> listener, Intent shareIntent) {
        if (!isAppInstalled()) {
            IMRetCode.retByIMSDK(15, 15, "need install app", listener);
        } else if (shareIntent != null) {
            IntentModuleFactory.getInstance(this.currentContext).dispatchIntent(listener, shareIntent);
        } else {
            IMRetCode.retByIMSDK(11, 11, "intent is null", listener);
        }
    }

    public void handleIntent(Intent intent, IMCallback<IMLaunchResult> callback) {
        IMLogger.d("linefriend handleIntent start handleIntent = " + intent.getData() + " intent = " + intent);
        if (callback == null) {
            IMLogger.w("callback is null");
        } else if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                IMLaunchResult launchResult = new IMLaunchResult(1, 1);
                launchResult.launchData = parseUri(uri);
                launchResult.launchUri = uri.toString();
                callback.onSuccess(launchResult);
                return;
            }
            IMLogger.w("uri is null");
            IMException exception = new IMException(3, 11);
            exception.thirdRetCode = 11;
            exception.thirdRetMsg = "uri is null";
            callback.onError(exception);
        } else {
            IMLogger.w("intent is null");
            IMException exception2 = new IMException(3, 11);
            exception2.thirdRetCode = 11;
            exception2.thirdRetMsg = "intent is null";
            callback.onError(exception2);
        }
    }

    private String parseUri(@NonNull Uri uri) {
        IMLogger.d("parseUri..." + (uri == null ? "uri is null" : uri.toString()));
        JSONObject jsonObject = new JSONObject();
        if (!(uri == null || uri.getQueryParameterNames() == null)) {
            for (String key : uri.getQueryParameterNames()) {
                try {
                    jsonObject.put(key, uri.getQueryParameter(key));
                } catch (JSONException e) {
                    IMLogger.w("catch json exception : " + e.getMessage());
                }
            }
        }
        return jsonObject.toString();
    }
}
