package com.tencent.imsdk.garena.friend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPhotoShare;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.GGTextShare;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.ndk.ShareRet;
import com.facebook.share.internal.ShareConstants;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.extend.garena.base.ExtendGarenaManager;
import com.tencent.imsdk.sns.api.IMFriend;
import com.tencent.imsdk.sns.api.IMLaunchHandler;
import com.tencent.imsdk.sns.base.IMBitmapTool;
import com.tencent.imsdk.sns.base.IMFriendBase;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLaunchResult;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class GarenaFriend extends IMFriendBase implements IMLaunchHandler {
    private static final String FB_MESSENGER_LAUNCH_KEY = "target_url";
    private static final String META_APPLICATION_ID = "com.garena.sdk.applicationId";
    private static final String META_APP_SDK_KEY = "com.tencent.imsdk.garena.APP_SDK_KEY";
    private static final String RETURN_ERROR_MSG_TAG = "share error,";
    private static final String VERSION = "1.16.0";
    /* access modifiers changed from: private */
    public String APP_SDK_ASSIGN_ID = "";
    private String APP_SDK_KEY = "";
    private final String PLUGIN_NAME = "imsdkgarena";
    /* access modifiers changed from: private */
    public Activity mContext;
    /* access modifiers changed from: private */
    public IMException mException = null;
    private InnerStat.Builder mSTBuilder;

    public interface IMGetBitmapImageCallBack {
        void onFail(String str);

        void onSuccess(Bitmap bitmap);
    }

    public boolean initialize(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                this.mContext = (Activity) context;
            }
            this.APP_SDK_ASSIGN_ID = String.valueOf(MetaDataUtil.readMetaIntFromApplication(context, "com.garena.sdk.applicationId"));
            IMLogger.d("APP_SDK_ASSIGN_ID:" + this.APP_SDK_ASSIGN_ID);
            this.APP_SDK_KEY = MetaDataUtil.readMetaDataFromApplication(context, META_APP_SDK_KEY);
            IMLogger.d("APP_SDK_KEY:" + this.APP_SDK_KEY);
            this.mSTBuilder = new InnerStat.Builder(context, "1.16.0", GGPlatform.GGGetSDKVersion(), "imsdkgarena");
        }
        return super.initialize(context);
    }

    public void invite(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        showLogIMFriendContentInfo("invite", content);
        ExtendGarenaManager.getInstance().setIMSDKFriendCallback(getSessionProviderSubChannel(), "invite", callback);
        if (content.user == null || content.user.length() <= 0) {
            GGPlatform.GGSendRequestInvitationToFacebook(this.mContext, content.title, content.content, ExtendGarenaManager.getInstance().getGarenaCallback());
            return;
        }
        try {
            GGPlatform.GGSendGameRequestToFacebookUser(this.mContext, Long.valueOf(content.user).longValue(), content.title, content.content, "", ExtendGarenaManager.getInstance().getGarenaCallback());
        } catch (NumberFormatException e) {
            IMLogger.d("invalid fb userId format : " + content.user);
            callback.onError(new IMException(3, IMRetCode.INVALID_ARGUMENT));
        }
    }

    public void sendImage(final IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        showLogIMFriendContentInfo("sendImage", content);
        final String currentPlatform = getSessionProviderSubChannel();
        ArrayList<String> supportList = new ArrayList<>();
        supportList.add("GRN_Gas");
        if (!checkSubChannelIsSupport(supportList)) {
            IMLogger.e(currentPlatform + " not support sendImage");
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatform + " not support sendImage");
            callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "sendImage", "check support"));
            return;
        }
        if (TextUtils.isEmpty(content.imagePath)) {
            this.mException = new IMException(3, "share error,imagePath is empty");
            callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "imagePath is empty", (String) null, true, "sendImage", "check argument"));
        }
        if (this.mContext != null) {
            this.mContext.runOnUiThread(new Runnable() {
                public void run() {
                    if (content.imagePath.startsWith(IMSDKFileProvider.FILE_SCHEME)) {
                        content.imagePath = content.imagePath.substring(IMSDKFileProvider.FILE_SCHEME.length(), content.imagePath.length());
                        IMLogger.d("sub imagePath:" + content.imagePath);
                    }
                    GGPhotoShare.Builder builder = new GGPhotoShare.Builder(0, content.imagePath, Integer.valueOf(GarenaFriend.this.APP_SDK_ASSIGN_ID).intValue());
                    String mediaTagName = GarenaFriend.this.parseFromJsonWithCallback(content.extraJson, "mediaTagName", callback);
                    if (!TextUtils.isEmpty(mediaTagName)) {
                        builder.setMediaTag(mediaTagName);
                        builder.setMessageAction("");
                        builder.setMessageExt(content.content);
                        ExtendGarenaManager.getInstance().setIMSDKFriendCallback(currentPlatform, "sendImage", callback);
                        GGPlatform.GGSendMediaToSession(GarenaFriend.this.mContext, builder.build(), ExtendGarenaManager.getInstance().getGarenaCallback());
                    }
                }
            });
        }
    }

    public void sendLink(IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        showLogIMFriendContentInfo("sendLink", content);
        final String currentPlatform = getSessionProviderSubChannel();
        ArrayList<String> supportList = new ArrayList<>();
        supportList.add("GRN_Gas");
        supportList.add("GRN_FB");
        if (!checkSubChannelIsSupport(supportList)) {
            IMLogger.e(currentPlatform + " not support sendLink");
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatform + " not support sendLink");
            callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "sendLink", "check support"));
            return;
        }
        String mediaTagName = parseFromJsonWithCallback(content.extraJson, "mediaTagName", callback);
        if (!TextUtils.isEmpty(mediaTagName)) {
            String caption = parseFromJsonWithCallback(content.extraJson, ShareConstants.FEED_CAPTION_PARAM, callback);
            if (!TextUtils.isEmpty(caption)) {
                ExtendGarenaManager.getInstance().setIMSDKFriendCallback(currentPlatform, "sendLink", callback);
                GGPlatform.GGSendLinkToSession(this.mContext, 0, mediaTagName, content.link, content.title, caption, content.content, content.thumbPath, new GGPlatform.ShareCallback() {
                    public void onPluginResult(ShareRet result) {
                        GarenaFriend.this.parsePluginResultWithCallback(currentPlatform, "sendLink", result, callback);
                    }
                });
            }
        }
    }

    public void sendText(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        showLogIMFriendContentInfo("sendText", content);
        String currentPlatform = getSessionProviderSubChannel();
        ArrayList<String> supportList = new ArrayList<>();
        supportList.add("GRN_Gas");
        if (!checkSubChannelIsSupport(supportList)) {
            IMLogger.e(currentPlatform + " not support sendText");
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatform + " not support send icon and text to platform app’s chat session");
            callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "sendText", "check support"));
            return;
        }
        final String mediaTagName = parseFromJsonWithCallback(content.extraJson, "mediaTagName", callback);
        if (!TextUtils.isEmpty(mediaTagName)) {
            final IMFriendContent iMFriendContent = content;
            final String str = currentPlatform;
            final IMCallback<IMFriendResult> iMCallback = callback;
            this.mContext.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        IMLogger.d("thumbPath:" + iMFriendContent.thumbPath);
                        if (iMFriendContent.type != 1) {
                            return;
                        }
                        if (!TextUtils.isEmpty(iMFriendContent.thumbPath)) {
                            GarenaFriend.this.getBitmapFromURL(Uri.parse(iMFriendContent.thumbPath), new IMGetBitmapImageCallBack() {
                                public void onSuccess(final Bitmap icon) {
                                    if (GarenaFriend.this.mContext != null) {
                                        GarenaFriend.this.mContext.runOnUiThread(new Runnable() {
                                            public void run() {
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                try {
                                                    icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                    GGTextShare.Builder builder = new GGTextShare.Builder(stream.toByteArray(), Integer.valueOf(GarenaFriend.this.APP_SDK_ASSIGN_ID).intValue());
                                                    builder.setDesc(iMFriendContent.content);
                                                    builder.setMediaTag(mediaTagName);
                                                    builder.setTitle(iMFriendContent.title);
                                                    String link = iMFriendContent.link;
                                                    if (!TextUtils.isEmpty(link)) {
                                                        builder.setUrl(link);
                                                    } else {
                                                        builder.setUrl("http://www.garena.sg/");
                                                    }
                                                    ExtendGarenaManager.getInstance().setIMSDKFriendCallback(str, "sendMessage", iMCallback);
                                                    GGPlatform.GGSendGameToSession(GarenaFriend.this.mContext, builder.build(), ExtendGarenaManager.getInstance().getGarenaCallback());
                                                    if (stream != null) {
                                                        try {
                                                            stream.close();
                                                        } catch (IOException e) {
                                                            IMLogger.e(e.getMessage());
                                                        }
                                                    }
                                                } catch (Exception e2) {
                                                    IMLogger.e(e2.getMessage());
                                                    if (stream != null) {
                                                        try {
                                                            stream.close();
                                                        } catch (IOException e3) {
                                                            IMLogger.e(e3.getMessage());
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    if (stream != null) {
                                                        try {
                                                            stream.close();
                                                        } catch (IOException e4) {
                                                            IMLogger.e(e4.getMessage());
                                                        }
                                                    }
                                                    throw th;
                                                }
                                            }
                                        });
                                    }
                                }

                                public void onFail(final String errorMessage) {
                                    if (GarenaFriend.this.mContext != null) {
                                        GarenaFriend.this.mContext.runOnUiThread(new Runnable() {
                                            public void run() {
                                                IMException unused = GarenaFriend.this.mException = new IMException(3, GarenaFriend.RETURN_ERROR_MSG_TAG + errorMessage);
                                                iMCallback.onError(GarenaFriend.this.rebuild(GarenaFriend.this.mException, IMRetCode.SYSTEM_ERROR, -1, errorMessage, (String) null, true, "sendText", "getBitmapFromURL onFail"));
                                            }
                                        });
                                    }
                                }
                            });
                            return;
                        }
                        IMException unused = GarenaFriend.this.mException = new IMException(3, "share error,thumbPath cannot empty");
                        iMCallback.onError(GarenaFriend.this.rebuild(GarenaFriend.this.mException, IMRetCode.INVALID_ARGUMENT, -1, "thumbPath cannot empty", (String) null, true, "sendText", "getBitmapFromURL onFail"));
                    } catch (Exception e) {
                        iMCallback.onError(new IMException(3, GarenaFriend.RETURN_ERROR_MSG_TAG + e.getMessage()));
                        IMLogger.e(e.getMessage());
                    }
                }
            });
        }
    }

    public void sendMessage(final IMFriendContent content, final IMCallback<IMFriendResult> callback) {
        if (content.type > -1) {
            switch (content.type) {
                case 1:
                    sendText(content, callback);
                    return;
                case 2:
                    IMLogger.w("GarenaFriend not support sendLink");
                    this.mException = new IMException(3, "share error,GarenaFriend not support sendLink");
                    callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, "", ""));
                    return;
                case 3:
                    sendLink(content, callback);
                    return;
                case 4:
                    IMLogger.w("GarenaFriend not support sendImage");
                    this.mException = new IMException(3, "share error,GarenaFriend not support sendImage");
                    callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, "", ""));
                    return;
                case 5:
                    sendImage(content, callback);
                    return;
            }
        }
        showLogIMFriendContentInfo("sendMessage", content);
        final String currentPlatform = getSessionProviderSubChannel();
        ArrayList<String> supportList = new ArrayList<>();
        supportList.add("GRN_Gas");
        supportList.add("GRN_FB");
        if (!checkSubChannelIsSupport(supportList)) {
            IMLogger.e(currentPlatform + " not support sendMessage");
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatform + " not support sendMessage");
            callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, "", ""));
            return;
        }
        this.mContext.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    IMLogger.d("thumbPath:" + content.thumbPath);
                    try {
                        String fbObjectId = GarenaFriend.this.parseFromJsonWithCallback(content.extraJson, "fbObjectId", callback);
                        if (!TextUtils.isEmpty(fbObjectId)) {
                            String[] buddyArray = content.user.split(",");
                            ArrayList<String> buddies = new ArrayList<>();
                            Collections.addAll(buddies, buddyArray);
                            ExtendGarenaManager.getInstance().setIMSDKFriendCallback(currentPlatform, "sendMessage", callback);
                            GGPlatform.GGSendToGameFriends(GarenaFriend.this.mContext, content.title, content.content, content.thumbPath, (String) null, fbObjectId, buddies, ExtendGarenaManager.getInstance().getGarenaCallback());
                        }
                    } catch (Exception e) {
                        IMLogger.e(e.getMessage());
                        IMException unused = GarenaFriend.this.mException = new IMException(GarenaFriend.RETURN_ERROR_MSG_TAG + e.getMessage());
                        callback.onError(GarenaFriend.this.rebuild(GarenaFriend.this.mException, IMRetCode.SYSTEM_ERROR, -1, e.getMessage(), (String) null, true, "sendMessage", "GGSendToGameFriends"));
                    }
                } catch (Exception e2) {
                    callback.onError(new IMException(3, GarenaFriend.RETURN_ERROR_MSG_TAG + e2.getMessage()));
                    IMLogger.e(e2.getMessage());
                }
            }
        });
    }

    public void handleIntent(Intent intent, IMCallback<IMLaunchResult> callback) {
        Uri realUri;
        IMLogger.d("GarenaFriend handleIntent");
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_BUNDLE);
            Uri uri = intent.getData();
            if (bundle != null && bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_MEDIA_TAG) != null && bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_MEDIA_TAG).length() > 0) {
                String mediaTag = bundle.getString(SDKConstants.COM_GARENA_MSDK_GAME_LAUNCH_MEDIA_TAG);
                IMLaunchResult launchResult = new IMLaunchResult(1, IMRetCode.SUCCESS);
                launchResult.launchData = mediaTag;
                IMLogger.d("mediaTag is : " + mediaTag);
                callback.onSuccess(launchResult);
            } else if (uri == null || uri.getQueryParameterNames() == null) {
                callback.onError(new IMException(3, IMRetCode.INVALID_ARGUMENT));
            } else {
                IMLogger.w("bundle is null or mediaTag is null, uri is not null");
                IMLogger.d("handle uri : " + uri.toString());
                JSONObject jsonObject = new JSONObject();
                for (String key : uri.getQueryParameterNames()) {
                    try {
                        jsonObject.put(key, uri.getQueryParameter(key));
                    } catch (Exception e) {
                        IMLogger.w("put uri parameter " + key + " failed : " + e.getMessage());
                    }
                    if (key.equals(FB_MESSENGER_LAUNCH_KEY) && (realUri = Uri.parse(uri.getQueryParameter(key))) != null) {
                        for (String realKey : realUri.getQueryParameterNames()) {
                            try {
                                jsonObject.put(realKey, realUri.getQueryParameter(realKey));
                            } catch (Exception e2) {
                                IMLogger.w("put uri parameter " + key + " failed : " + e2.getMessage());
                            }
                        }
                    }
                }
                IMLaunchResult launchResult2 = new IMLaunchResult(1, IMRetCode.SUCCESS);
                launchResult2.launchData = jsonObject.toString();
                launchResult2.launchUri = uri.toString();
            }
        } else {
            IMLogger.w("intent is null");
            callback.onError(new IMException(3, IMRetCode.INVALID_ARGUMENT));
        }
    }

    /* access modifiers changed from: private */
    public void getBitmapFromURL(final Uri src, final IMGetBitmapImageCallBack callback) {
        new Thread(new Runnable() {
            public void run() {
                URLConnection connection;
                InputStream stream = null;
                try {
                    Looper.prepare();
                    Bitmap bitmap = null;
                    String bitmapPath = src.toString();
                    IMLogger.d("getBitmapFromURL bitmapPath:" + bitmapPath);
                    URL url = new URL(bitmapPath);
                    if (!IMBitmapTool.isHttpUrl(bitmapPath)) {
                        bitmap = IMBitmapTool.createFromPath(GarenaFriend.this.mContext, bitmapPath);
                    } else if (!(Uri.parse(bitmapPath) == null || (connection = url.openConnection()) == null)) {
                        stream = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(stream);
                    }
                    if (bitmap != null) {
                        IMLogger.d("getBitmapFromURL onSuccess");
                        callback.onSuccess(bitmap);
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e) {
                                IMLogger.e(e.getMessage());
                            }
                        }
                    } else {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e2) {
                                IMLogger.e(e2.getMessage());
                            }
                        }
                        callback.onFail("get image error");
                    }
                } catch (Exception e3) {
                    callback.onFail("get image error : " + e3.getMessage());
                    IMLogger.d("get image error : " + e3.getMessage());
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e4) {
                            IMLogger.e(e4.getMessage());
                        }
                    }
                } catch (Throwable th) {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e5) {
                            IMLogger.e(e5.getMessage());
                        }
                    }
                    throw th;
                }
            }
        }).start();
    }

    private String getSessionProviderSubChannel() {
        try {
            GGLoginSession.SessionProvider sessionProvider = GGLoginSession.getCurrentSession().getSessionProvider();
            if (sessionProvider == GGLoginSession.SessionProvider.GARENA) {
                return "GRN_Gas";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.FACEBOOK) {
                return "GRN_FB";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.GOOGLE) {
                return "GRN_GG";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.LINE) {
                return "GRN_LN";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.VK) {
                return "GRN_VK";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.GUEST) {
                return "GRN_GU";
            }
            return "";
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    private boolean checkSubChannelIsSupport(ArrayList<String> supportList) {
        if (supportList.isEmpty() || supportList.size() == 0) {
            return true;
        }
        String currentPlatform = getSessionProviderSubChannel();
        if (!TextUtils.isEmpty(currentPlatform)) {
            Iterator<String> it = supportList.iterator();
            while (it.hasNext()) {
                if (currentPlatform.equalsIgnoreCase(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showLogIMFriendContentInfo(String method, IMFriendContent info) {
        if (info != null) {
            try {
                HashMap<String, String> res = new HashMap<>();
                res.put(IMFriend.FriendPrarams.USER, info.user);
                res.put("title", info.title);
                res.put("content", info.content);
                res.put("link", info.link);
                res.put("imagePath", info.imagePath);
                res.put("thumbPath", info.thumbPath);
                res.put("extraJson", info.extraJson);
                IMLogger.d("Friend: method:" + method + "" + res.toString());
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void parsePluginResultWithCallback(String platform, String method, ShareRet result, IMCallback<IMFriendResult> callback) {
        IMLogger.d(platform + " " + method + " return back");
        HashMap<String, Object> res = new HashMap<>();
        res.put(DownloadDBHelper.FLAG, Integer.valueOf(result.flag));
        res.put("platform", Integer.valueOf(result.platform));
        res.put("desc", result.desc);
        if (callback != null) {
            if (result == null || result.flag != 0) {
                String returnStr = result.desc;
                if (TextUtils.isEmpty(returnStr)) {
                    returnStr = "Garena Share Fail";
                }
                this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + returnStr);
                IMCallback<IMFriendResult> iMCallback = callback;
                iMCallback.onError(rebuild(this.mException, IMRetCode.RETURN_THIRD, result.flag, returnStr, (String) null, true, "parsePluginResult", "garena callback"));
            } else {
                IMFriendResult ret = new IMFriendResult();
                ret.retCode = 1;
                ret.retMsg = "return flag:" + result.flag + " msg:" + result.desc;
                callback.onSuccess(rebuildForSuccess(ret, true, "parsePluginResult", "garena callback"));
            }
        }
        IMLogger.d(platform + " " + method + ":" + res.toString());
    }

    /* access modifiers changed from: private */
    public String parseFromJsonWithCallback(String jsonStr, String objectName, IMCallback<IMFriendResult> callback) {
        if (!TextUtils.isEmpty(jsonStr) && !TextUtils.isEmpty(objectName)) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                if (json != null) {
                    return json.getString(objectName);
                }
            } catch (JSONException e) {
                if (callback != null) {
                    this.mException = new IMException(3, "share error,extraJson-" + objectName + " parse fail");
                    callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, e.getMessage(), (String) null, true, "parseFromJson", "check argument"));
                }
                IMLogger.e(e.getMessage());
            }
        } else if (callback != null) {
            this.mException = new IMException(3, "share error,extraJson-" + objectName + " cannot be null");
            callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "extraJson-" + objectName + " cannot be null", (String) null, true, "parseFromJson", "check argument"));
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public IMException rebuild(IMException exception, int imsdkCode, int thirdCode, String thirdMsg, String extra, boolean autoStat, String function, String stage) {
        exception.imsdkRetCode = imsdkCode;
        exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        exception.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            exception.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            exception.retExtraJson = extra;
        }
        return exception;
    }

    /* access modifiers changed from: protected */
    public IMResult rebuild(IMResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra, boolean autoStat, String function, String stage) {
        result.imsdkRetCode = imsdkCode;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        result.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            result.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            result.retExtraJson = extra;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public IMLoginResult rebuildForSuccess(IMLoginResult result, boolean autoStat, String function, String stage) {
        result.imsdkRetCode = IMRetCode.SUCCESS;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    /* access modifiers changed from: protected */
    public IMResult rebuildForSuccess(IMResult result, boolean autoStat, String function, String stage) {
        result.imsdkRetCode = IMRetCode.SUCCESS;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    /* access modifiers changed from: protected */
    public IMFriendResult rebuildForSuccess(IMFriendResult result, boolean autoStat, String function, String stage) {
        result.imsdkRetCode = IMRetCode.SUCCESS;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
        return result;
    }

    /* access modifiers changed from: protected */
    public IMLoginResult rebuild(IMLoginResult result, int imsdkCode, int thirdCode, String thirdMsg, String extra, boolean autoStat, String function, String stage) {
        result.imsdkRetCode = imsdkCode;
        result.imsdkRetMsg = IMErrorMsg.getMessageByCode(imsdkCode);
        result.thirdRetCode = thirdCode;
        if (!TextUtils.isEmpty(thirdMsg)) {
            result.thirdRetMsg = thirdMsg;
        }
        if (!TextUtils.isEmpty(extra)) {
            result.retExtraJson = extra;
        }
        return result;
    }
}
