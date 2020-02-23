package com.tencent.imsdk.garena.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPhotoShare;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.GGTextShare;
import com.beetalk.sdk.facebook.FBPostItem;
import com.beetalk.sdk.line.LinePostItem;
import com.beetalk.sdk.ndk.ShareRet;
import com.beetalk.sdk.plugin.GGPluginCallback;
import com.beetalk.sdk.plugin.PluginResult;
import com.facebook.share.internal.ShareConstants;
import com.garena.pay.android.GGErrorCode;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.extend.garena.base.ExtendGarenaManager;
import com.tencent.imsdk.sns.base.IMBitmapTool;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.sns.base.IMShareBase;
import com.tencent.imsdk.sns.base.IMShareContent;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class GarenaShare extends IMShareBase {
    private static final String META_APPLICATION_ID = "com.garena.sdk.applicationId";
    private static final String RETURN_ERROR_MSG_TAG = "share error,";
    private static final String VERSION = "1.16.0";
    /* access modifiers changed from: private */
    public String APP_SDK_ASSIGN_ID = "";
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
        this.mContext = (Activity) context;
        if (context != null) {
            this.APP_SDK_ASSIGN_ID = String.valueOf(MetaDataUtil.readMetaIntFromApplication(context, "com.garena.sdk.applicationId"));
            IMLogger.d("APP_SDK_ASSIGN_ID:" + this.APP_SDK_ASSIGN_ID);
            this.mSTBuilder = new InnerStat.Builder(context, "1.16.0", GGPlatform.GGGetSDKVersion(), "imsdkgarena");
        }
        return super.initialize(context);
    }

    public void share(IMShareContent content, IMCallback<IMResult> callback) {
        if (content.type > -1) {
            switch (content.type) {
                case 1:
                    shareText(content, callback);
                    return;
                case 3:
                    shareLink(content, callback);
                    return;
                case 5:
                    shareImage(content, callback);
                    return;
                default:
                    IMLogger.w("Garena not support");
                    this.mException = new IMException(3, "share error,Garena not support");
                    callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "shareText", "check support"));
                    return;
            }
        }
    }

    public void shareText(final IMShareContent content, final IMCallback<IMResult> callback) {
        showLogIMShareContentInfo("sendText", content);
        final String currentPlatform = getSessionProviderSubChannel();
        ArrayList<String> supportList = new ArrayList<>();
        supportList.add("GRN_Gas");
        if (!checkSubChannelIsSupport(supportList)) {
            IMLogger.e(currentPlatform + " not support sendText");
            this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatform + " not support send icon and text to platform app’s buzz session");
            callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "shareText", "check support"));
            return;
        }
        this.mContext.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    IMLogger.d("thumbPath:" + content.thumbPath);
                    if (content.type != 1) {
                        return;
                    }
                    if (!TextUtils.isEmpty(content.thumbPath)) {
                        GarenaShare.this.getBitmapFromURL(Uri.parse(content.thumbPath), new IMGetBitmapImageCallBack() {
                            public void onSuccess(final Bitmap icon) {
                                if (GarenaShare.this.mContext != null) {
                                    GarenaShare.this.mContext.runOnUiThread(new Runnable() {
                                        public void run() {
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            try {
                                                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                GGTextShare.Builder builder = new GGTextShare.Builder(stream.toByteArray(), Integer.valueOf(GarenaShare.this.APP_SDK_ASSIGN_ID).intValue());
                                                builder.setDesc(content.content);
                                                String mediaTagName = GarenaShare.this.parseFromJson(content.extraJson, "mediaTagName", callback);
                                                if (!TextUtils.isEmpty(mediaTagName)) {
                                                    builder.setMediaTag(mediaTagName);
                                                    builder.setTitle(content.title);
                                                    String link = content.link;
                                                    if (!TextUtils.isEmpty(link)) {
                                                        builder.setUrl(link);
                                                    } else {
                                                        builder.setUrl("http://www.garena.sg/");
                                                    }
                                                    ExtendGarenaManager.getInstance().setIMSDKShareCallback(currentPlatform, "sendMessage", callback);
                                                    GGPlatform.GGSendGameToSession(GarenaShare.this.mContext, builder.build(), ExtendGarenaManager.getInstance().getGarenaCallback());
                                                    if (stream != null) {
                                                        try {
                                                            stream.close();
                                                        } catch (IOException e) {
                                                            IMLogger.e(e.getMessage());
                                                        }
                                                    }
                                                } else if (stream != null) {
                                                    try {
                                                        stream.close();
                                                    } catch (IOException e2) {
                                                        IMLogger.e(e2.getMessage());
                                                    }
                                                }
                                            } catch (Exception e3) {
                                                IMLogger.e(e3.getMessage());
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
                                    });
                                }
                            }

                            public void onFail(final String errorMessage) {
                                if (GarenaShare.this.mContext != null) {
                                    GarenaShare.this.mContext.runOnUiThread(new Runnable() {
                                        public void run() {
                                            IMException unused = GarenaShare.this.mException = new IMException(3, GarenaShare.RETURN_ERROR_MSG_TAG + errorMessage);
                                            callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.SYSTEM_ERROR, -1, errorMessage, (String) null, true, "shareText", "getBitmapFromURL fail"));
                                        }
                                    });
                                }
                            }
                        });
                    } else if (GarenaShare.this.mContext != null) {
                        GarenaShare.this.mContext.runOnUiThread(new Runnable() {
                            public void run() {
                                IMException unused = GarenaShare.this.mException = new IMException((Throwable) new IMException(3, "share error,thumbPath cannot empty"));
                                callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.INVALID_ARGUMENT, -1, "thumbPath cannot empty", (String) null, true, "shareText", "getBitmapFromURL fail"));
                            }
                        });
                    }
                } catch (Exception e) {
                    IMException unused = GarenaShare.this.mException = new IMException(3, GarenaShare.RETURN_ERROR_MSG_TAG + e.getMessage());
                    callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.SYSTEM_ERROR, -1, e.getMessage(), (String) null, true, "shareText", "getBitmapFromURL fail"));
                    IMLogger.e(e.getMessage());
                }
            }
        });
    }

    public void shareLink(IMShareContent content, IMCallback<IMResult> callback) {
        showLogIMShareContentInfo("shareLink", content);
        String currentPlatformTemp = getSessionProviderSubChannel();
        if (checkIfUserSpecifiedShareToFB(content, callback)) {
            currentPlatformTemp = "GRN_FB";
        } else if (checkIfUserSpecifiedShareToLN(content, callback)) {
            currentPlatformTemp = "GRN_LN";
        } else {
            ArrayList<String> supportList = new ArrayList<>();
            supportList.add("GRN_Gas");
            supportList.add("GRN_FB");
            if (!checkSubChannelIsSupport(supportList)) {
                IMLogger.e(currentPlatformTemp + " not support shareLink");
                this.mException = new IMException(3, RETURN_ERROR_MSG_TAG + currentPlatformTemp + " not support shareLink");
                IMCallback<IMResult> iMCallback = callback;
                iMCallback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "shareText", "check support"));
                return;
            }
        }
        final String currentPlatform = currentPlatformTemp;
        String caption = parseFromJson(content.extraJson, ShareConstants.FEED_CAPTION_PARAM, callback);
        if (!TextUtils.isEmpty(caption)) {
            if (currentPlatform.equalsIgnoreCase("GRN_FB")) {
                IMLogger.d("shareLink GRN_FB");
                if (TextUtils.isEmpty(content.thumbPath)) {
                    IMLogger.e("thumbPath cannot be null");
                    this.mException = new IMException(3, "share error,thumbPath cannot be null");
                    IMCallback<IMResult> iMCallback2 = callback;
                    iMCallback2.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "thumbPath cannot be null", (String) null, true, "shareText", "check argument"));
                    return;
                }
                ExtendGarenaManager.getInstance().setIMSDKShareCallback(currentPlatform, "shareLink", callback);
                GGPlatform.GGSendLinkToFacebook(this.mContext, new FBPostItem(content.title, caption, content.content, content.link, content.thumbPath), ExtendGarenaManager.getInstance().getGarenaCallback());
                IMLogger.d("shareLink GRN_FB end");
            } else if (currentPlatform.equalsIgnoreCase("GRN_Gas")) {
                String mediaTagName = parseFromJson(content.extraJson, "mediaTagName", callback);
                if (!TextUtils.isEmpty(mediaTagName)) {
                    ExtendGarenaManager.getInstance().setIMSDKShareCallback(currentPlatform, "shareLink", callback);
                    final IMCallback<IMResult> iMCallback3 = callback;
                    GGPlatform.GGSendLinkToSession(this.mContext, 1, mediaTagName, content.link, content.title, caption, content.content, content.thumbPath, new GGPlatform.ShareCallback() {
                        public void onPluginResult(ShareRet result) {
                            GarenaShare.this.parsePluginResult(currentPlatform, "shareLink", result, iMCallback3);
                        }
                    });
                }
            } else if (currentPlatform.equalsIgnoreCase("GRN_LN")) {
                shareToLine(LinePostItem.PostType.TEXT_LINK, content, callback);
            }
        }
    }

    public void shareImage(final IMShareContent content, final IMCallback<IMResult> callback) {
        this.mContext.runOnUiThread(new Runnable() {
            public void run() {
                GarenaShare.this.showLogIMShareContentInfo("shareImage", content);
                String currentPlatformTemp = GarenaShare.this.getSessionProviderSubChannel();
                if (GarenaShare.this.checkIfUserSpecifiedShareToFB(content, callback)) {
                    currentPlatformTemp = "GRN_FB";
                } else if (GarenaShare.this.checkIfUserSpecifiedShareToLN(content, callback)) {
                    currentPlatformTemp = "GRN_LN";
                } else {
                    ArrayList<String> supportList = new ArrayList<>();
                    supportList.add("GRN_Gas");
                    supportList.add("GRN_FB");
                    if (!GarenaShare.this.checkSubChannelIsSupport(supportList)) {
                        IMLogger.e(currentPlatformTemp + " not support shareImage");
                        IMException unused = GarenaShare.this.mException = new IMException(3, GarenaShare.RETURN_ERROR_MSG_TAG + currentPlatformTemp + " not support shareImage");
                        callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, true, "shareImage", "check support"));
                        return;
                    }
                }
                final String currentPlatform = currentPlatformTemp;
                if (TextUtils.isEmpty(content.imagePath)) {
                    IMLogger.e("imagePath can not be empty");
                    IMException unused2 = GarenaShare.this.mException = new IMException(3, GarenaShare.RETURN_ERROR_MSG_TAG + currentPlatformTemp + " imagePath can not be empty");
                    callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.INVALID_ARGUMENT, -1, "imagePath can not be empty", (String) null, true, "shareImage", "check argument"));
                } else if (currentPlatform.equalsIgnoreCase("GRN_Gas")) {
                    if (content.imagePath.startsWith(IMSDKFileProvider.FILE_SCHEME)) {
                        content.imagePath = content.imagePath.substring(IMSDKFileProvider.FILE_SCHEME.length(), content.imagePath.length());
                        IMLogger.d("sub imagePath:" + content.imagePath);
                    }
                    GGPhotoShare.Builder builder = new GGPhotoShare.Builder(1, content.imagePath, Integer.valueOf(GarenaShare.this.APP_SDK_ASSIGN_ID).intValue());
                    String mediaTagName = GarenaShare.this.parseFromJson(content.extraJson, "mediaTagName", callback);
                    builder.setMediaTag(mediaTagName);
                    builder.setMessageAction("");
                    builder.setMessageExt(mediaTagName);
                    ExtendGarenaManager.getInstance().setIMSDKShareCallback(currentPlatform, "shareImage", callback);
                    GGPlatform.GGSendMediaToSession(GarenaShare.this.mContext, builder.build(), ExtendGarenaManager.getInstance().getGarenaCallback());
                } else if (currentPlatform.equalsIgnoreCase("GRN_FB")) {
                    GarenaShare.this.getBitmapFromURL(Uri.parse(content.imagePath), new IMGetBitmapImageCallBack() {
                        public void onSuccess(final Bitmap bitmap) {
                            if (GarenaShare.this.mContext != null) {
                                GarenaShare.this.mContext.runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (bitmap == null) {
                                            IMException unused = GarenaShare.this.mException = new IMException(3, "share error,imagePath canot be parse");
                                            callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.INVALID_ARGUMENT, -1, "imagePath canot be parse", (String) null, true, "shareImage", "getBitmapFromURL"));
                                        } else if (currentPlatform.equalsIgnoreCase("GRN_FB")) {
                                            FBPostItem item = new FBPostItem(content.title, content.title, content.content, content.link);
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            try {
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                item.data = stream.toByteArray();
                                                ExtendGarenaManager.getInstance().setIMSDKShareCallback(currentPlatform, "shareImage", callback);
                                                GGPlatform.GGShareToFacebook(GarenaShare.this.mContext, item, ExtendGarenaManager.getInstance().getGarenaCallback());
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
                                    }
                                });
                            }
                        }

                        public void onFail(final String errorMessage) {
                            if (GarenaShare.this.mContext != null) {
                                GarenaShare.this.mContext.runOnUiThread(new Runnable() {
                                    public void run() {
                                        IMException unused = GarenaShare.this.mException = new IMException(3, GarenaShare.RETURN_ERROR_MSG_TAG + errorMessage);
                                        callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.SYSTEM_ERROR, -1, errorMessage, (String) null, true, "shareImage", "getBitmapFromURL"));
                                    }
                                });
                            }
                        }
                    });
                } else if (currentPlatform.equalsIgnoreCase("GRN_LN")) {
                    GarenaShare.this.shareToLine(LinePostItem.PostType.IMAGE, content, callback);
                } else {
                    IMException unused3 = GarenaShare.this.mException = new IMException(3);
                    callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.NO_SUPPORT, -1, currentPlatform + "is not support", (String) null));
                }
            }
        });
    }

    public void shareToLine(LinePostItem.PostType shareType, IMShareContent content, final IMCallback<IMResult> callback) {
        LinePostItem item;
        IMLogger.d("shareToLine start...");
        if (content == null) {
            IMLogger.e("Content is null");
            this.mException = new IMException(3);
            callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "Content cannot be null, check argument", (String) null));
        } else if (this.mContext == null) {
            IMLogger.e("Context is null, call initialize first");
            this.mException = new IMException(11);
            callback.onError(rebuild(this.mException, IMRetCode.INITIALIZE_ERROR, -1, "Context cannot be null, need initialize first", (String) null));
        } else {
            if (shareType == LinePostItem.PostType.TEXT_LINK) {
                IMLogger.d("share link to line, link is " + content.link);
                item = new LinePostItem.Builder(shareType).message(content.content).link(content.link).build();
            } else if (shareType == LinePostItem.PostType.IMAGE) {
                IMLogger.d("share image to line, image is " + content.imagePath);
                item = new LinePostItem.Builder(shareType).imagePath(content.imagePath).build();
            } else {
                this.mException = new IMException(7);
                callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, shareType.name() + " is not support now", (String) null));
                return;
            }
            GGPlatform.GGShareToLine(this.mContext, item, new GGPluginCallback<PluginResult>() {
                public void onPluginResult(PluginResult result) {
                    if (result == null) {
                        IMException unused = GarenaShare.this.mException = new IMException(3);
                        callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.RETURN_THIRD, -1, "Garena returns null", (String) null));
                    } else if (result.flag == GGErrorCode.SUCCESS.getCode().intValue()) {
                        callback.onSuccess(new IMResult(1, 1, result.flag));
                    } else if (result.flag == GGErrorCode.USER_CANCELLED.getCode().intValue()) {
                        callback.onCancel();
                    } else if (result.flag == GGErrorCode.APP_NOT_INSTALLED.getCode().intValue()) {
                        IMException unused2 = GarenaShare.this.mException = new IMException(3);
                        callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.NEED_INSTALL_APP, result.flag, result.message, (String) null));
                    } else {
                        IMException unused3 = GarenaShare.this.mException = new IMException(3);
                        callback.onError(GarenaShare.this.rebuild(GarenaShare.this.mException, IMRetCode.RETURN_THIRD, result.flag, result.message, (String) null));
                    }
                }
            });
        }
    }

    public void shareImage(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support shareImage");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareImage(Bitmap bitmap, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support shareImage");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareImageDialog(Uri imgUri, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support shareImageDialog");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareImageDialog(Bitmap bitmap, String title, String description, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support this type of shareImageDialog");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareLink(String link, String title, String description, String imgUrl, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support this type of shareLink");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareLinkDialog(String link, String title, String description, String imgUrl, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.e("Garena not support shareLinkDialog");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareText(String title, String content, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.w("Garena not support shareText");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
    }

    public void shareTextDialog(String title, String content, JSONObject extras, IMCallback<IMResult> callback) {
        IMLogger.w("Garena not support shareTextDialog");
        this.mException = new IMException(3, "share error,Garena not support shareImage");
        callback.onError(rebuild(this.mException, IMRetCode.NO_SUPPORT, -1, (String) null, (String) null, false, (String) null, (String) null));
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
                    IMLogger.d("bitmapPath:" + bitmapPath);
                    URL url = new URL(bitmapPath);
                    if (!IMBitmapTool.isHttpUrl(bitmapPath)) {
                        bitmap = IMBitmapTool.createFromPath(GarenaShare.this.mContext, bitmapPath);
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
                                IMLogger.e("catch IOException : " + e.getMessage());
                            }
                        }
                    } else {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e2) {
                                IMLogger.e("catch IOException : " + e2.getMessage());
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
                            IMLogger.e("catch IOException : " + e4.getMessage());
                        }
                    }
                } catch (Throwable th) {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e5) {
                            IMLogger.e("catch IOException : " + e5.getMessage());
                        }
                    }
                    throw th;
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public String getSessionProviderSubChannel() {
        try {
            GGLoginSession.SessionProvider sessionProvider = GGLoginSession.getCurrentSession().getSessionProvider();
            if (sessionProvider == GGLoginSession.SessionProvider.LINE) {
                return "GRN_LN";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.GARENA) {
                return "GRN_Gas";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.VK) {
                return "GRN_VK";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.GOOGLE) {
                return "GRN_GG";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.FACEBOOK) {
                return "GRN_FB";
            }
            if (sessionProvider == GGLoginSession.SessionProvider.GUEST) {
                return "GRN_GU";
            }
            return "";
        } catch (Exception e) {
            IMLogger.e("catch exception : " + e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public boolean checkSubChannelIsSupport(ArrayList<String> supportList) {
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

    /* access modifiers changed from: private */
    public void parsePluginResult(String platform, String method, ShareRet result, IMCallback<IMResult> callback) {
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
                IMCallback<IMResult> iMCallback = callback;
                iMCallback.onError(rebuild(this.mException, IMRetCode.RETURN_THIRD, result.flag, returnStr, (String) null, true, "parsePluginResult", "garena result"));
            } else {
                IMResult ret = new IMResult();
                ret.retCode = 1;
                ret.retMsg = "return flag:" + result.flag + " msg:" + result.desc;
                callback.onSuccess(rebuildForSuccess(ret, true, "parsePluginResult", "garena result"));
            }
        }
        IMLogger.d(platform + " " + method + ":" + res.toString());
    }

    /* access modifiers changed from: private */
    public void showLogIMShareContentInfo(String method, IMShareContent info) {
        if (info != null) {
            try {
                HashMap<String, String> res = new HashMap<>();
                res.put("title", info.title);
                res.put("content", info.content);
                res.put("link", info.link);
                res.put("imagePath", info.imagePath);
                res.put("thumbPath", info.thumbPath);
                res.put("extraJson", info.extraJson);
                IMLogger.d("Share: method:" + method + "" + res.toString());
            } catch (Exception e) {
                IMLogger.e("catch exception : " + e.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public String parseFromJson(String jsonStr, String objectName, IMCallback<IMResult> callback) {
        if (!TextUtils.isEmpty(jsonStr) && !TextUtils.isEmpty(objectName)) {
            try {
                JSONObject json = new JSONObject(jsonStr);
                if (json != null) {
                    return json.getString(objectName);
                }
            } catch (JSONException e) {
                if (callback != null && !objectName.equalsIgnoreCase("shareType")) {
                    this.mException = new IMException(3, "share error,extraJson-" + objectName + " parse fail");
                    callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "extraJson-" + objectName + " parse fail", (String) null, true, "parseFromJson", "check argument"));
                }
                IMLogger.e("catch JSONException : " + e.getMessage());
            }
        } else if (callback != null) {
            this.mException = new IMException(3, "share error,extraJson-" + objectName + " cannot be null");
            callback.onError(rebuild(this.mException, IMRetCode.INVALID_ARGUMENT, -1, "extraJson-" + objectName + " cannot be null", (String) null, true, "parseFromJson", "check argument"));
        }
        return "";
    }

    /* access modifiers changed from: private */
    public boolean checkIfUserSpecifiedShareToFB(IMShareContent content, IMCallback<IMResult> callback) {
        String shareFacebook = parseFromJson(content.extraJson, "shareType", callback);
        if (TextUtils.isEmpty(shareFacebook) || !shareFacebook.equalsIgnoreCase("GRN_FB")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean checkIfUserSpecifiedShareToLN(IMShareContent content, IMCallback<IMResult> callback) {
        String shareFacebook = parseFromJson(content.extraJson, "shareType", callback);
        if (TextUtils.isEmpty(shareFacebook) || !shareFacebook.equalsIgnoreCase("GRN_LN")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public IMException rebuild(IMException exception, int imsdkCode, int thirdCode, String thirdMsg, String extra) {
        return rebuild(exception, imsdkCode, thirdCode, thirdMsg, extra, true, (String) null, (String) null);
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
