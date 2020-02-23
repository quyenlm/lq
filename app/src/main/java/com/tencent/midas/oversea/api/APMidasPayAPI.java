package com.tencent.midas.oversea.api;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.tencent.mid.local.LocalMid;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.api.request.APMidasGameRequest;
import com.tencent.midas.oversea.api.request.APMidasGoodsRequest;
import com.tencent.midas.oversea.api.request.APMidasMonthRequest;
import com.tencent.midas.oversea.api.request.APMidasSubscribeRequest;
import com.tencent.midas.oversea.api.request.IGetProduct;
import com.tencent.midas.oversea.api.request.IProductInfoCallback;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.business.payhub.APBaseRestore;
import com.tencent.midas.oversea.business.payhub.APPayHub;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APDataReportCache;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.data.userInfo.APUserInfo;
import com.tencent.midas.oversea.network.http.APBaseHttpAns;
import com.tencent.midas.oversea.network.http.APIPList;
import com.tencent.midas.oversea.network.http.APNetCfg;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.safe.APSecretKeyManager;
import com.tencent.midas.oversea.safe.APUinSkeyManager;
import com.tencent.midas.oversea.utils.IabBroadcastReceiver;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.HashMap;
import java.util.List;

public class APMidasPayAPI {
    private static final String[] ENV = {APGlobalInfo.TestEnv, APGlobalInfo.DevEnv, "release", APGlobalInfo.TestingEnv};
    public static final int LANDSCAPE = 0;
    /* access modifiers changed from: private */
    public static final String LOGTAG = APMidasPayAPI.class.getSimpleName();
    public static final int PORTRAINT = 1;
    public static final int SAVETYPE_GAME = 0;
    public static final int SAVETYPE_GOODS = 1;
    public static final int SAVETYPE_MONTH = 4;
    public static final int SAVETYPE_QB = 3;
    public static final int SAVETYPE_QD = 2;
    public static final int SAVETYPE_SUBSCRIBE = 5;
    private static final String TAG = APMidasPayAPI.class.getSimpleName();
    public static Context applicationContext;
    private static volatile APMidasPayAPI gInstance = null;
    private static Activity mActivity;
    public static HashMap<String, String> mFetchGoodsChannel = new HashMap<>();
    /* access modifiers changed from: private */
    public static IAPPayUpdateCallBack mIAPMidasPayUpdateCallBack = null;
    private static IabBroadcastReceiver mIabBroadcastReceiver;
    private static boolean mNeedPayUpdate = false;
    public APMidasGameRequest gameRequest = null;
    public APMidasGoodsRequest goodsRequest = null;
    public int mBuyType = 0;
    public APMidasMonthRequest monthRequest = null;
    public IAPMidasNetCallBack netCallBack;
    public IAPMidasPayCallBack payCallBack;
    public APMidasSubscribeRequest subscribeRequest = null;

    static {
        mFetchGoodsChannel.put(UnityPayHelper.GWALLET, "com.tencent.midas.oversea.business.payhub.gw.APGWProductInfo");
    }

    private boolean checkCommParam(APMidasBaseRequest aPMidasBaseRequest) {
        if (TextUtils.isEmpty(APNetCfg.getIDC())) {
            APUICommonMethod.showToast(applicationContext, "setReleaseIDC has not set");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.offerId)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_offeridNull");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.openId)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_OpenidNull");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.openKey)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_openkeyNull");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.sessionId)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_sessionidNull");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.sessionType)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_sessiontypeNull");
            return false;
        } else if (TextUtils.isEmpty(aPMidasBaseRequest.pf)) {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_pfNull");
            return false;
        } else if (!TextUtils.isEmpty(aPMidasBaseRequest.pfKey)) {
            return true;
        } else {
            APUICommonMethod.showToast(applicationContext, "unipay_entry_pfkeyNull");
            return false;
        }
    }

    public static void getProductInfo(String str, List<String> list, IProductInfoCallback iProductInfoCallback) {
        if (TextUtils.isEmpty(str) || !mFetchGoodsChannel.containsKey(str)) {
            APLog.e(LOGTAG, "channel not support");
            return;
        }
        singleton();
        if (applicationContext == null) {
            APLog.e(LOGTAG, "applicationContext is empty");
        } else if (list == null || list.size() == 0) {
            APLog.e(LOGTAG, "sku is empty");
        } else if (iProductInfoCallback == null) {
            APLog.e(LOGTAG, "callback is empty");
        } else {
            try {
                singleton();
                ((IGetProduct) Class.forName(mFetchGoodsChannel.get(str)).newInstance()).getProductInfo(applicationContext, list, iProductInfoCallback);
            } catch (InstantiationException e) {
                APLog.e(LOGTAG, e.toString());
            } catch (IllegalAccessException e2) {
                APLog.e(LOGTAG, e2.toString());
            } catch (ClassNotFoundException e3) {
                APLog.e(LOGTAG, e3.toString());
            }
        }
    }

    private void init() {
        singleton();
        APAppDataInterface.singleton().setNetworkState(APTools.getNetWorkType(applicationContext));
        try {
            singleton();
            APAppDataInterface.singleton().setXGMid(LocalMid.getInstance(applicationContext).getLocalMid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initPlatform();
    }

    private void initAll(Activity activity, APMidasBaseRequest aPMidasBaseRequest) {
        APDataInterface.init();
        APAppDataInterface.singleton().setOfferid(aPMidasBaseRequest.offerId);
        APNetCfg.init();
        APPayMananger.singleton().init(activity);
        paramsInit(aPMidasBaseRequest);
        init();
    }

    private void initNetworkInfo(APMidasBaseRequest aPMidasBaseRequest) {
        boolean z = true;
        StringBuffer stringBuffer = new StringBuffer();
        singleton();
        String readSecretKey = APSecretKeyManager.getInstance(applicationContext).readSecretKey(aPMidasBaseRequest.openId);
        APAppDataInterface.singleton().setSecretKey(readSecretKey);
        singleton();
        String readCryptKey = APSecretKeyManager.getInstance(applicationContext).readCryptKey(aPMidasBaseRequest.openId);
        APAppDataInterface.singleton().setCryptKey(readCryptKey);
        singleton();
        String readCryptKeyTime = APSecretKeyManager.getInstance(applicationContext).readCryptKeyTime(aPMidasBaseRequest.openId);
        APAppDataInterface.singleton().setCryptKeyTime(readCryptKeyTime);
        boolean z2 = TextUtils.isEmpty(readCryptKey) || TextUtils.isEmpty(readCryptKeyTime) || TextUtils.isEmpty(readSecretKey);
        singleton();
        if (System.currentTimeMillis() - applicationContext.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).getLong("updateIPPreTime", 0) > TimeUtils.MILLIS_IN_DAY) {
            stringBuffer.append(APNetworkManager2.HTTP_KEY_GETIPLIST);
        } else {
            z = false;
        }
        APLog.i("initNetworkInfo", "isNeedUpdateIP:" + z);
        if (z || z2) {
            APNetworkManager2.getInstance().init(stringBuffer.toString(), aPMidasBaseRequest.offerId, aPMidasBaseRequest.openId, aPMidasBaseRequest.openKey, aPMidasBaseRequest.sessionId, aPMidasBaseRequest.sessionType, aPMidasBaseRequest.pf, aPMidasBaseRequest.pfKey, aPMidasBaseRequest.zoneId, aPMidasBaseRequest.extendInfo.iChannel, new IAPHttpAnsObserver() {
                public void onError(APBaseHttpAns aPBaseHttpAns) {
                    APMidasPayAPI.reportdata();
                }

                public void onFinish(APBaseHttpAns aPBaseHttpAns) {
                    APLog.i("APMidasPayAPI", "initNetworkInfo onFinish");
                    APMidasPayAPI.singleton();
                    Context context = APMidasPayAPI.applicationContext;
                    if (context != null) {
                        SharedPreferences.Editor edit = context.getSharedPreferences(APGlobalInfo.SharedPreferencesTag, 0).edit();
                        edit.putLong("updateIPPreTime", System.currentTimeMillis());
                        edit.commit();
                        if (aPBaseHttpAns.getResultCode() == 0) {
                            APLog.i("APMidasPayAPI", "start to chck restore");
                            APMidasPayAPI.restore();
                            return;
                        }
                        APMidasPayAPI.reportdata();
                    }
                }

                public void onStop(APBaseHttpAns aPBaseHttpAns) {
                    APMidasPayAPI.reportdata();
                }
            });
            return;
        }
        APLog.i("APMidasPayAPI", "start to chck restore");
        restore();
    }

    private void initPlatform() {
        singleton();
        APIPList.getInstance(applicationContext).init();
        APUserInfo userInfo = APDataInterface.singleton().getUserInfo();
        singleton();
        APAppDataInterface.singleton().setSecretKey(APSecretKeyManager.getInstance(applicationContext).readSecretKey(userInfo.openId));
        singleton();
        APAppDataInterface.singleton().setCryptKey(APSecretKeyManager.getInstance(applicationContext).readCryptKey(userInfo.openId));
        singleton();
        APAppDataInterface.singleton().setCryptKeyTime(APSecretKeyManager.getInstance(applicationContext).readCryptKeyTime(userInfo.openId));
        initUserUUID();
        APDataInterface singleton = APDataInterface.singleton();
        APMidasPayAPI aPMidasPayAPI = gInstance;
        singleton.setUserIMEI(APTools.getDeviceId(applicationContext));
        APDataInterface.singleton().setUserMAC(APTools.getLocalMacAddress());
        APAppDataInterface.singleton().redIsNewCGI();
    }

    private void initUserUUID() {
        String readInfoSDCard = APTools.readInfoSDCard("MidasDeviceId");
        if (TextUtils.isEmpty(readInfoSDCard)) {
            readInfoSDCard = APTools.getUUID();
            APTools.saveInfoSDCard("MidasDeviceId", readInfoSDCard);
        }
        APLog.i("MidasDeviceId", readInfoSDCard);
        APDataInterface.singleton().setUserUniqueUuid(readInfoSDCard);
    }

    private void paramsInit(APMidasBaseRequest aPMidasBaseRequest) {
        APUserInfo userInfo = APDataInterface.singleton().getUserInfo();
        userInfo.openId = aPMidasBaseRequest.openId;
        userInfo.openKey = aPMidasBaseRequest.openKey;
        userInfo.sessionId = aPMidasBaseRequest.sessionId;
        userInfo.sessionType = aPMidasBaseRequest.sessionType;
        userInfo.zoneId = aPMidasBaseRequest.zoneId;
        userInfo.pf = aPMidasBaseRequest.pf;
        userInfo.pfKey = aPMidasBaseRequest.pfKey;
        userInfo.iChannel = aPMidasBaseRequest.extendInfo.iChannel;
        userInfo.extras = aPMidasBaseRequest.extras;
        APAppDataInterface.singleton().mofferId = aPMidasBaseRequest.offerId;
        APAppDataInterface.singleton().mType = aPMidasBaseRequest.mType;
        try {
            singleton();
            userInfo.payId = APUinSkeyManager.getInstance(applicationContext).readPayId(userInfo.openId);
            singleton();
            userInfo.authKey = APUinSkeyManager.getInstance(applicationContext).readAuthKey(userInfo.openId);
        } catch (Exception e) {
            APLog.e("paramsInit", e.toString());
        }
        userInfo.acctType = aPMidasBaseRequest.acctType;
        userInfo.isUinLogin = false;
        APDataInterface.singleton().setCgiExtends(aPMidasBaseRequest.reserv);
        APDataInterface.singleton().mDrmInfo = aPMidasBaseRequest.getDrmInfo();
        APAppDataInterface.singleton().setIsShowSaveNum(aPMidasBaseRequest.extendInfo.isShowNum);
        APAppDataInterface.singleton().setElseNumberVisible(aPMidasBaseRequest.extendInfo.isShowListOtherNum);
    }

    public static void reportdata() {
        try {
            APLog.i("getDeviceInfo:", APAppDataInterface.singleton().getDeviceInfo());
            APDataReportManager.getInstance().insertData(APDataReportManager.PHONE_DEVICE, singleton().mBuyType, (String) null, (String) null, APAppDataInterface.singleton().getDeviceInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        APNetworkManager.getInstance().dataReport(new IAPHttpAnsObserver() {
            public void onError(APBaseHttpAns aPBaseHttpAns) {
                APMidasPayAPI.singleton();
                APDataReportCache aPDataReportCache = new APDataReportCache(APMidasPayAPI.applicationContext);
                aPDataReportCache.setDataList(APDataReportManager.getInstance().dataReport);
                aPDataReportCache.dataNativeCacheList();
            }

            public void onFinish(APBaseHttpAns aPBaseHttpAns) {
                APDataReportManager.getInstance().clearData();
                APDataReportManager.getInstance().saveDataId();
            }

            public void onStop(APBaseHttpAns aPBaseHttpAns) {
            }
        });
    }

    /* access modifiers changed from: private */
    public static void restore() {
        mNeedPayUpdate = false;
        restore(mActivity, new IAPPayUpdateCallBack() {
            public void onUpdate(int i, String str) {
                APLog.i(APMidasPayAPI.LOGTAG, "restore ret:" + i);
                if (APMidasPayAPI.mIAPMidasPayUpdateCallBack != null) {
                    APMidasPayAPI.mIAPMidasPayUpdateCallBack.onUpdate(i, str);
                }
                APMidasPayAPI.reportdata();
            }
        });
    }

    private static void restore(final Context context, final IAPPayUpdateCallBack iAPPayUpdateCallBack) {
        if (iAPPayUpdateCallBack == null) {
            APLog.i(LOGTAG, "IAPPayUpdateCallBack is  null");
            return;
        }
        for (final String next : APPayHub.mRetoreChannel.keySet()) {
            final APBaseRestore createRestoreChannel = APPayHub.createRestoreChannel(next);
            if (createRestoreChannel != null) {
                createRestoreChannel.restore(context, next, iAPPayUpdateCallBack);
                mIabBroadcastReceiver = new IabBroadcastReceiver(new IabBroadcastReceiver.IabBroadcastListener() {
                    public void receivedBroadcast() {
                        APLog.i(APMidasPayAPI.LOGTAG, "bBroadcastReceiver start to restore");
                        APBaseRestore.this.restore(context, next, iAPPayUpdateCallBack);
                    }
                });
                if (next.equals(UnityPayHelper.GWALLET)) {
                    APLog.i(LOGTAG, "iap registerReceiver");
                    applicationContext.registerReceiver(mIabBroadcastReceiver, new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED"));
                }
            }
        }
    }

    public static APMidasPayAPI singleton() {
        if (gInstance == null) {
            synchronized (APMidasPayAPI.class) {
                if (gInstance == null) {
                    gInstance = new APMidasPayAPI();
                }
            }
        }
        return gInstance;
    }

    public void deinit() {
        if (mNeedPayUpdate && mIabBroadcastReceiver != null) {
            APLog.i(LOGTAG, "iap unregisterReceiver");
            singleton();
            applicationContext.unregisterReceiver(mIabBroadcastReceiver);
            mIabBroadcastReceiver = null;
        }
    }

    public String getEnv() {
        return APAppDataInterface.singleton().getEnv();
    }

    public boolean getLogable() {
        return APLog.getLogEnable();
    }

    public String getReleaseIDC() {
        return APNetCfg.getIDC();
    }

    public void init(Activity activity) {
    }

    public void init(Activity activity, APMidasBaseRequest aPMidasBaseRequest) {
        init(activity, aPMidasBaseRequest, true, (IAPPayUpdateCallBack) null);
    }

    public void init(Activity activity, APMidasBaseRequest aPMidasBaseRequest, boolean z, IAPPayUpdateCallBack iAPPayUpdateCallBack) {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 128);
            if (applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("MIDAS.APP_SDK_SKIP_INIT") || !applicationInfo.metaData.getBoolean("MIDAS.APP_SDK_SKIP_INIT")) {
                mNeedPayUpdate = z;
                mIAPMidasPayUpdateCallBack = iAPPayUpdateCallBack;
                mActivity = activity;
                APLog.i(LOGTAG, "call init  func  at:" + System.currentTimeMillis());
                singleton();
                applicationContext = activity.getApplicationContext();
                APNetCfg.init();
                APAppDataInterface.singleton().setOfferid(aPMidasBaseRequest.offerId);
                initPlatform();
                paramsInit(aPMidasBaseRequest);
                initNetworkInfo(aPMidasBaseRequest);
                return;
            }
            APLog.i(LOGTAG, "garena channel skip init");
        } catch (Exception e) {
            APLog.i(LOGTAG, "read MIDAS.APP_SDK_ASSIGN_ID  exception:" + e.toString());
        }
    }

    public void net(String str, Activity activity, APMidasBaseRequest aPMidasBaseRequest, IAPMidasNetCallBack iAPMidasNetCallBack) {
        if (iAPMidasNetCallBack == null) {
            throw new IllegalArgumentException("callBack is null");
        }
        this.netCallBack = iAPMidasNetCallBack;
        singleton();
        applicationContext = activity.getApplicationContext();
        initAll(activity, aPMidasBaseRequest);
        APPayMananger.singleton().net(str, aPMidasBaseRequest, iAPMidasNetCallBack);
    }

    public void pay(Activity activity, APMidasBaseRequest aPMidasBaseRequest, IAPMidasPayCallBack iAPMidasPayCallBack) {
        if (APTools.isFastClick()) {
            APLog.i(LOGTAG, "fast click");
        } else if (iAPMidasPayCallBack == null) {
            throw new IllegalArgumentException("callBack is null");
        } else {
            APLog.i(LOGTAG, "call pay  func at:" + System.currentTimeMillis());
            this.payCallBack = iAPMidasPayCallBack;
            singleton();
            applicationContext = activity.getApplicationContext();
            initAll(activity, aPMidasBaseRequest);
            if (!checkCommParam(aPMidasBaseRequest)) {
                APMidasResponse aPMidasResponse = new APMidasResponse();
                aPMidasResponse.resultCode = 3;
                APLog.e(TAG, "request params error!!");
                iAPMidasPayCallBack.MidasPayCallBack(aPMidasResponse);
                return;
            }
            APPayMananger.singleton().pay(activity, aPMidasBaseRequest, iAPMidasPayCallBack);
        }
    }

    public void setEnv(String str) {
        APLog.i(LOGTAG, "env:" + str);
        for (String equals : ENV) {
            if (equals.equals(str)) {
                APAppDataInterface.singleton().setEnv(str);
                return;
            }
        }
        throw new IllegalArgumentException("env is IllegalArgument!only test|release can be set");
    }

    public void setLogEnable(boolean z) {
        APLog.setLogEnable(z);
    }

    public void setReleaseIDC(String str) {
        APLog.i(LOGTAG, "idc:" + str);
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("idcName is empty!!");
        }
        APNetCfg.set(str);
    }

    public void setScreenType(int i) {
        if (i == 1 || i == 0) {
            APAppDataInterface.singleton().setScreenType(i);
        } else {
            APAppDataInterface.singleton().setScreenType(-1);
        }
    }
}
