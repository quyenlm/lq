package com.tencent.imsdk.sns.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.imsdk.BuildConfig;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.base.GuestLogin;
import com.tencent.imsdk.sns.base.IMLoginBase;
import com.tencent.imsdk.sns.base.IMLoginBindResult;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.sns.innerapi.IMLoginStat;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class IMLogin {
    private static final String CHANNEL_PREF = "IMLogin";
    private static final String CHANNEL_PREF_KEY = "LastLoginChannel";
    private static final String CHANNEL_PREF_KEY_FOR_AUTOLOGIN = "LastLoginChannelForAutoLogin";
    static final int IMSDK_CANCEL_CODE = 2;
    static final int IMSDK_LOGIN_NO_CACHED_DATA = 1001;
    static final int IMSDK_NOT_INITIALIZED_CODE = 17;
    static final int IMSDK_NO_CHANNEL_CODE = 18;
    static final int IMSDK_NO_LOGIN_CODE = 10;
    static final int IMSDK_NO_PACKAGE_CODE = 9;
    static final int IMSDK_SUCCESS_CODE = 1;
    static final int IMSDK_SYSTEM_ERROR_CODE = 3;
    private static final String MODULE_LOGIN = "base_login";
    /* access modifiers changed from: private */
    public static String currentChannel = "";
    public static Context currentContext = null;
    private static IMLoginListener currentListener = null;
    private static volatile int listenerTag = 0;
    private static final Object lock = new Object();
    /* access modifiers changed from: private */
    public static IMLoginBase loginInstance = null;
    /* access modifiers changed from: private */
    public static HashMap<Integer, IMLoginListener> loginListeners = new HashMap<>();
    /* access modifiers changed from: private */
    public static InnerStat.Builder mSTBuilder;

    static abstract class LoginTask {
        /* access modifiers changed from: package-private */
        public abstract void run(IMCallback<IMLoginResult> iMCallback);

        LoginTask() {
        }

        /* access modifiers changed from: package-private */
        public void prepare() {
        }

        /* access modifiers changed from: package-private */
        public void onSuccess(IMLoginResult loginResult) {
        }

        /* access modifiers changed from: package-private */
        public void onCancel() {
        }

        /* access modifiers changed from: package-private */
        public void onError(IMException exception) {
        }

        /* access modifiers changed from: package-private */
        public void onNoInitialized() {
        }

        /* access modifiers changed from: package-private */
        public void onNoChannel() {
        }

        /* access modifiers changed from: package-private */
        public void onNoPackage() {
        }
    }

    private static boolean initialize() {
        if (currentContext == null) {
            return false;
        }
        IMConfig.initialize(currentContext);
        IMLoginStat.initialize(currentContext);
        mSTBuilder = new InnerStat.Builder(currentContext, BuildConfig.VERSION_NAME);
        return true;
    }

    public static boolean initialize(Context context) {
        if (context == null) {
            IMLogger.e("initialize context is null ! ");
            return false;
        }
        IMLogger.d("initialize with context : " + context.getClass().getName());
        currentContext = context;
        initialize();
        String prefChannel = context.getSharedPreferences(CHANNEL_PREF, 0).getString(CHANNEL_PREF_KEY, "");
        if (prefChannel == null || prefChannel.length() <= 0) {
            IMLogger.d("login channel is not assigned and cannot get last saved channel !");
            return true;
        }
        IMLogger.d("login use saved channel : " + prefChannel);
        return initialize(context, prefChannel);
    }

    public static boolean initialize(Context context, String channel) {
        if (context == null) {
            IMLogger.e("initialize context is null ! ");
            return false;
        }
        currentChannel = channel;
        currentContext = context;
        initialize();
        return setChannel(channel);
    }

    public static boolean isInitialized() {
        if (currentContext != null) {
            return true;
        }
        IMLogger.e("login module is not initialized, please call initialize function first !");
        return false;
    }

    private static boolean isChannelSetup() {
        return currentChannel != null;
    }

    private static boolean isCurrentChannelAvailable() {
        return loginInstance != null;
    }

    public static String getChannel() {
        return currentChannel;
    }

    public static IMLoginBase getInstance(String channel) {
        String channelClass;
        IMLoginBase instance;
        if (channel == null || channel.length() <= 0) {
            IMLogger.e("your channel param is empty, you must assign the channel !");
            return null;
        }
        if ("Guest".equalsIgnoreCase(channel)) {
            channelClass = "Guest";
            IMLogger.d("try to get class GuestLogin in base");
            instance = new GuestLogin();
        } else {
            channelClass = "com.tencent.imsdk." + channel.toLowerCase() + ".login." + channel + "Login";
            IMLogger.d("try to get class : " + channelClass);
            instance = (IMLoginBase) IMModules.getInstance().getModule(channelClass);
        }
        if (instance == null) {
            IMLogger.e("get " + channelClass + " failed, check whether the package exists !");
            return instance;
        }
        IMLogger.d("get login instance : " + instance.getClass().getName() + " success !");
        if (instance.isInitialized() || currentContext == null) {
            return instance;
        }
        instance.initialize(currentContext);
        return instance;
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
        editor.putString(CHANNEL_PREF_KEY_FOR_AUTOLOGIN, channel);
        if (editor.commit()) {
            IMLogger.d("autoLogin : save user preference channel : " + channel);
        }
        IMLogger.d("switch channel to : " + currentChannel);
        loginInstance = getInstance(currentChannel);
        if (loginInstance != null) {
            if (!loginInstance.isInitialized() && currentContext != null) {
                IMLogger.d("initialize instance : " + loginInstance.getClass().getName() + " with context : " + currentContext.getClass().getName());
                try {
                    loginInstance.initialize(currentContext);
                } catch (Exception e) {
                    IMLogger.e("login instance " + loginInstance.getClass().getName() + " initialize get exception : " + e.getMessage());
                    return false;
                }
            }
            return true;
        }
        IMLogger.e("get channel  " + currentChannel + " instance failed !");
        return false;
    }

    public static void setLoginType(String type) {
        if (loginInstance != null) {
            loginInstance.setLoginType(type);
        }
    }

    public static IMLoginListener getListener() {
        return currentListener;
    }

    public static void setListener(IMLoginListener listener) {
        currentListener = listener;
    }

    protected static boolean loginCallbackByNotInitialized(int tag) {
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMLoginResult loginResult = new IMLoginResult(9);
            loginResult.imsdkRetCode = 17;
            loginResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginResult.imsdkRetCode);
            thisListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static boolean loginCallbackByNoChannel(int tag) {
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMLoginResult loginResult = new IMLoginResult(9);
            loginResult.imsdkRetCode = 18;
            loginResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginResult.imsdkRetCode);
            thisListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static boolean loginCallbackByNoPackage(int tag) {
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMLoginResult loginResult = new IMLoginResult(9);
            loginResult.imsdkRetCode = 9;
            loginResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginResult.imsdkRetCode);
            thisListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static boolean loginCallbackByResult(int tag, IMLoginResult result) {
        IMLogger.d("imsdk login " + tag + " callback return with imsdk code : " + result.imsdkRetCode);
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            thisListener.onLogin(result);
            loginListeners.remove(Integer.valueOf(tag));
            IMLoginStat.reportLogin();
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static boolean loginCallbackByCancel(int tag) {
        IMLogger.d("imsdk login " + tag + " canceled ! ");
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMLoginResult loginResult = new IMLoginResult(2);
            loginResult.imsdkRetCode = 2;
            loginResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginResult.imsdkRetCode);
            thisListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static boolean loginCallbackByException(int tag, IMException exception) {
        IMLogger.d("imsdk login " + tag + " callback error with imsdk code : " + exception.imsdkRetCode);
        IMLoginListener thisListener = loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            IMLoginResult loginResult = new IMLoginResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg);
            loginResult.retExtraJson = exception.retExtraJson;
            thisListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    protected static void loginCallbackBySystemError(int tag, Exception exception) {
        IMLogger.d("imsdk login " + tag + " callback system error with message : " + exception.getMessage());
        if (loginListeners.get(Integer.valueOf(tag)) != null) {
            IMLoginResult loginResult = new IMLoginResult(3);
            loginResult.imsdkRetCode = 3;
            loginResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginResult.imsdkRetCode);
            loginResult.thirdRetCode = exception.hashCode();
            loginResult.thirdRetMsg = exception.getMessage();
            currentListener.onLogin(loginResult);
            loginListeners.remove(Integer.valueOf(tag));
            return;
        }
        IMLogger.d("login listener not set, no callback !");
    }

    protected static void runLoginTask(final LoginTask loginTask) {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (loginListeners != null) {
                loginListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        try {
            loginTask.prepare();
            if (!isInitialized()) {
                loginCallbackByNotInitialized(thisTag);
                loginTask.onNoInitialized();
            } else if (!isChannelSetup()) {
                loginCallbackByNoChannel(thisTag);
                loginTask.onNoChannel();
            } else if (!isCurrentChannelAvailable()) {
                loginCallbackByNoPackage(thisTag);
                loginTask.onNoPackage();
            } else {
                loginTask.run(new IMCallback<IMLoginResult>() {
                    public void onSuccess(IMLoginResult result) {
                        IMLogin.loginCallbackByResult(thisTag, result);
                        loginTask.onSuccess(result);
                    }

                    public void onCancel() {
                        IMLogin.loginCallbackByCancel(thisTag);
                        loginTask.onCancel();
                    }

                    public void onError(IMException exception) {
                        IMLogin.loginCallbackByException(thisTag, exception);
                        loginTask.onError(exception);
                    }
                });
            }
        } catch (Exception exception) {
            loginCallbackBySystemError(thisTag, exception);
        }
    }

    protected static void setChannelPrefKeyForAutoLogin(String channel) {
        if (currentContext == null) {
            IMLogger.e("not initailized");
            return;
        }
        SharedPreferences.Editor editor = currentContext.getSharedPreferences(CHANNEL_PREF, 0).edit();
        if (editor == null) {
            IMLogger.e("get preference editor error !");
            return;
        }
        editor.putString(CHANNEL_PREF_KEY_FOR_AUTOLOGIN, channel);
        if (editor.commit()) {
            IMLogger.d("save user preference channel : " + channel);
        }
    }

    public static void login() {
        login(true);
    }

    public static void login(boolean needGuid) {
        loginWithPermissions(new ArrayList<>(), needGuid);
    }

    public static void loginWithPermissions(List<String> permissionList) {
        loginWithPermissions(permissionList, true);
    }

    public static void loginWithPermissions(final List<String> permissionList, final boolean needGuid) {
        runLoginTask(new LoginTask() {
            List<String> permissions;

            public void prepare() {
                if (permissionList == null || permissionList.isEmpty()) {
                    this.permissions = new ArrayList();
                } else {
                    this.permissions = permissionList;
                }
            }

            public void run(IMCallback<IMLoginResult> callback) {
                IMCallback<IMLoginResult> callback2 = callback;
                if (IMLogin.mSTBuilder != null) {
                    callback2 = IMLogin.mSTBuilder.create().proxyListener4EventReport(IMLogin.currentChannel, callback, "login-" + IMLogin.currentChannel + "-loginWithPermissions");
                }
                IMLogin.loginInstance.loginWithPermission(this.permissions, callback2, needGuid);
            }

            public void onSuccess(IMLoginResult loginResult) {
                IMLogin.setChannelPrefKeyForAutoLogin(IMLogin.currentChannel);
            }

            public void onCancel() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            public void onError(IMException exception) {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoInitialized() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoChannel() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoPackage() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }
        });
    }

    public static void quickLogin() {
        runLoginTask(new LoginTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMLoginResult> callback) {
                IMCallback<IMLoginResult> callback2 = callback;
                if (IMLogin.mSTBuilder != null) {
                    callback2 = IMLogin.mSTBuilder.create().proxyListener4EventReport(IMLogin.currentChannel, callback, "login-" + IMLogin.currentChannel + "-quickLogin");
                }
                IMLogin.loginInstance.quickLogin(callback2);
            }
        });
    }

    public static void strictLogin() {
        strictLoginWithPermissions(new ArrayList<>());
    }

    public static void strictLoginWithPermissions(final List<String> permissionList) {
        runLoginTask(new LoginTask() {
            /* access modifiers changed from: package-private */
            public void run(IMCallback<IMLoginResult> callback) {
                IMCallback<IMLoginResult> callback2 = callback;
                if (IMLogin.mSTBuilder != null) {
                    callback2 = IMLogin.mSTBuilder.create().proxyListener4EventReport(IMLogin.currentChannel, callback, "login-" + IMLogin.currentChannel + "-strictLoginWithPermissions");
                }
                IMLogin.loginInstance.strictLogin(permissionList, callback2, true);
            }

            public void onSuccess(IMLoginResult loginResult) {
                IMLogin.setChannelPrefKeyForAutoLogin(IMLogin.currentChannel);
            }

            public void onCancel() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            public void onError(IMException exception) {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoInitialized() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoChannel() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }

            /* access modifiers changed from: package-private */
            public void onNoPackage() {
                IMLogin.setChannelPrefKeyForAutoLogin("");
            }
        });
    }

    public static boolean isChannelInstalled() {
        if (loginInstance != null) {
            return loginInstance.isChannelInstalled();
        }
        IMLogger.e("login instance is null, please call setChannel first!");
        return false;
    }

    public static boolean isChannelSupportApi() {
        if (loginInstance != null) {
            return loginInstance.isChannelSupportApi();
        }
        IMLogger.e("login instance is null, please call setChannel first!");
        return false;
    }

    public static boolean isLogin() {
        return isInitialized() && loginInstance != null && loginInstance.isLogin();
    }

    public static void logout() {
        if (!isInitialized() || loginInstance == null) {
            IMLogger.w(currentChannel + " not initialized yet, nothing todo");
            return;
        }
        IMLogger.d(currentChannel + " is logging out");
        loginInstance.logout();
        IMLoginStat.reset();
    }

    public static IMLoginResult getLoginResult() {
        if (!isInitialized()) {
            IMLoginResult result = new IMLoginResult(9, "not initialized");
            result.imsdkRetCode = 17;
            result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
            return result;
        } else if (loginInstance == null) {
            IMLoginResult result2 = new IMLoginResult(9, "need set channel");
            result2.imsdkRetCode = 18;
            result2.imsdkRetMsg = IMErrorMsg.getMessageByCode(result2.imsdkRetCode);
            return result2;
        } else {
            IMLoginResult result3 = loginInstance.getLoginResult();
            if (result3 == null) {
                result3 = new IMLoginResult(10, "need to login first");
                result3.imsdkRetCode = 1001;
                result3.imsdkRetMsg = IMErrorMsg.getMessageByCode(result3.imsdkRetCode);
            }
            if (result3.retCode != 1) {
                return result3;
            }
            IMLoginStat.reportLoginResult(result3);
            return result3;
        }
    }

    public static void bind(String channel) {
        final int thisTag;
        IMLogger.d("bind to channel : " + channel);
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (currentListener != null) {
                loginListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        if (!isInitialized()) {
            loginCallbackByNotInitialized(thisTag);
        } else if (!isChannelSetup()) {
            loginCallbackByNoChannel(thisTag);
        } else if (!isCurrentChannelAvailable()) {
            loginCallbackByNoPackage(thisTag);
        } else if (!isLogin()) {
            IMException exception = new IMException(10, "need to login first");
            exception.imsdkRetCode = 10;
            exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(10);
            loginCallbackByException(thisTag, exception);
        } else {
            final IMLoginBase targetInstance = getInstance(channel);
            if (targetInstance == null) {
                IMException exception2 = new IMException(9, "bind target package not exist");
                exception2.imsdkRetCode = 9;
                exception2.imsdkRetMsg = IMErrorMsg.getMessageByCode(9);
                loginCallbackByException(thisTag, exception2);
                return;
            }
            if (!targetInstance.isInitialized()) {
                targetInstance.initialize(currentContext);
            }
            final IMLoginResult currentLoginResult = getLoginResult();
            IMCallback<IMLoginResult> callback = new IMCallback<IMLoginResult>() {
                public void onSuccess(IMLoginResult result) {
                    IMLogin.loginCallbackByResult(thisTag, result);
                    if (result.channelId == currentLoginResult.channelId) {
                        IMLogin.loginInstance.setLoginResult(result);
                    } else if (result.channelId == targetInstance.getChannelId()) {
                        targetInstance.setLoginResult(result);
                    }
                    IMLogin.setChannel(result.channel);
                }

                public void onCancel() {
                    IMLogin.loginCallbackByCancel(thisTag);
                }

                public void onError(IMException exception) {
                    IMLogin.loginCallbackByException(thisTag, exception);
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(getChannel(), callback);
            }
            targetInstance.bindChannel(currentLoginResult, callback);
        }
    }

    public static void bind(String imsdkChannel, String bindSubChannel, JSONObject extras) {
        if (loginInstance != null) {
            loginInstance.setBindSubChannel(bindSubChannel, extras);
        }
        bind(imsdkChannel);
    }

    protected static boolean getBindInfoCallbackByResult(int tag, IMLoginBindResult result) {
        IMLogger.d("imsdk login get bind data " + tag + " callback return with imsdk code : " + result.imsdkRetCode);
        IMLoginListenerEx thisListener = (IMLoginListenerEx) loginListeners.get(Integer.valueOf(tag));
        if (thisListener != null) {
            thisListener.onGetBindInfo(result);
            loginListeners.remove(Integer.valueOf(tag));
            return true;
        }
        IMLogger.d("login listener not set, no callback !");
        return false;
    }

    public static void getBindInfo() {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (currentListener != null) {
                loginListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        if (!isInitialized()) {
            IMLoginBindResult loginBindResult = new IMLoginBindResult(9);
            loginBindResult.imsdkRetCode = 17;
            loginBindResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginBindResult.imsdkRetCode);
            getBindInfoCallbackByResult(thisTag, loginBindResult);
        } else if (!isChannelSetup()) {
            IMLoginBindResult loginBindResult2 = new IMLoginBindResult(9);
            loginBindResult2.imsdkRetCode = 18;
            loginBindResult2.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginBindResult2.imsdkRetCode);
            getBindInfoCallbackByResult(thisTag, loginBindResult2);
        } else if (!isCurrentChannelAvailable()) {
            IMLoginBindResult loginBindResult3 = new IMLoginBindResult(9);
            loginBindResult3.imsdkRetCode = 9;
            loginBindResult3.imsdkRetMsg = IMErrorMsg.getMessageByCode(loginBindResult3.imsdkRetCode);
        } else {
            IMCallback<IMLoginBindResult> callback = new IMCallback<IMLoginBindResult>() {
                public void onSuccess(IMLoginBindResult result) {
                    IMLogin.getBindInfoCallbackByResult(thisTag, result);
                }

                public void onCancel() {
                    IMLoginBindResult loginBindResult = new IMLoginBindResult(2);
                    loginBindResult.imsdkRetCode = 2;
                    loginBindResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(10);
                    IMLogin.getBindInfoCallbackByResult(thisTag, loginBindResult);
                }

                public void onError(IMException exception) {
                    IMLogin.getBindInfoCallbackByResult(thisTag, new IMLoginBindResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(getChannel(), callback);
            }
            loginInstance.getBindInfo(callback);
        }
    }

    public static void autoLogin() {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (currentListener != null) {
                loginListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        String prefChannel = currentContext.getSharedPreferences(CHANNEL_PREF, 0).getString(CHANNEL_PREF_KEY_FOR_AUTOLOGIN, "");
        IMLogger.d("use saved channel : " + prefChannel);
        if (TextUtils.isEmpty(prefChannel)) {
            IMLogger.w("autoLogin : channel is not assigned and cannot get last saved channel !");
            IMException exception = new IMException(10);
            exception.imsdkRetCode = 1001;
            exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(exception.imsdkRetCode);
            loginCallbackByException(thisTag, exception);
        } else if (!isInitialized()) {
            loginCallbackByNotInitialized(thisTag);
        } else if (!isChannelSetup()) {
            loginCallbackByNoChannel(thisTag);
        } else if (!isCurrentChannelAvailable()) {
            loginCallbackByNoPackage(thisTag);
        } else if (getInstance(prefChannel) == null) {
            IMException exception2 = new IMException(9);
            exception2.imsdkRetCode = 9;
            exception2.imsdkRetMsg = IMErrorMsg.getMessageByCode(exception2.imsdkRetCode);
            loginCallbackByException(thisTag, exception2);
        } else {
            setChannel(prefChannel);
            IMCallback<IMLoginResult> callback = new IMCallback<IMLoginResult>() {
                public void onSuccess(IMLoginResult result) {
                    if (result.retCode == 1 || result.imsdkRetCode == 1) {
                        IMLogin.loginCallbackByResult(thisTag, result);
                        return;
                    }
                    IMLogin.setListener((IMLoginListener) IMLogin.loginListeners.get(Integer.valueOf(thisTag)));
                    IMLogin.login();
                }

                public void onCancel() {
                    IMLogin.setListener((IMLoginListener) IMLogin.loginListeners.get(Integer.valueOf(thisTag)));
                    IMLogin.login();
                }

                public void onError(IMException exception) {
                    IMLogin.setListener((IMLoginListener) IMLogin.loginListeners.get(Integer.valueOf(thisTag)));
                    IMLogin.login();
                }
            };
            if (mSTBuilder != null) {
                callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
            }
            loginInstance.quickLogin(callback);
        }
    }

    public static void setPlayingChannel(String channel) {
        IMUserState.setChannel(channel);
    }

    public static void activatePlayingReport(String extraJson) {
        IMUserState.activatePlayingReport(extraJson);
    }

    public static void deactivatePlayingReport() {
        IMUserState.deactivatePlayingReport();
    }

    public static void logout4Callback() {
        final int thisTag;
        synchronized (lock) {
            thisTag = listenerTag + 1;
            listenerTag = thisTag;
            if (currentListener != null) {
                loginListeners.put(Integer.valueOf(listenerTag), currentListener);
            }
        }
        if (!isInitialized() || loginInstance == null) {
            IMResult logoutRet = new IMResult();
            logoutRet.retCode = 11;
            logoutRet.imsdkRetCode = 17;
            logoutCallbackByResult(thisTag, logoutRet);
            IMLogger.w(currentChannel + " not initialized yet, nothing todo");
            return;
        }
        IMLogger.d(currentChannel + " is logging out");
        IMCallback<IMResult> callback = new IMCallback<IMResult>() {
            public void onSuccess(IMResult result) {
                IMLogin.logoutCallbackByResult(thisTag, result);
            }

            public void onCancel() {
                IMResult logoutResult = new IMResult(2);
                logoutResult.imsdkRetCode = 2;
                logoutResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(logoutResult.imsdkRetCode);
                IMLogin.logoutCallbackByResult(thisTag, logoutResult);
            }

            public void onError(IMException exception) {
                IMLogin.logoutCallbackByResult(thisTag, new IMResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg));
            }
        };
        if (mSTBuilder != null) {
            callback = mSTBuilder.create().proxyListener4EventReport(currentChannel, callback);
        }
        loginInstance.logout4callback(callback);
        IMLoginStat.reset();
    }

    protected static void logoutCallbackByResult(int tag, IMResult result) {
        IMLogger.d("imsdk logout callback data " + tag + " callback return with imsdk code : " + result.imsdkRetCode);
        try {
            IMLoginListenerEx thisListener = (IMLoginListenerEx) loginListeners.get(Integer.valueOf(tag));
            if (thisListener != null) {
                thisListener.onLogout(result);
                loginListeners.remove(Integer.valueOf(tag));
                return;
            }
            IMLogger.d("logout listener not set, no callback !");
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }
}
