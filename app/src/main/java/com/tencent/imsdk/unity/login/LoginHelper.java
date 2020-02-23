package com.tencent.imsdk.unity.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.api.IMLoginListenerEx;
import com.tencent.imsdk.sns.api.IMUserState;
import com.tencent.imsdk.sns.base.IMLoginBindResult;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import java.util.ArrayList;
import org.json.JSONObject;

public class LoginHelper extends IMLogin {
    private static final int IMSDK_INVALID_ARGUMENT = 11;
    private static final int IMSDK_SYSTEM_ERROR = 3;
    public static volatile Context currentContext = null;
    /* access modifiers changed from: private */
    public static volatile String unityGameObject = "Tencent.iMSDK.IMLogin";

    interface UnityTask {
        void run();
    }

    protected static void runUnityTask(final String functionTag, int callbackTag, final String unityCallbackFunction, UnityTask task) {
        final int thisTag = callbackTag;
        try {
            IMLogin.setListener(new IMLoginListenerEx() {
                public void onLogin(final IMLoginResult result) {
                    ((Activity) LoginHelper.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d(functionTag + " finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(LoginHelper.unityGameObject, unityCallbackFunction, thisTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("send unity message error : " + e.getMessage());
                            }
                        }
                    });
                }

                public void onGetBindInfo(final IMLoginBindResult result) {
                    ((Activity) LoginHelper.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d(functionTag + " finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(LoginHelper.unityGameObject, unityCallbackFunction, thisTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("send unity message error : " + e.getMessage());
                            }
                        }
                    });
                }

                public void onLogout(final IMResult result) {
                    ((Activity) LoginHelper.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                IMLogger.d(functionTag + " finished, calling unity : " + result.toUnityString());
                                UnityPlayer.UnitySendMessage(LoginHelper.unityGameObject, unityCallbackFunction, thisTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("send unity message error : " + e.getMessage());
                            }
                        }
                    });
                }
            });
            task.run();
        } catch (Exception e) {
            IMLogger.e("unity " + functionTag + " error : " + e.getMessage());
            callbackByException(unityCallbackFunction, thisTag, e);
        }
    }

    protected static void callbackByError(String unityCallbackFunction, int callbackTag, int retCode, String retMsg, int iMSDKRetCode, int thirdCode, String thirdMsg) {
        IMResult imResult = new IMResult(retCode, retMsg);
        imResult.imsdkRetCode = iMSDKRetCode;
        imResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(imResult.imsdkRetCode);
        imResult.thirdRetCode = thirdCode;
        imResult.thirdRetMsg = thirdMsg;
        try {
            UnityPlayer.UnitySendMessage(unityGameObject, unityCallbackFunction, callbackTag + "|" + imResult.toUnityString());
        } catch (Exception ex) {
            IMLogger.e("send unity message error : " + ex.getMessage());
        }
    }

    protected static void callbackByException(String unityCallbackFunction, int callbackTag, Exception e) {
        callbackByError(unityCallbackFunction, callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
    }

    public static boolean initialize() {
        try {
            currentContext = UnityPlayer.currentActivity;
            return IMLogin.initialize(currentContext);
        } catch (Exception e) {
            IMLogger.e("unity initialize error : " + e.getMessage());
            return false;
        }
    }

    public static boolean initialize(String channel) {
        IMLogger.d("in unity initialize : " + channel);
        try {
            currentContext = UnityPlayer.currentActivity;
            return IMLogin.initialize(currentContext, channel);
        } catch (Exception e) {
            IMLogger.e("unity initialize error : " + e.getMessage());
            return false;
        }
    }

    public static boolean setChannel(String channel) {
        IMLogger.d("in unity set channel : " + channel);
        try {
            return IMLogin.setChannel(channel);
        } catch (Exception e) {
            IMLogger.e("unity set channel error : " + e.getMessage());
            return false;
        }
    }

    public static void quickLogin(int callbackTag) {
        IMLogger.d("in unity quick login : " + callbackTag);
        runUnityTask("quick login", callbackTag, "OnQuickLogin", new UnityTask() {
            public void run() {
                IMLogin.quickLogin();
            }
        });
    }

    public static void autoLogin(int callbackTag) {
        IMLogger.d("in unity auto login : " + callbackTag);
        runUnityTask("auto login", callbackTag, "OnAutoLogin", new UnityTask() {
            public void run() {
                IMLogin.autoLogin();
            }
        });
    }

    protected static ArrayList<String> parsePermissionString(String permissions) {
        if (permissions == null) {
            return new ArrayList<>();
        }
        String[] permissionArray = permissions.split("\\,");
        ArrayList<String> permissionList = new ArrayList<>();
        for (String item : permissionArray) {
            if (item.length() > 0) {
                permissionList.add(item);
            }
        }
        return permissionList;
    }

    public static void loginWithPermissions(int callbackTag, final String permissions, final boolean needGuid) {
        IMLogger.d("in unity loginWithPermissions : " + permissions + ", needGuid : " + needGuid + ", callback tag : " + callbackTag);
        runUnityTask("loginWithPermissions", callbackTag, "OnLogin", new UnityTask() {
            public void run() {
                ArrayList<String> permissionList = LoginHelper.parsePermissionString(permissions);
                IMLogger.d("call instance login function : " + permissionList + "|" + needGuid);
                IMLogin.loginWithPermissions(permissionList, needGuid);
            }
        });
    }

    public static void strictLoginWithPermissions(int callbackTag, final String permissions, final boolean needGuid) {
        IMLogger.d("in unity strictLoginWithPermissions : " + permissions + ", needGuid : " + needGuid + ", callback tag : " + callbackTag);
        runUnityTask("strictLoginWithPermissions", callbackTag, "OnLogin", new UnityTask() {
            public void run() {
                ArrayList<String> permissionList = LoginHelper.parsePermissionString(permissions);
                IMLogger.d("call instance login function : " + permissionList + "|" + needGuid);
                IMLogin.strictLoginWithPermissions(permissionList);
            }
        });
    }

    public static void logout() {
        IMLogin.logout();
    }

    public static void logout4Callback(int callbackTag) {
        IMLogger.d("in unity logout with callback : " + callbackTag);
        runUnityTask("logout with callback", callbackTag, "OnLogout", new UnityTask() {
            public void run() {
                IMLogin.logout4Callback();
            }
        });
    }

    public static boolean isLogin() {
        return IMLogin.isLogin();
    }

    public static String unityGetLoginResult() {
        String returnStr = "{}";
        try {
            returnStr = IMLogin.getLoginResult().toUnityString();
        } catch (Exception e) {
            IMLogger.e("unity get login result error : " + e.getMessage());
        }
        IMLogger.d("get login result json string : " + returnStr);
        return returnStr;
    }

    public static void bind(int callbackTag, final String channel) {
        IMLogger.d("in unity bind : " + channel);
        runUnityTask("bind", callbackTag, "OnBind", new UnityTask() {
            public void run() {
                IMLogin.bind(channel);
            }
        });
    }

    public static void bind(final int callbackTag, final String imsdkChannel, final String bindSubChannel, final String extrasJsonStr) {
        IMLogger.d("in unity bind channel: " + imsdkChannel + ", bindSubChannel : " + bindSubChannel);
        runUnityTask("bind with sub channel", callbackTag, "OnBind", new UnityTask() {
            public void run() {
                if (TextUtils.isEmpty(extrasJsonStr)) {
                    IMLogin.bind(imsdkChannel, bindSubChannel, (JSONObject) null);
                    return;
                }
                try {
                    IMLogin.bind(imsdkChannel, bindSubChannel, new JSONObject(extrasJsonStr));
                } catch (Exception e) {
                    IMLogger.e("unity parse extra json string error : " + e.getMessage());
                    LoginHelper.callbackByError("OnBind", callbackTag, 3, e.getMessage(), 11, -1, e.getMessage());
                }
            }
        });
    }

    public static void getBindInfo(int callbackTag) {
        IMLogger.d("in unity get bind info : " + callbackTag);
        runUnityTask("get bind info", callbackTag, "OnGetBindInfo", new UnityTask() {
            public void run() {
                IMLogin.getBindInfo();
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c A[SYNTHETIC, Splitter:B:25:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0078 A[SYNTHETIC, Splitter:B:31:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getChannelFromApk(java.lang.String r14) {
        /*
            android.content.Context r12 = currentContext
            android.content.pm.ApplicationInfo r0 = r12.getApplicationInfo()
            java.lang.String r8 = r0.sourceDir
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "META-INF/"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r12 = r12.append(r14)
            java.lang.String r6 = r12.toString()
            java.lang.String r7 = ""
            r10 = 0
            java.util.zip.ZipFile r11 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x0066 }
            r11.<init>(r8)     // Catch:{ IOException -> 0x0066 }
            java.util.Enumeration r3 = r11.entries()     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
        L_0x0027:
            boolean r12 = r3.hasMoreElements()     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
            if (r12 == 0) goto L_0x003e
            java.lang.Object r4 = r3.nextElement()     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
            java.util.zip.ZipEntry r4 = (java.util.zip.ZipEntry) r4     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
            java.lang.String r5 = r4.getName()     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
            boolean r12 = r5.startsWith(r6)     // Catch:{ IOException -> 0x0084, all -> 0x0081 }
            if (r12 == 0) goto L_0x0027
            r7 = r5
        L_0x003e:
            if (r11 == 0) goto L_0x0087
            r11.close()     // Catch:{ IOException -> 0x0060 }
            r10 = r11
        L_0x0044:
            java.lang.String r12 = "_"
            java.lang.String[] r9 = r7.split(r12)
            java.lang.String r1 = ""
            if (r9 == 0) goto L_0x005f
            int r12 = r9.length
            r13 = 2
            if (r12 < r13) goto L_0x005f
            r12 = 0
            r12 = r9[r12]
            int r12 = r12.length()
            int r12 = r12 + 1
            java.lang.String r1 = r7.substring(r12)
        L_0x005f:
            return r1
        L_0x0060:
            r2 = move-exception
            r2.printStackTrace()
            r10 = r11
            goto L_0x0044
        L_0x0066:
            r2 = move-exception
        L_0x0067:
            r2.printStackTrace()     // Catch:{ all -> 0x0075 }
            if (r10 == 0) goto L_0x0044
            r10.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0044
        L_0x0070:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0044
        L_0x0075:
            r12 = move-exception
        L_0x0076:
            if (r10 == 0) goto L_0x007b
            r10.close()     // Catch:{ IOException -> 0x007c }
        L_0x007b:
            throw r12
        L_0x007c:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x007b
        L_0x0081:
            r12 = move-exception
            r10 = r11
            goto L_0x0076
        L_0x0084:
            r2 = move-exception
            r10 = r11
            goto L_0x0067
        L_0x0087:
            r10 = r11
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.unity.login.LoginHelper.getChannelFromApk(java.lang.String):java.lang.String");
    }

    public static void setPlayingReportChannel(String channel) {
        try {
            IMUserState.setChannel(channel);
        } catch (Exception e) {
            IMLogger.e("set state report channel error : " + e.getMessage());
        }
    }

    public static void activatePlayingReport(final String extraJson) {
        try {
            ((Activity) currentContext).runOnUiThread(new Runnable() {
                public void run() {
                    IMUserState.activatePlayingReport(extraJson);
                }
            });
        } catch (Exception ex) {
            IMLogger.e(ex.getMessage());
        }
    }

    public static void deactivatePlayingReport() {
        try {
            IMUserState.deactivatePlayingReport();
        } catch (Exception ex) {
            IMLogger.e(ex.getMessage());
        }
    }

    public static boolean isChannelInstalled() {
        try {
            return IMLogin.isChannelInstalled();
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            return false;
        }
    }

    public static boolean isChannelSupportApi() {
        try {
            return IMLogin.isChannelSupportApi();
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            return false;
        }
    }
}
