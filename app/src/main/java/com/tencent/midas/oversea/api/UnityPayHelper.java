package com.tencent.midas.oversea.api;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import com.beetalk.sdk.GGLoginSession;
import com.garena.pay.android.GGAndroidPaymentPlatform;
import com.garena.pay.android.GoogleIapInventoryScanCallback;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.api.request.APMidasGameRequest;
import com.tencent.midas.oversea.api.request.APMidasGoodsRequest;
import com.tencent.midas.oversea.api.request.APMidasSubscribeRequest;
import com.tencent.midas.oversea.api.request.IProductInfoCallback;
import com.tencent.midas.oversea.comm.APLog;
import com.unity3d.player.UnityPlayer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnityPayHelper {
    public static final String APPEXTENDS = "appExtends";
    public static final String AP_MIDAS_RESP_RESULT_CANCEL = "1";
    public static final String AP_MIDAS_RESP_RESULT_ERROR = "-1";
    public static final String AP_MIDAS_RESP_RESULT_NET_ERROR = "3";
    public static final String AP_MIDAS_RESP_RESULT_OK = "0";
    public static final String AP_MIDAS_RESP_RESULT_PARAM_ERROR = "2";
    private static final String CALLBACKGETINFO = "MidasGetInfoCallback";
    private static final String CALLBACKGETPRODUCT = "MidasGetProductCallback";
    private static final String CALLBACKINIT = "MidasInitCallback";
    private static final String CALLBACKLOGIN = "MidasLoginExpiredCallback";
    private static final String CALLBACKOBJ = "MidasUnityPay.CallBackUtils";
    private static final String CALLBACKPAY = "MidasPayCallback";
    private static final String CALLBACKQUERYPROMOTION = "MidasQueryPromotionCallback";
    private static final String CALLBACKREPROVIDE = "MidasReProvidetCallback";
    public static final String CHANNELEXTRAS = "channelExtras";
    public static final String COUNTRY = "country";
    public static final String CURRENCYTYPE = "currencyType";
    public static final String DRMINFO = "drmInfo";
    public static final String GOODSZONEID = "goodsZoneId";
    public static final String GWALLET = "gwallet";
    /* access modifiers changed from: private */
    public static final String LOGTAG = UnityPayHelper.class.getSimpleName();
    public static final String NET_FAILED = "{\"ret\":-1,\"err_code\":\"\",\"msg\":\"get info failed\"}";
    public static final String OFFERID = "offerId";
    public static final String OFFICAL = "os_offical";
    public static final String OPENID = "openId";
    public static final String PAYCHANNEL = "payChannel";
    public static final int PAYRESULT_CANCEL = 2;
    public static final int PAYRESULT_ERROR = -1;
    public static final int PAYRESULT_PARAMERROR = 3;
    public static final int PAYRESULT_SUCC = 0;
    public static final int PAYRESULT_UNKOWN = 4;
    public static final String PENKEY = "openKey";
    public static final String PF = "pf";
    public static final String PFKEY = "pfKey";
    public static final String PRODUCTID = "productId";
    public static final String RESID = "resId";
    public static final String RES_CODE = "resultCode";
    public static final String RES_MSG = "resultMsg";
    public static final int RET_OK = 0;
    public static final String SERVICECODE = "serviceCode";
    public static final String SERVICENAME = "serviceName";
    public static final String SESSIONID = "sessionId";
    public static final String SESSIONTYPE = "sessionType";
    public static final String ZONEID = "zoneId";
    public static String mAppExtends = "";

    /* access modifiers changed from: private */
    public static String errAdapter(int errorCode) {
        switch (errorCode) {
            case -1:
                return AP_MIDAS_RESP_RESULT_ERROR;
            case 0:
                return "0";
            case 2:
                return "1";
            case 3:
                return "2";
            case 4:
                return AP_MIDAS_RESP_RESULT_ERROR;
            default:
                return AP_MIDAS_RESP_RESULT_ERROR;
        }
    }

    private static APMidasGameRequest getInitReq(String req) throws JSONException {
        APMidasGameRequest gameReq = new APMidasGameRequest();
        JSONObject obj = new JSONObject(req);
        gameReq.offerId = obj.getString(OFFERID);
        gameReq.openId = obj.getString(OPENID);
        gameReq.openKey = obj.getString(PENKEY);
        gameReq.sessionId = obj.getString(SESSIONID);
        gameReq.sessionType = obj.getString(SESSIONTYPE);
        gameReq.zoneId = obj.getString(ZONEID);
        gameReq.pf = obj.getString(PF);
        gameReq.pfKey = obj.getString(PFKEY);
        if (obj.has(APPEXTENDS)) {
            gameReq.reserv = obj.getString(APPEXTENDS);
            mAppExtends = gameReq.reserv;
        } else {
            mAppExtends = "";
        }
        if (obj.has(CHANNELEXTRAS)) {
            gameReq.extras = obj.getString(CHANNELEXTRAS);
        }
        return gameReq;
    }

    private static APMidasGameRequest getGameReq(String req) throws JSONException {
        APMidasGameRequest gameReq = new APMidasGameRequest();
        JSONObject obj = new JSONObject(req);
        gameReq.offerId = obj.getString(OFFERID);
        gameReq.openId = obj.getString(OPENID);
        gameReq.openKey = obj.getString(PENKEY);
        gameReq.sessionId = obj.getString(SESSIONID);
        gameReq.sessionType = obj.getString(SESSIONTYPE);
        gameReq.zoneId = obj.getString(ZONEID);
        gameReq.pf = obj.getString(PF);
        gameReq.pfKey = obj.getString(PFKEY);
        if (obj.has(APPEXTENDS)) {
            gameReq.reserv = obj.getString(APPEXTENDS);
        }
        if (obj.has(CHANNELEXTRAS)) {
            gameReq.extras = obj.getString(CHANNELEXTRAS);
        }
        gameReq.country = "US";
        gameReq.currency_type = "USD";
        gameReq.mpInfo.payChannel = "os_garena";
        if (obj.has(PRODUCTID)) {
            gameReq.mpInfo.productid = obj.getString(PRODUCTID);
        }
        return gameReq;
    }

    private static APMidasGoodsRequest getGoodsReq(String req) throws JSONException {
        APMidasGoodsRequest reqObj = new APMidasGoodsRequest();
        JSONObject obj = new JSONObject(req);
        reqObj.offerId = obj.getString(OFFERID);
        reqObj.openId = obj.getString(OPENID);
        reqObj.openKey = obj.getString(PENKEY);
        reqObj.sessionId = obj.getString(SESSIONID);
        reqObj.sessionType = obj.getString(SESSIONTYPE);
        reqObj.zoneId = obj.getString(ZONEID);
        reqObj.goodsZoneId = obj.optString(GOODSZONEID);
        reqObj.pf = obj.getString(PF);
        reqObj.pfKey = obj.getString(PFKEY);
        if (obj.has(APPEXTENDS)) {
            reqObj.reserv = obj.getString(APPEXTENDS);
        }
        if (obj.has(CHANNELEXTRAS)) {
            reqObj.extras = obj.getString(CHANNELEXTRAS);
        }
        if (obj.has("country")) {
            reqObj.country = obj.getString("country");
        }
        if (obj.has(CURRENCYTYPE)) {
            reqObj.currency_type = obj.getString(CURRENCYTYPE);
        }
        if (obj.has(PRODUCTID)) {
            reqObj.mpInfo.productid = obj.getString(PRODUCTID);
        }
        if (obj.has(PAYCHANNEL)) {
            reqObj.mpInfo.payChannel = obj.getString(PAYCHANNEL);
        }
        if (obj.has(RESID)) {
            try {
                reqObj.resId = Integer.parseInt(obj.getString(RESID));
            } catch (Exception e) {
                APLog.e(LOGTAG, "resid error:" + e.toString());
            }
        }
        if (obj.has(DRMINFO)) {
            reqObj.mpInfo.drmInfo = obj.getString(DRMINFO);
        }
        return reqObj;
    }

    private static APMidasSubscribeRequest getSubsReq(String req) throws JSONException {
        APMidasSubscribeRequest reqObj = new APMidasSubscribeRequest();
        JSONObject obj = new JSONObject(req);
        reqObj.offerId = obj.getString(OFFERID);
        reqObj.openId = obj.getString(OPENID);
        reqObj.openKey = obj.getString(PENKEY);
        reqObj.zoneId = obj.getString(ZONEID);
        reqObj.sessionId = obj.getString(SESSIONID);
        reqObj.sessionType = obj.getString(SESSIONTYPE);
        reqObj.pf = obj.getString(PF);
        reqObj.pfKey = obj.getString(PFKEY);
        if (obj.has(APPEXTENDS)) {
            reqObj.reserv = obj.getString(APPEXTENDS);
        }
        if (obj.has(CHANNELEXTRAS)) {
            reqObj.extras = obj.getString(CHANNELEXTRAS);
        }
        if (obj.has("country")) {
            reqObj.country = obj.getString("country");
        }
        if (obj.has(CURRENCYTYPE)) {
            reqObj.currency_type = obj.getString(CURRENCYTYPE);
        }
        if (obj.has(PRODUCTID)) {
            reqObj.mpInfo.productid = obj.getString(PRODUCTID);
        }
        if (obj.has(PAYCHANNEL)) {
            reqObj.mpInfo.payChannel = obj.getString(PAYCHANNEL);
        }
        if (obj.has(RESID)) {
            try {
                reqObj.resId = Integer.parseInt(obj.getString(RESID));
            } catch (Exception e) {
                APLog.e(LOGTAG, "resid error:" + e.toString());
            }
        }
        if (obj.has(DRMINFO)) {
            reqObj.mpInfo.drmInfo = obj.getString(DRMINFO);
        }
        reqObj.serviceCode = obj.getString(SERVICECODE);
        reqObj.serviceName = obj.getString(SERVICENAME);
        return reqObj;
    }

    public static void SetLogEnable(boolean enable) {
        APLog.d("UnityPayHelper", "SetLogEnable enable: " + enable);
        APMidasPayAPI.singleton().setLogEnable(enable);
    }

    public static void Initialize(final String idc, final String env, final String req) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                UnityPayHelper._Initialize(idc, env, req);
            }
        });
    }

    public static void GetProductInfo(final String channel, final String productList) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                UnityPayHelper._GetProductInfo(channel, productList);
            }
        });
    }

    public static void GetInfo(final String reqType, final String bizType, final String req) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                UnityPayHelper._GetInfo(reqType, bizType, req);
            }
        });
    }

    public static void Reprovide(final String req) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                UnityPayHelper._Reprovide(req);
            }
        });
    }

    public static void _GetProductInfo(String channel, String productList) {
        APLog.d("_GetProductInfo", "channel: " + channel + "productList: " + productList);
        if (channel.equals(OFFICAL)) {
            channel = GWALLET;
        }
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(productList);
            for (int i = 0; i < json.length(); i++) {
                list.add(json.getString(i));
            }
            APMidasPayAPI.singleton();
            APMidasPayAPI.getProductInfo(channel, list, new IProductInfoCallback() {
                public void onProductInfoResp(String s) {
                    APLog.d("_GetProductInfo", "onProductInfoResp: " + s);
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKGETPRODUCT, s);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static void _Initialize(String idc, String env, String req) {
        APMidasPayAPI.singleton().setEnv(env);
        APMidasPayAPI.singleton().setReleaseIDC(idc);
        APLog.d("UnityPayHelper", "Initialize message idc: " + idc);
        APLog.d("UnityPayHelper", "Initialize message env: " + env);
        APLog.d("UnityPayHelper", "Initialize message req: " + req);
        try {
            APMidasPayAPI.singleton().init(UnityPlayer.currentActivity, getInitReq(req), true, new IAPPayUpdateCallBack() {
                public void onUpdate(int i, String s) {
                    if (i == 0) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (obj.getString("paychannelid").equals(UnityPayHelper.GWALLET)) {
                                obj.put("paychannelid", UnityPayHelper.OFFICAL);
                            }
                            UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKINIT, obj.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            APLog.e(LOGTAG, "InitReq error:" + e.toString());
        }
    }

    /* access modifiers changed from: private */
    public static void _Reprovide(String req) {
        APLog.d("UnityPayHelper", "reprovideReq message req: " + req);
        try {
            APMidasPayAPI.singleton().reProvide(UnityPlayer.currentActivity, getInitReq(req), new IAPPayUpdateCallBack() {
                public void onUpdate(int i, String s) {
                    JSONObject obj;
                    if (i == 0) {
                        try {
                            obj = new JSONObject(s);
                            if (obj.getString("paychannelid").equals(UnityPayHelper.GWALLET)) {
                                obj.put("paychannelid", UnityPayHelper.OFFICAL);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        obj = new JSONObject();
                        obj.put(UnityPayHelper.RES_CODE, i);
                        obj.put(UnityPayHelper.RES_MSG, s);
                    }
                    APLog.d(UnityPayHelper.LOGTAG, "reprovide result: " + obj.toString());
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKREPROVIDE, obj.toString());
                }
            });
        } catch (JSONException e) {
            APLog.e(LOGTAG, "reprovideReq error:" + e.toString());
        }
    }

    public static void Pay(final String bizType, final String req) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                APLog.d("UnityPayHelper", "Initialize Current Thread: " + Thread.currentThread().getName());
                UnityPayHelper._Pay(bizType, req);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void _GetInfo(String reqType, String bizType, String req) {
        APMidasBaseRequest payReq = getBaseRequest(bizType, req);
        if (payReq == null) {
            UnityPlayer.UnitySendMessage(CALLBACKOBJ, CALLBACKGETINFO, NET_FAILED);
        } else {
            APMidasPayAPI.singleton().net(reqType, UnityPlayer.currentActivity, payReq, new IAPMidasNetCallBack() {
                public void MidasNetError(String s, int i, String s1) {
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKGETINFO, UnityPayHelper.NET_FAILED);
                }

                public void MidasNetStop(String s) {
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKGETINFO, UnityPayHelper.NET_FAILED);
                }

                public void MidasNetFinish(String s, String s1) {
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKGETINFO, s1);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void _Pay(String bizType, String req) {
        APMidasBaseRequest payReq = getBaseRequest(bizType, req);
        if (payReq == null) {
            UnityPlayer.UnitySendMessage(CALLBACKOBJ, CALLBACKPAY, "{\"retCode\":AP_MIDAS_RESP_RESULT_PARAM_ERROR,\"retMessage\":\"not support bizType\"}");
            return;
        }
        APLog.d("UnityPayHelper", "payReq payChannel: " + payReq.mpInfo.payChannel);
        APLog.d("UnityPayHelper", "payReq ProductID: " + payReq.mpInfo.productid);
        if (payReq.mpInfo.payChannel.equals(OFFICAL)) {
            payReq.mpInfo.payChannel = GWALLET;
        }
        APMidasPayAPI.singleton().pay(UnityPlayer.currentActivity, payReq, new IAPMidasPayCallBack() {
            public void MidasPayNeedLogin() {
                APLog.d("UnityPayHelper", "MidasPayNeedLogin callback");
                UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKLOGIN, "");
            }

            public void MidasPayCallBack(APMidasResponse resp) {
                UnityResponse unityResp = new UnityResponse();
                unityResp.resultCode = UnityPayHelper.errAdapter(resp.resultCode);
                unityResp.resultInerCode = resp.resultInerCode + "";
                unityResp.billno = resp.newbillno;
                unityResp.resultMsg = resp.resultMsg;
                if (resp.newPayChannel.equals(UnityPayHelper.GWALLET)) {
                    unityResp.payChannel = UnityPayHelper.OFFICAL;
                } else {
                    unityResp.payChannel = resp.newPayChannel + "";
                }
                unityResp.appExtends = "";
                APLog.d("UnityPayHelper", "MidasPayCallBack ");
                UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKPAY, unityResp.toJson());
            }
        });
    }

    private static APMidasBaseRequest getBaseRequest(String bizType, String req) {
        APMidasBaseRequest payReq;
        APLog.d("UnityPayHelper", "Pay message req: " + req);
        try {
            if (bizType.equals("APMidasGameRequest")) {
                payReq = getGameReq(req);
            } else if (bizType.equals("APMidasGoodsRequest")) {
                payReq = getGoodsReq(req);
            } else if (bizType.equals("APMidasSubscribeRequest")) {
                payReq = getSubsReq(req);
            } else {
                APLog.e("UnityPayHelper", "not support bizType");
                return null;
            }
            APMidasBaseRequest aPMidasBaseRequest = payReq;
            return payReq;
        } catch (JSONException e) {
            APLog.e(LOGTAG, "payReq error:" + e.toString());
            return null;
        }
    }

    public static void ScanGoogleInventory(final int serverId, final int roleId) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                ApplicationInfo appInfo = null;
                try {
                    appInfo = UnityPlayer.currentActivity.getPackageManager().getApplicationInfo(UnityPlayer.currentActivity.getPackageName(), 128);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (appInfo == null) {
                    APLog.e("ScanGoogleInventory", "appInfo is null");
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKQUERYPROMOTION, "");
                } else if (!GGLoginSession.checkSessionValidity()) {
                    APLog.e("ScanGoogleInventory", "session invalide");
                    UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKQUERYPROMOTION, "");
                } else {
                    GGAndroidPaymentPlatform.scanGoogleInAppPurchaseInventory(UnityPlayer.currentActivity, appInfo.metaData.getString("MIDAS.APP_SDK_ASSIGN_ID"), GGLoginSession.getCurrentSession().getTokenValue().getAuthToken(), serverId, roleId, new GoogleIapInventoryScanCallback() {
                        public void onResult(@NonNull List<GoogleIapInventoryScanCallback.Result> processedItemResults) {
                            JSONArray array = new JSONArray();
                            for (GoogleIapInventoryScanCallback.Result result : processedItemResults) {
                                if (result.success && result.item.isPromotion()) {
                                    result.item.isPromotion();
                                    result.item.getItemName();
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("sku", result.item.getItemSku());
                                        jsonObject.put("name", result.item.getItemName());
                                        jsonObject.put("amount", result.item.getAmount());
                                        jsonObject.put("icon", result.item.getItemIcon());
                                        array.put(jsonObject);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            UnityPlayer.UnitySendMessage(UnityPayHelper.CALLBACKOBJ, UnityPayHelper.CALLBACKQUERYPROMOTION, array.toString());
                        }
                    });
                }
            }
        });
    }
}
