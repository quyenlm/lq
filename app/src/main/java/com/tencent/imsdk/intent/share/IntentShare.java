package com.tencent.imsdk.intent.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Patterns;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.android.friend.tools.IMRetCode;
import com.tencent.imsdk.android.friend.tools.IntentModuleFactory;
import com.tencent.imsdk.sns.base.IMShareBase;
import com.tencent.imsdk.sns.base.IMShareContent;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import org.json.JSONObject;

public abstract class IntentShare extends IMShareBase {
    public static final int SHARE_INTENT = 0;

    /* access modifiers changed from: protected */
    public abstract Intent initIntent();

    /* access modifiers changed from: protected */
    public Intent prepareTextIntent(Intent intent, IMShareContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public Intent prepareLinkIntent(Intent intent, IMShareContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public Intent prepareImageIntent(Intent intent, IMShareContent info) {
        return intent;
    }

    /* access modifiers changed from: protected */
    public boolean isAppInstalled() {
        return true;
    }

    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public void share2Intent(IMCallback<IMResult> listener, Intent shareIntent) {
        if (!isAppInstalled()) {
            IMRetCode.retByIMSDK(15, 15, "need install app", listener);
        } else if (shareIntent != null) {
            IntentModuleFactory.getInstance(this.currentContext).dispatchIntent(listener, shareIntent);
        } else {
            listener.onError(new IMException(3, "intent or context is null"));
        }
    }

    public void shareTextDialog(String title, String content, JSONObject extras, IMCallback<IMResult> iMCallback) {
    }

    public void shareImageDialog(final IMShareContent info, final IMCallback<IMResult> listener) {
        IMLogger.d("intentshare share start!");
        if (info == null) {
            IMRetCode.retByIMSDK(11, 11, "intentshare share text or image only one value can be passed !!", listener);
        } else if (info.type == 1) {
            if (T.ckIsEmpty(info.content)) {
                IMRetCode.retByIMSDK(11, 11, "share text need context", listener);
            } else {
                share2Intent(listener, prepareTextIntent(IntentModuleFactory.getInstance(this.currentContext).getTextIntent(initIntent(), info.content), info));
            }
        } else if (info.type != 5) {
            IMRetCode.retByIMSDK(7, 7, "intentshare share only support text or image, plz check your type !!", listener);
        } else if (T.ckIsEmpty(info.imagePath)) {
            IMRetCode.retByIMSDK(11, 11, "share image need image path", listener);
        } else if (Patterns.WEB_URL.matcher(info.imagePath).matches()) {
            IntentModuleFactory.getInstance(this.currentContext).shareNetworkImage(info.imagePath, new IMCallback<String>() {
                public void onSuccess(String path) {
                    IntentShare.this.share2Intent(listener, IntentShare.this.prepareImageIntent(IntentModuleFactory.getInstance(IntentShare.this.currentContext).getImageIntent(IntentShare.this.initIntent(), path), info));
                }

                public void onCancel() {
                    listener.onCancel();
                }

                public void onError(IMException exception) {
                    IMRetCode.retByIMSDK(3, 3, "intentshare share error = " + exception.getMessage(), listener);
                }
            });
        } else {
            share2Intent(listener, prepareImageIntent(IntentModuleFactory.getInstance(this.currentContext).getImageIntent(initIntent(), info.imagePath), info));
        }
    }

    public void shareText(String title, String content, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareText not support !!", callback);
    }

    public void shareLink(String link, String title, String description, String imgUrl, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareLink not support !!", callback);
    }

    public void shareLinkDialog(String link, String title, String description, String imgUrl, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareLinkDialog not support !!", callback);
    }

    public void shareImage(Bitmap bitmap, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareImage not support !!", callback);
    }

    public void shareImageDialog(Bitmap bitmap, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareImageDialog not support !!", callback);
    }

    public void shareImageDialog(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareImageDialog not support !!", callback);
    }

    public void shareImage(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMRetCode.retByIMSDK(7, 7, "intent shareImage not support !!", callback);
    }
}
