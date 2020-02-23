package com.tencent.imsdk.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.feedback.api.IMFeedback;
import com.tencent.imsdk.notice.api.IMNotice;
import com.tencent.imsdk.pay.api.IMPay;
import com.tencent.imsdk.push.api.IMPush;
import com.tencent.imsdk.sns.api.IMFriend;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.api.IMShare;
import com.tencent.imsdk.stat.api.IMStat;
import com.tencent.imsdk.webview.api.IMWebview;

public final class IMSDKApi {

    public static class Config extends IMConfig {
    }

    public static class Feedback extends IMFeedback {
    }

    public static class Friend extends IMFriend {
    }

    public static class Login extends IMLogin {
    }

    public static class Notice extends IMNotice {
    }

    public static class Pay extends IMPay {
    }

    public static class Push extends IMPush {
    }

    public static class Share extends IMShare {
    }

    public static class Stat extends IMStat {
    }

    public static class WebView extends IMWebview {
    }

    private IMSDKApi() {
    }

    public static boolean initialize(Context context) {
        IMSystem.getInstance().onCreate((Activity) context);
        return true;
    }

    public static void onCreate(Activity activity) {
        IMSystem.getInstance().onCreate(activity);
    }

    public static void onResume(Activity activity) {
        IMSystem.getInstance().onResume(activity);
    }

    public static void onPause(Activity activity) {
        IMSystem.getInstance().onPause(activity);
    }

    public static void onStop(Activity activity) {
        IMSystem.getInstance().onStop(activity);
    }

    public static void onDestroy(Activity activity) {
        IMSystem.getInstance().onDestroy(activity);
    }

    public static void onRestart(Activity activity) {
        IMSystem.getInstance().onRestart(activity);
    }

    public static void onNewIntent(Intent intent) {
        IMSystem.getInstance().onNewIntent(intent);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        IMSystem.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
