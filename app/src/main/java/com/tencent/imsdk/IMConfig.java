package com.tencent.imsdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.DeviceUuidFactory;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.apache.http.HttpHost;

public class IMConfig {
    private static final String DEBUG_LEVEL_META_NAME = "com.tencent.imsdk.debug.level";
    private static final String DEBUG_SERVER_HOST = "com.tencent.imsdk.DebugServerHost";
    private static final String DEFALT_NET_TIMEOUT = "com.tencent.imsdk.DefaultTimeout";
    private static final String FEEDBACK_GAMEID_META_NAME = "com.tencent.imsdk.feedback.gameId";
    private static final String GAME_ID_META_NAME = "com.tencent.imsdk.GameId";
    private static final String GUEST_CLIENT_KEY_META_NAME = "com.tencent.imsdk.GuestClientKey";
    private static final String IMSDK_INNERSTAT_NEW_FLAG = "com.tencent.imsdk.innerstat.flag";
    private static final String INSTALL_SOURCE = "com.tencent.imsdk.InstallSource";
    private static final String IS_DEBUG = "com.tencent.imsdk.IsDebug";
    private static final String NOTICE_INTERVAL = "com.tencent.imsdk.noticeTime";
    private static final String NOTICE_OPTION = "com.tencent.imsdk.needNotice";
    private static final String PUSH_OPTION = "com.tencent.imsdk.needPush";
    private static final String PUSH_SERVER = "com.tencent.imsdk.pushServer";
    private static final String SDK_SERVER_META_NAME = "com.tencent.imsdk.SdkServer";
    private static final String SDK_SERVER_VERSION = "v1.0";
    private static final String SDK_SERVER_VERSION_META_NAME = "com.tencent.imsdk.SdkServerVersion";
    public static final String[] SERVER_CERTIFICATE_FILES = {"iMSDKServer.cer", "iMSDKServer_0.cer", "iMSDKServer_1.cer"};
    public static final String SERVER_CERTIFICATE_FILE_DEFAULT = "NONE";
    private static final String SERVER_PLATFORM = "2";
    public static final String VIBER_API_META_NAME = "com.tencent.imsdk.ViberApi";
    private static final String WEBVIEW_TICKET_SERVER = "com.tencent.imsdk.webviewTicketServer";
    private static final String WECHAT_APPID_META_NAME = "com.tencent.imsdk.WechatAppId";
    private static final String XG_APP_ID = "XG_V2_ACCESS_ID";
    private static final String XG_APP_ID2 = "com.tencent.imsdk.XGAppId";
    private static final String ZALO_APP_ID = "appID";
    private static final String ZALO_APP_ID2 = "com.tencent.imsdk.zaloAppId";
    private static final String ZALO_APP_SECRET_KEY = "com.tencent.imsdk.zaloAppSecretKey";
    private static Context currentContext = null;
    public static boolean debugMode = false;
    /* access modifiers changed from: private */
    public static String mApkInstallSource = null;
    private static boolean mApkInstallSourceFlag = false;
    private static String mApkSHA1 = null;

    public static class IMErrorMessage {
    }

    public static boolean initialize(Context context) {
        if (context == null || currentContext == context) {
            return false;
        }
        currentContext = context;
        IMLogger.d("Before level control : curLevel = " + getDebugLevel());
        IMLogger.init(1, getDebugLevel());
        DeviceUuidFactory.getInstance(currentContext).syncGoogleAdId();
        IMErrorMsg.initialize(currentContext);
        try {
            new Thread(new Runnable() {
                public void run() {
                    String unused = IMConfig.mApkInstallSource = IMConfig.getInstallSource();
                }
            }).start();
            return true;
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            return true;
        }
    }

    public static Context getCurContext() {
        return currentContext;
    }

    public static String getPlatform() {
        return "2";
    }

    public static String getMD5Key() {
        try {
            if (currentContext == null) {
                IMLogger.w("config not initialized !");
                return "";
            }
            String md5Key = getGuestClientKey();
            if (!TextUtils.isEmpty(md5Key)) {
                return md5Key;
            }
            if (mApkSHA1 != null && mApkSHA1.length() > 0) {
                return mApkSHA1;
            }
            IMLogger.d("package name : " + currentContext.getApplicationContext().getPackageName());
            try {
                Signature[] signatures = currentContext.getPackageManager().getPackageInfo(currentContext.getPackageName(), 64).signatures;
                if (signatures == null || signatures.length <= 0) {
                    IMLogger.e("apk signatures is empty");
                    return "";
                }
                InputStream input = new ByteArrayInputStream(signatures[0].toByteArray());
                CertificateFactory cf = null;
                try {
                    cf = CertificateFactory.getInstance("X509");
                } catch (CertificateException e) {
                    IMLogger.e(e.getMessage());
                }
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA1");
                    X509Certificate c = (X509Certificate) cf.generateCertificate(input);
                    if (c != null) {
                        byte[] publicKey = md.digest(c.getEncoded());
                        StringBuffer hexString = new StringBuffer();
                        int i = 0;
                        while (i < publicKey.length) {
                            try {
                                String appendString = Integer.toHexString(publicKey[i] & 255);
                                if (appendString.length() == 1) {
                                    hexString.append("0");
                                }
                                hexString.append(appendString);
                                i++;
                            } catch (NoSuchAlgorithmException e2) {
                                e1 = e2;
                                StringBuffer stringBuffer = hexString;
                            } catch (CertificateEncodingException e3) {
                                e = e3;
                                StringBuffer stringBuffer2 = hexString;
                                IMLogger.e(e.getMessage());
                                return null;
                            } catch (CertificateException e4) {
                                e = e4;
                                StringBuffer stringBuffer3 = hexString;
                                IMLogger.e(e.getMessage());
                                return null;
                            } catch (Exception e5) {
                                e = e5;
                                StringBuffer stringBuffer4 = hexString;
                                IMLogger.e(e.getMessage());
                                return null;
                            }
                        }
                        mApkSHA1 = hexString.toString();
                        return hexString.toString();
                    }
                } catch (NoSuchAlgorithmException e6) {
                    e1 = e6;
                    IMLogger.e(e1.getMessage());
                    return null;
                } catch (CertificateEncodingException e7) {
                    e = e7;
                    IMLogger.e(e.getMessage());
                    return null;
                } catch (CertificateException e8) {
                    e = e8;
                    IMLogger.e(e.getMessage());
                    return null;
                } catch (Exception e9) {
                    e = e9;
                    IMLogger.e(e.getMessage());
                    return null;
                }
                return null;
            } catch (PackageManager.NameNotFoundException e10) {
                IMLogger.e(e10.getMessage());
                return "";
            }
        } catch (Exception e11) {
            IMLogger.e("get apk signature error : " + e11.getMessage());
        }
    }

    public static String getSdkServerVersion() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return SDK_SERVER_VERSION;
        }
        try {
            String sdkServerVersion = MetaDataUtil.readMetaDataFromApplication(currentContext, SDK_SERVER_VERSION_META_NAME);
            return (sdkServerVersion == null || sdkServerVersion.length() <= 0) ? SDK_SERVER_VERSION : sdkServerVersion;
        } catch (Exception e) {
            IMLogger.w("get sdk version error, use default : v1.0");
        }
    }

    public static String getSdkServerUrl() {
        return getSdkUrl(SDK_SERVER_META_NAME) + getSdkServerVersion();
    }

    public static String getSdkUrl(String metaName) {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String sdkServerUrl = MetaDataUtil.readMetaDataFromApplication(currentContext, metaName);
        if (sdkServerUrl == null || sdkServerUrl.length() <= 0) {
            IMLogger.e("need meta in application : " + metaName);
            return "";
        }
        if (!sdkServerUrl.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            sdkServerUrl = "https://" + sdkServerUrl;
        }
        if (!sdkServerUrl.endsWith(Constants.URL_PATH_DELIMITER)) {
            return sdkServerUrl + Constants.URL_PATH_DELIMITER;
        }
        return sdkServerUrl;
    }

    public static int getGameId() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return 0;
        }
        try {
            String gameIdString = MetaDataUtil.readMetaDataFromApplication(currentContext, GAME_ID_META_NAME);
            if (gameIdString == null || gameIdString.length() <= 0) {
                int gameId = MetaDataUtil.readMetaIntFromApplication(currentContext, GAME_ID_META_NAME);
                if (gameId > 0) {
                    return gameId;
                }
                IMLogger.e("need meta in application : com.tencent.imsdk.GameId");
            }
            return Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            IMLogger.d("getGameId NumberFormatException e = " + e.getMessage());
            return 0;
        }
    }

    public static boolean getNewInnerStatFlag() {
        if (currentContext != null) {
            return MetaDataUtil.readMetaDataFromApplication(currentContext, IMSDK_INNERSTAT_NEW_FLAG, false);
        }
        IMLogger.w("config not initialized !");
        return false;
    }

    public static String getInstallSource() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        } else if (mApkInstallSourceFlag) {
            return mApkInstallSource;
        } else {
            mApkInstallSourceFlag = true;
            mApkInstallSource = DeviceInfoUtils.getPackageChannelId(currentContext);
            if (mApkInstallSource != null && mApkInstallSource.length() > 0) {
                return mApkInstallSource;
            }
            mApkInstallSource = getChannelFromApk("imsdkchannel");
            IMLogger.d("imsdk channel is:" + mApkInstallSource);
            return mApkInstallSource;
        }
    }

    public static String getWechatAppId() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String wechatAppId = MetaDataUtil.readMetaDataFromApplication(currentContext, WECHAT_APPID_META_NAME);
        if (wechatAppId != null && wechatAppId.length() > 0) {
            return wechatAppId;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.WechatAppId");
        return wechatAppId;
    }

    public static String getGuestClientKey() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String guestClientId = MetaDataUtil.readMetaDataFromApplication(currentContext, GUEST_CLIENT_KEY_META_NAME);
        if (guestClientId == null || guestClientId.length() <= 0) {
            return guestClientId;
        }
        IMLogger.w("you are using debug config key : com.tencent.imsdk.GuestClientKey[" + guestClientId + "], please BE SURE delete this config and use publish " + "keystore to rebuild you project before you publish  you app !");
        return guestClientId;
    }

    public static int getDefaultTimeout() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return -1;
        }
        String defaultTimeout = MetaDataUtil.readMetaDataFromApplication(currentContext, DEFALT_NET_TIMEOUT);
        if (defaultTimeout == null || defaultTimeout.length() <= 0) {
            int timeout = MetaDataUtil.readMetaIntFromApplication(currentContext, DEFALT_NET_TIMEOUT);
            if (timeout <= 0) {
                return -1;
            }
            IMLogger.d("you are using http timeout config : com.tencent.imsdk.DefaultTimeout[" + timeout + "]");
            return timeout;
        }
        int timeout2 = Integer.parseInt(defaultTimeout);
        if (timeout2 <= 0) {
            return -1;
        }
        IMLogger.d("you are using http timeout config : com.tencent.imsdk.DefaultTimeout[" + timeout2 + "]");
        return timeout2;
    }

    public static boolean getNoticeOption() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return false;
        }
        String noticeOption = MetaDataUtil.readMetaDataFromApplication(currentContext, NOTICE_OPTION);
        if (TextUtils.isEmpty(noticeOption)) {
            IMLogger.e("need meta in application : com.tencent.imsdk.needNotice");
            return false;
        } else if (noticeOption.toLowerCase().contains("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDebugServerHost() {
        if (currentContext != null) {
            return MetaDataUtil.readMetaDataFromApplication(currentContext, DEBUG_SERVER_HOST);
        }
        IMLogger.w("config not initialized !");
        return "";
    }

    public static InputStream getCertificateFile(String fileName) {
        if (fileName == null || SERVER_CERTIFICATE_FILE_DEFAULT.equals(fileName) || fileName.length() <= 0) {
            return null;
        }
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return null;
        }
        try {
            InputStream inputStream = currentContext.getAssets().open(fileName);
            if (inputStream != null) {
                return inputStream;
            }
        } catch (IOException e) {
            IMLogger.w("need assert file : " + fileName + ", " + e.getMessage());
        }
        return null;
    }

    public static int getNoticeInterval() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return -1;
        }
        String defauInterval = MetaDataUtil.readMetaDataFromApplication(currentContext, NOTICE_INTERVAL);
        if (TextUtils.isEmpty(defauInterval)) {
            IMLogger.e("need meta in application : com.tencent.imsdk.noticeTime");
            return -1;
        }
        int interval = Integer.parseInt(defauInterval);
        if (interval <= 0) {
            return -1;
        }
        return interval;
    }

    public static boolean getPushOption() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return false;
        }
        String pushOption = MetaDataUtil.readMetaDataFromApplication(currentContext, PUSH_OPTION);
        if (TextUtils.isEmpty(pushOption)) {
            IMLogger.e("need meta in application : com.tencent.imsdk.needPush");
            return false;
        } else if (pushOption.toLowerCase().contains("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPushServerUrl() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String sdkServerUrl = getSdkServerUrl();
        if (TextUtils.isEmpty(sdkServerUrl)) {
            return "";
        }
        String pushServerUrl = MetaDataUtil.readMetaDataFromApplication(currentContext, PUSH_SERVER);
        if (!TextUtils.isEmpty(pushServerUrl)) {
            return sdkServerUrl + pushServerUrl;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.pushServer");
        return "";
    }

    public static String getWebviewTicketServerUrl() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String sdkServerUrl = getSdkServerUrl();
        if (TextUtils.isEmpty(sdkServerUrl)) {
            return "";
        }
        String webviewTicketServerUrl = MetaDataUtil.readMetaDataFromApplication(currentContext, WEBVIEW_TICKET_SERVER);
        if (!TextUtils.isEmpty(webviewTicketServerUrl)) {
            return sdkServerUrl + webviewTicketServerUrl;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.webviewTicketServer");
        return "";
    }

    public static String getZaloAppId() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String zaloAppId = MetaDataUtil.readMetaDataFromApplication(currentContext, ZALO_APP_ID);
        if (zaloAppId != null && zaloAppId.length() > 0) {
            return zaloAppId;
        }
        String zaloAppId2 = MetaDataUtil.readMetaDataFromApplication(currentContext, ZALO_APP_ID2);
        if (zaloAppId2 != null && zaloAppId2.length() > 0) {
            return zaloAppId2;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.zaloAppId");
        return zaloAppId2;
    }

    public static String getXGAppId() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String xgAppId = MetaDataUtil.readMetaDataFromApplication(currentContext, XG_APP_ID);
        if (xgAppId != null && xgAppId.length() > 0) {
            return xgAppId;
        }
        String xgAppId2 = MetaDataUtil.readMetaDataFromApplication(currentContext, XG_APP_ID2);
        if (xgAppId2 != null && xgAppId2.length() > 0) {
            return xgAppId2;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.XGAppId");
        return xgAppId2;
    }

    public static String getZaloAppSecretKey() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String zaloAppSecretKey = MetaDataUtil.readMetaDataFromApplication(currentContext, ZALO_APP_SECRET_KEY);
        if (zaloAppSecretKey != null && zaloAppSecretKey.length() > 0) {
            return zaloAppSecretKey;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.zaloAppSecretKey");
        return zaloAppSecretKey;
    }

    public static String getFeedbackGameId() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return "";
        }
        String feedbackGameId = MetaDataUtil.readMetaDataFromApplication(currentContext, FEEDBACK_GAMEID_META_NAME);
        if (feedbackGameId != null && feedbackGameId.length() > 0) {
            return feedbackGameId;
        }
        IMLogger.e("need meta in application : com.tencent.imsdk.WechatAppId");
        return feedbackGameId;
    }

    public static int getDebugLevel() {
        if (currentContext == null) {
            IMLogger.w("config not initialized !");
            return 2;
        }
        String debugLevel = MetaDataUtil.readMetaDataFromApplication(currentContext, DEBUG_LEVEL_META_NAME);
        if (debugLevel == null || debugLevel.length() <= 0) {
            int debugLevelInt = MetaDataUtil.readMetaIntFromApplication(currentContext, DEBUG_LEVEL_META_NAME);
            if (debugLevelInt >= 2 && debugLevelInt <= 7) {
                return debugLevelInt;
            }
            IMLogger.e("need meta in application : com.tencent.imsdk.debug.level");
            return 2;
        }
        int level = 2;
        try {
            level = Integer.parseInt(debugLevel);
        } catch (NumberFormatException ex) {
            IMLogger.d("get debug level error : " + ex.getMessage());
        }
        if (level > 7 || level < 2) {
            level = 2;
        }
        return level;
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.IMConfig.getChannelFromApk(java.lang.String):java.lang.String");
    }

    public static boolean setDebugMode(boolean debug) {
        debugMode = debug;
        return debug;
    }

    public static boolean isDebugMode() {
        if (debugMode) {
            return true;
        }
        return MetaDataUtil.readMetaDataFromApplication(currentContext, IS_DEBUG, debugMode);
    }
}
