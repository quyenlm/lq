package com.tencent.imsdk.facebook.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMProxyRunner;
import com.tencent.imsdk.IMProxyTask;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.sns.base.IMShareBase;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

public class FacebookShare extends IMShareBase {
    private final String AT_FRIENDS = "friends";
    private final String STAT_ID = "share_facebook";
    private final String VERSION = "1.12.1";
    private IMInnerStat mInnerStat;

    /* access modifiers changed from: private */
    public void callbackByResult(@Nullable IMCallback<IMResult> callback, IMResult result) {
        if (callback != null) {
            callback.onSuccess(result);
        } else {
            IMLogger.w("callback is null");
        }
    }

    private void callbackByCancel(@Nullable IMCallback<IMResult> callback) {
        if (callback != null) {
            callback.onCancel();
        } else {
            IMLogger.w("callback is null");
        }
    }

    /* access modifiers changed from: private */
    public void callbackByException(@Nullable IMCallback<IMResult> callback, IMException exception) {
        if (callback != null) {
            callback.onError(exception);
        } else {
            IMLogger.w("callback is null");
        }
    }

    public boolean initialize(final Context context) {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(context, (FacebookSdk.InitializeCallback) new FacebookSdk.InitializeCallback() {
                public void onInitialized() {
                    try {
                        if (context instanceof Activity) {
                            AppEventsLogger.activateApp((Context) ((Activity) context).getApplication());
                        }
                    } catch (Exception e) {
                        IMLogger.w("active facebook automatic logging failed : " + e.getMessage());
                    }
                }
            });
        }
        initStat(context);
        return super.initialize(context);
    }

    /* access modifiers changed from: private */
    public List<String> GetPeople(JSONObject extras) {
        List<String> peoples = new ArrayList<>();
        if (extras != null && extras.has("friends")) {
            try {
                JSONArray list = extras.getJSONArray("friends");
                for (int i = 0; i < list.length(); i++) {
                    String id = list.getString(i);
                    IMLogger.d("add receiver : " + id);
                    peoples.add(id);
                }
            } catch (Exception e) {
                IMLogger.e("add receiver error : " + e.getMessage());
            }
        }
        return peoples;
    }

    public void shareText(String title, String content, JSONObject extras, final IMCallback<IMResult> callback) {
        IMLogger.d("in share text : " + title + ", " + content);
        reportEvent("shareText", "start", "success", IMInnerStat.convertProperties(title + ", " + content));
        if (AccessToken.getCurrentAccessToken() == null || AccessToken.getCurrentAccessToken().isExpired() || !AccessToken.getCurrentAccessToken().getPermissions().contains("publish_actions")) {
            IMLogger.e("share text need login with publish_actions permission !");
            IMException exception = IMRetCode.rebuild(new IMException(1001, "share need login with publish_actions permission !"), 12, -1, "share need publish_actions permission", (String) null);
            callbackByException(callback, exception);
            reportEvent("shareText", "facebook shareText callback", "error", IMInnerStat.convertProperties(exception));
            return;
        }
        Bundle params = new Bundle();
        params.putString("message", content);
        String tagString = "";
        List<String> peoples = GetPeople(extras);
        for (int i = 0; i < peoples.size(); i++) {
            if (i != 0) {
                tagString = tagString + ",";
            }
            tagString = tagString + peoples.get(i);
        }
        if (tagString.length() > 0) {
            params.putString("tags", tagString);
        }
        final GraphRequest request = new GraphRequest();
        request.setAccessToken(AccessToken.getCurrentAccessToken());
        request.setGraphPath("/me/feed");
        request.setHttpMethod(HttpMethod.POST);
        request.setParameters(params);
        request.setCallback(new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                IMLogger.d("share test result : " + response.toString());
                try {
                    if ((response.getJSONObject() == null || response.getJSONObject().get("id") == null) && response.getError().getErrorCode() != 506) {
                        IMLogger.e(response.getError().toString());
                        IMException exception = IMRetCode.rebuild(new IMException(4, response.getError().getErrorMessage()), 9999, response.getError().getErrorCode(), response.getError().getErrorMessage(), (String) null);
                        callback.onError(exception);
                        FacebookShare.this.reportEvent("shareText", "facebook shareText callback", "error", IMInnerStat.convertProperties(exception));
                        return;
                    }
                    IMLogger.d("response json : " + response.getJSONObject());
                    IMResult result = new IMResult(1);
                    result.retMsg = "SUCCESS";
                    IMResult result2 = IMRetCode.rebuildForSuccess(result);
                    FacebookShare.this.callbackByResult(callback, result2);
                    FacebookShare.this.reportEvent("shareText", "facebook shareText callback", "success", IMInnerStat.convertProperties((JsonSerializable) result2));
                } catch (Exception e) {
                    IMException exception2 = IMRetCode.rebuild(new IMException(3, e.getMessage()), 3, -1, e.getMessage(), (String) null);
                    FacebookShare.this.callbackByException(callback, exception2);
                    FacebookShare.this.reportEvent("shareText", "facebook shareText callback", "error", IMInnerStat.convertProperties(exception2));
                }
            }
        });
        ((Activity) this.currentContext).runOnUiThread(new Runnable() {
            public void run() {
                IMLogger.d("start facebook text share");
                request.executeAsync();
            }
        });
    }

    public void shareTextDialog(String title, String content, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.d("in share text dialog : " + title + ", " + content);
        IMException exception = IMRetCode.rebuild(new IMException(7, "not support"), 7, -1, (String) null, (String) null);
        callbackByException(callback, exception);
        reportEvent("shareTextDialog", "facebook not support shareTextDialog", "error", IMInnerStat.convertProperties(exception));
    }

    public void shareLink(String link, String title, String description, String imgUrl, JSONObject extras, final IMCallback<IMResult> callback) {
        reportEvent("shareLink", "start", "success", new Properties());
        IMLogger.d("in share link : " + link + ", " + title + ", " + description + ", " + imgUrl);
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
        builder.setContentUrl(Uri.parse(link));
        builder.setContentTitle(title);
        builder.setImageUrl(Uri.parse(imgUrl));
        builder.setContentDescription(description);
        builder.setPeopleIds(GetPeople(extras));
        final ShareLinkContent content = builder.build();
        ((Activity) this.currentContext).runOnUiThread(new Runnable() {
            public void run() {
                IMLogger.d("start facebook link share");
                ShareApi.share(content, new FacebookCallback<Sharer.Result>() {
                    public void onSuccess(Sharer.Result result) {
                        if (callback != null) {
                            IMResult shareResult = new IMResult(1);
                            shareResult.retMsg = "SUCCESS";
                            IMResult shareResult2 = IMRetCode.rebuildForSuccess(shareResult);
                            callback.onSuccess(shareResult2);
                            FacebookShare.this.reportEvent("shareLink", "facebook shareLink callback", "success", IMInnerStat.convertProperties((JsonSerializable) shareResult2));
                        }
                    }

                    public void onCancel() {
                        if (callback != null) {
                            callback.onCancel();
                            FacebookShare.this.reportEvent("shareLink", "facebook shareLink callback", "cancel", new Properties());
                        }
                    }

                    public void onError(FacebookException error) {
                        if (callback != null) {
                            IMException exception = IMRetCode.rebuild(new IMException(3, error.getMessage()), 9999, -1, error.getMessage(), (String) null);
                            callback.onError(exception);
                            FacebookShare.this.reportEvent("shareLink", "facebook shareLink callback", "error", IMInnerStat.convertProperties(exception));
                        }
                    }
                });
            }
        });
    }

    public void shareLinkDialog(String link, String title, String description, String imgUrl, JSONObject extras, IMCallback<IMResult> callback) {
        reportEvent("shareLinkDialog", "start", "success", new Properties());
        IMLogger.d("in share link dialog : " + link + ", " + title + ", " + this.currentContext + ", " + imgUrl);
        final String str = link;
        final String str2 = title;
        final String str3 = imgUrl;
        final String str4 = description;
        final JSONObject jSONObject = extras;
        final IMCallback<IMResult> iMCallback = callback;
        IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
            boolean bActivityCallbackFlag = false;
            private CallbackManager mCallbackManager = null;

            public void onPreProxy() {
                this.bActivityCallbackFlag = false;
                this.mCallbackManager = CallbackManager.Factory.create();
            }

            public void onPostProxy(Activity activity) {
                IMLogger.d("in share link dialog");
                ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
                builder.setContentUrl(Uri.parse(str));
                builder.setContentTitle(str2);
                if (str3 != null && str3.length() > 0) {
                    builder.setImageUrl(Uri.parse(str3));
                }
                builder.setContentDescription(str4);
                builder.setPeopleIds(FacebookShare.this.GetPeople(jSONObject));
                ShareLinkContent content = builder.build();
                ShareDialog dialog = new ShareDialog(activity);
                if (iMCallback != null) {
                    IMLogger.d("register call back");
                    dialog.registerCallback(this.mCallbackManager, new FacebookCallback<Sharer.Result>() {
                        public void onSuccess(Sharer.Result result) {
                            if (result == null) {
                                IMLogger.d("facebook image Sharer.Result is null");
                            } else {
                                IMLogger.d("facebook image post id : " + result.getPostId());
                            }
                            IMResult shareResult = new IMResult(1);
                            shareResult.retMsg = "SUCCESS";
                            IMRetCode.rebuildForSuccess(shareResult);
                            iMCallback.onSuccess(shareResult);
                            FacebookShare.this.reportEvent("shareLinkDialog", "facebook shareLinkDialog callback", "success", IMInnerStat.convertProperties((JsonSerializable) shareResult));
                        }

                        public void onCancel() {
                            IMLogger.d("facebook share image cancel");
                            iMCallback.onCancel();
                        }

                        public void onError(FacebookException error) {
                            IMLogger.d("facebook share image get error : " + error.getMessage());
                            IMException exception = IMRetCode.rebuild(new IMException(3, error.getMessage()), 9999, -1, error.getMessage(), (String) null);
                            iMCallback.onError(exception);
                            FacebookShare.this.reportEvent("shareLinkDialog", "facebook shareLinkDialog callback", "error", IMInnerStat.convertProperties(exception));
                        }
                    });
                }
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    dialog.show(content);
                    IMLogger.d("show share link dialog");
                    return;
                }
                IMLogger.e("ShareLinkContent cannot be shown !");
                IMException exception = IMRetCode.rebuild(new IMException(1007), 15, -1, (String) null, (String) null);
                iMCallback.onError(exception);
                FacebookShare.this.reportEvent("shareLinkDialog", "facebook shareLinkDialog callback", "error", IMInnerStat.convertProperties(exception));
                this.bActivityCallbackFlag = true;
                activity.finish();
            }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                this.bActivityCallbackFlag = true;
                this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }

            public void onDestroy() {
                super.onDestroy();
                FacebookShare.this.activityDestroyWithoutCallback(this.bActivityCallbackFlag, "shareLinkDialog", iMCallback);
                this.bActivityCallbackFlag = true;
            }
        });
    }

    /* access modifiers changed from: private */
    public Bitmap getBitmapFromURL(Uri src) {
        try {
            Bitmap retBitmap = BitmapFactory.decodeStream(new URL(src.toString()).openConnection().getInputStream());
            if (retBitmap != null) {
                return retBitmap;
            }
            return null;
        } catch (Exception e) {
            IMLogger.d("get image from net error : " + e.getMessage());
        }
    }

    public void shareImage(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        reportEvent("shareImage", "start", "success", new Properties());
        IMLogger.d("in share image uri : " + imgUri.toString() + ", " + title + ", " + description);
        final Uri uri = imgUri;
        final String str = title;
        final String str2 = description;
        final JSONObject jSONObject = extras;
        final IMCallback<IMResult> iMCallback = callback;
        new Thread(new Runnable() {
            public void run() {
                Bitmap image = FacebookShare.this.getBitmapFromURL(uri);
                if (image != null) {
                    FacebookShare.this.shareImage(image, str, str2, jSONObject, (IMCallback<IMResult>) iMCallback);
                } else {
                    ((Activity) FacebookShare.this.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            IMException exception = IMRetCode.rebuild(new IMException(4, "get image form net error"), 4, -1, (String) null, (String) null);
                            iMCallback.onError(exception);
                            FacebookShare.this.reportEvent("shareImage", "facebook shareImage callback", "error", IMInnerStat.convertProperties(exception));
                        }
                    });
                }
            }
        }).start();
    }

    public void shareImageDialog(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.d("in share image bitmap : bitmap, " + title + ", " + description);
        reportEvent("shareImageDialog", "start", "success", new Properties());
        final Uri uri = imgUri;
        final String str = title;
        final String str2 = description;
        final JSONObject jSONObject = extras;
        final IMCallback<IMResult> iMCallback = callback;
        new Thread(new Runnable() {
            public void run() {
                Bitmap image = FacebookShare.this.getBitmapFromURL(uri);
                if (image != null) {
                    FacebookShare.this.shareImageDialog(image, str, str2, jSONObject, (IMCallback<IMResult>) iMCallback);
                } else {
                    ((Activity) FacebookShare.this.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            IMException exception = IMRetCode.rebuild(new IMException(4, "get image form net error"), 4, -1, (String) null, (String) null);
                            iMCallback.onError(exception);
                            FacebookShare.this.reportEvent("shareImageDialog", "facebook shareImageDialog callback", "error", IMInnerStat.convertProperties(exception));
                        }
                    });
                }
            }
        }).start();
    }

    public void shareImage(Bitmap bitmap, String title, String description, JSONObject extras, final IMCallback<IMResult> callback) {
        reportEvent("shareImage", "start", "success", new Properties());
        IMLogger.d("in share image bitmap : bitmap, " + title + ", " + description);
        SharePhoto.Builder photoBuilder = new SharePhoto.Builder();
        photoBuilder.setBitmap(bitmap);
        SharePhotoContent.Builder contentBuilder = new SharePhotoContent.Builder();
        contentBuilder.addPhoto(photoBuilder.build());
        contentBuilder.setPeopleIds(GetPeople(extras));
        final SharePhotoContent content = contentBuilder.build();
        ((Activity) this.currentContext).runOnUiThread(new Runnable() {
            public void run() {
                IMLogger.d("start share bitmap ... ");
                ShareApi.share(content, new FacebookCallback<Sharer.Result>() {
                    public void onSuccess(Sharer.Result result) {
                        IMLogger.d("share bitmap success with post id : " + result.getPostId());
                        IMResult ret = new IMResult(1);
                        ret.retMsg = "SUCCESS";
                        IMRetCode.rebuildForSuccess(ret);
                        callback.onSuccess(ret);
                        FacebookShare.this.reportEvent("shareImage", "facebook share bitmap callback", "success", IMInnerStat.convertProperties((JsonSerializable) ret));
                    }

                    public void onCancel() {
                        IMLogger.d("share bitmap cancel");
                        callback.onCancel();
                        FacebookShare.this.reportEvent("shareImage", "facebook share bitmap callback", "cancel", new Properties());
                    }

                    public void onError(FacebookException error) {
                        IMLogger.d("share bitmap error : " + error.getMessage());
                        IMException exception = IMRetCode.rebuild(new IMException(3, error.getMessage()), 9999, -1, error.getMessage(), (String) null);
                        callback.onError(exception);
                        FacebookShare.this.reportEvent("shareImage", "facebook share bitmap callback", "error", IMInnerStat.convertProperties(exception));
                    }
                });
            }
        });
    }

    public void shareImageDialog(Bitmap bitmap, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.d("in share image bitmap dialog : bitmap, " + title + ", " + description);
        reportEvent("shareImageDialog", "start", "success", new Properties());
        final Bitmap bitmap2 = bitmap;
        final JSONObject jSONObject = extras;
        final IMCallback<IMResult> iMCallback = callback;
        IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
            boolean bActivityCallbackFlag = false;
            CallbackManager mCallbackManager = null;

            public void onPreProxy() {
                this.bActivityCallbackFlag = false;
                this.mCallbackManager = CallbackManager.Factory.create();
            }

            public void onPostProxy(Activity activity) {
                SharePhoto.Builder photoBuilder = new SharePhoto.Builder();
                photoBuilder.setBitmap(bitmap2);
                SharePhotoContent.Builder contentBuilder = new SharePhotoContent.Builder();
                contentBuilder.addPhoto(photoBuilder.build());
                contentBuilder.setPeopleIds(FacebookShare.this.GetPeople(jSONObject));
                SharePhotoContent content = contentBuilder.build();
                ShareDialog dialog = new ShareDialog(activity);
                if (iMCallback != null) {
                    IMLogger.d("register call back");
                    dialog.registerCallback(this.mCallbackManager, new FacebookCallback<Sharer.Result>() {
                        public void onSuccess(Sharer.Result result) {
                            IMResult shareResult = new IMResult(1);
                            shareResult.retMsg = "SUCCESS";
                            IMRetCode.rebuildForSuccess(shareResult);
                            iMCallback.onSuccess(shareResult);
                            FacebookShare.this.reportEvent("shareLinkDialog", "facebook share bitmap dialog callback", "success", IMInnerStat.convertProperties((JsonSerializable) shareResult));
                        }

                        public void onCancel() {
                            iMCallback.onCancel();
                            FacebookShare.this.reportEvent("shareLinkDialog", "facebook share bitmap dialog callback", "cancel", new Properties());
                        }

                        public void onError(FacebookException error) {
                            IMException exception = IMRetCode.rebuild(new IMException(3, error.getMessage()), 9999, -1, error.getMessage(), (String) null);
                            iMCallback.onError(exception);
                            FacebookShare.this.reportEvent("shareLinkDialog", "facebook share bitmap dialog callback", "error", IMInnerStat.convertProperties(exception));
                        }
                    });
                }
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    IMLogger.d("show share dialog");
                    dialog.show(content);
                    return;
                }
                IMLogger.e("SharePhotoContent cannot be shown !");
                IMException exception = IMRetCode.rebuild(new IMException(1007), 15, -1, (String) null, (String) null);
                iMCallback.onError(exception);
                FacebookShare.this.reportEvent("shareLinkDialog", "facebook share bitmap dialog callback", "error", IMInnerStat.convertProperties(exception));
                activity.finish();
                this.bActivityCallbackFlag = true;
            }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                this.bActivityCallbackFlag = true;
                this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }

            public void onDestroy() {
                super.onDestroy();
                FacebookShare.this.activityDestroyWithoutCallback(this.bActivityCallbackFlag, "shareImageDialog", iMCallback);
                this.bActivityCallbackFlag = true;
            }
        });
    }

    /* access modifiers changed from: private */
    public void activityDestroyWithoutCallback(boolean bActivityCallbackFlag, String function, IMCallback callback) {
        if (!bActivityCallbackFlag && callback != null) {
            IMException exception = new IMException(2, 2, -1);
            callback.onError(exception);
            reportEvent(function, "activity destroy without callback", "cancel", IMInnerStat.convertProperties(exception));
        }
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent("share_facebook", false, function, stage, result, getStatOpenId(), properties);
        }
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties, boolean encrypt) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent("share_facebook", false, function, stage, result, getStatOpenId(), properties, encrypt);
        }
    }

    private void initStat(Context context) {
        if (this.mInnerStat == null) {
            this.mInnerStat = new IMInnerStat(context, "1.12.1");
            this.mInnerStat.reportEvent("share_facebook", true, "initialize", "create", "success", getStatOpenId(), IMInnerStat.convertProperties(context));
        }
    }

    /* access modifiers changed from: protected */
    public String getStatOpenId() {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult != null) {
            return loginResult.openId;
        }
        return "";
    }
}
