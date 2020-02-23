package com.tencent.imsdk.facebook.friend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.GameRequestDialog;
import com.facebook.share.widget.MessageDialog;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMProxyRunner;
import com.tencent.imsdk.IMProxyTask;
import com.tencent.imsdk.sns.api.IMLaunchHandler;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMBitmapTool;
import com.tencent.imsdk.sns.base.IMFriendBase;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendInfo;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLaunchResult;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.innerapi.IMInnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookFriend extends IMFriendBase implements IMLaunchHandler {
    private static final String STAT_ID = "friend_facebook";
    private final String FB_MESSENGER_LAUNCH_KEY = "target_url";
    private final String GRAPH_PATH_FRIENDS = "/me/invitable_friends";
    /* access modifiers changed from: private */
    public CallbackManager callbackManager = null;
    private IMInnerStat mInnerStat;
    /* access modifiers changed from: private */
    public GameRequestDialog requestDialog = null;

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
        this.callbackManager = CallbackManager.Factory.create();
        initStat(context);
        return super.initialize(context);
    }

    public void invite(final IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        reportEvent("invite", "start", "success", new Properties());
        IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
            boolean bActivityCallbackFlag = false;

            public void onPreProxy() {
                this.bActivityCallbackFlag = false;
            }

            public void onPostProxy(Activity activity) {
                GameRequestDialog unused = FacebookFriend.this.requestDialog = new GameRequestDialog(activity);
                FacebookFriend.this.requestDialog.registerCallback(FacebookFriend.this.callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        IMLogger.d("invite result : " + result.getRequestId());
                        if (result.getRequestId() == null) {
                            callback.onCancel();
                            FacebookFriend.this.reportEvent("invite", "facebook invite callback return", "cancel", new Properties());
                            return;
                        }
                        IMFriendResult friendResult = IMRetCode.rebuildForSuccess(new IMFriendResult(1));
                        if (result.getRequestRecipients() != null) {
                            friendResult.friendInfoList = new ArrayList();
                            for (String id : result.getRequestRecipients()) {
                                IMFriendInfo friendInfo = new IMFriendInfo();
                                friendInfo.channelUserId = id;
                                friendResult.friendInfoList.add(friendInfo);
                            }
                        }
                        callback.onSuccess(friendResult);
                        FacebookFriend.this.reportEvent("invite", "facebook invite callback return", "success", IMInnerStat.convertProperties((JsonSerializable) friendResult));
                    }

                    public void onCancel() {
                        callback.onCancel();
                        FacebookFriend.this.reportEvent("invite", "facebook invite callback return", "cancel", new Properties());
                    }

                    public void onError(FacebookException error) {
                        IMException exception = new IMException(3, error.getMessage());
                        IMRetCode.rebuild(exception, 9999, -1, error.getMessage(), (String) null);
                        callback.onError(exception);
                        FacebookFriend.this.reportEvent("invite", "facebook invite callback return", "error", IMInnerStat.convertProperties(exception));
                    }
                });
                GameRequestContent.Builder builder = new GameRequestContent.Builder();
                builder.setTitle(content.title);
                builder.setMessage(content.content);
                boolean isUserSetted = false;
                StringBuilder friendListSB = new StringBuilder();
                if (content.extraJson != null && content.extraJson.length() > 0) {
                    IMLogger.d("content.extraJson = " + content.extraJson);
                    try {
                        JSONObject json = new JSONObject(content.extraJson);
                        if (!json.has("invitetype") || !json.has("userlist")) {
                            IMLogger.w("ExtraJson not null, but no invitetype | userlist data setted in extraJson, exec default invite!");
                        } else {
                            JSONArray userListArr = json.getJSONArray("userlist");
                            for (int i = 0; i < userListArr.length(); i++) {
                                if (i != 0) {
                                    friendListSB.append(",");
                                }
                                friendListSB.append(userListArr.getString(i));
                            }
                            IMLogger.d("friendListString = " + friendListSB.toString());
                            if (friendListSB.length() > 0) {
                                builder.setTo(friendListSB.toString());
                                isUserSetted = true;
                            }
                        }
                    } catch (Exception e) {
                        IMLogger.e(e.toString());
                        IMException exception = new IMException(1002);
                        IMRetCode.rebuild(exception, 11, -1, (String) null, (String) null);
                        callback.onError(exception);
                        FacebookFriend.this.reportEvent("invite", "facebook invite Json error", "error", IMInnerStat.convertProperties(exception));
                        this.bActivityCallbackFlag = true;
                        activity.finish();
                        return;
                    }
                }
                if (!isUserSetted) {
                    builder.setFilters(GameRequestContent.Filters.APP_NON_USERS);
                }
                GameRequestContent content = builder.build();
                if (FacebookFriend.this.requestDialog.canShow(content)) {
                    FacebookFriend.this.requestDialog.show(content);
                    return;
                }
                IMException exception2 = new IMException(1007);
                IMRetCode.rebuild(exception2, 15, -1, (String) null, (String) null);
                callback.onError(exception2);
                FacebookFriend.this.reportEvent("invite", "facebook invite callback return", "error", IMInnerStat.convertProperties(exception2));
                this.bActivityCallbackFlag = true;
                activity.finish();
            }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                FacebookFriend.this.callbackManager.onActivityResult(requestCode, resultCode, data);
                this.bActivityCallbackFlag = true;
            }

            public void onDestroy() {
                super.onDestroy();
                FacebookFriend.this.activityDestroyWithoutCallback(this.bActivityCallbackFlag, "invite", callback);
                this.bActivityCallbackFlag = true;
            }
        });
    }

    public void sendText(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        IMException exception = new IMException(7);
        IMRetCode.rebuild(exception, 7, -1, (String) null, (String) null);
        callback.onError(exception);
        reportEvent("sendText", "facebook not support send text", "error", IMInnerStat.convertProperties(exception));
    }

    public void sendLink(final IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        reportEvent("sendLink", "start", "success", new Properties());
        IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
            boolean bActivityCallbackFlag = false;

            public void onPreProxy() {
            }

            public void onPostProxy(Activity activity) {
                MessageDialog dialog = new MessageDialog(activity);
                dialog.registerCallback(FacebookFriend.this.callbackManager, new FacebookCallback<Sharer.Result>() {
                    public void onSuccess(Sharer.Result result) {
                        IMLogger.d("send link message result : " + result.getPostId());
                        IMFriendResult ret = new IMFriendResult(1);
                        IMRetCode.rebuildForSuccess(ret);
                        callback.onSuccess(ret);
                        FacebookFriend.this.reportEvent("sendLink", "facebook sendLink callback return", "success", IMInnerStat.convertProperties((JsonSerializable) ret));
                    }

                    public void onCancel() {
                        callback.onCancel();
                        FacebookFriend.this.reportEvent("sendLink", "facebook sendLink callback return", "cancel", new Properties());
                    }

                    public void onError(FacebookException error) {
                        IMLogger.e("send message error : " + error.getMessage());
                        IMException exception = new IMException(3, error.getMessage());
                        IMRetCode.rebuild(exception, 9999, -1, error.getMessage(), (String) null);
                        callback.onError(exception);
                        FacebookFriend.this.reportEvent("sendLink", "facebook sendLink callback return", "error", IMInnerStat.convertProperties(exception));
                    }
                });
                ShareLinkContent.Builder builder = new ShareLinkContent.Builder();
                builder.setContentTitle(content.title);
                builder.setContentDescription(content.content);
                builder.setContentUrl(Uri.parse(content.link));
                if (content.imagePath != null && content.imagePath.length() > 0) {
                    builder.setImageUrl(Uri.parse(content.imagePath));
                }
                if (content.thumbPath != null && content.thumbPath.length() > 0) {
                    builder.setImageUrl(Uri.parse(content.thumbPath));
                }
                if (dialog.canShow(builder.build())) {
                    dialog.show(builder.build());
                    return;
                }
                IMException exception = new IMException(1007);
                IMRetCode.rebuild(exception, 15, -1, (String) null, (String) null);
                callback.onError(exception);
                FacebookFriend.this.reportEvent("sendLink", "facebook sendLink callback return", "error", IMInnerStat.convertProperties(new IMException(1007)));
                this.bActivityCallbackFlag = true;
                activity.finish();
            }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                this.bActivityCallbackFlag = true;
                FacebookFriend.this.callbackManager.onActivityResult(requestCode, resultCode, data);
            }

            public void onDestroy() {
                super.onDestroy();
                FacebookFriend.this.activityDestroyWithoutCallback(this.bActivityCallbackFlag, "sendLink", callback);
                this.bActivityCallbackFlag = true;
            }
        });
    }

    public void sendImage(final IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        reportEvent("sendImage", "start", "success", new Properties());
        IMProxyRunner.getInstance().startProxyTask(new IMProxyTask(this.currentContext) {
            boolean bActivityCallbackFlag = false;

            public void onPreProxy() {
            }

            public void onPostProxy(Activity activity) {
                MessageDialog dialog = new MessageDialog(activity);
                dialog.registerCallback(FacebookFriend.this.callbackManager, new FacebookCallback<Sharer.Result>() {
                    public void onSuccess(Sharer.Result result) {
                        IMLogger.d("send image message result : " + result.getPostId());
                        IMFriendResult ret = new IMFriendResult(1);
                        IMRetCode.rebuildForSuccess(ret);
                        callback.onSuccess(ret);
                        FacebookFriend.this.reportEvent("sendImage", "facebook sendImage callback return", "success", IMInnerStat.convertProperties((JsonSerializable) ret));
                    }

                    public void onCancel() {
                        callback.onCancel();
                        FacebookFriend.this.reportEvent("sendImage", "facebook sendImage callback return", "cancel", new Properties());
                    }

                    public void onError(FacebookException error) {
                        IMException exception = new IMException(3, error.getMessage());
                        IMRetCode.rebuild(exception, 9999, -1, error.getMessage(), (String) null);
                        callback.onError(exception);
                        FacebookFriend.this.reportEvent("sendImage", "facebook sendImage callback return", "error", IMInnerStat.convertProperties(new IMException(3, error.getMessage())));
                    }
                });
                SharePhotoContent.Builder builder = new SharePhotoContent.Builder();
                SharePhoto.Builder photoBuilder = new SharePhoto.Builder();
                if (IMBitmapTool.isHttpUrl(content.imagePath)) {
                    Uri uri = Uri.parse(content.imagePath);
                    if (uri != null) {
                        photoBuilder.setImageUrl(uri);
                    }
                } else {
                    photoBuilder.setBitmap(IMBitmapTool.createFromPath(activity, content.imagePath));
                }
                builder.addPhoto(photoBuilder.build());
                if (dialog.canShow(builder.build())) {
                    dialog.show(builder.build());
                    return;
                }
                IMException exception = new IMException(1007);
                IMRetCode.rebuild(exception, 15, -1, (String) null, (String) null);
                callback.onError(exception);
                FacebookFriend.this.reportEvent("sendImage", "facebook sendImage callback return", "error", IMInnerStat.convertProperties(exception));
                this.bActivityCallbackFlag = true;
                activity.finish();
            }

            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                FacebookFriend.this.callbackManager.onActivityResult(requestCode, resultCode, data);
                this.bActivityCallbackFlag = true;
            }

            public void onDestroy() {
                super.onDestroy();
                FacebookFriend.this.activityDestroyWithoutCallback(this.bActivityCallbackFlag, "sendImage", callback);
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
            this.mInnerStat.reportEvent(STAT_ID, false, function, stage, result, getStatOpenId(), properties);
        }
    }

    /* access modifiers changed from: protected */
    public void reportEvent(String function, String stage, String result, Properties properties, boolean encrypt) {
        if (this.mInnerStat != null) {
            this.mInnerStat.reportEvent(STAT_ID, false, function, stage, result, getStatOpenId(), properties, encrypt);
        }
    }

    private void initStat(Context context) {
        if (this.mInnerStat == null) {
            this.mInnerStat = new IMInnerStat(context, "1.14.0");
            this.mInnerStat.reportEvent(STAT_ID, true, "initialize", "create", "success", getStatOpenId(), IMInnerStat.convertProperties(context));
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

    public void getInviteFriends(final int page, int count, String extraJson, final IMCallback<IMFriendResult> callback) {
        IMLogger.d("getFriends page=" + page + "|count=" + count + "|extraJson" + extraJson);
        if (page <= 0 || count <= 0) {
            IMLogger.e("getFriends invalid argument, page=" + page + "|count=" + count);
            IMException exception = new IMException(11);
            IMRetCode.rebuild(exception, 11, -1, "page <= 0 || count <= 0", (String) null);
            callback.onError(exception);
            return;
        }
        GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/invitable_friends", new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                IMLogger.d("getFriends onCompleted: " + response.toString());
                if (response.getJSONObject() != null) {
                    FacebookFriend.this.parseFriendsJson(page, response.getJSONObject(), new ArrayList<>(), callback);
                    return;
                }
                IMLogger.e("getFriends onCompleted response = null");
                IMException exception = new IMException(9999, response.getError().getErrorMessage());
                IMRetCode.rebuild(exception, 9999, response.getError().getErrorCode(), response.getError().getErrorMessage(), (String) null);
                callback.onError(exception);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("limit", String.valueOf(count));
        request.setParameters(bundle);
        request.executeAsync();
    }

    /* access modifiers changed from: protected */
    public void callbackWithResult(ArrayList<IMFriendInfo> friendsList, IMCallback<IMFriendResult> callback) {
        IMFriendResult result = new IMFriendResult(1);
        IMRetCode.rebuildForSuccess(result);
        result.friendInfoList = friendsList;
        if (friendsList != null) {
            result.count = friendsList.size();
        }
        if (callback != null) {
            callback.onSuccess(result);
        }
    }

    /* access modifiers changed from: protected */
    public void parseFriendsJson(int page, JSONObject friendsJson, ArrayList<IMFriendInfo> friendsList, IMCallback<IMFriendResult> callback) {
        IMLogger.d("parseFriendsJson page=" + page + "|friendJson=" + friendsJson);
        if (friendsJson == null || !friendsJson.has(ShareConstants.WEB_DIALOG_PARAM_DATA)) {
            IMLogger.d("parseFriendsJson friendsJson null || friendsJson not contains data");
            callbackWithResult(friendsList, callback);
            return;
        }
        try {
            JSONArray jsonArray = friendsJson.getJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                IMFriendInfo friendInfo = new IMFriendInfo();
                if (jsonObject.has("id")) {
                    friendInfo.channelUserId = jsonObject.getString("id");
                }
                if (jsonObject.has("name")) {
                    friendInfo.guidUserNick = jsonObject.getString("name");
                }
                if (jsonObject.has("picture") && jsonObject.getJSONObject("picture").has(ShareConstants.WEB_DIALOG_PARAM_DATA) && jsonObject.getJSONObject("picture").getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).has("url")) {
                    friendInfo.guidUserPortrait = jsonObject.getJSONObject("picture").getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("url");
                }
                friendsList.add(friendInfo);
            }
            if (page == 1) {
                callbackWithResult(friendsList, callback);
                return;
            }
            final int tempPage = page - 1;
            friendsList.clear();
            if (!friendsJson.has("paging") || !friendsJson.getJSONObject("paging").has("next")) {
                IMLogger.d("parseFriendsJson no next, But tempPage>0, tempPage=" + tempPage);
                IMException exception = new IMException(11);
                IMRetCode.rebuild(exception, 11, -1, "page conflict next, error page|count argument!!!", (String) null);
                callback.onError(exception);
                return;
            }
            String get_next_url = friendsJson.getJSONObject("paging").getString("next");
            IMLogger.d("get_next_url=" + get_next_url);
            final ArrayList<IMFriendInfo> arrayList = friendsList;
            final IMCallback<IMFriendResult> iMCallback = callback;
            httpClient.get(get_next_url, (Map<String, String>) null, new IMCallback<String>() {
                public void onSuccess(String result) {
                    try {
                        FacebookFriend.this.parseFriendsJson(tempPage, new JSONObject(result), arrayList, iMCallback);
                    } catch (JSONException e) {
                        IMLogger.w("parseFriendsJson httpClient get catch error:" + e.getMessage());
                        FacebookFriend.this.callbackWithResult(arrayList, iMCallback);
                    }
                }

                public void onCancel() {
                    IMLogger.w("parseFriendsJson httpClient onCancel");
                    FacebookFriend.this.callbackWithResult(arrayList, iMCallback);
                }

                public void onError(IMException exception) {
                    IMLogger.w("parseFriendsJson httpClient get error" + exception.getMessage());
                    FacebookFriend.this.callbackWithResult(arrayList, iMCallback);
                }
            });
        } catch (JSONException e) {
            IMLogger.w("parseFriendsJson catch error:" + e.getMessage());
            callbackWithResult(friendsList, callback);
        }
    }

    public void handleIntent(Intent intent, IMCallback<IMLaunchResult> callback) {
        Uri realUri;
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                IMLogger.d("handle uri : " + uri.toString());
                IMLaunchResult launchResult = new IMLaunchResult(1, 1);
                JSONObject jsonObject = new JSONObject();
                for (String key : uri.getQueryParameterNames()) {
                    try {
                        jsonObject.put(key, uri.getQueryParameter(key));
                    } catch (Exception e) {
                        IMLogger.w("put uri parameter " + key + " failed : " + e.getMessage());
                    }
                    if (key.equals("target_url") && (realUri = Uri.parse(uri.getQueryParameter(key))) != null) {
                        for (String realKey : realUri.getQueryParameterNames()) {
                            try {
                                jsonObject.put(realKey, realUri.getQueryParameter(realKey));
                            } catch (Exception e2) {
                                IMLogger.w("put uri parameter " + key + " failed : " + e2.getMessage());
                            }
                        }
                    }
                }
                launchResult.launchUri = uri.toString();
                launchResult.launchData = jsonObject.toString();
                callback.onSuccess(launchResult);
                return;
            }
            IMLogger.w("uri is null");
            callback.onError(new IMException(3, 11));
            return;
        }
        IMLogger.w("intent is null");
        callback.onError(new IMException(3, 11));
    }
}
