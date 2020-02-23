package com.tencent.imsdk.sms.friend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.drive.DriveFile;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.android.friend.tools.IMRetCode;
import com.tencent.imsdk.intent.friend.IntentFriend;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;

public class SMSFriend extends IntentFriend {
    private static final String SMS_BODY_KEY = "sms_body";
    private static final String SMS_URI = "smsto:";

    public boolean initialize(Context context) {
        return super.initialize(context);
    }

    /* access modifiers changed from: protected */
    public void send2Intent(IMCallback<IMFriendResult> iMCallback, Intent shareIntent) {
    }

    /* access modifiers changed from: protected */
    public Intent initIntent() {
        return null;
    }

    public void sendMessage(IMFriendContent info, IMCallback<IMFriendResult> listener) {
        IMLogger.d("smsfriend sendMessage " + info.type);
        if (info == null) {
            IMRetCode.retByIMSDK(11, 11, "info is null", listener);
        } else if (info.type != 1) {
            IMRetCode.retByIMSDK(7, 7, "no support type" + info.type, listener);
        } else if (T.ckIsEmpty(info.content)) {
            IMRetCode.retByIMSDK(11, 11, "content is null", listener);
        } else {
            sendSMS(info, listener);
        }
    }

    private void sendSMS(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        if (this.currentContext == null) {
            IMLogger.w("currentContext is null, please check init first");
            if (callback != null) {
                callback.onError(new IMException(11, 17, 17));
            }
        } else if (content != null) {
            try {
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(SMS_URI));
                intent.putExtra(SMS_BODY_KEY, content.content);
                startActivity(this.currentContext, intent, callback);
            } catch (Exception e) {
                IMLogger.w("catch exception : " + e.getMessage());
                IMRetCode.retByIMSDK(3, 3, e.getMessage(), callback);
            }
        } else {
            IMLogger.w("content is null");
            IMRetCode.retByIMSDK(11, 11, "content is null", callback);
        }
    }

    private void startActivity(Context context, Intent intent, IMCallback<IMFriendResult> callback) {
        try {
            if (intent.resolveActivity(this.currentContext.getPackageManager()) != null) {
                if (context instanceof Activity) {
                    context.startActivity(intent);
                } else {
                    intent.setFlags(DriveFile.MODE_READ_ONLY);
                    context.startActivity(intent);
                }
                callback.onSuccess(new IMFriendResult(1, 1));
                return;
            }
            callback.onError(new IMException(15, 15));
        } catch (Exception e) {
            callback.onError(new IMException(3, 3));
        }
    }
}
