package com.tencent.imsdk.sns.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import com.tencent.imsdk.BuildConfig;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.IMShareBase;
import com.tencent.imsdk.sns.base.IMShareContent;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.HashMap;
import org.json.JSONObject;

public class IMShare {
    private static final String CHANNEL_PREF = "IMShare";
    private static final String CHANNEL_PREF_KEY = "LastShareChannel";
    static final int IMSDK_CANCEL_CODE = 2;
    static final int IMSDK_SHARE_NOT_INITIALIZED_CODE = 17;
    static final int IMSDK_SYSTEM_ERROR_CODE = 3;
    private static final String MODULE_SHARE = "base_share";
    public static String currentChannel = "";
    protected static Context currentContext = null;
    private static IMShareListener currentListener = null;
    private static volatile int listenerTag = 0;
    private static Object lock = new Object();
    /* access modifiers changed from: private */
    public static InnerStat.Builder mSTBuilder;
    public static IMShareBase shareInstance = null;
    private static HashMap<Integer, IMShareListener> shareListeners = new HashMap<>();

    public static class ShareParams {
        public static final String CONTENT = "content";
        public static final String EXTRA_JSON = "extraJson";
        public static final String IMAGE_PATH = "imagePath";
        public static final String LINK = "link";
        public static final String THUMB_PATH = "thumbPath";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
    }

    static abstract class ShareTask {
        /* access modifiers changed from: package-private */
        public abstract void run(IMCallback<IMResult> iMCallback);

        ShareTask() {
        }
    }

    private static boolean initialize() {
        if (currentContext == null) {
            return false;
        }
        IMConfig.initialize(currentContext);
        return true;
    }

    public static boolean initialize(Context context) {
        if (context == null) {
            IMLogger.e("initialize context is null ! ");
            return false;
        }
        mSTBuilder = new InnerStat.Builder(context, BuildConfig.VERSION_NAME);
        IMLogger.d("initialize with context : " + context.getClass().getName());
        currentContext = context;
        initialize();
        String prefChannel = context.getSharedPreferences(CHANNEL_PREF, 0).getString(CHANNEL_PREF_KEY, "");
        if (prefChannel == null || prefChannel.length() <= 0) {
            IMLogger.w("channel is not assigned and cannot get last saved channel !");
            return false;
        }
        IMLogger.d("use saved channel : " + prefChannel);
        return initialize(context, prefChannel);
    }

    public static boolean initialize(Context context, String channel) {
        if (context == null) {
            IMLogger.e("initialize context is null ! ");
            return false;
        }
        currentContext = context;
        currentChannel = channel;
        initialize();
        return setChannel(currentChannel);
    }

    public static boolean isInitialize() {
        if (currentContext == null) {
            IMLogger.e("login module is not initialized, please call initialize function first !");
            return false;
        } else if (shareInstance != null) {
            return true;
        } else {
            IMLogger.e("can not get instance : " + currentChannel + ", did you add the package and call initialize function ?");
            return false;
        }
    }

    public static void setListener(IMShareListener listener) {
        currentListener = listener;
    }

    public static IMShareListener getListener() {
        return currentListener;
    }

    public static IMShareBase getInstance(String channel) {
        if (channel == null || channel.length() <= 0) {
            IMLogger.e("your channel is empty, you must assign the channel !");
            return null;
        }
        String channelClass = "com.tencent.imsdk." + channel.toLowerCase() + ".share." + channel + "Share";
        IMLogger.d("try to get class : " + channelClass);
        IMShareBase instance = (IMShareBase) IMModules.getInstance().getModule(channelClass);
        if (instance == null) {
            IMLogger.e("get " + channelClass + " failed, check whether the package exists !");
            return instance;
        }
        IMLogger.d("get share instance : " + instance.getClass().getName() + " success !");
        if (instance.isInitialized() || currentContext == null) {
            return instance;
        }
        instance.initialize(currentContext);
        return instance;
    }

    public static String getChanel() {
        return currentChannel;
    }

    public static boolean setChannel(String channel) {
        currentChannel = channel;
        if (currentContext == null) {
            IMLogger.e("initialize should be called before set channel !");
            return false;
        }
        SharedPreferences.Editor editor = currentContext.getSharedPreferences(CHANNEL_PREF, 0).edit();
        editor.putString(CHANNEL_PREF_KEY, channel);
        if (editor.commit()) {
            IMLogger.d("save user preference channel : " + channel);
        }
        IMLogger.d("switch channel to : " + currentChannel);
        shareInstance = getInstance(currentChannel);
        if (shareInstance != null) {
            return true;
        }
        IMLogger.e("get channel  " + currentChannel + " instance failed !");
        return false;
    }

    protected static void callbackByNotInitialized(int tag) {
        IMLogger.d("imsdk share " + tag + " not initialized !");
        IMShareListener thisListener = shareListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMResult shareResult = new IMResult(9);
            shareResult.imsdkRetCode = 17;
            shareResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(shareResult.imsdkRetCode);
            thisListener.onShare(shareResult);
            shareListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("share listener not set, no callback !");
    }

    protected static void callbackByResult(int tag, IMResult result) {
        IMLogger.d("imsdk share " + tag + " callback return with imsdk code : " + result.imsdkRetCode);
        IMShareListener thisListener = shareListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            thisListener.onShare(result);
            shareListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("share listener not set, no callback !");
    }

    protected static void callbackByCancel(int tag) {
        IMLogger.d("imsdk share " + tag + " canceled ! ");
        IMShareListener thisListener = shareListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMResult shareResult = new IMResult(2);
            shareResult.imsdkRetCode = 2;
            shareResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(shareResult.imsdkRetCode);
            thisListener.onShare(shareResult);
            shareListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("share listener not set, no callback !");
    }

    protected static void callbackByException(int tag, IMException exception) {
        IMLogger.d("imsdk share " + tag + " callback error with imsdk code : " + exception.imsdkRetCode);
        if (shareListeners.get(Integer.valueOf(tag)) != null) {
            currentListener.onShare(new IMResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
            shareListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("share listener not set, no callback !");
    }

    protected static void callbackBySystemError(int tag, Exception exception) {
        IMLogger.d("imsdk share " + tag + " callback system error with message : " + exception.getMessage());
        if (shareListeners.get(Integer.valueOf(tag)) != null) {
            IMResult shareResult = new IMResult(3);
            shareResult.imsdkRetCode = 3;
            shareResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(shareResult.imsdkRetCode);
            shareResult.thirdRetCode = exception.hashCode();
            shareResult.thirdRetMsg = exception.getMessage();
            currentListener.onShare(shareResult);
            shareListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("share listener not set, no callback !");
    }

    protected static IMCallback<IMResult> newShareCallback(final int tag) {
        return new IMCallback<IMResult>() {
            public void onSuccess(IMResult result) {
                IMShare.callbackByResult(tag, result);
            }

            public void onCancel() {
                IMShare.callbackByCancel(tag);
            }

            public void onError(IMException exception) {
                IMShare.callbackByException(tag, exception);
            }
        };
    }

    protected static void runShareTask(ShareTask task) {
        int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (shareListeners != null) {
                shareListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        try {
            if (isInitialize()) {
                task.run(newShareCallback(thisTag));
            } else {
                callbackByNotInitialized(thisTag);
            }
        } catch (Exception exception) {
            callbackBySystemError(thisTag, exception);
        }
    }

    public static void share(final IMShareContent content) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-share");
                }
                IMShare.shareInstance.share(content, callback2);
            }
        });
    }

    public static void shareText(final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareText");
                }
                IMShare.shareInstance.shareText(title, content, extras, callback2);
            }
        });
    }

    public static void shareTextDialog(final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareTextDialog");
                }
                IMShare.shareInstance.shareTextDialog(title, content, extras, callback2);
            }
        });
    }

    public static void shareLink(String link, String title, String content, String imgUrl, JSONObject extras) {
        final String str = link;
        final String str2 = title;
        final String str3 = content;
        final String str4 = imgUrl;
        final JSONObject jSONObject = extras;
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareLink");
                }
                IMShare.shareInstance.shareLink(str, str2, str3, str4, jSONObject, callback2);
            }
        });
    }

    public static void shareLinkDialog(String link, String title, String content, String imgUrl, JSONObject extras) {
        final String str = link;
        final String str2 = title;
        final String str3 = content;
        final String str4 = imgUrl;
        final JSONObject jSONObject = extras;
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareLinkDialog");
                }
                IMShare.shareInstance.shareLinkDialog(str, str2, str3, str4, jSONObject, callback2);
            }
        });
    }

    public static void shareImage(final Uri imageUri, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImage");
                }
                IMShare.shareInstance.shareImage(imageUri, title, content, extras, callback2);
            }
        });
    }

    public static void shareImageDialog(final Uri imageUri, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImageDialog");
                }
                IMShare.shareInstance.shareImageDialog(imageUri, title, content, extras, callback2);
            }
        });
    }

    public static void shareImage(final Bitmap image, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImage");
                }
                IMShare.shareInstance.shareImage(image, title, content, extras, callback2);
            }
        });
    }

    public static void shareImageDialog(final Bitmap image, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImageDialog");
                }
                IMShare.shareInstance.shareImageDialog(image, title, content, extras, callback2);
            }
        });
    }

    public static void shareImage(final String imagePath, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImage");
                }
                IMShare.shareInstance.shareImage(imagePath, title, content, extras, callback2);
            }
        });
    }

    public static void shareImageDialog(final String imagePath, final String title, final String content, final JSONObject extras) {
        runShareTask(new ShareTask() {
            public void run(IMCallback<IMResult> callback) {
                IMCallback<IMResult> callback2 = callback;
                if (IMShare.mSTBuilder != null) {
                    callback2 = IMShare.mSTBuilder.create().proxyListener4EventReport(IMShare.currentChannel, callback, "share-" + IMShare.currentChannel + "-shareImageDialog");
                }
                IMShare.shareInstance.shareImageDialog(imagePath, title, content, extras, callback2);
            }
        });
    }
}
