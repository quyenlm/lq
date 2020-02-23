package com.tencent.imsdk.unity.tool;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.extend.tool.DeviceInfoUtils;
import com.tencent.imsdk.extend.tool.Tools;
import com.tencent.imsdk.sns.base.IMUrlResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.UrlUtils;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;

public class ToolHelper extends Tools {
    static final int IMSDK_CANCEL_CODE = 2;
    static final Context context = UnityPlayer.currentActivity;
    /* access modifiers changed from: private */
    public static volatile String unityGameObject = "Tencent.iMSDK.IMTool";

    public static String unityGetInfo() {
        return convertIMDeviceInfoToJsonStr(getInfo(context));
    }

    public static String convertIMDeviceInfoToJsonStr(DeviceInfoUtils.IMDeviceInfo deviceInfo) {
        try {
            return deviceInfo.toUnityString();
        } catch (JSONException e) {
            IMLogger.w("ToolHelper convertIMDeviceInfoToJsonStr exception : " + e.getMessage());
            return "";
        }
    }

    public static String unityGetAppVersionName() {
        return getAppVersionName(context);
    }

    public static int unityGetAppVersionCode() {
        return getAppVersionCode(context);
    }

    public static String unityGetOSName() {
        return getOSName();
    }

    public static String unityGetOSVersion() {
        return getOSVersion();
    }

    public static int unityGetWidth() {
        return getWidth(context);
    }

    public static int unityGetHeight() {
        return getHeight(context);
    }

    public static String unityGetNetwork() {
        return getNetwork(context);
    }

    public static String unityGetIMEI() {
        return getIMEI(context);
    }

    public static String unityGetOperators() {
        return getOperators(context);
    }

    public static String unityGetApn() {
        return getApn(context);
    }

    public static String unityGetBrand() {
        return getBrand();
    }

    public static String unityGetManufacturer() {
        return getManufacturer();
    }

    public static String unityGetModel() {
        return getModel();
    }

    public static String unityGetPhoneName() {
        return getPhoneName();
    }

    public static String unityGetLanguage() {
        return getLanguage();
    }

    public static String unityGetCountry() {
        return getCountry();
    }

    public static String unityGetAndroidId() {
        return getAndroidId(context);
    }

    public static String unityGetSeriesId() {
        return getSeriesId();
    }

    public static String unityGetMac() {
        return getMac(context);
    }

    public static String unityGetPackageChannelId() {
        return getPackageChannelId(context);
    }

    public static String unityGetGuestId() {
        return getGuestId(context);
    }

    /* JADX WARNING: type inference failed for: r7v22, types: [java.util.List] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unityInitSchemeUrl(final int r10, java.lang.String r11, final java.lang.String r12) {
        /*
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "in unityInitSchemeUrl : schemeData "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r11)
            java.lang.String r8 = ", callback tag : "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.String r7 = r7.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
            com.tencent.imsdk.unity.tool.ToolHelper$1 r4 = new com.tencent.imsdk.unity.tool.ToolHelper$1
            r4.<init>(r12, r10)
            android.content.Context r7 = context
            if (r7 == 0) goto L_0x00ad
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.List r7 = com.tencent.imsdk.extend.tool.JsonUtils.toList((java.lang.String) r11)     // Catch:{ JSONException -> 0x005d }
            r0 = r7
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ JSONException -> 0x005d }
            r5 = r0
            java.util.Iterator r7 = r5.iterator()     // Catch:{ JSONException -> 0x005d }
        L_0x003a:
            boolean r8 = r7.hasNext()     // Catch:{ JSONException -> 0x005d }
            if (r8 == 0) goto L_0x0061
            java.lang.Object r6 = r7.next()     // Catch:{ JSONException -> 0x005d }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ JSONException -> 0x005d }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x005d }
            r8.<init>()     // Catch:{ JSONException -> 0x005d }
            java.lang.String r9 = "schemeList:"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ JSONException -> 0x005d }
            java.lang.StringBuilder r8 = r8.append(r6)     // Catch:{ JSONException -> 0x005d }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x005d }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r8)     // Catch:{ JSONException -> 0x005d }
            goto L_0x003a
        L_0x005d:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0061:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "call instance unityInitSchemeUrl function : "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r5)
            java.lang.String r7 = r7.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "currentActivity name:"
            java.lang.StringBuilder r7 = r7.append(r8)
            android.app.Activity r8 = com.unity3d.player.UnityPlayer.currentActivity
            java.lang.Class r8 = r8.getClass()
            java.lang.String r8 = r8.getName()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
            android.app.Activity r7 = com.unity3d.player.UnityPlayer.currentActivity
            android.content.Intent r3 = r7.getIntent()
            android.net.Uri r1 = r3.getData()
            if (r1 != 0) goto L_0x00ae
            java.lang.String r7 = "no input data"
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
        L_0x00a8:
            android.app.Activity r7 = com.unity3d.player.UnityPlayer.currentActivity
            initSchemeUrl(r7, r5, r4)
        L_0x00ad:
            return
        L_0x00ae:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "data:"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r1.toString()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r7)
            goto L_0x00a8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.unity.tool.ToolHelper.unityInitSchemeUrl(int, java.lang.String, java.lang.String):void");
    }

    public static void unityGetShortUrl(final int callbackTag, String url, final String unityCallbackFunction) {
        IMLogger.d("in unityGetShortUrl " + callbackTag + ", " + url + ", " + unityCallbackFunction);
        try {
            IMConfig.initialize(UnityPlayer.currentActivity);
            UrlUtils.initialize(UnityPlayer.currentActivity);
            UrlUtils.shortUrl(url, new IMCallback<IMUrlResult>() {
                public void onSuccess(IMUrlResult result) {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + result.toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }

                public void onCancel() {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + new IMUrlResult(2, 2).toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }

                public void onError(IMException exception) {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + new IMUrlResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg).toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e("get short url error : " + e.getMessage());
        }
    }

    public static void unityGetShortUrl(final int callbackTag, String url, String urlTypeMark, final String unityCallbackFunction) {
        IMLogger.d("in unityGetShortUrl " + callbackTag + ", " + url + ", " + unityCallbackFunction);
        try {
            IMConfig.initialize(UnityPlayer.currentActivity);
            UrlUtils.initialize(UnityPlayer.currentActivity);
            UrlUtils.shortUrl(url, urlTypeMark, new IMCallback<IMUrlResult>() {
                public void onSuccess(IMUrlResult result) {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + result.toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }

                public void onCancel() {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + new IMUrlResult(2, 2).toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }

                public void onError(IMException exception) {
                    try {
                        UnityPlayer.UnitySendMessage(ToolHelper.unityGameObject, unityCallbackFunction, callbackTag + "|" + new IMUrlResult(exception.errorCode, exception.getMessage(), exception.imsdkRetCode, exception.imsdkRetMsg, exception.thirdRetCode, exception.thirdRetMsg).toUnityString());
                    } catch (Exception e) {
                        IMLogger.e("send unity message error : " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e("get short url error : " + e.getMessage());
        }
    }
}
