package com.tencent.imsdk.pay.api;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.api.IMSDKApi;
import com.tencent.imsdk.pay.base.IMPayBase;
import com.tencent.imsdk.pay.entity.IMPayRequestInfo;
import com.tencent.imsdk.pay.entity.IMPayResponseInfo;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.json.JsonSerializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class IMPay {
    protected static String ENV = "release";
    public static String IDC_CA = "ca";
    public static String IDC_HK = "hk";
    public static String IDC_VN = "vn";
    protected static final String PF = "IEG_iTOP";
    protected static final String PF_CONFIG_FILE = "IMSDK/IMSDKPFMap.json";
    protected static final String PF_UNKNOWN_CHANNEL = "UnknowChannel";
    protected static String currentChannel = "";
    protected static IMPayExtendListener currentExtendListener = null;
    protected static IMPayListener currentListener = null;
    protected static IMPayListener2 currentListener2 = null;
    protected static IMPayBase currentPayInstance = null;
    protected static Activity currentactivity = null;
    protected static HashMap<String, IMPayBase> payInstanceList = new HashMap<>();

    public static boolean isInitialize() {
        if (currentPayInstance != null) {
            return true;
        }
        IMLogger.e("current pay Channel is null, did you add the package and call initialize function");
        return false;
    }

    public static void initialize(IMPayChannelsInterface initInterface, Activity con) {
        initialize(initInterface, con, "");
    }

    public static void initialize(IMPayChannelsInterface initInterface, Activity con, String publickey) {
        try {
            currentactivity = con;
            IMConfig.initialize(currentactivity);
            currentPayInstance = null;
            payInstanceList.clear();
            if (initInterface == null) {
                IMLogger.d("IMPay: ", "The initInterface is null, you have to implement this interface");
            } else if (initInterface.setPayChannels() != null && initInterface.setPayChannels().length != 0) {
                for (String instance : initInterface.setPayChannels()) {
                    getInstance(instance, con, publickey);
                }
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getInstance(String setChannel, Activity con, String publickey) {
        IMLogger.d("getInstance Channel to : " + setChannel);
        String Channel = setChannel;
        if (!payInstanceList.containsKey(Channel)) {
            IMLogger.d("switch Channel to : " + Channel);
            String ChannelClass = "com.tencent.imsdk.pay." + Channel.toLowerCase() + "." + Channel + "PayHelper";
            IMLogger.d("try to get class : " + ChannelClass);
            IMPayBase tempInstance = (IMPayBase) IMModules.getInstance().getModule(ChannelClass);
            if (tempInstance != null) {
                tempInstance.initialize(currentactivity);
                tempInstance.init(con, publickey);
                payInstanceList.put(Channel, tempInstance);
                return;
            }
            IMLogger.e("get class : " + ChannelClass + " failed !");
        }
    }

    public static boolean setChannel(String channel) {
        IMLogger.d("setChannel java pay channel : " + channel);
        currentChannel = channel;
        if (payInstanceList.containsKey(currentChannel)) {
            currentPayInstance = payInstanceList.get(channel);
            currentPayInstance.setEnv(ENV);
            IMLogger.d("setChannel java currentPayInstance is channel : " + channel);
            return true;
        }
        currentPayInstance = null;
        IMLogger.d("setChannel java fail, Channel name mismatching");
        return false;
    }

    public static String getChannel() {
        return currentChannel;
    }

    @Deprecated
    public static void setPayListener(IMPayListener payListener) {
        try {
            if (currentPayInstance != null) {
                currentListener = payListener;
                currentPayInstance.setPayListener(currentListener);
                return;
            }
            IMLogger.e("setPayListener fail,currentPayInstance is null");
        } catch (Exception e) {
            IMLogger.e("set pay listener failed : " + e.getMessage());
        }
    }

    @Deprecated
    public static void setPayListener2(IMPayListener2 payListener2) {
        try {
            if (currentPayInstance != null) {
                currentListener2 = payListener2;
                currentPayInstance.setPayListener2(currentListener2);
                return;
            }
            IMLogger.e("setPayListener fail,currentPayInstance is null");
        } catch (Exception e) {
            IMLogger.e("set pay listener 2 failed : " + e.getMessage());
        }
    }

    @Deprecated
    public static void prepare(final IMPayRequestInfo requestInfo) {
        try {
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.prepare(requestInfo);
                    } else {
                        IMLogger.e("currentPayInstance is null, call setChannel first !");
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e("prepare failed : " + e.getMessage());
        }
    }

    @Deprecated
    public static void prepare2(final IMPayRequestInfo requestInfo, final boolean needPayUpdate) {
        try {
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.prepare2(requestInfo, needPayUpdate);
                    } else {
                        IMLogger.e("currentPayInstance is null");
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e("prepare 2 failed : " + e.getMessage());
        }
    }

    public static void deinit() {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.deinit();
            } else {
                IMLogger.e("currentPayInstance is null");
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void prepareExtend(final Object object) {
        try {
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.prepareExtend(object);
                    } else {
                        IMLogger.e("currentPayInstance is null");
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void prepare2Extend(final Object object, final boolean needPayUpdate) {
        try {
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.prepare2Extend(object, needPayUpdate);
                    } else {
                        IMLogger.e("currentPayInstance is null");
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void realPayExtend(String content) {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.realPayExtend(content);
            } else {
                IMLogger.e("currentPayInstance is null");
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void realPrepareExtend(String content) {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.realPrepareExtend(content);
            } else {
                IMLogger.e("currentPayInstance is null");
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void realPrepare2Extend(String content, boolean needPayUpdate) {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.realPrepare2Extend(content, needPayUpdate);
            } else {
                IMLogger.e("currentPayInstance is null");
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void realGetMpExtend(String content) {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.realGetMpExtend(content);
            } else {
                IMLogger.e("currentPayInstance is null");
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void setPayExtendListener(IMPayExtendListener payListener) {
        try {
            if (currentPayInstance != null) {
                currentExtendListener = payListener;
                currentPayInstance.setPayExtendListener(currentExtendListener);
                return;
            }
            IMLogger.e("setPayExtendListener fail,currentPayInstance is null");
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void setCppPayListener() {
        IMLogger.d("java setCppPayListener start");
        try {
            currentListener = new IMPayListener() {
                public void onLoginExpiry() {
                    IMPayNativeListener.onLoginExpiry();
                }

                public void onPayCallBack(IMPayResponseInfo responseInfo) {
                    IMPayNativeListener.onPayCallBack(responseInfo);
                }

                public void onGetMpCallBack(IMPayResponseInfo responseInfo) {
                    IMPayNativeListener.onGetMpCallBack(responseInfo);
                }

                public void onGetSkuCallBack(IMPayResponseInfo responseInfo) {
                    IMPayNativeListener.onGetSkuCallBack(responseInfo);
                }

                public void onSetUpFinishSuccess() {
                    IMPayNativeListener.onSetUpFinishSuccess();
                }

                public void onSetUpFinishFailure(IMPayRequestInfo requestInfo, String errorMessage, int code) {
                    IMPayNativeListener.onSetUpFinishFailure(requestInfo, errorMessage, code);
                }

                public void onRestoreFinishFailue(String errorMessage, int code) {
                    IMPayNativeListener.onRestoreFinishFailue(errorMessage, code);
                }

                public void onQueryProductInfoFailure(IMPayRequestInfo requestInfo) {
                    IMPayNativeListener.onQueryProductInfoFailure(requestInfo);
                }

                public void onPurchaseFinishSuccess(IMPayRequestInfo requestInfo) {
                    IMPayNativeListener.onPurchaseFinishSuccess(requestInfo);
                }

                public void onPurchaseFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
                    IMPayNativeListener.onPurchaseFinishFailuer(requestInfo, errorMessage, code);
                }

                public void onOrderFinishSuccess(IMPayRequestInfo requestInfo) {
                    IMPayNativeListener.onOrderFinishSuccess(requestInfo);
                }

                public void onOrderFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
                    IMPayNativeListener.onOrderFinishFailuer(requestInfo, errorMessage, code);
                }

                public void onNetWorkError(IMPayRequestInfo requestInfo, int code) {
                    IMPayNativeListener.onNetWorkError(requestInfo, code);
                }

                public void onDeliverFinishSuccess(IMPayRequestInfo requestInfo) {
                    IMPayNativeListener.onDeliverFinishSuccess(requestInfo);
                }

                public void onDeliverFinishFailuer(IMPayRequestInfo requestInfo, String errorMessage, int code) {
                    IMPayNativeListener.onDeliverFinishFailuer(requestInfo, errorMessage, code);
                }
            };
            currentPayInstance.setPayListener(currentListener);
            currentListener2 = new IMPayListener2() {
                public void onPayUpdateCallBack(int retCode, String info) {
                    IMPayNativeListener.onPayUpdateCallBack(retCode, info);
                }
            };
            currentPayInstance.setPayListener2(currentListener2);
            currentExtendListener = new IMPayExtendListener() {
                public void onLoginExpiry() {
                    IMPayNativeExtendListener.onLoginExpiry();
                }

                public void onPayCallBack(Object retObject) {
                    try {
                        IMPayNativeExtendListener.onPayCallBack(((JsonSerializable) retObject).toJSONString());
                    } catch (JSONException e) {
                        IMLogger.d("native get pay callback error : " + e.getMessage());
                    }
                }

                public void onGetProductCallBack(int retCode, String retMsg, List<Object> retObject) {
                    try {
                        List<String> retList = new ArrayList<>();
                        Iterator<Object> it = retObject.iterator();
                        while (it.hasNext()) {
                            retList.add(((JsonSerializable) it.next()).toJSONString());
                        }
                        IMPayNativeExtendListener.onGetProductCallBack(retCode, retMsg, retList);
                    } catch (JSONException e) {
                        IMLogger.d("native get product callback error : " + e.getMessage());
                    }
                }

                public void onGetMpCallBack(Object retObject) {
                    try {
                        IMPayNativeExtendListener.onGetMpCallBack(((JsonSerializable) retObject).toJSONString());
                    } catch (JSONException e) {
                        IMLogger.d("native get MP callback error : " + e.getMessage());
                    }
                }
            };
            currentPayInstance.setPayExtendListener(currentExtendListener);
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static String getPF() {
        return getPF("", "", "", "");
    }

    public static String getPF(String registerChannel, String setupChannel, String appCode, String openid) {
        String registerStr;
        String setupStr;
        String appCodeStr;
        String openIdStr;
        String loginType;
        if (TextUtils.isEmpty(registerChannel)) {
            registerStr = "2001";
        } else {
            registerStr = registerChannel;
        }
        if (TextUtils.isEmpty(setupChannel)) {
            setupStr = "2011";
        } else {
            setupStr = setupChannel;
        }
        if (TextUtils.isEmpty(appCode)) {
            appCodeStr = "AppCode";
        } else {
            appCodeStr = appCode;
        }
        String appGameId = String.valueOf(IMConfig.getGameId());
        if (TextUtils.isEmpty(openid)) {
            openIdStr = "1000000";
        } else {
            openIdStr = openid;
        }
        IMLoginResult loginResult = IMSDKApi.Login.getLoginResult();
        if (loginResult.retCode == 1 || loginResult.imsdkRetCode == 1) {
            String openId = IMSDKApi.Login.getLoginResult().openId;
            String loginChannelStr = IMSDKApi.Login.getLoginResult().channel;
            if (TextUtils.isEmpty(openIdStr)) {
                if (TextUtils.isEmpty(openId)) {
                    openIdStr = "1000000";
                } else {
                    openIdStr = openId;
                }
            }
            if (TextUtils.isEmpty(loginChannelStr)) {
                loginType = PF_UNKNOWN_CHANNEL;
            } else {
                loginType = loginChannelStr;
            }
        } else {
            if (TextUtils.isEmpty(openIdStr)) {
                openIdStr = "1000000";
            }
            loginType = "guest";
        }
        String loginType2 = matchLoginType(loginType);
        if (loginType.equalsIgnoreCase(PF_UNKNOWN_CHANNEL)) {
            try {
                if (currentPayInstance != null) {
                    return currentPayInstance.getPF(registerChannel, setupChannel, appCode, openid);
                }
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
        }
        return "IEG_iTOP-" + registerStr + "-android-" + setupStr + "-" + loginType2 + "-" + appGameId + "-" + openIdStr + "-" + appCodeStr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0072 A[SYNTHETIC, Splitter:B:21:0x0072] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0077 A[SYNTHETIC, Splitter:B:24:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007c A[SYNTHETIC, Splitter:B:27:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x017a A[SYNTHETIC, Splitter:B:62:0x017a] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x017f A[SYNTHETIC, Splitter:B:65:0x017f] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0184 A[SYNTHETIC, Splitter:B:68:0x0184] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String matchLoginTypeFromConfig(java.lang.String r21, java.lang.String r22) {
        /*
            java.lang.String r5 = "UnknowChannel"
            android.app.Activity r17 = currentactivity
            android.app.Activity r18 = currentactivity
            if (r18 != 0) goto L_0x0011
            android.content.Context r17 = com.tencent.imsdk.IMConfig.getCurContext()
            if (r17 != 0) goto L_0x0011
            r6 = r5
            r7 = r5
        L_0x0010:
            return r7
        L_0x0011:
            android.content.res.AssetManager r2 = r17.getAssets()
            r9 = 0
            r10 = 0
            r3 = 0
            java.lang.String r12 = "{}"
            r0 = r22
            java.io.InputStream r9 = r2.open(r0)     // Catch:{ IOException -> 0x0210 }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0210 }
            java.lang.String r18 = "UTF-8"
            java.nio.charset.Charset r18 = java.nio.charset.Charset.forName(r18)     // Catch:{ IOException -> 0x0210 }
            r0 = r18
            r11.<init>(r9, r0)     // Catch:{ IOException -> 0x0210 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0213, all -> 0x0207 }
            r4.<init>(r11)     // Catch:{ IOException -> 0x0213, all -> 0x0207 }
            java.lang.String r12 = ""
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r16.<init>()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
        L_0x0039:
            java.lang.String r14 = r4.readLine()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            if (r14 == 0) goto L_0x0097
            r0 = r16
            r0.append(r14)     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            goto L_0x0039
        L_0x0045:
            r8 = move-exception
            r3 = r4
            r10 = r11
        L_0x0048:
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x0177 }
            r18.<init>()     // Catch:{ all -> 0x0177 }
            java.lang.String r19 = "read pay pf file error , check "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0177 }
            r0 = r18
            r1 = r22
            java.lang.StringBuilder r18 = r0.append(r1)     // Catch:{ all -> 0x0177 }
            java.lang.String r19 = " : "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0177 }
            java.lang.String r19 = r8.getMessage()     // Catch:{ all -> 0x0177 }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x0177 }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x0177 }
            com.tencent.imsdk.tool.etc.IMLogger.e(r18)     // Catch:{ all -> 0x0177 }
            if (r3 == 0) goto L_0x0075
            r3.close()     // Catch:{ Exception -> 0x0120 }
        L_0x0075:
            if (r10 == 0) goto L_0x007a
            r10.close()     // Catch:{ Exception -> 0x013d }
        L_0x007a:
            if (r9 == 0) goto L_0x007f
            r9.close()     // Catch:{ Exception -> 0x015a }
        L_0x007f:
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01dc }
            r13.<init>(r12)     // Catch:{ JSONException -> 0x01dc }
            r0 = r21
            java.lang.String r15 = r13.getString(r0)     // Catch:{ JSONException -> 0x01dc }
            if (r15 == 0) goto L_0x0093
            int r18 = r15.length()     // Catch:{ JSONException -> 0x01dc }
            if (r18 <= 0) goto L_0x0093
            r5 = r15
        L_0x0093:
            r6 = r5
            r7 = r5
            goto L_0x0010
        L_0x0097:
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r18.<init>()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r0 = r18
            java.lang.StringBuilder r18 = r0.append(r12)     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            java.lang.String r19 = r16.toString()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            java.lang.String r12 = r18.toString()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r4.close()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r11.close()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            r9.close()     // Catch:{ IOException -> 0x0045, all -> 0x020b }
            if (r4 == 0) goto L_0x00bc
            r4.close()     // Catch:{ Exception -> 0x00c9 }
        L_0x00bc:
            if (r11 == 0) goto L_0x00c1
            r11.close()     // Catch:{ Exception -> 0x00e5 }
        L_0x00c1:
            if (r9 == 0) goto L_0x0217
            r9.close()     // Catch:{ Exception -> 0x0101 }
            r3 = r4
            r10 = r11
            goto L_0x007f
        L_0x00c9:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close buffer failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            goto L_0x00bc
        L_0x00e5:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close stream reader failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            goto L_0x00c1
        L_0x0101:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close stream failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            r3 = r4
            r10 = r11
            goto L_0x007f
        L_0x0120:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close buffer failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            goto L_0x0075
        L_0x013d:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close stream reader failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            goto L_0x007a
        L_0x015a:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "close stream failed : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r18)
            goto L_0x007f
        L_0x0177:
            r18 = move-exception
        L_0x0178:
            if (r3 == 0) goto L_0x017d
            r3.close()     // Catch:{ Exception -> 0x0188 }
        L_0x017d:
            if (r10 == 0) goto L_0x0182
            r10.close()     // Catch:{ Exception -> 0x01a4 }
        L_0x0182:
            if (r9 == 0) goto L_0x0187
            r9.close()     // Catch:{ Exception -> 0x01c0 }
        L_0x0187:
            throw r18
        L_0x0188:
            r8 = move-exception
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "close buffer failed : "
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r19)
            goto L_0x017d
        L_0x01a4:
            r8 = move-exception
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "close stream reader failed : "
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r19)
            goto L_0x0182
        L_0x01c0:
            r8 = move-exception
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            r19.<init>()
            java.lang.String r20 = "close stream failed : "
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r20 = r8.getMessage()
            java.lang.StringBuilder r19 = r19.append(r20)
            java.lang.String r19 = r19.toString()
            com.tencent.imsdk.tool.etc.IMLogger.w(r19)
            goto L_0x0187
        L_0x01dc:
            r8 = move-exception
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "read pay pf file error , check config file "
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r18
            r1 = r22
            java.lang.StringBuilder r18 = r0.append(r1)
            java.lang.String r19 = " : "
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r19 = r8.getMessage()
            java.lang.StringBuilder r18 = r18.append(r19)
            java.lang.String r18 = r18.toString()
            com.tencent.imsdk.tool.etc.IMLogger.e(r18)
            goto L_0x0093
        L_0x0207:
            r18 = move-exception
            r10 = r11
            goto L_0x0178
        L_0x020b:
            r18 = move-exception
            r3 = r4
            r10 = r11
            goto L_0x0178
        L_0x0210:
            r8 = move-exception
            goto L_0x0048
        L_0x0213:
            r8 = move-exception
            r10 = r11
            goto L_0x0048
        L_0x0217:
            r3 = r4
            r10 = r11
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.pay.api.IMPay.matchLoginTypeFromConfig(java.lang.String, java.lang.String):java.lang.String");
    }

    private static String matchLoginType(String loginType) {
        if (loginType.equalsIgnoreCase("facebook") || loginType.contains("facebook") || loginType.contains("FB")) {
            return "FB";
        }
        if (loginType.equalsIgnoreCase("weixin") || loginType.equalsIgnoreCase("wechat")) {
            return "WX";
        }
        if (loginType.equalsIgnoreCase("guest")) {
            return "GU";
        }
        if (loginType.equalsIgnoreCase("google")) {
            return "GG";
        }
        if (loginType.equalsIgnoreCase("gamecenter")) {
            return "GC";
        }
        if (loginType.equalsIgnoreCase("zalo")) {
            return "ZL";
        }
        if (loginType.equalsIgnoreCase("zingid") || loginType.equalsIgnoreCase("zm")) {
            return "ZM";
        }
        if (loginType.equalsIgnoreCase("zalo_guest")) {
            return "ZGU";
        }
        if (loginType.equalsIgnoreCase("sqw")) {
            return "SQW";
        }
        if (loginType.equalsIgnoreCase("link")) {
            return "LK";
        }
        if (loginType.equalsIgnoreCase("gm")) {
            return "GM";
        }
        if (loginType.equalsIgnoreCase("GM_FB")) {
            return "FB";
        }
        if (loginType.equalsIgnoreCase("GM_NORM")) {
            return "GM";
        }
        if (loginType.equalsIgnoreCase("GM_GU")) {
            return "GU";
        }
        return matchLoginTypeFromConfig(loginType, PF_CONFIG_FILE);
    }

    public static void setScreenType(boolean isLandscape) {
        try {
            if (currentPayInstance != null) {
                currentPayInstance.setScreenType(isLandscape);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    @Deprecated
    public static void restorePay(final String productid) {
        try {
            if (currentPayInstance != null) {
                IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                    public void run() {
                        IMPay.currentPayInstance.restorePay(productid);
                    }
                });
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    @Deprecated
    public static void dispose() {
        try {
            for (Map.Entry<String, IMPayBase> entry : payInstanceList.entrySet()) {
                IMPayBase payInstance = (IMPayBase) entry.getValue();
                if (payInstance != null) {
                    payInstance.dispose();
                }
            }
            onDestory();
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    @Deprecated
    public static void onDestory() {
        currentPayInstance = null;
        payInstanceList.clear();
    }

    public static void setDebugLog(boolean flag) {
        try {
            for (Map.Entry<String, IMPayBase> entry : payInstanceList.entrySet()) {
                IMPayBase payInstance = (IMPayBase) entry.getValue();
                if (payInstance != null) {
                    payInstance.setDebugLog(flag);
                }
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void setEnv(String env) {
        try {
            ENV = env;
            for (Map.Entry<String, IMPayBase> entry : payInstanceList.entrySet()) {
                IMPayBase payInstance = (IMPayBase) entry.getValue();
                if (payInstance != null) {
                    payInstance.setEnv(env);
                }
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void getProductExtend(final List<Object> productList) {
        try {
            IMLogger.d("getProductExtend java start");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.getProductExtend(productList);
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void realGetProductExtend(final List<String> productList) {
        try {
            IMLogger.d("realGetProductExtend java start");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.realGetProductExtend(productList);
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    @Deprecated
    public static void getSkuDetails(final IMPayRequestInfo info) {
        try {
            IMLogger.d("getSkuDetails java start");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.getSkuDetails(info);
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void setReleaseIDC(String IDC) {
        if (currentPayInstance != null) {
            currentPayInstance.setReleaseIDC(IDC);
        }
    }

    @Deprecated
    public static void getMp(final IMPayRequestInfo info) {
        try {
            IMLogger.d("getMp java start");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.getMp(info);
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void getMpExtend(final Object o) {
        try {
            IMLogger.d("getMpExtend java start");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.getMpExtend(o);
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    @Deprecated
    public static void pay(final IMPayRequestInfo info) {
        try {
            IMLogger.d("java pay");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMLogger.d("java pay currentPayInstance.pay info=" + info.toString());
                        if (info.payProductType > 0) {
                            switch (info.payProductType) {
                                case 1:
                                    IMPayRequestInfo iMPayRequestInfo = info;
                                    IMPayRequestInfo.productType producttype = info.productType;
                                    iMPayRequestInfo.productType = IMPayRequestInfo.productType.Consumable;
                                    break;
                                case 2:
                                    IMPayRequestInfo iMPayRequestInfo2 = info;
                                    IMPayRequestInfo.productType producttype2 = info.productType;
                                    iMPayRequestInfo2.productType = IMPayRequestInfo.productType.Non_Consumable;
                                    break;
                                case 3:
                                    IMPayRequestInfo iMPayRequestInfo3 = info;
                                    IMPayRequestInfo.productType producttype3 = info.productType;
                                    iMPayRequestInfo3.productType = IMPayRequestInfo.productType.Subscription;
                                    break;
                                default:
                                    IMPayRequestInfo iMPayRequestInfo4 = info;
                                    IMPayRequestInfo.productType producttype4 = info.productType;
                                    iMPayRequestInfo4.productType = IMPayRequestInfo.productType.Consumable;
                                    break;
                            }
                        }
                        info.changeKey = IMPayRequestInfo.changkeyType.Nonmal;
                        info.keyType = IMPayRequestInfo.keytype.BaseKey;
                        IMLogger.d("java pay currentPayInstance.pay(info)");
                        IMPay.currentPayInstance.pay(info);
                        return;
                    }
                    IMLogger.d("java pay currentPayInstance is null");
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void payExtend(final Object object) {
        try {
            IMLogger.d("java pay");
            IMPayManager.getInstance().getMainHandler().post(new Runnable() {
                public void run() {
                    if (IMPay.currentPayInstance != null) {
                        IMPay.currentPayInstance.payExtend(object);
                    } else {
                        IMLogger.d("java pay currentPayInstance is null");
                    }
                }
            });
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (currentPayInstance != null) {
                return currentPayInstance.handleActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        return false;
    }

    public static IMPayListener getIMPayListener() {
        return currentListener;
    }

    public static IMPayListener2 getIMPayListener2() {
        return currentListener2;
    }

    public static IMPayExtendListener getIMPayExtendListener() {
        return currentExtendListener;
    }
}
