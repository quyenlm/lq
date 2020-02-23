package com.tencent.imsdk.sns.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.tencent.imsdk.BuildConfig;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.sns.base.IMFriendBase;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLaunchResult;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.HashMap;

public class IMFriend {
    private static final String CHANNEL_PREF = "IMFriend";
    private static final String CHANNEL_PREF_KEY = "LastFriendChannel";
    static final int FRIENDS_DEFAULT = 0;
    static final int FRIENDS_INVITE = 1;
    static final int IMSDK_CANCEL_CODE = 2;
    static final int IMSDK_NOT_SUPPORT_CODE = 7;
    static final int IMSDK_NO_PACKAGE_CODE = 9;
    private static final String MODULE_FRIEND = "base_friend";
    private static String currentChannel = "";
    protected static Context currentContext = null;
    private static IMFriendListener currentListener = null;
    private static IMFriendBase friendInstance = null;
    /* access modifiers changed from: private */
    public static HashMap<Integer, IMFriendListener> friendListeners = new HashMap<>();
    private static volatile int listenerTag = 0;
    static final Object lock = new Object();
    private static InnerStat.Builder mSTBuilder;

    public static class FriendPrarams {
        public static final String CONTENT = "content";
        public static final String EXTRA_JSON = "extraJson";
        public static final String IMAGE_PATH = "imagePath";
        public static final String LINK = "link";
        public static final String THUMB_PATH = "thumbPath";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
        public static final String USER = "user";
    }

    private static boolean initialize() {
        if (currentContext == null) {
            return false;
        }
        IMConfig.initialize(currentContext);
        mSTBuilder = new InnerStat.Builder(currentContext, BuildConfig.VERSION_NAME);
        return true;
    }

    public static boolean initialize(Context context) {
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
        currentContext = context;
        currentChannel = channel;
        initialize();
        return setChannel(channel);
    }

    public static boolean isInitialize() {
        if (friendInstance != null) {
            return true;
        }
        IMLogger.e("can not get instance : " + currentChannel + ", did you add the package and call initialize function ?");
        return false;
    }

    public static String getChannel() {
        return currentChannel;
    }

    public static IMFriendBase getInstance(String channel) {
        if (channel == null || channel.length() <= 0) {
            IMLogger.e("your channel is empty, you must assign the channel !");
            return null;
        }
        String channelClass = "com.tencent.imsdk." + channel.toLowerCase() + ".friend." + channel + "Friend";
        IMLogger.d("try to get class : " + channelClass);
        IMFriendBase instance = (IMFriendBase) IMModules.getInstance().getModule(channelClass);
        if (instance == null) {
            IMLogger.e("get " + channelClass + " failed, check whether the package exists !");
            return instance;
        } else if (instance.isInitialized() || currentContext == null) {
            return instance;
        } else {
            instance.initialize(currentContext);
            return instance;
        }
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
            IMLogger.d("save user preferenced channel : " + channel);
        }
        IMLogger.d("switch channel to : " + currentChannel);
        friendInstance = getInstance(currentChannel);
        if (friendInstance != null) {
            return true;
        }
        IMLogger.e("get channel  " + currentChannel + " instance failed !");
        return false;
    }

    public static void setListener(IMFriendListener listener) {
        currentListener = listener;
    }

    public static IMFriendListener getListener() {
        return currentListener;
    }

    public static void getFriends(int page, int count, int type, String extraJson) {
        if (type != 1) {
            getFriends(page, count);
        } else {
            getInviteFriends(page, count, extraJson);
        }
    }

    public static void getFriends(int page, int count) {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            friendListeners.put(Integer.valueOf(listenerTag), currentListener);
        }
        if (isInitialize()) {
            IMCallback<IMFriendResult> callback = new IMCallback<IMFriendResult>() {
                public void onSuccess(IMFriendResult result) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(result);
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onCancel() {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(2, 2));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onError(IMException exception) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
            }
            friendInstance.getFriends(page, count, callback);
            return;
        }
        IMFriendListener thisListener = friendListeners.get(Integer.valueOf(thisTag));
        if (thisListener != null) {
            thisListener.onFriend(new IMFriendResult(9, 9));
            friendListeners.remove(Integer.valueOf(thisTag));
        }
    }

    private static void getInviteFriends(int page, int count, String extraJson) {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            friendListeners.put(Integer.valueOf(listenerTag), currentListener);
        }
        if (isInitialize()) {
            IMCallback<IMFriendResult> callback = new IMCallback<IMFriendResult>() {
                public void onSuccess(IMFriendResult result) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(result);
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onCancel() {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(2, 2));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onError(IMException exception) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
            }
            friendInstance.getInviteFriends(page, count, extraJson, callback);
            return;
        }
        IMFriendListener thisListener = friendListeners.get(Integer.valueOf(thisTag));
        if (thisListener != null) {
            thisListener.onFriend(new IMFriendResult(9, 9));
            friendListeners.remove(Integer.valueOf(thisTag));
        }
    }

    public static void invite(IMFriendContent content) {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            friendListeners.put(Integer.valueOf(listenerTag), currentListener);
        }
        if (isInitialize()) {
            IMCallback<IMFriendResult> callback = new IMCallback<IMFriendResult>() {
                public void onSuccess(IMFriendResult result) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(result);
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onCancel() {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(2, 2));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onError(IMException exception) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
            }
            friendInstance.invite(content, callback);
            return;
        }
        IMFriendListener thisListener = friendListeners.get(Integer.valueOf(thisTag));
        if (thisListener != null) {
            thisListener.onFriend(new IMFriendResult(9, 9));
            friendListeners.remove(Integer.valueOf(thisTag));
        }
    }

    public static void sendMessage(IMFriendContent content) {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            friendListeners.put(Integer.valueOf(listenerTag), currentListener);
        }
        if (isInitialize()) {
            IMCallback<IMFriendResult> callback = new IMCallback<IMFriendResult>() {
                public void onSuccess(IMFriendResult result) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(result);
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onCancel() {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(2, 2));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }

                public void onError(IMException exception) {
                    IMFriendListener thisListener = (IMFriendListener) IMFriend.friendListeners.get(Integer.valueOf(thisTag));
                    if (thisListener != null) {
                        thisListener.onFriend(new IMFriendResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                        IMFriend.friendListeners.remove(Integer.valueOf(thisTag));
                    }
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
            }
            friendInstance.sendMessage(content, callback);
            return;
        }
        IMFriendListener thisListener = friendListeners.get(Integer.valueOf(thisTag));
        if (thisListener != null) {
            thisListener.onFriend(new IMFriendResult(9, 9));
            friendListeners.remove(Integer.valueOf(thisTag));
        }
    }

    public static void handleIntent(String channel, Intent intent, IMCallback<IMLaunchResult> callback) {
        IMLogger.d("handleIntent...");
        IMFriendBase instance = getInstance(channel);
        if (instance == null) {
            IMLogger.w("instance of channel : " + channel + " is null");
            callback.onError(new IMException(9, 9));
        } else if (instance instanceof IMLaunchHandler) {
            ((IMLaunchHandler) instance).handleIntent(intent, callback);
        } else {
            IMLogger.w("instance of channel " + channel + " not implements IMLaunchHandler");
            callback.onError(new IMException(7, 7));
        }
    }
}
